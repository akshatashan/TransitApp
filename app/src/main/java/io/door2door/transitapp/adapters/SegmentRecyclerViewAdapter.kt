package io.door2door.transitapp.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import io.door2door.transitapp.R
import io.door2door.transitapp.Utils
import io.door2door.transitapp.databinding.RecyclerviewSegmentHorizontalBinding
import io.door2door.transitapp.models.Attributes
import io.door2door.transitapp.models.Route
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*


class SegmentRecyclerViewAdapter(private val mContext: Context,
                                 private val  mSparseArrayIcons: SparseArray<Bitmap>?,
                                 private val mRoute: Route?,
                                 private val mProviderAttributes: HashMap<String, Attributes>) : RecyclerView.Adapter<io.door2door.transitapp.adapters.SegmentRecyclerViewAdapter.SegmentHolder>() {
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
        val iconUrl = mRoute!!.segments!![position].icon_url
        val color = mRoute!!.segments!![position].color
        if (mSparseArrayIcons!!.indexOfKey(iconUrl!!.hashCode()) > -1) {
            viewDataBinding.imgTravelModeIcon.setImageBitmap(mSparseArrayIcons!!.get(iconUrl!!.hashCode()))
        } else {
            async() {
                val bmp = Utils.svgToBitmap(mContext, 40, iconUrl!!, Color.parseColor(color))
                uiThread {
                    viewDataBinding.imgTravelModeIcon.setImageBitmap(bmp)
                    if (mSparseArrayIcons!!.indexOfKey(iconUrl.hashCode()) < 0) {
                        //key does not exist so put it in
                        mSparseArrayIcons!!.put(iconUrl.hashCode(), bmp)
                    }
                }
            }
        }
    }

    class SegmentHolder(val viewDataBinding: RecyclerviewSegmentHorizontalBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            this.viewDataBinding.executePendingBindings()
        }
    }
}

