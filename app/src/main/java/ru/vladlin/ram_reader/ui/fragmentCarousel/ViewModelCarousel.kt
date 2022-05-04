package ru.vladlin.ram_reader.ui.fragmentCarousel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingSource
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
import retrofit2.Response
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.data.network.NetworkErrorInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterLocalInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterNetworkInteractor
import ru.vladlin.ram_reader.domain.model.CharacterList
import ru.vladlin.ram_reader.domain.model.CharacterModel
import ru.vladlin.ram_reader.eventbus.UpdateList
import ru.vladlin.ram_reader.lifecycle.SingleLiveEvent
import ru.vladlin.ram_reader.lifecycle.BrokerTripleLiveData
import javax.inject.Inject

class ViewModelCarousel @Inject constructor(
    val characterNetworkInteractor: CharacterNetworkInteractor,
    val characterLocalInteractor: CharacterLocalInteractor,
    val networkErrorInteractor: NetworkErrorInteractor,
    val context: Context
): ViewModel() {

    private val feedAll: MutableLiveData<List<CharacterModel>> = MutableLiveData()
    private val feedSaved: MutableLiveData<List<CharacterModel>> = MutableLiveData()
    private val idSaved: MutableLiveData<List<Long>> = MutableLiveData()

    val dataFeed = BrokerTripleLiveData.brokerLiveData(feedAll, feedSaved, idSaved)

    var isRefreshed: MutableLiveData<Boolean> = MutableLiveData()
    val errorFeed: SingleLiveEvent<String?> = SingleLiveEvent()
    val errorSnackbar: SingleLiveEvent<String> = SingleLiveEvent()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            loadAllCharacters()
            loadSavedCharacters()
            loadIdSavedCharacters()
        }
    }

    private suspend fun loadAllCharacters() {
        val flow: Flow<Response<CharacterList>> = characterNetworkInteractor.getAllNetCharacters()
        flow
            .flowOn(Dispatchers.IO)
            .catch {
                if (feedAll.value.isNullOrEmpty())
                    errorFeed.postValue(networkErrorInteractor.getError(it))
                else
                    errorSnackbar.postValue(networkErrorInteractor.getError(it))
            }
            .collect {
                if (it.isSuccessful) {
                    val pg: PagingSource.LoadResult<Int, CharacterModel>
                    pg = PagingSource.LoadResult.Page(
                        data = it.body()?.results!!,
                        prevKey = null,
                        nextKey = null
                    )
                    feedAll.postValue(pg.data)
                } else {
                    errorFeed.postValue(null)
                }
            }
    }

    private suspend fun loadSavedCharacters() {
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
                if (feedSaved.value.isNullOrEmpty())
                    errorFeed.postValue(networkErrorInteractor.getError(it))
                else
                    errorSnackbar.postValue(networkErrorInteractor.getError(it))
            }
            .collect {
                if (it.isNullOrEmpty()) {
                    errorFeed.postValue(context.resources.getString(R.string.empty_list))
                    feedSaved.postValue(emptyList())
                } else {
                    errorFeed.postValue(null)
                    feedSaved.postValue(it)
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
                    if (it.isNullOrEmpty()) {
                        idSaved.postValue(emptyList())
                    } else {
                        idSaved.postValue(it)
                    }
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
                    EventBus.getDefault().post(UpdateList(1))
                }
        }
    }

}