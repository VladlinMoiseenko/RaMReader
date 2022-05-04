package ru.vladlin.ram_reader.ui.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.databinding.ComponentLargeCharacterBinding

@EpoxyModelClass(layout = R.layout.component_large_character)
abstract class LargeCharacterModel
    : EpoxyModelWithHolder<LargeCharacterModel.LargeCharacterHolder>(), View.OnClickListener {

    @field:EpoxyAttribute
    open var isSaved: Boolean? = false

    @field:EpoxyAttribute
    open var imageUrl: String? = null

    @field:EpoxyAttribute
    open var title_: String? = null

    @field:EpoxyAttribute
    open var id_: Long? = 0

    interface Listener {
        fun savedClick(id: Long)
        fun readMoreClick(id: Long)
    }
    @EpoxyAttribute
    lateinit var listener: Listener

    override fun bind(holder: LargeCharacterHolder) {
        holder.binding.apply {
            title.text =  title_
            Glide.with(imageView)
                .load(imageUrl)
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.ic_broken_image)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
            title.setOnClickListener(this@LargeCharacterModel)
            if (isSaved!!) {
                lottieFavorites.setAnimation("lottie_favorites.json")
                lottieFavorites.playAnimation()
            } else {
                lottieFavorites.setAnimation("lottie_list.json")
                lottieFavorites.playAnimation()
            }
            lottieFavorites.setOnClickListener(this@LargeCharacterModel)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.lottie_favorites -> {
                listener.savedClick(id_!!)
            }
            R.id.title -> {
                listener.readMoreClick(id_!!)
            }
        }
    }

    class LargeCharacterHolder : EpoxyHolder() {
        lateinit var binding: ComponentLargeCharacterBinding
            private set

        override fun bindView(itemView: View) {
            binding = ComponentLargeCharacterBinding.bind(itemView)
        }
    }
}