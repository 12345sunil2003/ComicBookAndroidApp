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
import com.bumptech.glide.Glide
import com.read.comicbook.MyApp
import com.read.comicbook.R
import com.read.comicbook.databinding.FragmentPostSummaryBinding
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.model.PostInfoModel
import com.read.comicbook.user.UserViewModel
import com.read.comicbook.utils.FileUtils

class PostSummaryFragment: Fragment() {
    lateinit var binding: FragmentPostSummaryBinding
    private val args : PostSummaryFragmentArgs by navArgs()
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
        binding = FragmentPostSummaryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        binding.btnStart.setOnClickListener {
            if (args.postInfoModel.isPaid){
                navigateToPayment(args.postInfoModel)
            }else navigateToPostDetails(args.postInfoModel)
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        args.postInfoModel.apply {
            binding.tvPostName.text = title
            binding.tvRating.text = rating
            binding.price.setText("$$price")
            binding.tvSummary.text = desc
            binding.price.isVisible = isPaid
            binding.btnStart.text = if (isPaid) getString(R.string.pay) else getString(R.string.start)
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

    private fun navigateToPayment(postInfoModel: PostInfoModel) {
        val action = PostSummaryFragmentDirections.actionPostSummaryFragmentToPaymentFragment(postInfoModel)
        findNavController().navigate(action)
    }

    private fun checkIsFav(): Boolean {
        return getUser()?.fav?.any {
            it == args.postInfoModel.postId.toString()
        }?:false
    }

    private fun setFavDrawable(fav: Boolean) {
        val favResId = if (fav){
            R.drawable.heart
        }else R.drawable.fav
        Glide.with(requireContext())
            .load(favResId)
            .into(binding.isFav)
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

    private fun navigateToPostDetails(postInfoModel: PostInfoModel) {
        val action = PostSummaryFragmentDirections.actionPostSummaryFragmentToPostDetailsFragment(postInfoModel)
        findNavController().navigate(action)
    }

    private fun getUser(): UserEntity? {
        return (context?.applicationContext as MyApp).getUser()
    }
}