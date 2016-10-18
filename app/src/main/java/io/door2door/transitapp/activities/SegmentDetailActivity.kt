package io.door2door.transitapp.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import io.door2door.transitapp.R
import io.door2door.transitapp.fragments.MapSegmentInterface
import io.door2door.transitapp.fragments.SegmentDetailListFragment
import io.door2door.transitapp.models.Attributes
import io.door2door.transitapp.models.Route
import java.util.*

/** displays the sliding panel to show SegmentDetailListFragment and Google MapFragment
 * inflates activity_segment_detail which includes content_segment_detail */

class SegmentDetailActivity : AppCompatActivity(), MapSegmentInterface {
    var mCurrentSegment: Int? = null
    private var mLayout: SlidingUpPanelLayout? = null
    private var mMap: GoogleMap? = null
    lateinit var lstRoutes: ArrayList<Route>
    lateinit var hashMapAttributes: HashMap<String, Attributes>
    private var currentPosition: Int = 0
    private var routeCount: Int = 0
    lateinit var segmentCarouselFragment: SegmentDetailListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segment_detail)

        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val intent = intent
        lstRoutes = intent.extras.get(SegmentDetailActivity.ROUTE_LIST) as ArrayList<Route>
        hashMapAttributes = intent.extras.get(SegmentDetailActivity.PROVIDER_ATTRIBUTES) as HashMap<String, Attributes>
        currentPosition = intent.extras.get(SegmentDetailActivity.POSITION) as Int
//        val currentRoute = intent.extras.get(SegmentDetailActivity.ROUTE) as Route


        val mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.mapContainer, mapFragment).commit()
        mapFragment.getMapAsync{p0 ->
            mMap = p0
            if(segmentCarouselFragment!=null){
                updateFragments(currentPosition)
            }
        }

        segmentCarouselFragment = SegmentDetailListFragment.newInstance(currentPosition,  lstRoutes.size, hashMapAttributes)
        supportFragmentManager.beginTransaction().replace(R.id.segmentCarouselContainer, segmentCarouselFragment).commit()

        mLayout = findViewById(R.id.sliding_layout) as SlidingUpPanelLayout
        mLayout!!.addPanelSlideListener(object : PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset)
            }

            override fun onPanelStateChanged(panel: View, previousState: PanelState, newState: PanelState) {
                Log.i(TAG, "onPanelStateChanged " + newState)
            }
        })
        mLayout!!.setFadeOnClickListener { mLayout!!.panelState = PanelState.COLLAPSED }
    }

    override fun setCurrentPosition(position: Int) {
        currentPosition = position
        if(mMap!=null){
            updateFragments(currentPosition)
        }
    }

    private fun  updateFragments(currentPosition: Int) {
        var decodedPath: List<LatLng>? = null
        var strColor: String?
        mMap!!.clear()
        lstRoutes[currentPosition]!!.segments!!.forEach { i ->
            if(i.polyline != null) {
                decodedPath = PolyUtil.decode(i.polyline!!)
                if(i.color == null) strColor = "#000000"
                else strColor = i.color

                mMap!!.addPolyline(PolylineOptions().addAll(decodedPath).color(Color.parseColor(strColor)).width(18f))
                mMap!!.addMarker(MarkerOptions().position(LatLng(i.stops.first().lat!!, i.stops.first().lng!!)).title(i.stops.first().name))
                mMap!!.addMarker(MarkerOptions().position(LatLng(i.stops.last().lat!!, i.stops.last().lng!!)).title(i.stops.last().name))
            }
        }
        if(decodedPath!=null && decodedPath!!.count() > 0) {
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(decodedPath!!.first(), 13.0f))
        }

        //bind the segment list details to the carousel view page for each route
        segmentCarouselFragment.updateSegmentDetailList(lstRoutes[currentPosition],currentPosition)
        segmentCarouselFragment.customCarouselView.setCurrentItem(currentPosition)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = "SegmentDetailActivity"
        val ROUTE_LIST = "route_list"
        val ROUTE_COUNT = "route_count"
        val ROUTE = "route"
        val POSITION = "position"
        val PROVIDER_ATTRIBUTES= "provider_attributes"
    }
}
