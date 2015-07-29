package com.br.markus.muapi.interfaces

import com.br.markus.muapi.trdparty.google.geocoding.Coordinate
import org.codehaus.jettison.json.JSONObject

interface IMUSearchAPI {
    def doSearch(Coordinate coor, String query) throws Exception;
}