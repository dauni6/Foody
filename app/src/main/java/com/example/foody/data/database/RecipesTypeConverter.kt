package com.example.foody.data.database

import androidx.room.TypeConverter
import com.example.foody.model.FoodRecipe
import com.example.foody.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    // DB에 저장할 때 객체를 저장하지 않고 MySQL에서도 보통 INTEGER, VARCHAR를 사용하듯이
    // 저장할때 String형이든 int형이든 바꾸어서 넣어주어야 한다.
    // 네트워크 통신을 통해 DB에 전달되는 쿼리스트링은 항상 String형이다.
    // 따라서 DB에 저장할때도 String형이고 받아올때는 다시 DB에 저장된 데이터를 객체로 변환해주어야 한다.
    // 현재는 네트워크로 저장하는게 아니지만, DB에 보낼때는 String으로 저장할 때를 떠올려보자.

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipes(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        val listType = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, listType)
    }


}
