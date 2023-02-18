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

    fun setSample(sample: String) = keyValueStore.setString(KEY_SAMPLE, sample)

    fun getSample(): String? = keyValueStore.getString(KEY_SAMPLE)

    companion object {
        // keys
        const val KEY_SAMPLE = "SAMPLE"
    }
}
