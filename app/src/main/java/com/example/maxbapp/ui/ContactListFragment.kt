package com.example.maxbapp.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.maxbapp.R
import com.example.maxbapp.databinding.FragmentContactListBinding
import com.example.maxbapp.model.UserModel
import com.example.maxbapp.viewmodel.ContactViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_contact_list.*
import kotlinx.coroutines.*

class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showToolbar(true)
        (activity as MainActivity).showFab(true)

        GlobalScope.launch(Dispatchers.IO) {
            val users = (activity as MainActivity).contactVM.getUserList()
            withContext(Dispatchers.Main){
                loadUsers(users)
            }
        }
    }

    private fun loadUsers(users: List<UserModel>?) {
        Log.d("users",users.toString())
        val contactAdapter = ContactAdapter(users?.toMutableList())
        binding.recyclerviewContact.adapter = contactAdapter
        contactAdapter.clickListener = {
            val bundle = Bundle()
            bundle.putParcelable("user",users?.get(it))
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment,bundle)
        }
        attachSwipeToDel(contactAdapter)

    }

    private fun attachSwipeToDel(contactAdapter: ContactAdapter){
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val userModel = contactAdapter.getItemAt(position)!!

                    GlobalScope.launch(Dispatchers.IO) {
                        val isComplete = (activity as MainActivity).contactVM.deleteUser(userModel)

                        withContext(Dispatchers.Main){
                            if(isComplete)
                                contactAdapter.removeItemAt(position)
                        }
                    }

                    Snackbar.make(
                        (activity as MainActivity).coordinator_root,
                        getString(R.string.deleted),
                        Snackbar.LENGTH_LONG).show()
                }
            }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerviewContact)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_save).isVisible = false
        menu.findItem(R.id.action_search).isVisible = true
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