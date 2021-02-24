package com.example.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.foody.R
import com.example.foody.databinding.IngredientsRowLayoutBinding
import com.example.foody.model.ExtendedIngredient
import com.example.foody.util.Constants.Companion.BASE_IMAGE_URL
import com.example.foody.util.RecipesDiffUtil
import timber.log.Timber
import java.util.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    override fun getItemCount(): Int = ingredientsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder = IngredientsViewHolder.from(parent)

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(ingredientsList[position])
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this@IngredientsAdapter)
    }

    class IngredientsViewHolder(private val binding: IngredientsRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: ExtendedIngredient) {
            Timber.d("image url = ${ingredient.image} / amount = ${ingredient.amount} / unit = ${ingredient.unit} / consistency = ${ingredient.consistency} / original = ${ingredient.original}")

            binding.ivIngredient.load(BASE_IMAGE_URL + ingredient.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            binding.tvIngredientName.text = ingredient.name.capitalize(Locale.ROOT)
            binding.tvIngredientAmount.text = ingredient.amount.toString()
            binding.tvIngredientUnit.text = ingredient.unit
            binding.tvIngredientConsistency.text = ingredient.consistency
            binding.tvIngredientOriginal.text = ingredient.original
        }

        companion object {
            fun from(parent: ViewGroup): IngredientsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return IngredientsViewHolder(binding)
            }
        }

    }

}
