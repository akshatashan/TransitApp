package io.leapingwolf.transitapp.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.graphics.Color
import android.widget.ImageView
import io.leapingwolf.transitapp.R
import io.leapingwolf.transitapp.Helper
import io.leapingwolf.transitapp.models.Segment
import java.util.*

/**
 * Created by akshata on 23/02/2017.
 */


class SegmentViewModel(segment : Segment,ctx: Context) : BaseObservable() {
    var mSegment: Segment = segment
    var mContext: Context = ctx

    fun getCardBackGroundColor(): Int {
        return Helper.setCardBackGroundColor(mSegment.color)
    }

    fun getName(): String? {
        return mSegment.name
    }

    fun getIconUrl(): String? {
        return mSegment.icon_url
    }

    fun getDescription(): String?{
        return mSegment.description ?: mContext.resources.getString(R.string.no_display_name)
    }

    fun getStart(): String? {
        //segment name
        var strName: String
        if (mSegment.stops[0].name != null) {
            if (mSegment.name != null) strName = mSegment.name + " " + mSegment.stops[0].name!!.toString()
            else strName = mSegment.stops[0].name!!.toString()
        } else strName = mSegment.stops[0].lat!!.toString() + "," + mSegment.stops[0].lng!!.toString()
        return strName
    }

    fun getColor(): Int {
        return Color.parseColor(mSegment.color)
    }

    fun showHideArrow(): Int {
        if (mSegment.num_stops > 1) return 1
        else return 0
    }

    fun getTravelMode(): String? {
        return mSegment.travel_mode ?: ""
    }

    fun getTime(): String {
        return Helper.setTime(mSegment.stops[0].datetime.toString())
    }

    fun getDuration(): String {
        var stopsCount = mSegment.stops.count()
        if (stopsCount > 0) {
            val firstStopDt = mSegment.stops.first().datetime!!
            val lastStopDt = mSegment.stops.last().datetime!!
            return Helper.setDuration(mContext, lastStopDt, firstStopDt)
        } else return ""
    }

    fun getNumStops(): String? {
        if (mSegment.num_stops > 0)
            return mSegment.num_stops.toString() + mContext.resources.getString(R.string.stops)
        else
            return mContext.resources.getString(R.string.no_display_name)
    }
}

@BindingAdapter("bind:url")
fun loadImage(view: ImageView, url: String?) {
    if (url != null) {
        Helper.svgToBitmap(view, 20, url)
    }
}