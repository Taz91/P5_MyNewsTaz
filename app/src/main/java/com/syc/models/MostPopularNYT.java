package com.syc.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopularNYT implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<MostResult> results = null;
    private final static long serialVersionUID = 7311869528992796374L;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getNumResults() {
        return numResults;
    }
    public void setNumResults(Integer numResults) {
        this.numResults = numResults;
    }

    public List<MostResult> getResults() { return results; }
    public void setResults(List<MostResult> results) {
        this.results = results;
    }
}


