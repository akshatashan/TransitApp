package io.leapingwolf.transitapp.network

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import io.leapingwolf.transitapp.models.Response

/**
 * Created by sam on 2/25/17.
 */


class ResponseService {
    companion object{
        fun get(url: String) : Result<Response, FuelError> {
            val result = Fuel.get(url).responseObject(Response.Deserializer()).third
            return result
        }
    }
}