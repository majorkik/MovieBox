package com.majorik.moviebox.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.majorik.domain.tmdbModels.auth.ResponseSession

class SharedPrefsManager(context: Context) {
    companion object {
        const val SP_NAME = "tmdb"
        const val TMDB_LOGGED = "tmdb_logged"
        const val TMDB_SESSION_ID = "tmdb_session_id"
        const val TMDB_GUEST_SESSION_ID = "tmdb_guest_session_id"
    }

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    fun saveTmdbLogged(responseSession: ResponseSession) {
        sharedPrefs.edit {
            putBoolean(TMDB_LOGGED, responseSession.success ?: false)
            putString(TMDB_SESSION_ID, responseSession.sessionId ?: "")
        }
    }

    fun saveLoggedStatus(isLogged: Boolean?) {
        sharedPrefs.edit { putBoolean(TMDB_LOGGED, isLogged ?: false) }
    }

    fun saveTmdbSessionID(sessionID: String?) {
        sharedPrefs.edit { putString(TMDB_SESSION_ID, sessionID ?: "") }
    }

    fun saveGuestSessionID(guestSessionID: String?) {
        sharedPrefs.edit { putString(TMDB_GUEST_SESSION_ID, guestSessionID ?: "") }
    }

    fun getTmdbSessionID(): String = sharedPrefs.getString(TMDB_SESSION_ID, "") ?: ""

    fun getTmdbLoggedStatus(): Boolean = sharedPrefs.getBoolean(TMDB_LOGGED, false)

    fun getTmdbGuestSessionID(): String = sharedPrefs.getString(TMDB_GUEST_SESSION_ID, "") ?: ""

    fun clearAll() { sharedPrefs.edit().clear().apply() }
}
