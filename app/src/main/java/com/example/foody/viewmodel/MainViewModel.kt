package com.example.foody.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.foody.data.Repository
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.data.database.entities.RecipesEntity
import com.example.foody.model.FoodRecipe
import com.example.foody.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM DATABASE */
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes()
        .asLiveData() // coroutine Flow를 LiveData로 바꾸기 위해서 asLiveData()를 사용

    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipes()
        .asLiveData()


    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    private fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoritesRecipe(favoritesEntity)
        }

    private fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoritesRecipe(favoritesEntity)
        }

    private fun deleteAllFavoriteRecipe() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoritesRecipes()
        }

    /** RETROFIT */
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) =
        viewModelScope.launch {
            getRecipesSafeCall(queries)
        }

    fun searchRecipes(searchQuery: Map<String, String>) =
        viewModelScope.launch {
            searchRecipesSafeCall(searchQuery)
        }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                // ROOM에 넣기
                val foodRecipe = recipesResponse.value!!.data
                foodRecipe?.let {
                    offlineCacheRecipes(it)
                }

            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error(message = "Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error(message = "No Internet Connection.")
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchRecipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                searchRecipesResponse.value = NetworkResult.Error(message = "Recipes not found.")
            }
        } else {
            searchRecipesResponse.value = NetworkResult.Error(message = "No Internet Connection.")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout")
            }
            response.code() == 402 -> { // 402 error = payment required.
                return NetworkResult.Error(message = "API ket Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error(message = "Recipes not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!) // 위의 body를 처리하는 부분에 걸리지 않았다면 이곳이 실행되므로 non-null 마크(!!)를 사용할 수 있음
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}
