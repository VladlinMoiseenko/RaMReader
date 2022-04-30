package ru.vladlin.ram_reader.ui.fragmentDetail

import com.airbnb.epoxy.TypedEpoxyController

class ListImageController: TypedEpoxyController<List<String>>()
{
    override fun buildModels(data: List<String>?)
    {
        data?.forEach {
            val model = ListImageModel_().apply {
                id(it.hashCode())
                url = it
            }

            add(model)
        }
    }
}