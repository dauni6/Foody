package com.example.foody.view.fragment.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    private val mAdapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity(), mainViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_recipes, container, false)
        binding.lifecycleOwner = this@FavoriteRecipesFragment
        binding.mainViewModel = this.mainViewModel
        binding.mAdapter = this.mAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favorite_recipes_menu) {
            mainViewModel.deleteAllFavoriteRecipe()
            showSnackBar()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.root,
            "All recipes removed.",
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mAdapter.clearContextualActionMode()
    }

}