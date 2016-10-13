package io.door2door.transitapp

import android.content.Context
import android.util.Log
import io.door2door.transitapp.models.Attributes
import io.door2door.transitapp.models.Price
import io.door2door.transitapp.models.Segment
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * databinding methods
 */

class Utils {

    companion object {
        val TAG = "Utils"

        fun setDuration(mContext: Context, lastDt: String, firstDt: String): String {
            var strDuration: String = ""
            val dtFirstStopDate = convert(firstDt)
            val dtLastStopDate = convert(lastDt)
            if (dtFirstStopDate != null && dtLastStopDate != null) {
                val lngDtDiff = (dtLastStopDate!!.time - dtFirstStopDate!!.time) / 60 / 1000
                strDuration = lngDtDiff.toString()
            }
            return strDuration
        }

        fun convert(strDate: String): Date? {
            var date: Date? = null
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                date = dateFormat.parse(strDate)
            } catch (ex: ParseException) {
                Log.d(TAG, ex.message.toString())
            }
            return date
        }

        fun setTime(strDate: String): String {
            val inDtFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val outDtFormat = SimpleDateFormat("HH:mm")
            var date = inDtFormat.parse(strDate)
            val outputDate = outDtFormat.format(date)
            return outputDate
        }

        fun setSegmentLayoutParams(segmentList: ArrayList<Segment>?): String {
            val firstSegmentFirstStop = segmentList!!.first().stops.first()
            val lastSegmentLastStop = segmentList!!.last().stops.last()
            return (setTime(firstSegmentFirstStop.datetime!!) + " -> " + setTime(lastSegmentLastStop.datetime!!))
        }

        fun setProviderDisplayName(provider: String?, hashMapAttributes: HashMap<String, Attributes>): String? {
            if (hashMapAttributes[provider]!!.display_name == null) return ""
            return hashMapAttributes[provider]!!.display_name.toString()
        }

        fun setPrice(context: Context, price: Price?): String {
            if (price != null) {
                val amount = ((price.amount.toDouble() / 100).toDouble())
                val outFormat = DecimalFormat("##.00")
                return context.resources.getString(R.string.euro) + outFormat.format(amount)
            } else return ""
        }

        fun setDuration(mContext: Context, segmentList: ArrayList<Segment>?): String {
            val firstSegmentFirstStop = segmentList!!.first().stops.first()
            val lastSegmentLastStop = segmentList!!.last().stops.last()
            return setDuration(mContext, lastSegmentLastStop.datetime!!, firstSegmentFirstStop.datetime!!)
        }
    }
}