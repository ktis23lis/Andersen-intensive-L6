package com.example.testlistcontact

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    private val onClick: (PersonContact, Int) -> Unit,
    private val fragment: Fragment):
    RecyclerView.Adapter<Adapter.ContactViewHolder>() {

    private var menuPosition: Int = 0

    var contactList = emptyList<PersonContact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val pos = holder.adapterPosition
        holder.bind(contactList[position])
        holder.itemView.setOnClickListener {
            onClick(contactList[position], pos)
        }
        holder.registerMenu(holder.itemView)
        holder.itemView.setOnLongClickListener {
            menuPosition = position
            holder.itemView.showContextMenu(10F, 10F)
        }
    }

    override fun getItemCount(): Int = contactList.size

    @JvmName("getMenuPosition1")
    fun getMenuPosition(): Int {
        return menuPosition
    }

    fun filterList(list: ArrayList<PersonContact>) {
        contactList = list
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lastNameTextView: TextView = itemView.findViewById(R.id.lastNameItemTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameItemTextView)

        fun bind(personContact: PersonContact) {
            lastNameTextView.text = personContact.lastName
            nameTextView.text = personContact.name
        }

        fun registerMenu(itemView: View) {
            itemView.setOnLongClickListener {
                menuPosition = layoutPosition
                false
            }
            fragment.registerForContextMenu(itemView)
        }
    }
}