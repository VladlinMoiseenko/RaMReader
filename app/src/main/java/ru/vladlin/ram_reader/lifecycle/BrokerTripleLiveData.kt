package ru.vladlin.ram_reader.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class BrokerTripleLiveData
{
    companion object {
        fun <A, B, C> brokerLiveData(a: LiveData<A>?, b: LiveData<B>?, c: LiveData<C>?): LiveData<Triple<A, B, C>> {
            return MediatorLiveData<Triple<A, B, C>>().apply {
                var liveDataA: A? = null
                var liveDataB: B? = null
                var liveDataC: C? = null

                fun update() {
                    if (liveDataA != null && liveDataB != null && liveDataC != null) {
                        this.value = Triple(liveDataA!!, liveDataB!!, liveDataC!!)
                    }
                }

                addSource(a!!) {
                    liveDataA = it
                    update()
                }

                addSource(b!!) {
                    liveDataB = it
                    update()
                }

                addSource(c!!) {
                    liveDataC = it
                    update()
                }

            }
        }
    }
}