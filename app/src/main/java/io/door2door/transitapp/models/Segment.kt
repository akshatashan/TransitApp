package io.door2door.transitapp.models
import com.bignerdranch.expandablerecyclerview.model.Parent
import io.mironov.smuggler.AutoParcelable
import java.util.*

/**
 * Created by akshata on 12/10/16.
 */

data class Segment(
        val name: String?,
        val num_stops: Int,
        val stops: ArrayList<Stop>,
        val travel_mode: String?,
        val description: String?,
        val color: String?,
        val icon_url: String?,
        val polyline: String?) : AutoParcelable, Parent<Stop> {

    override fun getChildList(): MutableList<Stop> {
        var childStops = ArrayList<Stop>()
        if (num_stops > 0) childStops = stops
        return childStops
    }

    override fun isInitiallyExpanded(): Boolean {
        return false
    }
}
