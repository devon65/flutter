package com.example.flutter.Utils

import android.content.Context
import android.preference.PreferenceManager

object PreferencesUtil {
    fun getBoolean(context: Context?, key: String, defaultBoolean: Boolean): Boolean{
        if(context == null){return defaultBoolean}
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(key, defaultBoolean)
    }

    fun setBoolean(context: Context?, key: String, value: Boolean): Boolean{
        if(context == null){return false}
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putBoolean(key, value).apply()
        return true
    }

    fun getString(context: Context?, key: String, defaultString: String = ""): String{
        if(context == null){return defaultString}
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(key, defaultString)
    }

    fun setString(context: Context?, key: String, value: String): Boolean{
        if(context == null){return false}
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putString(key, value).apply()
        return true
    }
}