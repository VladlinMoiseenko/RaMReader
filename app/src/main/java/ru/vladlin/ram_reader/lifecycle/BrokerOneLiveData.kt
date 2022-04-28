package ru.vladlin.ram_reader.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class BrokerOneLiveData
{
    companion object {
        fun <A> brokerLiveData(a: LiveData<A>): LiveData<A> {
            return MediatorLiveData<A>().apply {
                var liveDataA: A? = null

                fun update() {
                    if (liveDataA != null)
                        this.value = liveDataA!!
                }

                addSource(a) {
                    liveDataA = it
                    update()
                }

            }
        }
    }
}