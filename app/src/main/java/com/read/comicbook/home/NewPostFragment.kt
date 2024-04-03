package com.read.comicbook.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.read.comicbook.MyApp
import com.read.comicbook.R
import com.read.comicbook.data
import com.read.comicbook.databinding.FragmentNewPostBinding
import com.read.comicbook.db.schema.PostEntity
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.showSnackBar
import com.read.comicbook.utils.FileUtils.saveImageToAppData
import kotlin.random.Random

class NewPostFragment: Fragment() {
    lateinit var binding: FragmentNewPostBinding
    private var selectedThumbnailName:String?=null
    private var selectedImageFileList= ArrayList<String>()
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
        binding = FragmentNewPostBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setCategoryList() {
        val categoryList = arrayOf("Action", "Romance", "Drama", "Date", "Comedy", "Horror")
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_dropdown_item_1line, categoryList)
        binding.category.setAdapter(adapter)
        binding.category.setOnClickListener {
            binding.category.showDropDown()
        }
    }

    private fun setPremiumList() {
        val premiumList = arrayOf("Free", "Paid")
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_dropdown_item_1line, premiumList)
        binding.selectPremium.setAdapter(adapter)
        binding.selectPremium.setOnClickListener {
            binding.selectPremium.showDropDown()
        }
    }

    private fun initView() {
        setCategoryList()
        setPremiumList()
        binding.apply {
            btnChooseThumbnail.setOnClickListener {
                pickImageLauncher.launch("image/*")
            }
            btnChooseFiles.setOnClickListener {
                pickMultipleImages.launch("image/*")
            }
            btnCreatePost.setOnClickListener {
                if (isValid()){
                    performCreatePost()
                }

            }
        }
    }

    private fun performCreatePost() {
        binding.apply {
            val post = PostEntity(
                email = getUser()?.email,
                title = title.data(),
                desc = storySummary.data(),
                thumbnail = selectedThumbnailName,
                imgList = selectedImageFileList,
                category = category.data(),
                isPremium =(selectPremium.data().equals("Paid", true)),
                price = price.data(),
                rating = getRating(),
                createdDate = System.currentTimeMillis()
            )
            viewModel.createPost(post){isSuccess: Boolean, msg: String ->
                if (isSuccess){
                    resetFields()
                    root.showSnackBar("Post Created Successfully")
                }else{
                    root.showSnackBar(msg)
                }
            }
        }
    }

    private fun isValid(): Boolean {
        var isValid = true
        binding.apply {
            if (title.data().isNullOrEmpty()){
                isValid = false
            }else if (category.data().isNullOrEmpty()){
                isValid = false
            }else if (selectedThumbnailName.isNullOrEmpty()){
                isValid = false
            }else if (selectedImageFileList.isNullOrEmpty()){
                isValid = false
            }else if (selectPremium.data().equals("Paid",true) && price.data().isNullOrEmpty()){
                isValid = false
            }else if (selectPremium.data().isNullOrEmpty()){
                isValid = false
            }else if (storySummary.data().isNullOrEmpty()){
                isValid = false
            }
        }
        if (!isValid){
            binding.root.showSnackBar("Check all fields are filled")
        }
        return isValid

    }

    private fun getRating(): String {
        val result =  Random.nextDouble(1.00, 10.00)
        return String.format("%.2f", result)
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { selectedImageUri ->
                saveImageToAppData(selectedImageUri, requireActivity()){
                    selectedThumbnailName = it
                    binding.btnChooseThumbnail.apply {
                        setText( getString(R.string.selected_1_file))
                    }
                }
            }
        }

    private val pickMultipleImages =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
            uris?.let {
                for (uri in uris) {
                    saveImageToAppData(uri, requireActivity()) {
                        selectedImageFileList.add(it)
                    }
                }
                binding.btnChooseFiles.apply {
                    setText( getString(R.string.selected_multiple_files, selectedImageFileList.size))
                }
            }
        }

    private fun resetFields(){
        binding.apply {
            title.text?.clear()
            category.text?.clear()
            selectedThumbnailName = null
            selectedImageFileList.clear()
            price.text?.clear()
            selectPremium.text?.clear()
            storySummary.text?.clear()
            btnChooseFiles.text?.clear()
            btnChooseThumbnail.text?.clear()
        }
    }

    private fun getUser(): UserEntity? {
        return (context?.applicationContext as MyApp).getUser()
    }
}