package io.leapingwolf.transitapp.adapters

/**
 * child of expandable recycler view
 */


import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import io.leapingwolf.transitapp.databinding.ViewStopsBinding
import io.leapingwolf.transitapp.models.Stop
import io.leapingwolf.transitapp.BR
import io.leapingwolf.transitapp.viewmodels.StopViewModel
import java.text.SimpleDateFormat

class StopViewHolder(val viewDataBinding: ViewStopsBinding) : ChildViewHolder<Stop>(viewDataBinding.root) {

    init {
        this.viewDataBinding.executePendingBindings()
    }

    fun bind(stop: Stop) {
        viewDataBinding.setVariable(BR.handler, StopViewModel(stop))
    }

    fun getTime(strDate: String): String{
        val inDtFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outDtFormat = SimpleDateFormat("HH:mm")
        var date = inDtFormat.parse(strDate)
        val outputDate = outDtFormat.format(date)
        return outputDate
    }
}
