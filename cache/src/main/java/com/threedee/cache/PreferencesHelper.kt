package com.threedee.cache

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

/**
 * General Preferences Helper class, used for storing preference values using the Preference API
 */
@Singleton
open class PreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private val PREF_THREE_PACKAGE_NAME = "com.threedee.cache.preferences"

        private val PREF_KEY_LAST_CACHE = "last_cache"
        private val PREF_KEY_IS_USER_FIRST_TIME = "first-time"
        private val PREF_KEY_IS_USER_LOGGED_IN = "login"
    }

    private val appPref: SharedPreferences

    init {
        appPref = context.getSharedPreferences(PREF_THREE_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was cached
     */
    var lastCacheTime: Long
        get() = appPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = appPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

    var isUserFirstTime: Boolean
        get() = appPref.getBoolean(PREF_KEY_IS_USER_FIRST_TIME, true)
        set(value) = appPref.edit().putBoolean(PREF_KEY_IS_USER_FIRST_TIME, value).apply()
    var isUserLoggedIn: Boolean
        get() = appPref.getBoolean(PREF_KEY_IS_USER_LOGGED_IN, false)
        set(value) = appPref.edit().putBoolean(PREF_KEY_IS_USER_LOGGED_IN, value).apply()
}
