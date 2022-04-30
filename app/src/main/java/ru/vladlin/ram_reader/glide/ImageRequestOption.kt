package ru.vladlin.ram_reader.glide

import com.bumptech.glide.request.RequestOptions
import ru.vladlin.ram_reader.R

fun getImageRequestOption(): RequestOptions
{
    return RequestOptions()
        .placeholder(R.drawable.list_placeholder_img)
        .error(R.drawable.list_placeholder_err_img)
}