package com.siduron.java.iTravel.Model.Entities;

/**
 * Created by musad on 29/01/2017.
 */


import android.graphics.drawable.Icon;
import android.media.Image;

/**
 * Drawer navigation menu item model
 * @author Moshe Nahari
 */
public class NavDrawerItem {
    private String title;
    private int icon;

    public NavDrawerItem(String title0, int icon0)
    {
        title=title0;
        icon=icon0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
