package com.siduron.java.iTravel.model.entities;

import java.util.Date;

/**
 * Created by musad on 19/01/2017.
 */

public class Activity {

    //Activity identification
    private int id;
    private int bussinessID;
    private String activityName;
    private String activityDescription;

    //Activity date
    private Date startDay;
    private Date endDate;

    private Category activityCategory;
    private String activitylocation;

    private double activityPrice;

    public void setId(int id) {
        this.id = id;
    }

    /**
     * CTOR takes all the Activity class's details
     * @param id0
     * @param bussinesID0
     * @param name activity's name
     * @param descrip activity's description
     * @param start date of the activity
     * @param end date of the activity
     * @param category type of the activity
     * @param location activity's location
     * @param price activity's price
     */
    public Activity(int id0,int bussinesID0,String name,String descrip,
                    Date start,Date end,
                    Category category,String location,double price)
    {
        id=id0;
        bussinessID=bussinesID0;
        activityName=name;
        activityDescription=descrip;
        startDay=start;
        endDate=end;
        activityCategory=category;
        activitylocation=location;
        activityPrice=price;
    }


    //Activity's Getters and Setters methods

    public int getId() {
        return id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Category getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(Category activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getActivitylocation() {
        return activitylocation;
    }

    public void setActivitylocation(String activitylocation) {
        this.activitylocation = activitylocation;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }



    public int getBussinessID() {
        return bussinessID;
    }

    public void setBussinessID(int bussinessID) {
        this.bussinessID = bussinessID;
    }
}
