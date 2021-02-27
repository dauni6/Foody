package com.example.foody.view.fragment.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.R
import com.example.foody.adapters.IngredientsAdapter
import com.example.foody.databinding.FragmentIngredientsBinding
import com.example.foody.model.Result
import com.example.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import timber.log.Timber

class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initData()
    }

    private fun initData() {
        // 해당 arguments는 PagerAdapter 클래스에서 각 프래그먼트에 뿌려줌
        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)
        Timber.d("likes = ${myBundle?.aggregateLikes}")
        myBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvIngredients.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}