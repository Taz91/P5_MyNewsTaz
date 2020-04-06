package com.syc.models;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponse implements Serializable
{
    @SerializedName("docs")
    @Expose
    private List<SearchDoc> searchdocs = null;

    private final static long serialVersionUID = -1826949279503154993L;

    public List<SearchDoc> getDocs() { return searchdocs; }
    public void setDocs(List<SearchDoc> searchdocs) { this.searchdocs = searchdocs; }
}