package com.example.foody.data

import com.example.foody.data.network.FoodRecipeApi
import com.example.foody.model.FoodRecipe
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipeApi: FoodRecipeApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipeApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipeApi.searchRecipes(searchQuery)
    }

}
