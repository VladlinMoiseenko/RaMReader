package ru.vladlin.ram_reader.network

import android.content.Context
import ru.vladlin.ram_reader.R
import ru.vladlin.ram_reader.data.network.NetworkErrorInteractor
import java.net.UnknownHostException

class NetworkErrorInteractorImpl(val context: Context) : NetworkErrorInteractor
{
    override fun getError(ex: Throwable): String
    {
        when (ex)
        {
            is UnknownHostException -> {
                return context.resources.getString(R.string.no_internet)
            }
            else -> {
                return context.resources.getString(R.string.no_internet)
            }
        }
    }
}