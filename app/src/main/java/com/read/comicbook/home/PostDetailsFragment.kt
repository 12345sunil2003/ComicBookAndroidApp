package com.read.comicbook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.read.comicbook.MyApp
import com.read.comicbook.R
import com.read.comicbook.Utils
import com.read.comicbook.adapter.ComicImageAdapter
import com.read.comicbook.adapter.TopReadersAdapter
import com.read.comicbook.databinding.FragmentHomeBinding
import com.read.comicbook.databinding.FragmentLoginBinding
import com.read.comicbook.databinding.FragmentNewPostBinding
import com.read.comicbook.databinding.FragmentPostDetailsBinding
import com.read.comicbook.databinding.FragmentPostSummaryBinding
import com.read.comicbook.databinding.FragmentSignupBinding
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.user.UserViewModel
import com.read.comicbook.utils.FileUtils

class PostDetailsFragment: Fragment() {
    lateinit var binding: FragmentPostDetailsBinding
    private val args : PostDetailsFragmentArgs by navArgs()
    private lateinit var comicImageAdapter: ComicImageAdapter
    private var isFavourite = false
    private val viewModel: UserViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        setComicImageList()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        args.postInfoModel.apply {
            binding.tvPostName.text = title
            binding.tvRating.text = rating
            binding.price.isVisible = isPaid
            binding.price.text = "$"+price
            setFavDrawable(checkIsFav())
            isFavourite = checkIsFav()
            binding.isFav.setOnClickListener {
                isFavourite = !isFavourite
                setFavDrawable(isFavourite)
                updateFavDb()
            }
            Glide.with(requireContext())
                .load(FileUtils.retrieveImgFile(thumbnail?:"", requireContext()))
                .into(binding.roundedImageView)
        }

    }

    private fun updateFavDb() {
        getUser()?.let {user->
            args.postInfoModel.postId?.let {
                val favId = user.fav?.toMutableList()?: arrayListOf()
                val favIdx = favId.indexOfFirst { id->
                    id==args.postInfoModel.postId.toString()
                }

                if (favIdx == -1 && isFavourite){
                    favId.add(args.postInfoModel.postId.toString())
                }else{
                    favId.removeAt(favIdx)
                }

                viewModel.updateUser(
                    UserEntity(
                        userId = user.userId,
                        email = user.email,
                        name = user.name,
                        phone = user.phone,
                        thumbnail = user.thumbnail,
                        password = user.password,
                        rated = user.rated,
                        fav = favId,
                        userDesc = user.userDesc
                    )
                ){isSuccess: Boolean, msg: String ->  }
            }
        }
    }

    private fun setFavDrawable(fav: Boolean) {
        val favResId = if (fav){
            R.drawable.heart
        }else R.drawable.fav
        Glide.with(requireContext())
            .load(favResId)
            .into(binding.isFav)
    }

    private fun setComicImageList() {
        comicImageAdapter = ComicImageAdapter()
        args.postInfoModel.imgFiles?.let {
            comicImageAdapter.setModelList(it)
        }
        binding.rvComicImg.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = comicImageAdapter
        }
    }

    private fun checkIsFav(): Boolean {
        return getUser()?.fav?.any {
            it == args.postInfoModel.postId.toString()
        }?:false
    }

    private fun getUser(): UserEntity? {
        return (context?.applicationContext as MyApp).getUser()
    }
}