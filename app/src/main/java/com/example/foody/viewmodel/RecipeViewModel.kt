package com.example.foody.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foody.util.Constants
import com.example.foody.util.Constants.Companion.API_KEY
import com.example.foody.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foody.util.Constants.Companion.QUERY_API_KEY
import com.example.foody.util.Constants.Companion.QUERY_DIET
import com.example.foody.util.Constants.Companion.QUERY_FILLING_INGREDIENTS
import com.example.foody.util.Constants.Companion.QUERY_NUMBER
import com.example.foody.util.Constants.Companion.QUERY_TYPE

class RecipeViewModel(application: Application): AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        return HashMap<String, String>().also {
            it[QUERY_NUMBER] = "50"
            it[QUERY_API_KEY] = API_KEY
            it[QUERY_TYPE] = "snack"
            it[QUERY_DIET] = "vegan"
            it[QUERY_ADD_RECIPE_INFORMATION] = "true"
            it[QUERY_FILLING_INGREDIENTS] = "true"
        }
    }

}
