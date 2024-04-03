package com.read.comicbook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.read.comicbook.MyApp
import com.read.comicbook.adapter.ReviewGridAdapter
import com.read.comicbook.databinding.FragmentFavBinding
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.model.PostInfoModel

class FavouriteFragment: Fragment() {
    lateinit var binding: FragmentFavBinding
    private lateinit var favAdapter: ReviewGridAdapter
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
        binding = FragmentFavBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        binding.apply {
            setFavList()
        }
        observeLiveData()
        getUser()?.fav?.let {
            viewModel.getPostsById(it)
        }
    }

    private fun getUser(): UserEntity? {
        return (context?.applicationContext as MyApp).getUser()
    }

    private fun observeLiveData() {
        viewModel.postsByCategory.observe(this){
            favAdapter.setModelList(it)
        }
    }

    private fun setFavList() {
        favAdapter = ReviewGridAdapter{
            navigateToPostSummary(it)
        }
        binding.rvFav.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = favAdapter
        }
    }
    private fun navigateToPostSummary(it: PostInfoModel) {
        val action = FavouriteFragmentDirections.actionFavouriteFragmentToPostSummaryFragment(it)
        findNavController().navigate(action)
    }
}