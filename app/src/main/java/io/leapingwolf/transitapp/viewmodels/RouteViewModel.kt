package io.leapingwolf.transitapp.viewmodels

import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.util.Log
import android.view.View
import io.leapingwolf.transitapp.Helper
import io.leapingwolf.transitapp.activities.SegmentDetailActivity
import io.leapingwolf.transitapp.models.Attributes
import io.leapingwolf.transitapp.models.Route
import java.util.*

/**
 * Created by akshata on 23/02/2017.
 */
class RouteViewModel(route: Route, ctx: Context,  mRouteList: ArrayList<Route>?, mProviderAttributes:  HashMap<String, Attributes>) : BaseObservable() {
    var mRoute : Route = route
    val mContext: Context = ctx
    var mRouteList: ArrayList<Route>? = mRouteList
    var mProviderAttributes:  HashMap<String, Attributes> = mProviderAttributes

    fun getProvider(): String {
        if(mProviderAttributes[mRoute.provider]?.display_name !=null) {
            return mProviderAttributes[mRoute.provider]?.display_name.toString()
        }
        return ""
    }

    fun onCardViewClicked() : View.OnClickListener{
        return View.OnClickListener {
            Log.d("TAG", "start seg activity")
            if(mRouteList!=null) {
                val intent = Intent(mContext, SegmentDetailActivity::class.java)
                intent.putExtra(SegmentDetailActivity.ROUTE_LIST, mRouteList)
                intent.putExtra(SegmentDetailActivity.POSITION, mRouteList!!.indexOf(mRoute))
                intent.putExtra(SegmentDetailActivity.ROUTE, mRoute)
                intent.putExtra(SegmentDetailActivity.PROVIDER_ATTRIBUTES, mProviderAttributes)
                mContext.startActivity(intent)
            }
        }
    }
//
    fun getSegmentDetailLayout() : String{
        return Helper.setSegmentLayoutParams(mRoute.segments)
    }

    fun getPrice(): String {
        if(mRoute.price != null) return Helper.setPrice(mContext,mRoute.price)
        else return ""
    }

    fun getDuration(): String {
        return (Helper.setDuration(mContext,mRoute.segments))
    }
}