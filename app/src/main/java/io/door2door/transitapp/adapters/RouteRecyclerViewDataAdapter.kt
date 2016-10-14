package io.door2door.transitapp.adapters

/**
 * displays each route
 * inflates recyclerview_route_vertical and binds segments to each route
 *
 *
 */


import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.door2door.transitapp.BR
import io.door2door.transitapp.R
import io.door2door.transitapp.Utils
import io.door2door.transitapp.databinding.RecyclerviewRouteVerticalBinding
import io.door2door.transitapp.models.Attributes
import io.door2door.transitapp.models.Route
import io.door2door.transitapp.models.Segment
import java.util.*

class RouteRecyclerViewDataAdapter(private val mContext: Context,
                                   private val mSparseArrayIcons: SparseArray<Bitmap>,
                                   private val mRouteList: ArrayList<Route>?, private val mProviderAttributes:  HashMap<String, Attributes>) : RecyclerView.Adapter<RouteRecyclerViewDataAdapter.RouteHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RouteHolder {
        val binding = DataBindingUtil.inflate<RecyclerviewRouteVerticalBinding>(LayoutInflater.from(viewGroup.context), R.layout.recyclerview_route_vertical, viewGroup, false)
        return RouteHolder(binding)
    }

    override fun onBindViewHolder(customViewHolder: RouteHolder, i: Int) {
        val viewDataBinding = customViewHolder.viewDataBinding
        viewDataBinding.setVariable(BR.route, mRouteList!![i])
        viewDataBinding.setVariable(BR.handler, this)
        val segmentRecyclerViewAdapter = SegmentRecyclerViewAdapter(mContext, mSparseArrayIcons, mRouteList!![i], mProviderAttributes)
        viewDataBinding.recyclerViewSegmentHorizontal.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.recyclerViewSegmentHorizontal.adapter = segmentRecyclerViewAdapter

    }

    fun onCardViewClicked(v: View, route: Route) {
        //TODO: segment inflation
    }

    override fun getItemCount(): Int {
        return if (null != mRouteList) mRouteList.size else 0
    }

    fun setProviderDisplayName(provider: String?): String? {
        return Utils.setProviderDisplayName(provider,mProviderAttributes)
    }

    fun setSegmentDetailLayout(segmentList: ArrayList<Segment>?): String {
        return Utils.setSegmentLayoutParams(mContext,segmentList!!)
    }

    fun setPrice(route: Route): String {
        if(route.price != null) return Utils.setPrice(mContext,route.price!!)
        else return ""
    }

    fun setDuration(segmentList: ArrayList<Segment>?): String {
        return (Utils.setDuration(mContext,segmentList!!))
    }

    class RouteHolder(val viewDataBinding: RecyclerviewRouteVerticalBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            this.viewDataBinding.executePendingBindings()
        }
    }
}

