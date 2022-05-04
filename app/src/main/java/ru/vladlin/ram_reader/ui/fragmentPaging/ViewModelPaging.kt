package ru.vladlin.ram_reader.ui.fragmentPaging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vladlin.ram_reader.data.character.repo.CharacterListPagingSource
import ru.vladlin.ram_reader.data.source.retrofit.ApiService
import ru.vladlin.ram_reader.domain.boundaries.CharacterLocalInteractor
import ru.vladlin.ram_reader.domain.model.CharacterModel
import ru.vladlin.ram_reader.lifecycle.BrokerPairLiveData
import javax.inject.Inject

class ViewModelPaging @Inject constructor(
    val characterLocalInteractor: CharacterLocalInteractor,
    val apiService: ApiService
): ViewModel() {

    private val idSavedCharacters: MutableLiveData<List<Long>> = MutableLiveData()
    private val feedPagingData: MutableLiveData<PagingData<CharacterModel>> = MutableLiveData()
    val dataFeed = BrokerPairLiveData.brokerLiveData(feedPagingData, idSavedCharacters)

    fun loadData(
        characterName: String?,
        characterStatus: String?,
        characterGender: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            loadIdSavedCharacters()
            setCharactersList(characterName, characterGender, characterStatus)
        }
    }

    private suspend fun setCharactersList(
        characterName: String?,
        characterStatus: String?,
        characterGender: String?) {
        filterCharacters(characterName,characterGender,characterStatus).collectLatest {
            feedPagingData.postValue(it)
        }
    }

    fun filterCharacters(name: String?,gender:String?,status:String?) = Pager(
        PagingConfig(20, 40, enablePlaceholders = false)
        ) {
            CharacterListPagingSource(apiService,name,gender,status)
        }.flow
            .cachedIn(viewModelScope)

    fun loadIdSavedCharacters() {
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
                    //errorSnackbar.postValue(networkErrorInteractor.getError(it))
                }
                .collect{
                    loadIdSavedCharacters()
                }
        }
    }

}