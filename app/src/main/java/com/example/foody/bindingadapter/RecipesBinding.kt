package com.example.foody.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.foody.data.database.entities.RecipesEntity
import com.example.foody.model.FoodRecipe
import com.example.foody.util.NetworkResult

object RecipesBinding {

    @BindingAdapter("app:readApiResponse", "app:readDatabase", requireAll = true)
    @JvmStatic
    fun handleReadDataErrors(
        view: View,
        apiResponse: NetworkResult<FoodRecipe>?,
        database: List<RecipesEntity>?
    ) {
        when (view) {
            is ImageView -> {
                view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
            }
            is TextView -> {
                view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                view.text = apiResponse?.message.toString()
            }
        }
    }

    // 위의 메서드로 하나로 압축 가능
//    @BindingAdapter("app:readApiResponse", "app:readDatabase", requireAll = true)
//    @JvmStatic
//    fun errorImageViewVisibility(
//        imageView: ImageView,
//        apiResponse: NetworkResult<FoodRecipe>?,
//        database: List<RecipesEntity>?
//    ) {
//        if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
//            imageView.visibility = View.VISIBLE
//        } else if (apiResponse is NetworkResult.Loading) {
//            imageView.visibility = View.INVISIBLE
//        } else if (apiResponse is NetworkResult.Success) {
//            imageView.visibility = View.INVISIBLE
//        }
//    }
//
//    @BindingAdapter("app:readApiResponse2", "readDatabase2", requireAll = true)
//    @JvmStatic
//    fun errorTextViewVisibility(
//        textView: TextView,
//        apiResponse: NetworkResult<FoodRecipe>?,
//        database: List<RecipesEntity>?
//    ) {
//        if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
//            textView.visibility = View.VISIBLE
//            textView.text = apiResponse.message.toString()
//        } else if (apiResponse is NetworkResult.Loading) {
//            textView.visibility = View.INVISIBLE
//        } else if (apiResponse is NetworkResult.Success) {
//            textView.visibility = View.INVISIBLE
//        }
//    }

}
