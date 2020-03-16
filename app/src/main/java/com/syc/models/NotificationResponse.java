package com.syc.models;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponse implements Serializable
{
    @SerializedName("docs")
    @Expose
    private List<NotificationDoc> docs = null;
    @SerializedName("meta")
    @Expose
    private NotificationMeta NotificationMeta;
    private final static long serialVersionUID = -1826949279503154993L;

    public List<NotificationDoc> getDocs() { return docs; }
    public void setDocs(List<NotificationDoc> docs) { this.docs = docs; }

    public NotificationMeta getMeta() { return NotificationMeta; }
    public void setMeta(NotificationMeta meta) { this.NotificationMeta = NotificationMeta; }

}
