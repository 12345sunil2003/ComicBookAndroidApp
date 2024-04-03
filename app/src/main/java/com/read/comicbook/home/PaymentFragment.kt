package com.read.comicbook.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.read.comicbook.databinding.FragmentPaymentBinding
import com.read.comicbook.model.PostInfoModel
import com.read.comicbook.showSnackBar

class PaymentFragment : Fragment() {
    lateinit var binding: FragmentPaymentBinding
    private val args : PaymentFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun initView() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnPay.setOnClickListener {
                navigateToPostDetails(args.postInfoModel)
                root.showSnackBar("Payment Success")
            }

            price.text = "$"+args.postInfoModel.price
        }
    }

    private fun navigateToPostDetails(postInfoModel: PostInfoModel) {
        val action = PaymentFragmentDirections.actionPaymentFragmentToPostDetailsFragment(postInfoModel)
        findNavController().navigate(action)
    }

}