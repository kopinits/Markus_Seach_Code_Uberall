package com.br.markus.muapi.trdparty

import org.scribe.builder.api.DefaultApi10a
import org.scribe.model.Token

class YelpOAuth extends DefaultApi10a {

    @Override
    public String getAccessTokenEndpoint() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(Token arg0) {
        return null;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return null;
    }
}
