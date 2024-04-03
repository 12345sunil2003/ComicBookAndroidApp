package com.read.comicbook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.read.comicbook.Utils
import com.read.comicbook.adapter.CategoryAdapter
import com.read.comicbook.adapter.SubCategoryAdapter
import com.read.comicbook.databinding.FragmentCategoryBinding
import com.read.comicbook.databinding.FragmentSubCategoryBinding
import com.read.comicbook.model.PostInfoModel

class SubCategoryFragment: Fragment() {
    lateinit var binding: FragmentSubCategoryBinding
    private lateinit var subCategoryAdapter: SubCategoryAdapter
    private val args : SubCategoryFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            subCategoryName.text = args.categoryModel.name
            setCategoryList()
        }
        observeLiveData()
        args.categoryModel.name?.let {
            viewModel.getPostsByCategory(it)
        }

    }

    private fun observeLiveData() {
        viewModel.postsByCategory.observe(this){
            subCategoryAdapter.setModelList(it)
        }
    }

    private fun setCategoryList() {
        subCategoryAdapter = SubCategoryAdapter{
            navigateToPostSummary(it)
        }
        binding.rvSubCategoryList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = subCategoryAdapter
        }
    }
    private fun navigateToPostSummary(it: PostInfoModel) {
        val action = SubCategoryFragmentDirections.actionSubCategoryFragmentToPostSummaryFragment(it)
        findNavController().navigate(action)
    }
}