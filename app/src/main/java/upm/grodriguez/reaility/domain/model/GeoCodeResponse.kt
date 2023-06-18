package upm.grodriguez.reaility.domain.model

data class GeoCodeResponse(
	val osmType: String? = null,
	val osmId: Int? = null,
	val licence: String? = null,
	val boundingbox: List<String?>? = null,
	val address: Address? = null,
	val powered_by: String? = null,
	val lon: String? = null,
	val display_name: String? = null,
	val place_id: Int? = null,
	val lat: String? = null
)
{
	override fun toString(): String {
		return "$display_name"
	}
}

data class Address(
	val country: String? = null,
	val country_code: String? = null,
	val state_district: String? = null,
	val locality: String? = null,
	val state: String? = null,
	val village: String? = null
)
