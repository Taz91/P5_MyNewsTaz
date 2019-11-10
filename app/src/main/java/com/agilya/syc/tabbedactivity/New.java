package com.agilya.syc.tabbedactivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Deyine Jiddou (deyine.jiddou@gmail.com)
 * Created at 2019-10-25
 */
public class New implements Serializable
{
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    private final static long serialVersionUID = 1267195745348537792L;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

}

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

