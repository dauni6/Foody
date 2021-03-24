package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foody.databinding.RecipesRowLayoutBinding
import com.example.foody.model.FoodRecipe
import com.example.foody.model.Result
import com.example.foody.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    private var recipes = emptyList<Result>()

    override fun getItemCount(): Int = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder = RecipesViewHolder.from(parent)

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    fun setData(newData: FoodRecipe) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results) // 첫번째 인자는 oldList이고 두번째는 newList이다
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this@RecipesAdapter)
//        notifyDataSetChanged() // DiffUtil을 사용하므로 비용이 높은 notifyDataSetChanged()를 사용하지 않음

    }

    class RecipesViewHolder(private val binding: RecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result // databinding의 variable에 result 넣기
            binding.executePendingBindings()
        }

        // 필요로 하는곳에서 만드는게 아니라 필요로하는 곳에서 갖다 쓰게만 하도록 아래와 같이 만들어서 주입시켜 준다는 느낌으로 해준다.
        companion object {
            fun from(parent: ViewGroup): RecipesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return RecipesViewHolder(binding)
            }
        }

    }

}