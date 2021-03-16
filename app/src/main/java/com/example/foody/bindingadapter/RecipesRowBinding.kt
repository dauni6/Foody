package com.example.foody.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.foody.R
import com.example.foody.model.Result
import com.example.foody.view.fragment.recipe.RecipesFragmentDirections
import org.jsoup.Jsoup
import timber.log.Timber

object RecipesRowBinding {

    @BindingAdapter("app:parseHtml")
    @JvmStatic
    fun parseHtml(textView: TextView, description: String?) {
        if (description != null) {
            val desc = Jsoup.parse(description).text()
            textView.text = desc
        }
    }

    @BindingAdapter("app:onRecipeClickListener")
    @JvmStatic
    fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result) {
        recipeRowLayout.setOnClickListener {
            try {
                val action =
                    RecipesFragmentDirections.actionRecipesFragmentToDetailActivity(result)
                recipeRowLayout.findNavController().navigate(action)
            } catch (error: Exception) {
                Timber.e("onRecipeClickListener / error : ${error.message.toString()}")
            }
        }
    }

    // 아래 2개의 함수는 직접 xml에서 string.valueOf를 통해 처리할 것이므로 주석처리 함.
//    @BindingAdapter("app:setNumberOfLikes")
//    @JvmStatic
//    fun setNumberOfLikes(textView: TextView, likes: Int) {
//        textView.text = likes.toString()
//    }
//
//    @BindingAdapter("app:setNumberOfMinutes")
//    @JvmStatic
//    fun setNumberOfMinutes(textView: TextView, minutes: Int) {
//        textView.text = minutes.toString()
//    }

    @BindingAdapter("app:applyVeganColor")
    @JvmStatic
    fun applyVeganColor(view: View, vegan: Boolean) {
        if (vegan) {
            when(view) {
                is TextView -> {
                    view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                }
                is ImageView -> {
                    view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                }
            }
        }
    }

    @BindingAdapter("app:loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
        imageView.load(imageUrl) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder) // 인터넷 연결이 끊겼거나 데이터를 불러올 수 없을 때, 에러이미지 보이기
        }
    }

}
