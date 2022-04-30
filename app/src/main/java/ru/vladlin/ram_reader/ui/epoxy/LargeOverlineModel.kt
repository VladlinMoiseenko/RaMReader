package ru.vladlin.ram_reader.ui.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.databinding.ComponentLargeOverlineBinding

@EpoxyModelClass(layout = R.layout.component_large_overline)
abstract class LargeOverlineModel : EpoxyModelWithHolder<LargeOverlineModel.LargeOverlineHolder>() {

    @field:EpoxyAttribute
    open var value: CharSequence? = null

    override fun bind(holder: LargeOverlineHolder) {
        holder.binding.root.text = value
    }

    class LargeOverlineHolder : EpoxyHolder() {
        lateinit var binding: ComponentLargeOverlineBinding
            private set

        override fun bindView(itemView: View) {
            binding = ComponentLargeOverlineBinding.bind(itemView)
        }
    }
}