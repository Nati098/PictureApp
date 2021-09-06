package ru.geekbrains.pictureapp.model.data

import com.google.gson.annotations.SerializedName

data class NeoServerResponseData (
    @field:SerializedName("element_count") val elementCount: Int,
    @field:SerializedName("near_earth_objects") val nearEarthObjects: Map<String, List<NearEarthObject>>
)

data class NearEarthObject(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("neo_reference_id") val neoReferenceId: String?,
    @field:SerializedName("nasa_jpl_url") val nasaJplUrl: String?,
    @field:SerializedName("absolute_magnitude_h") val absoluteMagnitudeH: Double?,

    @field:SerializedName("estimated_diameter") val estimatedDiameter: DiameterCollection?,
    @field:SerializedName("close_approach_data") val closeApproachData: List<CloseApproachData>?,

    @field:SerializedName("is_potentially_hazardous_asteroid") val isPotentiallyHazardousAstedoid: Boolean?,
    @field:SerializedName("is_sentry_object") val isSentryObject: Boolean?
)


data class CloseApproachData(
    @field:SerializedName("close_approach_date") val closeApproachDate: String?,
    @field:SerializedName("relative_velocity") val relativeVelocity: Velocity?,
    @field:SerializedName("miss_distance") val missDistance: Distance?,
)

data class DiameterCollection(
    @field:SerializedName("kilometers") val kilometers: MinMaxDiameter,
    @field:SerializedName("meters") val meters: MinMaxDiameter,
    @field:SerializedName("feet") val feet: MinMaxDiameter
)

data class MinMaxDiameter(
    @field:SerializedName("estimated_diameter_min") val estimatedDiameterMin: Double,
    @field:SerializedName("estimated_diameter_max") val estimatedDiameterMax: Double,
)

data class Distance(
    @field:SerializedName("astronomical") val astronomical: Double,
    @field:SerializedName("kilometers") val kilometers: Double,
    @field:SerializedName("miles") val miles: Double,
)

data class Velocity(
    @field:SerializedName("kilometers_per_hour") val kilometersPerHour: Double,
    @field:SerializedName("miles_per_hour") val milesPerHour: Double,
)