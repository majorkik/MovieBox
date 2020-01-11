package com.majorik.moviebox.utils

import android.content.Context
import android.content.SharedPreferences
import com.majorik.domain.tmdbModels.auth.ResponseSession

class SharedPrefsManager(private val context: Context) {
    companion object {
        const val SP_NAME = "tmdb"
        const val TMDB_LOGGED = "tmdb_logged"
        const val TMDB_SESSION_ID = "tmdb_session_id"
    }

    private fun get(): SharedPreferences =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    fun saveTmdbLogged(responseSession: ResponseSession) {
        get().edit().also { sharedPrefs ->
            sharedPrefs.putBoolean(TMDB_LOGGED, responseSession.success ?: false)
            sharedPrefs.putString(TMDB_SESSION_ID, responseSession.sessionId ?: "")
        }.apply()
    }

    fun getTmdbSessionId(): String {
        return get().getString(TMDB_SESSION_ID, "") ?: ""
    }

    fun getTmdbLoggedStatus(): Boolean {
        return get().getBoolean(TMDB_LOGGED, false)
    }
}
