package com.example.foody.bindingadapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.foody.R
import timber.log.Timber

object RecipesRowBinding {

    @BindingAdapter("app:setNumberOfLikes")
    @JvmStatic
    fun setNumberOfLikes(textView: TextView, likes: Int) {
        textView.text = likes.toString()
    }

    @BindingAdapter("app:setNumberOfMinutes")
    @JvmStatic
    fun setNumberOfMinutes(textView: TextView, minutes: Int) {
        textView.text = minutes.toString()
    }

    @BindingAdapter("app:applyVeganColor")
    @JvmStatic
    fun applyVeganColor(view: View, vegan: Boolean) {
        Timber.d("vegan = $vegan")
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
        }
    }

}
