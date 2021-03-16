package com.example.foody.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.adapters.FavoriteRecipesAdapter
import com.example.foody.data.database.entities.FavoritesEntity

object FavoriteRecipesBinding {
    
    // BindingAdapter의 value로 적혀있는 viewVisiBility는 함수의 2번째 파라미터인 favoritesEntity에 해당하고
    // setData는 3번째 파라미터인 mAdapter에 해당함
//    @BindingAdapter("app:viewVisibility", "app:setData", requireAll = false)
//    @JvmStatic
//    fun setDataAndViewVisibility(
//        view: View,
//        favoritesEntity: List<FavoritesEntity>?,
//        mAdapter: FavoriteRecipesAdapter?
//    ) {
//        if (favoritesEntity.isNullOrEmpty()) {
//            when(view) {
//                is ImageView -> {
//                    view.visibility = View.VISIBLE
//                }
//                is TextView -> {
//                    view.visibility = View.VISIBLE
//                }
//                is RecyclerView -> {
//                    view.visibility = View.INVISIBLE
//                }
//            }
//        } else {
//            when(view) {
//                is ImageView -> {
//                    view.visibility = View.INVISIBLE
//                }
//                is TextView -> {
//                    view.visibility = View.INVISIBLE
//                }
//                is RecyclerView -> {
//                    view.visibility = View.VISIBLE
//                    mAdapter?.setData(favoritesEntity)
//                }
//            }
//        }
//    }
    // 위의 메서드는 아래의 setVisibility로 압축하여 사용하므로 주석처리 함

    @BindingAdapter("app:setVisibility", "app:setData", requireAll = false)
    @JvmStatic
    fun setVisibility(
        view: View,
        favoritesEntity: List<FavoritesEntity>?,
        mAdapter: FavoriteRecipesAdapter?
    ) {
        when (view) {
            is RecyclerView -> {
                val dataCheck = favoritesEntity.isNullOrEmpty()
                view.isInvisible = dataCheck
                if (!dataCheck) {
                    favoritesEntity?.let { mAdapter?.setData(it) }
                }
            }
            else -> view.isVisible = favoritesEntity.isNullOrEmpty()
        }
    }



}
