package com.read.comicbook.user

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.read.comicbook.data
import com.read.comicbook.databinding.FragmentSignupBinding
import com.read.comicbook.db.schema.UserEntity
import com.read.comicbook.showSnackBar

class SignupFragment: Fragment() {
    lateinit var binding: FragmentSignupBinding
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
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSignUp.setOnClickListener {
            if (isValid()) {
                performSignup()
            }
        }
    }

    private fun isValid(): Boolean {
        var isValid = true
        binding.apply {
            if (email.text.isNullOrEmpty()){
                isValid = false
                rootView.showSnackBar("Fill all the fields")
            }else if (password.text.isNullOrEmpty()){
                isValid = false
                rootView.showSnackBar("Fill all the fields")
            }else if (confirmPassword.text.isNullOrEmpty()){
                isValid = false
                rootView.showSnackBar("Fill all the fields")
            }else if (password.data() != confirmPassword.data()){
                isValid = false
                rootView.showSnackBar("Check password")
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email.data()).matches()){
                isValid = false
                rootView.showSnackBar("Please check email")
            }

        }
        return isValid
    }

    private fun performSignup() {
        binding.apply {
            val user = UserEntity(email = email.data(), phone = phone.data(), password = password.data())
            viewModel.insertUser(user) { isSuccess, msg ->
                if (isSuccess){
                    rootView.showSnackBar("User Registered Successfully")
                    findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
                }else {
                    rootView.showSnackBar(msg)
                }
            }
        }

    }
}