package com.read.comicbook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.read.comicbook.R
import com.read.comicbook.Utils
import com.read.comicbook.adapter.CategoryAdapter
import com.read.comicbook.databinding.FragmentCategoryBinding

class CategoryFragment: Fragment() {
    lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {

        setCategoryList()
    }

    private fun setCategoryList() {
        categoryAdapter = CategoryAdapter{
            val action = CategoryFragmentDirections.actionCategoryFragmentToSubCategoryFragment(categoryModel = it)
            findNavController().navigate(action)
        }
        categoryAdapter.setModelList(Utils.getDummyCategories())
        binding.rvCategoryList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
        }
    }
}