package io.leapingwolf.transitapp

import android.content.Context
import io.leapingwolf.transitapp.viewmodels.RouteViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by akshata on 25/02/2017.
 */

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class RouteViewModelTest {
    var  mContext: Context? = null

    fun setup() : RouteViewModel{
        mContext = RuntimeEnvironment.application
        val route = MockModelUtil.createMockRoute("vbb","public transport",null)
        val hashMapAttributes = MockModelUtil.createMockProviderAttribues("https://d3m2tfu2xpiope.cloudfront.net/providers/vbb.svg","Our data is as live and real-time as possible.", null)
        val routeViewModel = RouteViewModel(route,mContext!!,null,hashMapAttributes!!)
        return routeViewModel
    }

    @Test
    fun shouldGetProvider(){
        val routeViewModel = setup()
        val actual = routeViewModel.getProvider()
        assertEquals("",actual)
    }

    @Test
    fun shouldGetPrice(){
        val routeViewModel = setup()
        val actual = routeViewModel.getPrice()
        assertEquals("€2.70",actual)
    }

    @Test
    fun shouldGetSegmentDetailLayout(){
        val routeViewModel = setup()
        val actual = routeViewModel.getSegmentDetailLayout()
        assertEquals("12:29 -> 12:46",actual)
    }

    @Test
    fun shouldGetDuration(){
        val routeViewModel = setup()
        val actual = routeViewModel.getDuration()
        assertEquals("17mins",actual)
    }
}
