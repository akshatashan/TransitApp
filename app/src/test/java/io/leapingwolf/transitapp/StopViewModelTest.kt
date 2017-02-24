package io.leapingwolf.transitapp

/**
 * Created by sam on 2/25/17.
 */


import android.content.Context
import io.leapingwolf.transitapp.viewmodels.StopViewModel
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
class StopViewModelTest{
    var  mContext: Context? = null

    fun setup() : StopViewModel {
        mContext = RuntimeEnvironment.application
        val route = MockModelUtil.createMockRoute("vbb","public transport",null)
        val stopViewModel = StopViewModel(route.segments!!.first().stops[1])
        return stopViewModel
    }

    @Test
    fun shouldGetTime(){
        val stopViewModel = setup()
        val actual = stopViewModel.getTime()
        assertEquals("12:46",actual)
    }

}