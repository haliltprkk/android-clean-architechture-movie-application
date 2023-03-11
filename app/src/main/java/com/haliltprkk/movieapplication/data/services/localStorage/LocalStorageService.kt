package com.haliltprkk.movieapplication.data.services.localStorage

import com.google.gson.Gson
import javax.inject.Inject

class LocalStorageService @Inject constructor(
    private var gson: Gson,
    private val keyValueStore: KeyValueStore
) {

    fun <T> getObject(key: String, a: Class<T>?): T? {
        val data = keyValueStore.getString(key)
        return if (data == null) {
            null
        } else {
            try {
                gson.fromJson(data, a)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun removeObject(key: String) = keyValueStore.remove(key)

    fun setLastUpdateTime(lastTimeFetched: Long) = keyValueStore.setLong(LAST_UPDATE_TIME, lastTimeFetched)

    fun getLastUpdateTime(): Long? = keyValueStore.getLong(LAST_UPDATE_TIME)

    companion object {
        // keys
        const val LAST_UPDATE_TIME = "LAST_UPDATE_TIME"
    }
}
