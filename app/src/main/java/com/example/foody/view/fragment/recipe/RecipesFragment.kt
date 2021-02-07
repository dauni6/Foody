package com.example.foody.view.fragment.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foody.R
import kotlinx.android.synthetic.main.fragment_recipes.view.*

class RecipesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)
        view.recyclerView.showShimmer() // 데이터가 로딩중임을 보이기 위해 Shimmer 사용
        return view
    }

}