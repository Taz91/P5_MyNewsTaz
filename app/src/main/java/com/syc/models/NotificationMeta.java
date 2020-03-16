package com.syc.models;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationMeta implements Serializable
{
    @SerializedName("hits")
    @Expose
    private Integer hits;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("time")
    @Expose
    private Integer time;
    private final static long serialVersionUID = 234290180713043655L;

    public Integer getHits() { return hits; }
    public void setHits(Integer hits) { this.hits = hits; }

    public Integer getOffset() { return offset; }
    public void setOffset(Integer offset) { this.offset = offset; }

    public Integer getTime() { return time; }
    public void setTime(Integer time) { this.time = time; }

}
