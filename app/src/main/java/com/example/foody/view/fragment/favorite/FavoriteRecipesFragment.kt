package com.example.foody.view.fragment.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.R
import com.example.foody.adapters.FavoriteRecipesAdapter
import com.example.foody.databinding.FragmentFavoriteRecipesBinding
import com.example.foody.databinding.FragmentRecipesBinding
import com.example.foody.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val mAdapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity()) }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_recipes, container, false)
        binding.lifecycleOwner = this@FavoriteRecipesFragment
        binding.mainViewModel = this.mainViewModel
        binding.mAdapter = this.mAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
//        initObservers()

    }

    private fun setupRecyclerView() {
        binding.rvFavorite.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initObservers() {
        // onCreateView에서
        // binding.lifecycleOwner = this@FavoriteRecipesFragment
        // binding.mainViewModel = this.mainViewModel
        // binding.mAdapter = this.mAdapter
        // 으로 정의해주므로 아래와 같이 observe 패턴을 사용할 필요가 없다.
        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner, { favoritesEntity ->
            mAdapter.setData(favoritesEntity)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}