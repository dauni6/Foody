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
import com.example.foody.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteViewHolder>(), ActionMode.Callback {

    private var multiSelection = false

    private lateinit var mActionMode : ActionMode
    private lateinit var rootView: View

    // 선택한 레서피를 삭제하기 위한 ArrayList
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

    private var favoriteRecipes = emptyList<FavoritesEntity>()

    private var viewHolders = arrayListOf<FavoriteViewHolder>()

    override fun getItemCount(): Int = favoriteRecipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder.from(parent)

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        viewHolders.add(holder)
        rootView = holder.binding.root

        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)

        saveItemStateOnScroll(currentRecipe, holder)

        /**
         * Single Click Listener
         * */
        holder.binding.clFavoriteRecipeRow.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailActivity(
                        favoriteRecipes[position].result
                    )
                holder.binding.root.findNavController().navigate(action)
            }
        }

        /**
         * Long Click Listener
         * */
        holder.binding.clFavoriteRecipeRow.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                applySelection(holder, currentRecipe)
                true
            }
        }
    }

    // 선택한 favorite 레서피의 아이템뷰가 재활용될 때 stroke가 뒤죽박죽 되는것을 제어하기 위한 메서드
    private fun saveItemStateOnScroll(currentRecipe: FavoritesEntity, holder: FavoriteViewHolder) {
        if (selectedRecipes.contains(currentRecipe)) {
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        } else {
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
    }

    private fun applySelection(holder: FavoriteViewHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: FavoriteViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.clFavoriteRecipeRow.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.binding.cvFavoriteRow.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    /**
     * @applySelection()에서 눌려져서 배열에 추가,제거될 때마다 호출 됨.
     * */
    private fun applyActionModeTitle() {
        when(selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
                multiSelection = false
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
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
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_recipe_menu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed.")

            multiSelection = false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        viewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    fun clearContextualActionMode() {
        if(this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

    class FavoriteViewHolder(val binding: FavoriteRecipesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        private var currentFavoriteEntity: FavoritesEntity? = null

        fun bind(favoritesEntity: FavoritesEntity) {
            this.currentFavoriteEntity = favoritesEntity
            binding.favoritesEntity = favoritesEntity // databinding의 variable에 favoritesEntity 넣기
            binding.executePendingBindings()
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
