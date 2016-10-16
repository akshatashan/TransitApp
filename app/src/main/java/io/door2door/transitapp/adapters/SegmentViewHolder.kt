package io.door2door.transitapp.adapters

import android.graphics.Color
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import io.door2door.transitapp.Utils
import io.door2door.transitapp.databinding.ExpandableRecyclerviewSegmentBinding
import io.door2door.transitapp.models.Segment
import io.door2door.transitapp.models.Stop
import java.util.*

/**
 * parent of expandable recycler view.
 */
class SegmentViewHolder(val viewDataBinding: ExpandableRecyclerviewSegmentBinding) : ParentViewHolder<Segment, Stop>(viewDataBinding.root) {

    init {
        this.viewDataBinding.executePendingBindings()
    }

    fun bind(segment: Segment) {
        viewDataBinding.setVariable(io.door2door.transitapp.BR.detail, segment)
        viewDataBinding.setVariable(io.door2door.transitapp.BR.handler, this)

    }

    fun setSegmentStart(segment: Segment): String{
        //segment name
        var strName: String = ""
            if (segment.stops[0].name != null) {
                if(segment.name != null) strName = segment.name + " " + segment.stops[0].name!!.toString()
                else  strName= segment.stops[0].name!!.toString()
            }
            else strName = segment.stops[0].lat!!.toString() + "," + segment.stops[0].lng!!.toString()
        return strName
    }

    fun showHideArrow(numStops: Int): Boolean{
        if(numStops > 1) return true
        else  return false
    }

    fun setColor(color: String): Int{
        return Color.parseColor(color)
    }

    fun setTime(strDate: String): String{
        return Utils.setTime(strDate)
    }

    fun setDuration(stops: ArrayList<Stop> ): String {
        var stopsCount = stops.count()
        if(stopsCount > 0){
            val firstStopDt = stops.first().datetime!!
            val lastStopDt = stops.last().datetime!!
            return Utils.setDuration(viewDataBinding.root.context,lastStopDt,firstStopDt)
        } else return ""
    }

    override fun onExpansionToggled(expanded: Boolean) {
        super.onExpansionToggled(expanded)
    }

}
