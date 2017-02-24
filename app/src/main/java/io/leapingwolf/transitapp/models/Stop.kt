package io.leapingwolf.transitapp.models

import io.mironov.smuggler.AutoParcelable

/**
 * Created by akshata on 12/10/16.
 */


data class Stop(
    val lat:Double?,
    val lng: Double?,
    val datetime: String?,
    val name:String?,
    val properties: String?
    ): AutoParcelable{

}