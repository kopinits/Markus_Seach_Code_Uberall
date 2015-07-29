package com.br.markus.muapi.trdparty.google.geocoding
import org.apache.wicket.util.io.IOUtils
import org.codehaus.jettison.json.JSONArray
import org.codehaus.jettison.json.JSONException
import org.codehaus.jettison.json.JSONObject

class GeocodingReader {
	static final String JSON_LOCATION_OBJECT = "location"
	static final String JSON_GEOMETRY_OBJECT = "geometry"
	static final String JSON_RESULTS_OBJECT = "results"
	static final String LNG = "lng"
	static final String LAT = "lat"
	static final String KEY_STATUS = "status"

	private static final String GOOGLE_RESULT_OK = "OK"

	private static final String GOOGLE_GEO_URL = "https://maps.googleapis.com/maps/api/geocode/json?address="
	private static final String GOOGLE_GEO_KEY = "&key=AIzaSyCgNyQlPcRTkBAQdvM_UHaE9YVX0h7fCKg"

	public Coordinate readGeocodingData(String address) throws Exception {
		try {
            def url = new URL(createURL(address))
            InputStream is = url.openStream()
            if (is != null) {
                try {
                    return decode(IOUtils.toString(is))
                } finally {
                    is.close()
                }
            }
        }catch (ConnectException ce){
            println(ce.message)
        }
		return null
	}

	def createURL(String address){
		address = address.replace(" ", "+")
		def url = GOOGLE_GEO_URL + address + GOOGLE_GEO_KEY
		url
	}
	
	private Coordinate decode(String response) throws Exception {
		String status = readStatus(response)
		if (!status.equals(GOOGLE_RESULT_OK)) {
			throw new Exception(status)
		}

		return new Coordinate(latitude:readLatitude(response), longitude:readLongitude(response))
	}

	private String readStatus(String response) throws JSONException {
		JSONObject myObject = new JSONObject(response)
		return myObject.get(KEY_STATUS).toString()
	}

	private String readLatitude(String response) throws JSONException {
		JSONObject myObject = findLocation(response)
		return myObject.get(LAT).toString()
	}

	private String readLongitude(String response) throws JSONException {
		JSONObject myObject = findLocation(response)
		return myObject.get(LNG).toString()
	}

	private JSONObject findLocation(String response) throws JSONException {
		JSONObject myObject = new JSONObject(response)
		myObject.get(JSON_RESULTS_OBJECT).asType(JSONArray.class).get(0).each {
			myObject = it.asType(JSONObject.class).get(JSON_GEOMETRY_OBJECT).asType(JSONObject.class).get(JSON_LOCATION_OBJECT)
		}
		return myObject
	}
}
