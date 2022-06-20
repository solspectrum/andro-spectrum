package org.androdevlinux.spectrum.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

private const val KEY_SEED_PHRASE = "seed_phrase"



class PreferenceProvider @Inject constructor(context: Context) {

    private var appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun clear() {
        preferences.edit(true) {
            clear()
            commit()
        }
    }

    fun setSeedPhrase(name: String) {
        preferences.edit(true) {
            putString(KEY_SEED_PHRASE, name)
            apply()
        }
    }

    fun getSeedPhrase(): String? {
        return preferences.getString(KEY_SEED_PHRASE, null)
    }

}