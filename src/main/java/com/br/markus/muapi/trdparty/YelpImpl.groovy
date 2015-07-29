package com.br.markus.muapi.trdparty

import com.br.markus.muapi.interfaces.impl.MuSearchAPI
import com.br.markus.muapi.trdparty.google.geocoding.Coordinate
import groovy.json.JsonBuilder
import org.codehaus.jettison.json.JSONObject
import org.scribe.builder.ServiceBuilder
import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.oauth.OAuthService

class YelpImpl extends MuSearchAPI {

    static final String CONSUMER_KEY = "u2VnJR-c8vaPZTPUL-98ww"
    static final String CONSUMER_SECRET = "exAuld6fOX25SZI0Lxi-_b9s7xM"
    static final String TOKEN = "3SFbCePbJMWjLcXNgOSfWdePY5N55ATM"
    static final String TOKEN_SECRET = "PCRyjAANYh3_fHKXrv1_08-sjWw"

    private static final String API_HOST = "api.yelp.com"
    private static final int SEARCH_LIMIT = 1
    private static final String SEARCH_PATH = "/v2/search"
    private static final String BUSINESS_PATH = "/v2/business"


    OAuthService service
    Token accessToken

    YelpImpl() {
        this.service =
                new ServiceBuilder().provider(YelpOAuth.class).apiKey(CONSUMER_KEY)
                        .apiSecret(CONSUMER_SECRET).build()
        this.accessToken = new Token(TOKEN, TOKEN_SECRET)
    }


    def doSearch(Coordinate coor, String query) throws Exception {
        createJSonResponde(searchForBusinessesByLocation(query, coor.city))
    }

    def createJSonResponde(String response) {
        new JsonBuilder(findJSONObject(response))
    }

    public String searchForBusinessesByLocation(String term, String location) {
        OAuthRequest request = createOAuthRequest(SEARCH_PATH)
        request.addQuerystringParameter("term", term)
        request.addQuerystringParameter("location", location)
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT))
        return sendRequestAndGetResponse(request)
    }

    private OAuthRequest createOAuthRequest(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path)
        return request
    }

    private String sendRequestAndGetResponse(OAuthRequest request) {
        this.service.signRequest(this.accessToken, request)
        Response response = request.send()
        return response.getBody()
    }

    @Override
    ArrayList<String> getArrayNodesInOrder() {
        return ["businesses"]
    }

    @Override
    String getDataNode() {
        return "location"
    }

    @Override
    def getJsonResponseObject(String response) {
        new JSONObject(response)
    }
}
