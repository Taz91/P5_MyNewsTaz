package com.syc.models;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessResponse implements Serializable
{
    @SerializedName("docs")
    @Expose
    private List<BusinessDoc> businessdocs = null;

    private final static long serialVersionUID = -1826949279503154993L;

    public List<BusinessDoc> getDocs() { return businessdocs; }
    public void setDocs(List<BusinessDoc> searchdocsdocs) { this.businessdocs = searchdocsdocs; }
}