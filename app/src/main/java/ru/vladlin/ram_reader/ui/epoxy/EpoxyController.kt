package ru.vladlin.ram_reader.ui.epoxy

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.Typed2EpoxyController
import ru.vladlin.ram_reader.domain.model.CharacterModel

class EpoxyController(val listenerFeed: CardModel.Listener)
    : Typed2EpoxyController<List<CharacterModel>, List<Long>>(
        EpoxyAsyncUtil.getAsyncBackgroundHandler(),
        EpoxyAsyncUtil.getAsyncBackgroundHandler()
    )
{
    override fun buildModels(data: List<CharacterModel>?, listSaved: List<Long>)
    {
        if (data == null) {
            loaderModelView { id("loader") }
            return
        }

        for (item in data) {

            largeOverline {
                id(item.id+item.id)
                value(item.name)
            }

            card {
                id(item.id)
                title(item.name)
                subtitle(item.species)
                imageUrl(item.image)
                id_(item.id)
                isSaved(listSaved.find { it == item.id} != null)
                listener(this@EpoxyController.listenerFeed)
            }
        }
    }
}
