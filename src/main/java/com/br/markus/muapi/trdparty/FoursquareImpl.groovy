package com.br.markus.muapi.trdparty

import com.br.markus.muapi.interfaces.impl.MuSearchAPI
import groovy.json.JsonBuilder;
import org.apache.wicket.util.io.IOUtils

import com.br.markus.muapi.trdparty.google.geocoding.Coordinate

class FoursquareImpl extends MuSearchAPI{

	static final String JSON_VENUE_OBJECT = "venue"
	static final String JSON_GROUPS_OBJECT = "groups"
    static final String JSON_ITEMS_OBJECT = "items"

	static final String FOURSQUARE_URL = "https://api.foursquare.com/v2/venues/explore?"
	static final String FOURSQUARE_KEY = "client_id=KFLW5PUSG25GYKSCJZITCZAFO3CQJARQ1KKM0G3WUDA2SYIF&client_secret=NZ0XYSMNC1TVXTS2URDI43DLX4EESVRXXV50DUFK43ZYUN41"
	static final String FOURSQUARE_LL = "&ll="
	static final String FOURSQUARE_VM = "&v=20140806&limit=1"
	static final String FOURSQUARE_QUERY = "&query="

	def doSearch(Coordinate coor, String query) throws Exception {
		InputStream is = new URL(createURL(coor, query)).openStream()

		if (is != null) {
			try {
				return createJSonResponde(IOUtils.toString(is))
			} finally {
				is.close()
			}
		}
		return null
	}

	def createURL(Coordinate coordinate, String query){
		def url = FOURSQUARE_URL + FOURSQUARE_KEY + FOURSQUARE_LL + coordinate.toString()
		url += FOURSQUARE_QUERY + query + FOURSQUARE_VM
		url
	}

	def createJSonResponde(String response){
		new JsonBuilder(findJSONObject(response))
	}


    @Override
    ArrayList<String> getArrayNodesInOrder() {
        return [JSON_GROUPS_OBJECT, JSON_ITEMS_OBJECT]
    }

    @Override
    String getDataNode() {
        return JSON_VENUE_OBJECT
    }
}
