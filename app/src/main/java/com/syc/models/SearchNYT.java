package com.syc.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchNYT implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("response")
    @Expose
    private SearchResponse response;
    private final static long serialVersionUID = -8238918989386938658L;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCopyright() { return copyright; }
    public void setCopyright(String copyright) { this.copyright = copyright; }

    public SearchResponse getResponse() { return response; }
    public void setResponse(SearchResponse response) { this.response = response; }

}