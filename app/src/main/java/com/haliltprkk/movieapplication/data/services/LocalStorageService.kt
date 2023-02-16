package com.haliltprkk.movieapplication.data.services

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.haliltprkk.movieapplication.BuildConfig

class LocalStorageService(context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, PREFERENCES_MODE)
    private var editor: SharedPreferences.Editor = preferences.edit()
    private var gson: Gson

    init {
        editor.apply()
        gson = Gson()
    }

    fun <T> getObject(key: String?, a: Class<T>?): T? {
        val data = preferences.getString(key, null)
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

    fun removeObject(key: String?) {
        editor.remove(key)
        editor.commit()
    }

    fun commit() {
        editor.commit()
    }

    fun setSample(sample: String) {
        editor.putString(SAMPLE, sample)
        editor.commit()
    }

    fun getSample(): String? = preferences.getString(SAMPLE, "")

    companion object {
        private const val PREFERENCES_NAME: String = BuildConfig.APPLICATION_ID
        private const val PREFERENCES_MODE = Activity.MODE_PRIVATE

        // keys
        private const val SAMPLE = "SAMPLE"
    }
}
