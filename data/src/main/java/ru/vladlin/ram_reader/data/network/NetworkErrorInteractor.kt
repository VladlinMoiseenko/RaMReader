package ru.vladlin.ram_reader.data.network

interface NetworkErrorInteractor
{
    fun getError(ex: Throwable): String
}