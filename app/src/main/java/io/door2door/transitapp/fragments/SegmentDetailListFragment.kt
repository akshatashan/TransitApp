package io.door2door.transitapp.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ViewListener
import io.door2door.transitapp.R
import io.door2door.transitapp.Utils
import io.door2door.transitapp.activities.SegmentDetailActivity
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
 */
class SegmentDetailListFragment: Fragment() {

    lateinit var lstRoutes: ArrayList<Route>
    lateinit var hashMapAttributes: HashMap<String, Attributes>
    private var mDataPassing: MapSegmentInterface? = null
    private var position: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        if (arguments != null) {
            lstRoutes = arguments.getParcelableArrayList(SegmentDetailActivity.ROUTE_LIST)
            hashMapAttributes = arguments.getSerializable(SegmentDetailActivity.PROVIDER_ATTRIBUTES) as HashMap<String, Attributes>
            position = arguments.getInt(SegmentDetailActivity.POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater!!.inflate(R.layout.carouselview_segment_custom, container, false)
        val customCarouselView = view.findViewById(R.id.customCarouselView) as CarouselView

        customCarouselView.pageCount = lstRoutes.size
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
                val currentRoute = lstRoutes[position]
                val customView = inflater!!.inflate(R.layout.carouselview_segment_detail, null)

                //bind the route with segment
                val txtProvider = customView.findViewById(R.id.txtProvider) as TextView
                txtProvider.text = Utils.setProviderDisplayName(currentRoute.provider, hashMapAttributes)

                val cardView = customView.findViewById(R.id.cardView) as CardView
                val txtPrice = cardView.findViewById(R.id.txtPrice) as TextView

                txtPrice.text = Utils.setPrice(activity, currentRoute.price)

                val txtSegmentDetailLayout = cardView.findViewById(R.id.txtSegmentDetailLayout) as TextView
                txtSegmentDetailLayout.text = Utils.setSegmentLayoutParams(currentRoute.segments)

                val txtSegmentDuration =  cardView.findViewById(R.id.txtSegmentDuration) as TextView
                txtSegmentDuration.text = Utils.setDuration(activity,currentRoute.segments)

                //TODO: bind the segment and its stops

                val recyclerViewSegmentHorizontal = cardView.findViewById(R.id.recyclerViewSegmentHorizontal) as RecyclerView
                recyclerViewSegmentHorizontal.setHasFixedSize(true)
                recyclerViewSegmentHorizontal.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                val sparseArrayIcons = SparseArray<Bitmap>()
                val segmentRecyclerViewAdapter = SegmentRecyclerViewAdapter(activity,sparseArrayIcons, lstRoutes[position], hashMapAttributes)
                recyclerViewSegmentHorizontal.adapter = segmentRecyclerViewAdapter

                return customView
            }
        })
        customCarouselView.setCurrentItem(position)
        return view
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
        fun newInstance(position: Int, lstRoute : ArrayList<Route>, hashMapAttributes: HashMap<String, Attributes>): SegmentDetailListFragment {
            val fragment = SegmentDetailListFragment()
            val args = Bundle()
            args.putParcelableArrayList(SegmentDetailActivity.ROUTE_LIST,lstRoute)
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
