package com.agilya.syc.tabbedactivity.model;
import java.util.Date;

/**
 *
 */
public class NewsItem {
    private String itemDescription;
    private String itemCategory;
    private Date itemDate;
    private String itemImgPath;

    public NewsItem(String itemDescription, String itemCategory, Date itemDate, String itemImgPath) {
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemDate = itemDate;
        this.itemImgPath = itemImgPath;
    }

    public String getItemDescription() {return itemDescription;}
    public String getItemCategory() {return itemCategory;}
    public Date getItemDate() {return itemDate;}
    public String getItemImgPath() {return itemImgPath;}
}
