package com.syc.models;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessMultimedium implements Serializable {

    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;

    private final static long serialVersionUID = -1108426580440827613L;

    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }

    public String getSubtype() { return subtype; }
    public void setSubtype(String subtype) { this.subtype = subtype; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUrl() { return url;    }
    public void setUrl(String url) { this.url = url; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
}