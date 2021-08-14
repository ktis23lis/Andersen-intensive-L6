package com.example.testlistcontact

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import com.example.testlistcontact.ui.ItemFragment
import com.example.testlistcontact.ui.ListFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), ListFragment.ItemSelected,
    ItemFragment.OnClickBottomChange {

    private lateinit var listFrameLayout: FrameLayout
    private lateinit var contactFrameLayout: FrameLayout
    var isLandscape: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        isLandscape = (resources.configuration.orientation
                == Configuration.ORIENTATION_LANDSCAPE)

        if (savedInstanceState == null) {
            if (isLandscape) {
                initListFragment()
            } else {
                contactFrameLayout.visibility = View.GONE
                initListFragment()
            }
        }
    }

    override fun onItemSelected(
        index: Int,
        personContact: PersonContact
    ) {
        if (isLandscape) {
            contactFrameLayout.visibility = View.VISIBLE

            val bundle = Bundle()
            bundle.putParcelable(ListFragment.LIST_TAG, personContact)

            supportFragmentManager.beginTransaction().run {
                val itemFragment = ItemFragment.newInstance(
                    personContact, index
                )
                itemFragment.arguments = bundle
                replace(R.id.contactContainer, itemFragment)
                commit()
            }
        } else {
            contactFrameLayout.visibility = View.VISIBLE
            listFrameLayout.visibility = View.GONE
            val bundle = Bundle()
            bundle.putParcelable(ListFragment.LIST_TAG, personContact)

            supportFragmentManager.beginTransaction().run {
                val itemFragment = ItemFragment.newInstance(
                    personContact, index
                )
                itemFragment.arguments = bundle
                replace(R.id.contactContainer, itemFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onClickedBottomChange(personContact: PersonContact) {
        replaceListFragment(personContact)
    }

    private fun initView() {
        contactFrameLayout = findViewById(R.id.contactContainer)
        listFrameLayout = findViewById(R.id.listContainer)
    }

    private fun initListFragment() {
        supportFragmentManager.beginTransaction().run {
            val listFragment = ListFragment.newInstance()
            replace(R.id.listContainer, listFragment)
            commit()
        }
    }

    private fun replaceListFragment(personContact: PersonContact) {
        val bundle = Bundle()
        bundle.putParcelable(ListFragment.LIST_TAG, personContact)
        supportFragmentManager.beginTransaction().run {
            val listFragment = ListFragment.newInstance()
            listFragment.arguments = bundle
            replace(R.id.listContainer, listFragment)
            commit()
        }
    }

}