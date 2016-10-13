package io.door2door.transitapp.models

import io.mironov.smuggler.AutoParcelable

/**
 * Created by akshata on 07/10/16.
 */

data class Price(
    val currency: String?,
    val amount: Int) : AutoParcelable{
}