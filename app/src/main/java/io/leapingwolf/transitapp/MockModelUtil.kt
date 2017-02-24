package io.leapingwolf.transitapp

import io.leapingwolf.transitapp.models.*
import java.util.*

/**
 * Created by akshata on 25/02/2017.
 */
class MockModelUtil{
    companion object {
        fun createMockProviderAttribues(provider_icon_url: String, disclamer: String, display_name: String?): HashMap<String,Attributes>?{
            val hMap =  HashMap<String,Attributes>()
            hMap?.put("vbb", Attributes(provider_icon_url,disclamer,display_name))
            return hMap
        }

        private fun createMockPrice(currency: String, amount: Int): Price? {
            return Price(currency, amount)
        }

        fun createMockRoute(provider: String, type: String, properties: Property?): Route {
            val provider = provider
            val type = type
            val properties = properties
            val price = createMockPrice("EUR", 270)
            val segments = createMockSegment(null, 2, "sunway", "S+U Potsdamer Platz", "#d64820", "https://d3m2tfu2xpiope.cloudfront.net/vehicles/subway.svg", "elr_I_fzpAfe@_Sf]dFr_@~UjCbg@yKvj@lFfb@`C|c@hNjc@")
            return Route(type, provider, segments, properties, price)
        }

        private fun createMockSegment(name: String?, num_stops: Int, travel_mode: String?, description: String?, color: String?, icon_url: String?, polyline: String?): ArrayList<Segment>? {
            val name = name
            val num_stops = num_stops
            val travel_mode = travel_mode
            val description = description
            val color = color
            val icon_url = icon_url
            val polyline = polyline
            val stops = ArrayList<Stop>()
            var stop = createMockStop(52.5302356, 13.4033659, "2015-04-17T12:29:00+01:00", "Torstraße 103, 10119 Berlin, Deutschland", null)
            stops.add(stop)
            stop = createMockStop(52.509071, 13.377977, "2015-04-17T12:46:00+01:00", "Leipziger Platz 7, 10117 Berlin, Deutschland", null)
            stops.add(stop)

            val segments = ArrayList<Segment>()
            val segment = Segment(name, num_stops, stops!!, travel_mode, description, color, icon_url, polyline)
            segments.add(segment)
            return segments
        }

        private fun createMockStop(lat: Double?, lng: Double?, datetime: String?, name: String?, properties: String?): Stop {
            var lat = lat
            var lng = lng
            var datetime = datetime
            var name = name
            var properties = properties
            val stop = Stop(lat, lng, datetime, name, properties)
            return stop
        }
    }
}