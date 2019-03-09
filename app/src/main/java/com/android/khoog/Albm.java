package com.android.khoog;
/**
 * Created by Lincoln on 18/05/16.
 */
public class Albm {
    private String name;
    private String thumbnail;
    private String price;
    private String comp;
    private String id;

    public Albm(String name, String thumbnail, String price, String comp, String id) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
        this.comp = comp;
        this.id = id;
    }

    public String getComp()
    {
        return comp;
    }
    public String getId()
    {
        return id;
    }


    public void setId(String id)
    {
        this.id = id;
    }


    public void setComp()
    {
        this.comp = comp;
    }

    public String getPrice()
    {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
