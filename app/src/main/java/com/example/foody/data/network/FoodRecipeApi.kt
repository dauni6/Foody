package com.example.foody.data.network

import com.example.foody.model.FoodJoke
import com.example.foody.model.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipeApi {

    @GET("recipes/complexSearch")
    suspend fun getRecipes( // 네트워크 통신은 main-thread(ui-thread)에서 돌아갈 수 없으므로 dispatchers.io를 통하여 호출해야함. 따라서 suspend keyword를 넣어야 한다.
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<FoodRecipe>

    @GET("food/jokes/random")
    suspend fun getFoodJoke(
        @Query("apiKey") apiKey: String
    ): Response<FoodJoke>

}
