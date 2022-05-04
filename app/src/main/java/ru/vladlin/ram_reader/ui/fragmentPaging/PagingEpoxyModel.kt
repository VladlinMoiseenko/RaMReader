package ru.vladlin.ram_reader.ui.fragmentPaging

import android.view.View
import com.bumptech.glide.Glide
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.databinding.ItemCharacterListBinding
import ru.vladlin.ram_reader.utils.ViewBindingEpoxyModel

class PagingEpoxyModel(
    val name: String,
    val id: Long,
    val status: String,
    val gender: String,
    val imageUrl: String,
) : ViewBindingEpoxyModel<ItemCharacterListBinding>(R.layout.item_character_list), View.OnClickListener {

    @JvmField
    var listener: Listener? = null
    interface Listener {
        fun onCharacterClicked(id: Long)
    }

    @JvmField
    var isSaved: Boolean? = false

    override fun ItemCharacterListBinding.bind() {
        Glide
            .with(characterImageView.context)
            .load(imageUrl)
            .into(characterImageView)

        characterNameTextView.text = name

        if (isSaved!!) {
            lottieFavorites.setAnimation("lottie_favorites.json")
            lottieFavorites.playAnimation()
        } else {
            lottieFavorites.setAnimation("lottie_list.json")
            lottieFavorites.playAnimation()
        }

        lottieFavorites.setOnClickListener(this@PagingEpoxyModel)

        when (gender) {
            "Male" -> aliveImageView.setImageResource(R.drawable.ic_male)
            "Female" -> aliveImageView.setImageResource(R.drawable.ic_female)
            else -> aliveImageView.setImageResource(R.drawable.ic_genderless)
        }
    }

    override fun onClick(view: View?)
    {
        when (view?.id) {
            R.id.lottieFavorites -> {
                listener!!.onCharacterClicked(id)
            }
        }
    }
}