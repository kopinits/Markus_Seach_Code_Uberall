package com.br.markus.muapi.controller
import com.br.markus.muapi.controller.form.SearchForm
import com.br.markus.muapi.trdparty.google.geocoding.Coordinate
import com.br.markus.muapi.trdparty.google.geocoding.GeocodingReader
import com.br.markus.muapi.user.UserData
import groovy.json.JsonBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.SessionAttributes

@Controller
@RequestMapping("/search")
@SessionAttributes("searchModel")
class SearchController {
    public static final String INVALID_REQUEST = "invalid_request";
    public static final String NO_RESULTS = "no_results";
    public static final String ACCESS_DENIED = "access_denied";
    public static final String ERROR_MESSAGE_PARAM = "error_message";
    public static final String SUCCESS_MESSAGE_PARAM = "success_message";
    public static final String PACKAGE_NAME = "com.br.markus.muapi.trdparty."

    @RequestMapping(method = RequestMethod.POST)
    String index(ModelMap model, @ModelAttribute(value = "searchModel") SearchForm searchForm) throws Exception {
        model.addAttribute("searchModel", searchForm);
        if (validateUserAndKey("", "")) {
            Coordinate coordinates = new GeocodingReader().readGeocodingData(searchForm.getAddress());
            if (coordinates != null) {
                coordinates.city = searchForm.city
                def resultMap = readDirectoriesResponse(coordinates, searchForm)
                if (resultMap.isEmpty()){
                    model.addAttribute(SUCCESS_MESSAGE_PARAM, NO_RESULTS);
                }else {
                    model.addAttribute(SUCCESS_MESSAGE_PARAM, resultMap.toString());
                }
            } else {
                model.addAttribute(ERROR_MESSAGE_PARAM, INVALID_REQUEST);
            }
        } else {
            model.addAttribute(ERROR_MESSAGE_PARAM, ACCESS_DENIED);
        }
        return "results";
    }

    def readDirectoriesResponse(Coordinate coordinates, SearchForm searchForm) {
        def resultMap = [:]
        getUserDirectories().each {
            def implClass = Class.forName(PACKAGE_NAME +it.value).newInstance()
            JsonBuilder jsonBuilder = (JsonBuilder) implClass.doSearch(coordinates, searchForm.getCompanyQuery());
            resultMap.put(it.key, jsonBuilder.getContent().toString())
        }
        resultMap
    }

    boolean validateUserAndKey(String id, String key) {
        return true;
    }

    def getUserDirectories() {
        new UserData().getDiretcoryMap();
    }

}
