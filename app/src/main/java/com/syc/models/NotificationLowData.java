package com.syc.models;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationLowData implements Serializable
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("response")
    @Expose
    private NotificationResponse notificationResponse;
    private final static long serialVersionUID = 4649603883211200818L;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCopyright() { return copyright; }
    public void setCopyright(String copyright) { this.copyright = copyright; }

    public NotificationResponse getResponse() { return notificationResponse; }
    public void setResponse(NotificationResponse response) { this.notificationResponse = response; }
}
