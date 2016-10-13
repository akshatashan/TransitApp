package io.door2door.transitapp.models

import io.mironov.smuggler.AutoParcelable
import java.util.*

/**
 * Created by akshata on 12/10/16.
 */

data class Route(
        val type: String?,
        val provider: String?,
        val segments: ArrayList<Segment>?,
        val properties: Property?,
        val price: Price?) : AutoParcelable{
}