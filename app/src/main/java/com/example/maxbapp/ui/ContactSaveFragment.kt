package com.example.maxbapp.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.maxbapp.R
import com.example.maxbapp.databinding.FragmentContactSaveBinding
import com.example.maxbapp.model.UserModel
import kotlinx.coroutines.*
import showToast
import java.lang.Exception

class ContactSaveFragment : Fragment() {

    private var _binding: FragmentContactSaveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContactSaveBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showToolbar(true)
        (activity as MainActivity).showFab(false)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_save).isVisible = true
        menu.findItem(R.id.action_search).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> true
            R.id.action_save -> saveUser()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveUser(): Boolean {
        GlobalScope.launch(Dispatchers.IO) {
            val userName = binding.nameInputEditText.text.toString()
            val userMobile = binding.telInputEditText.text.toString()
            val isNewPhone = (activity as MainActivity).contactVM.users?.filter { it.mobile.equals(userMobile) }.isNullOrEmpty()
            val isFormOk = userName.isNotBlank() && userMobile.isNotBlank()
            if(isNewPhone){
                if(isFormOk){
                val isComplete = (activity as MainActivity).contactVM.addNewUser(UserModel(userName,userMobile))
                withContext(Dispatchers.Main){
                    if(isComplete)
                        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        context?.showToast(getString(R.string.please_fill_form))
                    }
                }
            }else{
                withContext(Dispatchers.Main){
                    context?.showToast(getString(R.string.please_enter_phone))
                }
            }
        }
    return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}