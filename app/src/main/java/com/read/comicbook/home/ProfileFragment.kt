package com.read.comicbook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.read.comicbook.MyApp
import com.read.comicbook.R
import com.read.comicbook.Utils
import com.read.comicbook.adapter.ReviewGridAdapter
import com.read.comicbook.databinding.FragmentProfileBinding
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.model.PostInfoModel
import com.read.comicbook.utils.FileUtils

class ProfileFragment: Fragment() {
    lateinit var binding: FragmentProfileBinding
    private lateinit var reviewAdapter: ReviewGridAdapter
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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        setProfilePic()
        setReviewList()
        binding.btnEdit.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
        binding.name.text = getUser()?.name?:"Profile Name"
        observeListener()
        viewModel.getAllPosts()
    }

    private fun observeListener() {
        viewModel.allPosts.observe(this) {
            reviewAdapter.setModelList(it.shuffled())
        }
    }

    private fun setProfilePic() {
        val img:Any? = if (getUser()?.thumbnail!=null){
            FileUtils.retrieveImgFile(getUser()?.thumbnail ?: "", requireContext())
        }else{
            R.drawable.baseline_account_circle_24
        }
        Glide.with(this)
            .load(img)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(binding.profilePic)
    }

    private fun setReviewList() {
        reviewAdapter = ReviewGridAdapter{
            navigateToPostSummary(it)
        }
        //reviewAdapter.setModelList(Utils.getDummyComicMovies())
        binding.rvReview.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = reviewAdapter
        }
    }

    private fun navigateToPostSummary(it: PostInfoModel) {
        val action = ProfileFragmentDirections.actionProfileFragmentToPostSummaryFragment(it)
        findNavController().navigate(action)
    }

    private fun getUser(): UserEntity? {
        return (context?.applicationContext as MyApp).getUser()
    }
}