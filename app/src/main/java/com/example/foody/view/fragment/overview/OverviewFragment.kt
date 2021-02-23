package com.example.foody.view.fragment.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import coil.load
import com.example.foody.R
import com.example.foody.databinding.FragmentOverviewBinding
import com.example.foody.model.Result
import kotlinx.android.synthetic.main.fragment_overview.view.*
import org.jsoup.Jsoup
import timber.log.Timber

class OverviewFragment : Fragment() {

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

    }

    private fun initData() {
        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        binding.ivMain.load(myBundle?.image)
        binding.tvTitle.text = myBundle?.title
        binding.tvLikes.text = myBundle?.aggregateLikes.toString()
        binding.tvTime.text = myBundle?.readyInMinutes.toString()

        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            binding.tvSummary.text = summary
        }

        if (myBundle?.vegetarian == true) {
            binding.ivVegetarian.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvVegetarian.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.vegan == true) {
            binding.ivVegan.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvVegan.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.glutenFree == true) {
            binding.ivGlutenFree.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvGlutenFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.dairyFree == true) {
            binding.ivDiaryFree.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvDiaryFree.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.veryHealthy == true) {
            binding.ivHealthy.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvHealthy.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.cheap == true) {
            binding.ivCheap.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvCheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

    }

}