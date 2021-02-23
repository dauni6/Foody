package com.example.foody.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.foody.R
import com.example.foody.adapters.PagerAdapter
import com.example.foody.databinding.ActivityDetailBinding
import com.example.foody.view.fragment.ingredients.IngredientsFragment
import com.example.foody.view.fragment.instruction.InstructionFragment
import com.example.foody.view.fragment.overview.OverviewFragment
import timber.log.Timber

class DetailActivity : AppCompatActivity() {

    private val args by navArgs<DetailActivityArgs>()

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@DetailActivity, R.layout.activity_detail)

        setActionBar()

        binding.viewPager.adapter = PagerAdapter(
            createResultBundle(),
            createFragmentsList(),
            createTitlesList(),
            supportFragmentManager
        )

        binding.tabLayout.setupWithViewPager(binding.viewPager)

    }

    private fun createResultBundle(): Bundle {
        return Bundle().apply {
            this.putParcelable("recipeBundle", args.result)
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.d("onOptionsItemSelected / home = ${android.R.id.home}")
        Timber.d("onOptionsItemSelected / itemId = ${item.itemId}")
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}