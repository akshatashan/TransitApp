package io.door2door.transitapp.fragments

import android.content.Context
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
import io.door2door.transitapp.R
import io.door2door.transitapp.Utils
import io.door2door.transitapp.activities.SegmentDetailActivity
import io.door2door.transitapp.adapters.SegmentExpandableRecyclerAdapter
import io.door2door.transitapp.adapters.SegmentRecyclerViewAdapter
import io.door2door.transitapp.models.Attributes
import io.door2door.transitapp.models.Route
import java.util.*


/**
 *
 */
/** displays the segment and stop details in a custom carousel view
 * Inflatiion hierarchy -  carouselview_segment_custom - contains the custom carousel view (list of routes)
 *                          carouselview_segment_detail - viewpager for each route (single route)
 *                                                              - inflates recyclerview_segment_horizontal (list of segments)
 *                                                              - inflates expandable_recyclerview_segment (segment summary)
 *                                                                                  - inflated childview_stop (segment detail)*/
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

        val view = inflater!!.inflate(R.layout.carouselview_segment_custom, container, false)
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
                val customView = inflater!!.inflate(R.layout.carouselview_segment_detail, null)
                return customView
            }
        })
        return view
    }

    private fun  updateView(customView: View, currentRoute: Route) {
        val txtProvider = customView.findViewById(R.id.txtProvider) as TextView
        txtProvider.text = Utils.setProviderDisplayName(currentRoute.provider, hashMapAttributes)

        val cardView = customView.findViewById(R.id.cardView) as CardView
        val txtPrice = cardView.findViewById(R.id.txtPrice) as TextView

        txtPrice.text = Utils.setPrice(activity, currentRoute.price)

        val txtSegmentDetailLayout = cardView.findViewById(R.id.txtSegmentDetailLayout) as TextView
        txtSegmentDetailLayout.text = Utils.setSegmentLayoutParams(currentRoute.segments)

        val txtSegmentDuration =  cardView.findViewById(R.id.txtSegmentDuration) as TextView
        txtSegmentDuration.text = Utils.setDuration(activity,currentRoute.segments)

        //bind the segment and its stops
        val recyclerView = customView.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val mAdapter = SegmentExpandableRecyclerAdapter(activity, currentRoute.segments!!)
        recyclerView.adapter = mAdapter

        val recyclerViewSegmentHorizontal = cardView.findViewById(R.id.recyclerViewSegmentHorizontal) as RecyclerView
        recyclerViewSegmentHorizontal.setHasFixedSize(true)
        recyclerViewSegmentHorizontal.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val segmentRecyclerViewAdapter = SegmentRecyclerViewAdapter(currentRoute)
        recyclerViewSegmentHorizontal.adapter = segmentRecyclerViewAdapter
    }

    fun updateSegmentDetailList(currentRoute: Route, currentPosition: Int){
        val carouselViewPager = customCarouselView.getChildAt(0) as CarouselViewPager
        val segmentDetail = carouselViewPager.getChildAt(currentPosition)
        if(segmentDetail != null) {
            updateView(segmentDetail, currentRoute)
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
