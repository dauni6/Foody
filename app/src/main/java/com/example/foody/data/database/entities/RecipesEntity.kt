package com.example.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foody.model.FoodRecipe
import com.example.foody.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe // FoodRecipe는 List<Result> 를 갖고 있음
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}