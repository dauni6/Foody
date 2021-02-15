package com.example.foody.data

import android.content.Context
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataStoreRepository @Inject constructor(@ApplicationContext private val conText: Context) {

    private object PreferenceKeys {
        val selectedMealType = preferencesKey<String>("mealType")
    }

}
