package com.example.foody.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foody.R
import com.example.foody.adapters.PagerAdapter
import com.example.foody.data.database.entities.FavoritesEntity
import com.example.foody.databinding.ActivityDetailBinding
import com.example.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import com.example.foody.view.fragment.ingredients.IngredientsFragment
import com.example.foody.view.fragment.instruction.InstructionFragment
import com.example.foody.view.fragment.overview.OverviewFragment
import com.example.foody.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val args by navArgs<DetailActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this@DetailActivity, R.layout.activity_detail)

        setActionBar()

        binding.viewPager2.apply {
            adapter = PagerAdapter(
                createResultBundle(),
                createFragmentsList(),
                this@DetailActivity
            )
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = createTitlesList()[position]
        }.attach()

    }

    private fun createResultBundle(): Bundle {
        val bundle = Bundle()
        bundle.putParcelable(RECIPE_RESULT_KEY, args.result)
        return bundle
    }

    private fun createFragmentsList(): ArrayList<Fragment> {
        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionFragment())
        return fragments
    }

    private fun createTitlesList(): ArrayList<String> {
        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")
        return titles
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(
            ContextCompat.getColor(
                this@DetailActivity,
                R.color.white
            )
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Favorite star button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        checkSavedRecipes(menu)
        return true
    }

    private fun checkSavedRecipes(menu: Menu?) {
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        mainViewModel.readFavoriteRecipes.observe(this, { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.recipeId == args.result.recipeId) {
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                        changeMenuItemColor(menuItem!!, R.color.yellow)
                        break
                    } else {
                        changeMenuItemColor(menuItem!!, R.color.white)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 어떻게 home을하면 MainActivity를 알아서 뒤로가기가 되는지 궁금하여 밑에 로그로 확인하려함.
//        Timber.d("onOptionsItemSelected / home = ${android.R.id.home}")
//        Timber.d("onOptionsItemSelected / itemId = ${item.itemId}")

        val HOME = android.R.id.home
        val SAVE_TO_FAVORITES_MENU = R.id.save_to_favorites_menu

        if (item.itemId == HOME) {
            finish()
        } else if (item.itemId == SAVE_TO_FAVORITES_MENU && !recipeSaved) {
            saveToFavorite()
            changeMenuItemColor(item, R.color.yellow)
            showSnackBar(getString(R.string.txt_recipe_saved))
        } else if (item.itemId == SAVE_TO_FAVORITES_MENU && recipeSaved) {
            deleteFromFavorite()
            changeMenuItemColor(item, R.color.white)
            showSnackBar(getString(R.string.txt_favorite_removed))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction(getString(R.string.txt_favorite_okay)){}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    private fun saveToFavorite() {
        val favoritesEntity =
            FavoritesEntity(
                0,
                args.result
            )
        mainViewModel.insertFavoriteRecipe(favoritesEntity)
    }

    private fun deleteFromFavorite() {
        val favoritesEntity =
            FavoritesEntity(
                savedRecipeId,
                args.result
            )
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        recipeSaved = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
