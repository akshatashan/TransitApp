package io.leapingwolf.transitapp.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.leapingwolf.transitapp.R
import io.leapingwolf.transitapp.databinding.ViewSegmentsBinding
import io.leapingwolf.transitapp.models.Route
import io.leapingwolf.transitapp.viewmodels.SegmentViewModel

class SegmentRecyclerViewAdapter(private val mRoute: Route?, val mContext: Context) : RecyclerView.Adapter<io.leapingwolf.transitapp.adapters.SegmentRecyclerViewAdapter.SegmentHolder>() {

    override fun getItemCount(): Int {
        return if (null != mRoute?.segments) mRoute?.segments!!.count() else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SegmentHolder {
        val binding = DataBindingUtil.inflate<ViewSegmentsBinding>(LayoutInflater.from(parent!!.context), R.layout.view_segments, parent, false)
        return SegmentHolder(binding)
    }

    override fun onBindViewHolder(holder: SegmentHolder?, position: Int) {
        val viewDataBinding = holder!!.viewDataBinding
        viewDataBinding.setVariable(io.leapingwolf.transitapp.BR.handler, SegmentViewModel(mRoute?.segments!![position],mContext))
    }


    class SegmentHolder(val viewDataBinding: ViewSegmentsBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root){
        init {
            this.viewDataBinding.executePendingBindings()
        }
    }
}

