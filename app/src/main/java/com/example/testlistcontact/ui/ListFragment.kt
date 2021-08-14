package com.example.testlistcontact.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testlistcontact.Adapter
import com.example.testlistcontact.PersonContact
import com.example.testlistcontact.R

@Suppress("UNREACHABLE_CODE")
class ListFragment : Fragment(R.layout.fragment_list_contact) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var searchEditText: EditText
    private lateinit var itemSelected: ItemSelected
    private var index = 0
    private val range = 1..100

    companion object {
        var contactList = arrayListOf<PersonContact>()
        const val LIST_TAG = "LIST_TAG"
        fun newInstance() = ListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemSelected = context as ItemSelected
        adapter = Adapter(
            { personContact, i ->
              itemSelected.onItemSelected(i, personContact)},
            this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt("Index")
            contactList =
                savedInstanceState.getParcelableArrayList<PersonContact>("Array")
                as ArrayList<PersonContact>
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.contactRecyclerView)
        searchEditText = view.findViewById(R.id.searchEdinText)
        generatePerson()

        recyclerView.adapter = adapter
        adapter.contactList = contactList



        val decorator = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(resources.getDrawable(R.drawable.separator, null))
        recyclerView.addItemDecoration(decorator)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })
    }

    private fun generatePerson() {
        if (contactList.isEmpty()) {
            for (i in range) {
                contactList.add(
                    PersonContact(
                        "Last Name$i",
                        "Billy $i",
                        "$i",
                        "https://picsum.photos/400?random=$i"
                    )
                )
            }
        }
    }

    private fun filter(text: String) {
        val filterList = ArrayList<PersonContact>()
        for (i in contactList) {
            if (i.name.toLowerCase().contains(text.toLowerCase()) ||
                i.lastName.toLowerCase().contains(text.toLowerCase())
            ) {
                filterList.add(i)
            }
        }
        adapter.setDate(filterList)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.card_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adapter.getMenuPosition()
        when (item.itemId) {
            R.id.deleteItem -> {
                contactList.removeAt(position)
                adapter.notifyItemRemoved(position)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("Array", contactList)
        outState.putInt("Index", index)
        super.onSaveInstanceState(outState)
    }

    interface ItemSelected {
        fun onItemSelected(index: Int, personContact: PersonContact)
    }
}