package com.br.markus.muapi.interfaces.impl

import com.br.markus.muapi.interfaces.IMUSearchAPI
import org.codehaus.jettison.json.JSONException
import org.codehaus.jettison.json.JSONObject

abstract class MuSearchAPI implements IMUSearchAPI {
    static final String JSON_RESPONSE_OBJECT = "response"

    def getJsonResponseObject(String response) {
        new JSONObject(response).getJSONObject(JSON_RESPONSE_OBJECT)
    }

    abstract ArrayList<String> getArrayNodesInOrder();

    abstract String getDataNode();

    JSONObject findJSONObject(String response) throws JSONException {
        def result
        try {
            def object = getJsonResponseObject(response)
            getArrayNodesInOrder().each {
                object = object.getJSONArray(it).getJSONObject(0)
            }
            result = object.getJSONObject(getDataNode())
        } catch (Exception e){
            result = new JSONObject()
        }
        result
    }
}
