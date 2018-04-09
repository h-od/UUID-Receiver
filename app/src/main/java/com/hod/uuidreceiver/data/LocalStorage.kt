package com.hod.uuidreceiver.data

import android.content.SharedPreferences

class LocalStorage(private val sharedPreferences: SharedPreferences) : Local {

    companion object {
        private const val CODE = "KEY_CODE"
        private const val COUNT = "KEY_COUNT"
    }

    override fun fetch(): Entity = Entity(
            responseCode = sharedPreferences.getString(CODE, ""),
            count = sharedPreferences.getInt(COUNT, 0))

    override fun put(code: String): Entity {
        val entity = Entity(responseCode = code, count = sharedPreferences.getInt(COUNT, 0) + 1)

        sharedPreferences.edit().apply {
            putString(CODE, entity.responseCode)
            putInt(COUNT, entity.count)
            apply()
        }

        return entity
    }
}
