package ru.vladlin.ram_reader.ui.fragmentDetail

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
import retrofit2.Response
import ru.vladlin.ram_reader.data.network.NetworkErrorInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterLocalInteractor
import ru.vladlin.ram_reader.domain.boundaries.CharacterNetworkInteractor
import ru.vladlin.ram_reader.domain.model.CharacterList
import ru.vladlin.ram_reader.domain.model.CharacterModel
import ru.vladlin.ram_reader.lifecycle.SingleLiveEvent
import ru.vladlin.ram_reader.lifecycle.BrokerLiveData
import javax.inject.Inject

class ViewModelDetail @Inject constructor(
    val characterInteractor: CharacterInteractor,
    val characterLocalInteractor: CharacterLocalInteractor,
    val characterNetworkInteractor: CharacterNetworkInteractor,
    val networkErrorInteractor: NetworkErrorInteractor,
    val context: Context
): ViewModel() {
    var id: Long = 0L
        set(value) {
            field = value
            loadModel()
            loadIsSaved(id)
        }

    private val characterModel: MutableLiveData<CharacterModel> = MutableLiveData()
    private val idSavedCharacter: MutableLiveData<Boolean> = MutableLiveData()
    val isRefreshed: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    val errorSnackbar: SingleLiveEvent<String> = SingleLiveEvent()

    val data = BrokerLiveData.brokerLiveData(characterModel, idSavedCharacter)

    fun loadModel() {
        viewModelScope.launch(Dispatchers.IO) {
            characterInteractor.getCharacter(id)
                .flowOn(Dispatchers.IO)
                .onStart {
                    isRefreshed.postValue(true)
                }
                .onCompletion {
                    isRefreshed.postValue(false)
                }
                .catch {
                    error.postValue(networkErrorInteractor.getError(it))
                }
                .collect {
                    error.postValue(null)
                    characterModel.postValue(it)
                }
        }
    }

    private fun loadIsSaved(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            characterLocalInteractor.isSavedCharacter(id)
                .collect {
                    idSavedCharacter.postValue(it)
                }
        }
    }

    fun saveCharacter() {
        GlobalScope.launch(Dispatchers.IO) {
            characterLocalInteractor.saveCharacter(id)
                .flowOn(Dispatchers.IO)
                .catch {
                    errorSnackbar.postValue(networkErrorInteractor.getError(it))
                }
                .collect{
                    loadIsSaved(id)
                }
        }
    }
}