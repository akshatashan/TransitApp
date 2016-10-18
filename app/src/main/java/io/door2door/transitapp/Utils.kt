package io.door2door.transitapp

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.util.SparseArray
import android.widget.ImageView
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import io.door2door.transitapp.models.Attributes
import io.door2door.transitapp.models.Price
import io.door2door.transitapp.models.Segment
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * databinding methods
 */

class Utils {
    object ImageCache {
        var sparseArrayIcons: SparseArray<Bitmap>
        init {
            sparseArrayIcons = SparseArray<Bitmap>()
        }
    }

    companion object {
        val TAG = "Utils"

        fun svgToBitmap(view: ImageView, size: Int, url: String) {
            if (Utils.ImageCache.sparseArrayIcons.indexOfKey(url.hashCode()) > -1) {
                view.setImageBitmap(ImageCache.sparseArrayIcons.get(url.hashCode()))
            }
            else{
                var size = (size * view.context.resources.displayMetrics.density.toInt())
            async() {
                var bmp: Bitmap? = null
//                try {
                    val svg = readSvg(url)
                    bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bmp)
                    svg!!.renderToCanvas(canvas)
                    ImageCache.sparseArrayIcons.put(url.hashCode(), bmp)
                uiThread {
                    view.setImageBitmap(ImageCache.sparseArrayIcons.get(url.hashCode()))
                    }
                }
            }
        }

        fun readSvg(url: String): SVG? {
            var svg: SVG? = null
            try {
                val url = URL(url)
                val urlConnection = url.openConnection() as HttpURLConnection
                val inputStream = urlConnection.inputStream
                svg = com.caverock.androidsvg.SVG.getFromInputStream(inputStream)
                return svg
            } catch (ex: SVGParseException) {
                Log.d(TAG, ex.cause.toString())
            }
            return null
        }

        fun setDuration(mContext: Context, lastDt: String, firstDt: String): String {
            var strDuration: String = ""
            val dtFirstStopDate = convert(firstDt)
            val dtLastStopDate = convert(lastDt)
            if (dtFirstStopDate != null && dtLastStopDate != null) {
                val lngDtDiff = (dtLastStopDate!!.time - dtFirstStopDate!!.time) / 60 / 1000
                strDuration = lngDtDiff.toString()
            }
            return strDuration + mContext.resources.getString(R.string.minutes)
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

        fun setCardBackGroundColor(color: String?) : Int{
            return Color.parseColor(color)
        }
    }
}

@BindingAdapter("bind:url")
fun loadImage(view: ImageView, url: String?) {
    if(url!=null) {
        Utils.svgToBitmap(view, 20, url!!)
    }
}