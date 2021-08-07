package com.example.testlistcontact.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.testlistcontact.PersonContact
import com.example.testlistcontact.R
import com.squareup.picasso.Picasso
import javax.security.auth.callback.Callback

class ItemFragment : Fragment(R.layout.fragment_item) {

    private lateinit var nameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var numberTextView: TextView
    private lateinit var nameEdinText: EditText
    private lateinit var lastNameEdinText: EditText
    private lateinit var numberEdinText: EditText
    private lateinit var changeBtn: Button
    private lateinit var okBtn: Button
    private lateinit var personContact: PersonContact
    private lateinit var onClickBottomChange: OnClickBottomChange
    private lateinit var imageView: ImageView

    private var index = 0

    companion object {
        const val TAG = "ITEM_FRAGMENT"
        fun newInstance(personContact: PersonContact, index: Int) =
            ItemFragment().apply {
                this.personContact = personContact
                this.index = index
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickBottomChange = context as OnClickBottomChange
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            personContact = arguments?.getParcelable(ListFragment.LIST_TAG)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)

        nameTextView.text = personContact.name
        lastNameTextView.text = personContact.lastName
        numberTextView.text = personContact.number
        nameEdinText.setText(personContact.name)
        lastNameEdinText.setText(personContact.lastName)
        numberEdinText.setText(personContact.number)
        loadPage(personContact.image, imageView)

        changeBtn.setOnClickListener {
            nameTextView.visibility = View.GONE
            lastNameTextView.visibility = View.GONE
            numberTextView.visibility = View.GONE
            changeBtn.visibility = View.GONE
            nameEdinText.visibility = View.VISIBLE
            lastNameEdinText.visibility = View.VISIBLE
            numberEdinText.visibility = View.VISIBLE
            okBtn.visibility = View.VISIBLE

            okBtn.setOnClickListener {
                nameTextView.visibility = View.VISIBLE
                lastNameTextView.visibility = View.VISIBLE
                numberTextView.visibility = View.VISIBLE
                changeBtn.visibility = View.VISIBLE
                nameEdinText.visibility = View.GONE
                lastNameEdinText.visibility = View.GONE
                numberEdinText.visibility = View.GONE
                okBtn.visibility = View.GONE

                val imageContact = personContact.image
                val newName = nameEdinText.text.toString()
                val newLastName = lastNameEdinText.text.toString()
                val newNumber = numberEdinText.text.toString()

                personContact.name = newName
                personContact.lastName = newLastName
                personContact.number = newNumber
                personContact.image = imageContact

                nameTextView.text = newName
                lastNameTextView.text = newLastName
                numberTextView.text = newNumber
                loadPage(imageContact, imageView)

                ListFragment.contactList[index].name = newName
                ListFragment.contactList[index].lastName = newLastName
                ListFragment.contactList[index].number = newNumber
                ListFragment.contactList[index].image = imageContact

                onClickBottomChange.onClickedBottomChange(ListFragment.contactList[index])
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().findViewById<FrameLayout>(R.id.listContainer).visibility = View.VISIBLE
    }

    private fun initView(view: View) {
        nameTextView = view.findViewById(R.id.nameTextView)
        lastNameTextView = view.findViewById(R.id.lastNameTextView)
        numberTextView = view.findViewById(R.id.numberTextView)
        nameEdinText = view.findViewById(R.id.nameEditText)
        lastNameEdinText = view.findViewById(R.id.lastNameEditText)
        numberEdinText = view.findViewById(R.id.numberEditText)
        changeBtn = view.findViewById(R.id.changeBtn)
        okBtn = view.findViewById(R.id.okBtn)
        imageView = view.findViewById(R.id.imageView)
    }

    private fun loadPage(page: String, imageView: ImageView) {
        Picasso.get()
            .load(page)
            .into(imageView, object : Callback, com.squareup.picasso.Callback {
                override fun onSuccess() {
                    Log.d(TAG, resources.getString(R.string.success))
                }

                override fun onError(e: Exception?) {
                    Log.d(TAG, resources.getString(R.string.error))
                }
            })
    }

    interface OnClickBottomChange {
        fun onClickedBottomChange(personContact: PersonContact)
    }
}