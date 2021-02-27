package com.example.foody.view.fragment.instruction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.foody.R
import com.example.foody.databinding.FragmentInstructionBinding
import com.example.foody.model.Result
import com.example.foody.util.Constants
import timber.log.Timber

class InstructionFragment : Fragment() {

    private var _binding: FragmentInstructionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_instruction, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = initData()
        setupWebView(result)

    }

    private fun initData(): Result {
        // 해당 arguments는 PagerAdapter 클래스에서 각 프래그먼트에 뿌려줌
        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)
        return myBundle!!
    }

    private fun setupWebView(result: Result) {
        binding.wvInstruction.webViewClient = object : WebViewClient() {}
        val websiteUrl = result.sourceUrl
        Timber.d("setupWebView() url = $websiteUrl")

        binding.wvInstruction.loadUrl(websiteUrl)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}