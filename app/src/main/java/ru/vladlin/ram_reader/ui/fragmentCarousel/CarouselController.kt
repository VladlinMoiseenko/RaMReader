package ru.vladlin.ram_reader.ui.fragmentCarousel

import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.Typed3EpoxyController
import com.airbnb.epoxy.carousel
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.domain.model.CharacterModel
import ru.vladlin.ram_reader.ui.epoxy.CardModel
import ru.vladlin.ram_reader.ui.epoxy.LargeCharacterModel
import ru.vladlin.ram_reader.ui.epoxy.LargeCharacterModel_
import ru.vladlin.ram_reader.ui.epoxy.card
import ru.vladlin.ram_reader.ui.epoxy.loaderModelView
import ru.vladlin.ram_reader.ui.epoxy.overline

class CarouselController(val listenerLargeCharacter: LargeCharacterModel.Listener,
                         val listenerCardModel: CardModel.Listener)
    : Typed3EpoxyController<List<CharacterModel>, List<CharacterModel>, List<Long>>(
        EpoxyAsyncUtil.getAsyncBackgroundHandler(),
        EpoxyAsyncUtil.getAsyncBackgroundHandler()
    ) {

    override fun buildModels(dataAll: List<CharacterModel>?, dataSaved: List<CharacterModel>?, listSaved: List<Long>?) {

        overline {
            id("favorites:overline")
            value("Favorites")
        }

        if (dataSaved == null) {
            loaderModelView { id("loader") }
            return
        }

        carousel {
            id("carousel")
            hasFixedSize(true)
            paddingRes(R.dimen.view_pager_item_padding)
            models(dataSaved.mapIndexed { index, item ->
                LargeCharacterModel_()
                    .id("favorites:" + item.id)
                    .imageUrl(item.image)
                    .title_(item.name)
                    .id_(item.id)
                    .isSaved(listSaved!!.find { it == item.id} != null)
                    .listener(this@CarouselController.listenerLargeCharacter)
            })
        }

        overline {
            id("all-characters:overline")
            value("All characters")
        }

        if (dataAll == null) {
            loaderModelView { id("loader") }
            return
        }

        for (item in dataAll) {
            card {
                id("all-characters:${item.id}")
                imageUrl(item.image)
                title(item.name)
                subtitle(item.gender)
                id_(item.id)
                isSaved(listSaved!!.find { it == item.id} != null)
                listener(this@CarouselController.listenerCardModel)
            }
        }
    }
}