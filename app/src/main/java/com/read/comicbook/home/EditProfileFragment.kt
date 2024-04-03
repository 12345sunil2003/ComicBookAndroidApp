package com.read.comicbook.home

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.read.comicbook.MyApp
import com.read.comicbook.R
import com.read.comicbook.data
import com.read.comicbook.databinding.FragmentEditProfileBinding
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.showSnackBar
import com.read.comicbook.user.UserViewModel
import com.read.comicbook.utils.FileUtils
import com.read.comicbook.utils.FileUtils.retrieveImgFile

class EditProfileFragment : Fragment() {
    lateinit var binding: FragmentEditProfileBinding
    private val viewModel: UserViewModel by viewModels()
    private var selectedThumbnailName: String? = null
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { selectedImageUri ->
                FileUtils.saveImageToAppData(selectedImageUri, requireActivity()) {
                    selectedThumbnailName = it
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        setProfilePic(it)
                    },1500)

                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        setProfilePic(getUser()?.thumbnail)
        binding.apply {
            userName.setText(getUser()?.name)
            phone.setText(getUser()?.phone)
            email.setText(getUser()?.email)
            btnChangeProfilePic.setOnClickListener {
                pickImageLauncher.launch("image/*")
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnSubmit.setOnClickListener {
                performUpdate()

            }
        }
    }

    private fun performUpdate() {
        binding.apply {
            if (email.data().isNullOrEmpty()) {
                root.showSnackBar("Please enter email")
            }
            getUser()?.let { user ->
                viewModel.updateUser(
                    UserEntity(
                        userId = user.userId,
                        email = email.data(),
                        name = userName.data(),
                        phone = phone.data(),
                        thumbnail = selectedThumbnailName,
                        password = user.password,
                        rated = user.rated,
                        fav = user.fav,
                        userDesc = user.userDesc
                    ),
                ) { isSuccess: Boolean, msg: String ->
                    if (isSuccess) {
                        findNavController().popBackStack()
                    } else {
                        root.showSnackBar(msg)
                    }
                }
            }
        }


    }

    private fun setProfilePic(thumbnail: String?) {
        context?:return
        val img:Any? = if (thumbnail!=null){
            retrieveImgFile(thumbnail ?: "", requireContext())
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