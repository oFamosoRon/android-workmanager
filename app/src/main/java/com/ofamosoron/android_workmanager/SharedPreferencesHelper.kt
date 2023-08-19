package com.ofamosoron.android_workmanager

import android.content.Context

object SharedPreferencesHelper {

    fun write(context: Context, value: Int): Boolean {
        val prefs = context.getSharedPreferences(Constants.DAY_COUNTER, Context.MODE_PRIVATE)
        return prefs.edit().putInt(Constants.NUMBER_OF_DAYS, value).commit()
    }

    fun read(context: Context): Int {
        val prefs = context.getSharedPreferences(Constants.DAY_COUNTER, Context.MODE_PRIVATE)
        return prefs.getInt(Constants.NUMBER_OF_DAYS, 0)
    }
}