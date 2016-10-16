package io.door2door.transitapp.adapters

/**
 * child of expandable recycler view
 */


import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import io.door2door.transitapp.databinding.ChildviewStopBinding
import io.door2door.transitapp.models.Stop
import java.text.SimpleDateFormat

class StopViewHolder(val viewDataBinding: ChildviewStopBinding) : ChildViewHolder<Stop>(viewDataBinding.root) {

    init {
        this.viewDataBinding.executePendingBindings()
    }

    fun bind(stop: Stop) {
        viewDataBinding.setVariable(io.door2door.transitapp.BR.stop, stop)
        viewDataBinding.setVariable(io.door2door.transitapp.BR.handler, this)
    }

    fun getTime(strDate: String): String{
        val inDtFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outDtFormat = SimpleDateFormat("HH:mm")
        var date = inDtFormat.parse(strDate)
        val outputDate = outDtFormat.format(date)
        return outputDate
    }
}
