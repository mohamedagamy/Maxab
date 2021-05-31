package com.example.maxbapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.maxbapp.R
import com.example.maxbapp.model.UserModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_contact_list.view.*

internal class ContactAdapter(private var userList: MutableList<UserModel>?) :
    RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    var clickListener: ((position: Int) -> Unit)? = null
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var contactImage: CircleImageView = view.findViewById(R.id.contactImage)
        var contactName: AppCompatTextView = view.findViewById(R.id.contactName)
        var contactMobile: AppCompatTextView = view.findViewById(R.id.contactMobile)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = userList?.get(position)
        //holder.contactImage.setImageResource()
        holder.contactName.text = user?.name
        holder.contactMobile.text = user?.mobile
        holder.itemView.setOnClickListener {
            clickListener?.invoke(position)
        }
    }
    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    fun removeItemAt(position: Int) {
        userList?.remove(userList?.get(position))
        notifyItemRemoved(position)
    }

    fun getItemAt(position: Int): UserModel? {
        return userList?.get(position)
    }
}