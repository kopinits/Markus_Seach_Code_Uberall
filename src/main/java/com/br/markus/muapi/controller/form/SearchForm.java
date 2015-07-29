package com.br.markus.muapi.controller.form;

public class SearchForm {
    private String street;
    private String number;
    private String city;
    private String companyName;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getAddress(){
        return number+" "+street+" "+city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyQuery(){
        return companyName.replace(" ", "+");
    }
}
