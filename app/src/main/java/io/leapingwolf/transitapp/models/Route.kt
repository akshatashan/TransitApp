package io.leapingwolf.transitapp.models

import io.mironov.smuggler.AutoParcelable
import java.util.*

/**
 * Created by akshata on 12/10/16.
 */

data class Route(
        var type: String?,
        var provider: String?,
        var segments: ArrayList<Segment>?,
        var properties: Property?,
        var price: Price?) : AutoParcelable{
}