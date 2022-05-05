package ru.vladlin.ram_reader.ui.fragmentFavorites

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import ru.vladlin.ram_reader.data.network.NetworkErrorInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterLocalInteractor
import ru.vladlin.ram_reader.domain.model.CharacterModel
import ru.vladlin.ram_reader.eventbus.UpdateList
import ru.vladlin.ram_reader.lifecycle.SingleLiveEvent
import ru.vladlin.ram_reader.lifecycle.BrokerPairLiveData
import javax.inject.Inject

class ViewModelFavorites @Inject constructor(
    val characterLocalInteractor: CharacterLocalInteractor,
    val networkErrorInteractor: NetworkErrorInteractor,
    val context: Context
): ViewModel() {

    private val feed: MutableLiveData<List<CharacterModel>> = MutableLiveData()
    private val idSavedCharacters: MutableLiveData<List<Long>> = MutableLiveData()

    val dataFeed = BrokerPairLiveData.brokerLiveData(feed, idSavedCharacters)

    var isRefreshed: MutableLiveData<Boolean> = MutableLiveData()
    val errorFeed: SingleLiveEvent<String?> = SingleLiveEvent()
    val errorSnackbar: SingleLiveEvent<String> = SingleLiveEvent()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            loadCharacters()
            loadIdSavedCharacters()
        }
    }

    private suspend fun loadCharacters() {
        val flow: Flow<List<CharacterModel>> = characterLocalInteractor.getAllLocalCharacters()
        flow
            .flowOn(Dispatchers.IO)
            .onStart {
                isRefreshed.postValue(true)
            }
            .onCompletion {
                isRefreshed.postValue(false)
            }
            .catch {
                if (feed.value.isNullOrEmpty())
                    errorFeed.postValue(networkErrorInteractor.getError(it))
                else
                    errorSnackbar.postValue(networkErrorInteractor.getError(it))
            }
            .collect {
                if (it.isNullOrEmpty()) {
                    errorFeed.postValue(null)
                } else {
                    feed.postValue(it)
                }
            }
    }

    private suspend fun loadIdSavedCharacters() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                flow {
                    emit(
                        characterLocalInteractor.getAllLocalCharacters()
                            .flatMapMerge { it.asFlow() }
                            .map { it.id }
                            .toList()
                    )
                }
                    .flowOn(Dispatchers.IO)
                    .collect {
                        idSavedCharacters.postValue(it)
                    }
            }
        }
    }

    fun saveCharacter(id: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            characterLocalInteractor.saveCharacter(id)
                .flowOn(Dispatchers.IO)
                .catch {
                    errorSnackbar.postValue(networkErrorInteractor.getError(it))
                }
                .collect{
                    loadIdSavedCharacters()
                    loadData()
                    EventBus.getDefault().post(UpdateList(1))
                }
        }
    }
}