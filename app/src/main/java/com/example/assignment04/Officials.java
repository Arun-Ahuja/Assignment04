package com.example.assignment04;

import java.io.Serializable;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

public class Officials implements Comparable<Officials>,Serializable {

    private  String officename;
    private  String personname;
    private  String partyname;

    private String pimageurl;
    private String address;
    private String phoneNumber;
    private String email;
    private String website;
    private String displayFinaltext;
    private String facebookUrl;
    private String twitterUrl;
    private String youtubeUrl;
    private String code;




    public Officials(String officename, String personname,String partyname, String pimageurl, String address, String phoneNumber, String email, String website,String displayFinaltext,String facebookUrl, String twitterUrl, String youtubeUrl) {
        this.officename = officename;
        this.personname = personname;
        this.partyname = partyname;
        this.pimageurl = pimageurl;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
        this.displayFinaltext = displayFinaltext;
        this.facebookUrl = facebookUrl;
        this.twitterUrl = twitterUrl;
        this.youtubeUrl = youtubeUrl;

    }





    public String getOfficename() {
        return officename;
    }

    public void setOfficename(String officename) {
        this.officename = officename;
    }


    public String getOfficialparty() {
        return partyname;
    }

    public void setOfficialparty(String officialparty) {
        this.partyname = officialparty;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public String getPimageurl() {
        return pimageurl;
    }

    public void setPimageurl(String pimageurl) {
        this.pimageurl = pimageurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDisplayFinaltext() {
        return displayFinaltext;
    }

    public void setDisplayFinaltext(String displayFinaltext) {
        this.displayFinaltext = displayFinaltext;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int compareTo(Officials office) {
        return officename.compareTo(office.getOfficename());
    }

    public String toString() {
        return "Government{" +
                "title='" + officename + '\'' +
                ", name='" + personname + '\'' +
                ", party='" + partyname + '\'' +
                ", pimageurl='" + pimageurl + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}

