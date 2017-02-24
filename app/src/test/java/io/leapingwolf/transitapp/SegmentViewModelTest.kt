package io.leapingwolf.transitapp

import android.content.Context
import io.leapingwolf.transitapp.viewmodels.SegmentViewModel
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
class SegmentViewModelTest{
    var  mContext: Context? = null

    fun setup() : SegmentViewModel {
        mContext = RuntimeEnvironment.application
        val route = MockModelUtil.createMockRoute("vbb","public transport",null)
        val segmentViewModel = SegmentViewModel(route.segments!!.first(), mContext!!)
        return segmentViewModel
    }

    @Test
    fun shouldGetSegmentStart(){
        val segmentViewModel = setup()
        val actual = segmentViewModel.getStart()
        assertEquals("Torstraße 103, 10119 Berlin, Deutschland",actual)
    }

    @Test
    fun shouldGetTime(){
        val segmentViewModel = setup()
        val actual = segmentViewModel.getTime()
        assertEquals("12:29",actual)
    }

    @Test
    fun shouldGetDuration(){
        val segmentViewModel = setup()
        val actual = segmentViewModel.getDuration()
        assertEquals("17mins",actual)

    }

    @Test
    fun shouldGetNumStops(){
        val segmentViewModel = setup()
        val actual = segmentViewModel.getNumStops()
        assertEquals("2stops",actual)

    }

}