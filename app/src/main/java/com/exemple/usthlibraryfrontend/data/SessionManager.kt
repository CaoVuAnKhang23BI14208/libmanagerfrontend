package com.exemple.usthlibraryfrontend.data

import android.content.Context
import android.content.SharedPreferences
import com.exemple.usthlibraryfrontend.model.AuthUser
import androidx.core.content.edit

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREFS_NAME = "user_session"
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
    }

    fun saveUserSession(token: String, user: AuthUser) {
        prefs.edit {
            putString(USER_TOKEN, token)
            putString(USER_NAME, user.fullname)
            putString(USER_EMAIL, user.email)
        }
    }


    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchUserName(): String? {
        return prefs.getString(USER_NAME, "Guest")
    }


    fun fetchUserEmail(): String? {
        return prefs.getString(USER_EMAIL, "guest@example.com")
    }


    fun clearSession() {
        prefs.edit {
            clear()
        }
    }
}