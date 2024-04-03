package com.read.comicbook.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.read.comicbook.MyApp
import com.read.comicbook.R
import com.read.comicbook.Utils.getDummyComicMovies
import com.read.comicbook.Utils.getDummyUsers
import com.read.comicbook.databinding.FragmentHomeBinding
import com.read.comicbook.adapter.NewUpdatesAdapter
import com.read.comicbook.adapter.TopReadersAdapter
import com.read.comicbook.adapter.TrendingAdapter
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.model.PostInfoModel
import com.read.comicbook.user.LoginActivity
import com.read.comicbook.utils.FileUtils

class HomeFragment: Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var newUpdatesAdapter: NewUpdatesAdapter
    private lateinit var trendingAdapter: TrendingAdapter
    private lateinit var topReadersAdapter: TopReadersAdapter
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        setProfilePic()
        setNewUpdateList()
        setTrendingList()
        setTopReadersList()
        observeListener()
        viewModel.getAllPosts()
        binding.name.text = getUser()?.name?:"Name"
        binding.btnLogout.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        (context?.applicationContext as MyApp).apply {
            setUser(null)
            clearUserPref()
        }
        activity?.startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    private fun observeListener() {
        viewModel.allPosts.observe(this) {
            newUpdatesAdapter.setModelList(it.shuffled())
            trendingAdapter.setModelList(it.shuffled())
        }
    }

    private fun setNewUpdateList() {
        newUpdatesAdapter = NewUpdatesAdapter{
            navigateToPostSummaryScreen(it)
        }
        newUpdatesAdapter.setModelList(getDummyComicMovies())
        binding.rvNewUpdates.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = newUpdatesAdapter
        }
    }

    private fun setTrendingList() {
        trendingAdapter = TrendingAdapter{
            navigateToPostSummaryScreen(it)
        }
        trendingAdapter.setModelList(getDummyComicMovies())
        binding.rvTrending.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingAdapter
        }
    }

    private fun setTopReadersList() {
        topReadersAdapter = TopReadersAdapter()
        topReadersAdapter.setModelList(getDummyUsers())
        binding.rvTopReaders.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = topReadersAdapter
        }
    }


    private fun navigateToPostSummaryScreen(it: PostInfoModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToPostSummaryFragment(it)
        findNavController().navigate(action)
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

    private fun getUser(): UserEntity? {
        return (context?.applicationContext as MyApp).getUser()
    }
}