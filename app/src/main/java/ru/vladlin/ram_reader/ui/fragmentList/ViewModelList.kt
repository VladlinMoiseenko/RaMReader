package ru.vladlin.ram_reader.ui.fragmentList

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
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.vladlin.ram_reader.data.network.NetworkErrorInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterLocalInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterNetworkInteractor
import ru.vladlin.ram_reader.domain.model.CharacterList
import ru.vladlin.ram_reader.domain.model.CharacterModel
import ru.vladlin.ram_reader.lifecycle.SingleLiveEvent
import ru.vladlin.ram_reader.lifecycle.BrokerLiveData
import javax.inject.Inject

class ViewModelList @Inject constructor(
    val characterLocalInteractor: CharacterLocalInteractor,
    val characterNetworkInteractor: CharacterNetworkInteractor,
    val networkErrorInteractor: NetworkErrorInteractor,
    val context: Context
): ViewModel() {

    private val feed: MutableLiveData<List<CharacterModel>> = MutableLiveData()
    private val idSavedCharacters: MutableLiveData<List<Long>> = MutableLiveData()

    val dataFeed = BrokerLiveData.brokerLiveData(feed, idSavedCharacters)

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
        val flow: Flow<Response<CharacterList>> = characterNetworkInteractor.getAllNetCharacters()
        flow
            .flowOn(Dispatchers.IO)
            .catch {
                if (feed.value.isNullOrEmpty())
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
                    feed.postValue(pg.data)
                } else {
                    errorFeed.postValue(null)
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
                }
        }
    }
}