package com.example.foody.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.R
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.databinding.FavoriteRecipesRowLayoutBinding
import com.example.foody.model.Result
import com.example.foody.util.RecipesDiffUtil
import com.example.foody.view.fragment.favorite.FavoriteRecipesFragmentDirections

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteViewHolder>(), ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavoritesEntity>()

    override fun getItemCount(): Int = favoriteRecipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder.from(parent)

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteRecipes[position])
        holder.singleClickListenerForActionDetailActivity(favoriteRecipes[position].result)
        holder.longClickListener(requireActivity, this)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil =
            RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this@FavoriteRecipesAdapter)
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        applyStatusBarColor(R.color.statusBarColor)
    }

    class FavoriteViewHolder(private val binding: FavoriteRecipesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity // databinding의 variable에 favoritesEntity 넣기
            binding.executePendingBindings()
        }

        fun singleClickListenerForActionDetailActivity(result: Result) {
            binding.clFavoriteRecipeRow.setOnClickListener {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailActivity(
                        result
                    )
                binding.root.findNavController().navigate(action)
            }
        }

        fun longClickListener(requireActivity: FragmentActivity, favoriteRecipesAdapter: FavoriteRecipesAdapter) {
            binding.clFavoriteRecipeRow.setOnLongClickListener {
                requireActivity.startActionMode(favoriteRecipesAdapter)
                true
            }
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return FavoriteViewHolder(binding)
            }
        }

    }


}
