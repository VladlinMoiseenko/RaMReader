package ru.vladlin.ram_reader.ui.epoxy

import android.animation.Animator
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.databinding.ComponentCardBinding

@EpoxyModelClass(layout = R.layout.component_card)
abstract class CardModel : EpoxyModelWithHolder<CardModel.CardHolder>(), View.OnClickListener {

    @field:EpoxyAttribute
    open var isSaved: Boolean? = false

    @field:EpoxyAttribute
    open var title: CharSequence? = null

    @field:EpoxyAttribute
    open var subtitle: CharSequence? = null

    @field:EpoxyAttribute
    open var imageUrl: String? = null

    @field:EpoxyAttribute
    open var id_: Long? = 0

    interface Listener {
        fun savedClick(id: Long)
        fun readMoreClick(id: Long)
    }
    @EpoxyAttribute
    lateinit var listener: Listener

    class CardHolder : EpoxyHolder() {
        lateinit var resources: Resources
        lateinit var context: Context
        lateinit var binding: ComponentCardBinding
            private set

        override fun bindView(itemView: View) {
            binding = ComponentCardBinding.bind(itemView)
            resources = itemView.resources
            context = itemView.context.applicationContext
        }
    }

    override fun bind(holder: CardHolder) {
        holder.binding.apply {
            materialTextViewTitle.text = title
            materialTextViewSubtitle.text = subtitle
            readMoreMaterialTextView.text = holder.resources.getString(R.string.read_more)
            Glide.with(imageView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_broken_image)
                .fitCenter().centerCrop()
                .into(imageView)
            if (isSaved!!) {
                lottieFavorites.setAnimation("lottie_favorites.json")
            } else {
                lottieFavorites.setAnimation("lottie_list.json")
            }
            lottieFavorites.setOnClickListener(this@CardModel)
            readMoreMaterialTextView.setOnClickListener(this@CardModel)

        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.lottie_favorites -> {
                val lottieFavorites: LottieAnimationView = view.findViewById(R.id.lottie_favorites)
                lottieFavorites.playAnimation()
                lottieFavorites.addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        Log.e("MVVV", "Animation:start")
                    }
                    override fun onAnimationEnd(animation: Animator) {
                        Log.e("MVVV", "Animation:end")
                        listener.savedClick(id_!!)
                    }
                    override fun onAnimationCancel(animation: Animator) {
                        Log.e("MVVV", "Animation:Cancel")
                    }
                    override fun onAnimationRepeat(animation: Animator) {
                        Log.e("MVVV", "Animation:repeat")
                    }
                })
            }
            R.id.readMoreMaterialTextView -> {
                listener.readMoreClick(id_!!)
            }
        }
    }

    override fun unbind(holder: CardHolder) {
        holder.binding.lottieFavorites.setOnClickListener(null)
    }
}