package io.leapingwolf.transitapp.viewmodels

import android.databinding.BaseObservable
import io.leapingwolf.transitapp.models.Stop
import java.text.SimpleDateFormat

/**
 * Created by akshata on 24/02/2017.
 */

class StopViewModel(stop: Stop) : BaseObservable(){
    var mStop : Stop = stop

    fun getTime(): String{
        val inDtFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outDtFormat = SimpleDateFormat("HH:mm")
        var date = inDtFormat.parse(mStop.datetime)
        val outputDate = outDtFormat.format(date)
        return outputDate
    }

    fun getName(): String{
        if(mStop.name != null) {
            return mStop.name!!
        }
        return ""
    }
}