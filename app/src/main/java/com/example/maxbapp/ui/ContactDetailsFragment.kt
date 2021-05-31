package com.example.maxbapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.maxbapp.R
import com.example.maxbapp.databinding.FragmentContatctDetailsBinding
import com.example.maxbapp.model.UserModel
import openDialer

class ContactDetailsFragment : Fragment() {
    private var _binding: FragmentContatctDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContatctDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showToolbar(false)
        (activity as MainActivity).showFab(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userModel = arguments?.get("user") as UserModel
        Log.d("user",userModel.toString())

        binding.contactDetailsName.text = userModel.name
        binding.contactDetailsPhone.text = userModel.mobile
        binding.contactDetailsCard.setOnClickListener {
            context?.openDialer(userModel.mobile.toString())
        }

        binding.contactDetailsBack.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}