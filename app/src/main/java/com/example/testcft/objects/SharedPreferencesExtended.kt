package com.example.testcft.objects

import android.content.SharedPreferences
import com.example.testcft.model.History
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

object SharedPreferencesExtended {
    lateinit var sharedPreferences: SharedPreferences
    val isSharedPreferencesInitialized get() = this::sharedPreferences.isInitialized

    fun SharedPreferences.Editor.put(list: MutableList<History>, key: String) {
        val json = getJsonAdapter().toJson(list)
        putString(key, json)
        apply()
    }

    fun SharedPreferences.get(key: String): MutableList<History> {
        val json = getString(key, null) ?: return mutableListOf()
        return getJsonAdapter().fromJson(json) ?: mutableListOf()
    }

    private fun getJsonAdapter(): JsonAdapter<MutableList<History>> {
        val moshi = Moshi.Builder().build()
        val type: Type = Types.newParameterizedType(MutableList::class.java, History::class.java)
        return moshi.adapter(type)
    }
}