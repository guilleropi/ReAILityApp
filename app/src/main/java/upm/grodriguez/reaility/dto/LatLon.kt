package upm.grodriguez.reaility.dto

data class LatLon(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0)
{
    override fun toString(): String {
        return "$latitude, $longitude"
    }
}
