package com.example.testlistcontact

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonContact(
        var lastName: String,
        var name: String,
        var number: String,
        var image: String
) : Parcelable