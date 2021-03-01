package com.example.foody.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.adapters.FavoriteRecipesAdapter
import com.example.foody.data.database.entities.FavoritesEntity

object FavoriteRecipesBinding {
    
    // BindingAdapter의 value로 적혀있는 viewVisiBility는 함수의 2번째 파라미터인 favoritesEntity에 해당하고
    // setData는 3번째 파라미터인 mAdapter에 해당함
    @BindingAdapter("app:viewVisibility", "app:setData", requireAll = false)
    @JvmStatic
    fun setDataAndViewVisibility(
        view: View,
        favoritesEntity: List<FavoritesEntity>?,
        mAdapter: FavoriteRecipesAdapter?
    ) {
        if (favoritesEntity.isNullOrEmpty()) {
            when(view) {
                is ImageView -> {
                    view.visibility = View.VISIBLE
                }
                is TextView -> {
                    view.visibility = View.VISIBLE
                }
                is RecyclerView -> {
                    view.visibility = View.INVISIBLE
                }
            }
        } else {
            when(view) {
                is ImageView -> {
                    view.visibility = View.INVISIBLE
                }
                is TextView -> {
                    view.visibility = View.INVISIBLE
                }
                is RecyclerView -> {
                    view.visibility = View.VISIBLE
                    mAdapter?.setData(favoritesEntity)
                }
            }
        }
    }

}
