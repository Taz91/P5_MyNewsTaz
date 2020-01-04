package com.syc.models;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import static com.syc.utils.Utils.convertDate;

public class SearchDoc implements Serializable
{
    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("lead_paragraph")
    @Expose
    private String leadParagraph;
    @SerializedName("multimedia")
    @Expose
    private List<SearchMultimedium> multimedia = null;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("subsection_name")
    @Expose
    private String subsectionName;

    private final static long serialVersionUID = 648294846803604800L;

    @Expose
    private String category;
    @Expose
    private String dateString;

    // customize data
    public String getCategory(){
        this.category = (TextUtils.isEmpty(this.getSubsectionName())) ? this.getSectionName() : this.getSectionName() + " > " + this.getSubsectionName();
        return this.category;
    };

    public String getDate(){
        this.dateString = convertDate( this.getPubDate() );
        return this.dateString;
    }

    public String getWebUrl() { return webUrl; }
    public void setWebUrl(String webUrl) { this.webUrl = webUrl; }

    public String getLeadParagraph() { return leadParagraph; }
    public void setLeadParagraph(String leadParagraph) { this.leadParagraph = leadParagraph; }

    public List<SearchMultimedium> getMultimedia() { return multimedia; }
    public void setMultimedia(List<SearchMultimedium> multimedia) { this.multimedia = multimedia; }

    public String getPubDate() { return pubDate; }
    public void setPubDate(String pubDate) { this.pubDate = pubDate; }

    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }

    public String getSubsectionName() { return subsectionName; }
    public void setSubsectionName(String subsectionName) { this.subsectionName = subsectionName; }
}
