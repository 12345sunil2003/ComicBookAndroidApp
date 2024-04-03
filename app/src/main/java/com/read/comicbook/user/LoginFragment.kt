package com.read.comicbook.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.read.comicbook.R
import com.read.comicbook.data
import com.read.comicbook.databinding.FragmentLoginBinding
import com.read.comicbook.home.HomeActivity
import com.read.comicbook.showSnackBar

class LoginFragment: Fragment() {
    lateinit var binding: FragmentLoginBinding
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
        binding.btnSignIn.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin(){
        binding.apply {
            viewModel.login(email.data(), password.data()) { isSuccess, msg ->
                if (isSuccess){
                    activity?.let {
                        startActivity(Intent(it, HomeActivity::class.java))
                        it.finish()
                    }
                }else {
                    rootView.showSnackBar(msg)
                }
            }
        }
    }
}