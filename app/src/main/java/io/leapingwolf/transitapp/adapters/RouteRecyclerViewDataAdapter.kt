package io.leapingwolf.transitapp.adapters

/**
 * displays each route
 * inflates view_routes and binds segments to each route
 *
 *
 */


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.leapingwolf.transitapp.BR
import io.leapingwolf.transitapp.R
import io.leapingwolf.transitapp.Helper
import io.leapingwolf.transitapp.activities.SegmentDetailActivity
import io.leapingwolf.transitapp.databinding.ViewRoutesBinding
import io.leapingwolf.transitapp.models.Attributes
import io.leapingwolf.transitapp.models.Route
import io.leapingwolf.transitapp.models.Segment
import io.leapingwolf.transitapp.viewmodels.RouteViewModel
import java.util.*

class RouteRecyclerViewDataAdapter(private val mContext: Context,
                                   private val mRouteList: ArrayList<Route>?,
                                   private val mProviderAttributes:  HashMap<String, Attributes>) : RecyclerView.Adapter<RouteRecyclerViewDataAdapter.RouteHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RouteHolder {
        val binding = DataBindingUtil.inflate<ViewRoutesBinding>(LayoutInflater.from(viewGroup.context), R.layout.view_routes, viewGroup, false)
        return RouteHolder(binding)
    }

    override fun onBindViewHolder(customViewHolder: RouteHolder, i: Int) {
        val viewDataBinding = customViewHolder.viewDataBinding
//        viewDataBinding.setVariable(BR.route, mRouteList!![i])
        viewDataBinding.setVariable(BR.handler, RouteViewModel(mRouteList!![i],mContext,mRouteList,mProviderAttributes))
        val segmentRecyclerViewAdapter = SegmentRecyclerViewAdapter(mRouteList[i], mContext)
        viewDataBinding.recyclerViewSegmentHorizontal.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.recyclerViewSegmentHorizontal.adapter = segmentRecyclerViewAdapter

    }


    override fun getItemCount(): Int {
        return if (null != mRouteList) mRouteList.size else 0
    }


    class RouteHolder(val viewDataBinding: ViewRoutesBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

        init {
            this.viewDataBinding.executePendingBindings()
        }
    }
}

