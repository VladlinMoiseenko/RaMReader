package ru.vladlin.ram_reader.ui.fragmentPaging

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import ru.vladlin.ram_reader.domain.model.CharacterModel

class PagingController(val listenerFeed: PagingEpoxyModel.Listener)
    : PagingDataEpoxyController<CharacterModel>() {

    private var idSavedCharacters: List<Long> = emptyList()

    fun setIdSavedCharacters(_idSavedCharacters: List<Long>) {
        idSavedCharacters = _idSavedCharacters
        requestForcedModelBuild()
    }

    override fun buildItemModel(currentPosition: Int, item: CharacterModel?)
    : EpoxyModel<*> {
        return PagingEpoxyModel(
            item!!.name!!,
            item.id,
            item.status!!,
            item.gender!!,
            item.image!!
        ).also { model ->
            model.id(item.id)
            model.listener = listenerFeed
            model.isSaved = idSavedCharacters.find { it == item.id} != null
        }
    }

}

