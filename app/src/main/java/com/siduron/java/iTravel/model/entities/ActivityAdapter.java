package com.siduron.java.iTravel.model.entities;

/**
 * Created by musad on 19/01/2017.
 */


/**
 * This class is a link between one Activity (such as day trip or Weelend
 * activity, and her creator - as bussines.
 * @author Moshe Nahari & Haim Milikovski
 */
public class ActivityAdapter {
    private int activityId;
    private int bussinesId;

    /**
     *First CTOR
     * Reseting the IDs
     *
     * @param activity for linking
     * @param bussiness for linking.
     */
    public ActivityAdapter(Activity activity,Bussiness bussiness)
    {
        activityId = activity.getId();
        bussinesId = bussiness.getId();
    }

    /**
     * CTOR
     * @param aId activity ID
     * @param bId bussines ID
     */
    public ActivityAdapter(int aId,int bId)
    {
        activityId=aId;
        bussinesId=bId;
    }

    //Get methods

    public int getActivityID(){return  activityId;}
    public int getBussinessID(){return bussinesId;}

    //Set methods

    public void setActivityID(int id){activityId=id;}
    public void setBussinessID(int id){bussinesId=id;}

}
