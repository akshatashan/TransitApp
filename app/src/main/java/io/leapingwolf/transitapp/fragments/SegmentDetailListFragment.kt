package io.leapingwolf.transitapp.fragments

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.tool.Binding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.CarouselViewPager
import com.synnapps.carouselview.ViewListener
import io.leapingwolf.transitapp.BR
import io.leapingwolf.transitapp.R
import io.leapingwolf.transitapp.Helper
import io.leapingwolf.transitapp.activities.SegmentDetailActivity
import io.leapingwolf.transitapp.adapters.ExpandableSegmentDetailRecyclerAdapter
import io.leapingwolf.transitapp.adapters.SegmentRecyclerViewAdapter
import io.leapingwolf.transitapp.databinding.FragmentRouteDetailBinding
import io.leapingwolf.transitapp.models.Attributes
import io.leapingwolf.transitapp.models.Route
import io.leapingwolf.transitapp.viewmodels.RouteViewModel
import kotlinx.android.synthetic.main.fragment_route_detail.view.*
import kotlinx.android.synthetic.main.view_routes.view.*
import java.util.*


/**
 *
 */
/** displays the segment and stop details in a custom carousel view
 * Inflatiion hierarchy -  fragment_carouselview_route - contains the custom carousel view (list of view_routes)
 *                          fragment_route_detail - viewpager for each route (single route)
 *                                                              - inflates view_segments (list of segments)
 *                                                              - inflates fragment_segment_detail (segment summary)
 *                                                                                  - inflated view_stops (stops in segment)*/
class SegmentDetailListFragment: Fragment() {

    var routeCount: Int=0
    lateinit var hashMapAttributes: HashMap<String, Attributes>
    private var mDataPassing: MapSegmentInterface? = null
    private var position: Int =0
    lateinit  var customCarouselView: CarouselView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        if (arguments != null) {
            routeCount = arguments.getInt(SegmentDetailActivity.ROUTE_COUNT)
            hashMapAttributes = arguments.getSerializable(SegmentDetailActivity.PROVIDER_ATTRIBUTES) as HashMap<String, Attributes>
            position = arguments.getInt(SegmentDetailActivity.POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater!!.inflate(R.layout.fragment_carouselview_route, container, false)
        customCarouselView = view.findViewById(R.id.customCarouselView) as CarouselView

        customCarouselView.pageCount = routeCount
        customCarouselView.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int){
                mDataPassing!!.setCurrentPosition(position)
            }
        })

        customCarouselView.setViewListener(object: ViewListener {
            override fun setViewForPosition(position: Int): View {
                var binding = DataBindingUtil.inflate <FragmentRouteDetailBinding>(LayoutInflater.from(container?.context), R.layout.fragment_route_detail,container,false)
                return binding.root
            }
        })
        return view
    }

    fun updateSegmentDetailList(routeViewModel: RouteViewModel,currentPosition: Int, currentRoute: Route){
        var carouselViewPager = customCarouselView.getChildAt(0) as CarouselViewPager
        var routeDetail = carouselViewPager.getChildAt(currentPosition)
        //get the corresponding binding and update the route detail
        var binding = DataBindingUtil.getBinding<FragmentRouteDetailBinding>(routeDetail)
        binding.setVariable(BR.handler, routeViewModel)

        //bind the segments to its route
        val segmentRecyclerView = binding.root.recyclerViewSegmentHorizontal
        segmentRecyclerView.layoutManager = LinearLayoutManager(routeDetail?.context, LinearLayoutManager.HORIZONTAL, false)
        segmentRecyclerView.adapter = SegmentRecyclerViewAdapter(currentRoute,routeDetail?.context!!)

        //bind the segment to its stops
        val stopsRecyclerView = binding.root.segmentDetailRecyclerView
        stopsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        if(currentRoute.segments != null) {
            stopsRecyclerView.adapter = ExpandableSegmentDetailRecyclerAdapter(activity, currentRoute.segments!!)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MapSegmentInterface) {
            mDataPassing = context as MapSegmentInterface?
        } else {
            throw RuntimeException(context!!.toString() + " must implement MapSegmentInterface")
        }
    }



    override fun onDetach() {
        super.onDetach()
        mDataPassing = null
    }

    companion object {
        fun newInstance(position: Int, routeCount : Int, hashMapAttributes: HashMap<String, Attributes>): SegmentDetailListFragment {
            val fragment = SegmentDetailListFragment()
            val args = Bundle()
            args.putInt(SegmentDetailActivity.ROUTE_COUNT, routeCount)
            args.putSerializable(SegmentDetailActivity.PROVIDER_ATTRIBUTES, hashMapAttributes)
            args.putInt(SegmentDetailActivity.POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor

interface MapSegmentInterface {
    fun setCurrentPosition(position: Int)
}
