package com.syc.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import static com.syc.utils.Utils.convertDate;

public class MostResult implements Serializable
{
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("adx_keywords")
    @Expose
    private String adxKeywords;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("share_count")
    @Expose
    private Integer shareCount;
    @SerializedName("count_type")
    @Expose
    private String countType;
    @SerializedName("column")
    @Expose
    private Object column;
    @SerializedName("eta_id")
    @Expose
    private Integer etaId;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("nytdsection")
    @Expose
    private String nytdsection;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("media")
    @Expose
    private List<MostMedium> media = null;
    @SerializedName("uri")
    @Expose
    private String uri;
    private final static long serialVersionUID = -7133094194935452200L;

    @Expose
    private String category;
    @Expose
    private String dateString;

    // customize data
    public String getCategory(){
        if(getSubsection()==null){
            this.category = this.getSection();
        }else{
            if(getSubsection().isEmpty()){
                this.category = this.getSection();
            }else{
                this.category = this.getSection() + " > " + this.getSubsection();
            }
        }

        //this.category = ("".equals(getSubsection())) ? this.getSection() : this.getSection() + " > " + this.getSubsection();
        return this.category;
    };

    public String getDate(){
        this.dateString = convertDate( this.getPublishedDate() );
        return this.dateString;
    }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getAdxKeywords() { return adxKeywords; }
    public void setAdxKeywords(String adxKeywords) {this.adxKeywords = adxKeywords;}

    public String getSubsection() { return subsection; }
    public void setSubsection(String subsection) { this.subsection = subsection; }

    public Integer getShareCount() { return shareCount; }
    public void setShareCount(Integer shareCount) { this.shareCount = shareCount; }

    public String getCountType() { return countType; }
    public void setCountType(String countType) { this.countType = countType; }

    public Object getColumn() { return column;}
    public void setColumn(Object column) { this.column = column;}

    public Integer getEtaId() { return etaId; }
    public void setEtaId(Integer etaId) { this.etaId = etaId; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getNytdsection() { return nytdsection; }
    public void setNytdsection(String nytdsection) { this.nytdsection = nytdsection; }

    public String getByline() { return byline; }
    public void setByline(String byline) { this.byline = byline; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAbstract() { return _abstract; }
    public void setAbstract(String _abstract) { this._abstract = _abstract; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getUpdated() { return updated; }
    public void setUpdated(String updated) { this.updated = updated; }

    public List<MostMedium> getMedia() { return media; }
    public void setMedia(List<MostMedium> media) { this.media = media; }

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}



