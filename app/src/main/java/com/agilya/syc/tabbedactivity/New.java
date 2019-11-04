package com.agilya.syc.tabbedactivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Deyine Jiddou (deyine.jiddou@gmail.com)
 * Created at 2019-10-25
 */
public class New {
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;

    //private String category;

    public New() {
    }

    public String getSection() { return section; }
    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() { return subsection; }
    public void setSubsection(String subsection) { this.subsection = subsection; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAbstract() { return _abstract; }
    public void setAbstract(String _abstract) { this._abstract = _abstract; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    //public String getCategory(){ return category; };
    /*
    public void setCategory(){
        this.category = ("".equals(getSubsection())) ? getSection() : getSection() + " > " + getSubsection();

//        if("".equals(getSubsection())){
//            this.category = getSection();
//        }
//        else{
//            this.category = getSection() + " > " + getSubsection();
//        }

    };

 */
}
