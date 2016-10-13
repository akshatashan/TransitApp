package io.door2door.transitapp.models

import io.mironov.smuggler.AutoParcelable

/**
 * Created by akshata on 12/10/16.
 */

data class Property(
        val address: String?,
        val model: String?,
        val license_plate: String?,
        val fuel_level: Int?,
        val engine_type: String?,
        val internal_cleanliness: String?,
        val description: String?,
        val seats: Int?,
        val doors: Int?) : AutoParcelable