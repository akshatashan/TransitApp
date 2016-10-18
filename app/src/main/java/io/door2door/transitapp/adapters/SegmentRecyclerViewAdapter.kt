package io.door2door.transitapp.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.door2door.transitapp.R
import io.door2door.transitapp.Utils
import io.door2door.transitapp.databinding.RecyclerviewSegmentHorizontalBinding
import io.door2door.transitapp.models.Route

class SegmentRecyclerViewAdapter(private val mRoute: Route?) : RecyclerView.Adapter<io.door2door.transitapp.adapters.SegmentRecyclerViewAdapter.SegmentHolder>() {

    override fun getItemCount(): Int {
        return if (null != mRoute!!.segments) mRoute!!.segments!!.count() else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SegmentHolder {
        val binding = DataBindingUtil.inflate<RecyclerviewSegmentHorizontalBinding>(LayoutInflater.from(parent!!.context), R.layout.recyclerview_segment_horizontal, parent, false)
        return SegmentHolder(binding)
    }

    override fun onBindViewHolder(holder: SegmentHolder?, position: Int) {
        val viewDataBinding = holder!!.viewDataBinding
        viewDataBinding.setVariable(io.door2door.transitapp.BR.segment, mRoute!!.segments!![position])
        viewDataBinding.setVariable(io.door2door.transitapp.BR.handler, this)
    }

    fun setCardBackGroundColor(color: String?): Int{
        return Utils.setCardBackGroundColor(color)
    }

    class SegmentHolder(val viewDataBinding: RecyclerviewSegmentHorizontalBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root), View.OnClickListener  {
        var listener: SegmentViewHolder? = null
        override fun onClick(p0: View?) {

        }

        init {
            this.viewDataBinding.executePendingBindings()
        }
    }
}

