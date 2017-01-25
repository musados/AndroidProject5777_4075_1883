package com.siduron.java.iTravel.model.entities;

/**
 * Created by musad on 19/01/2017.
 */


/**
 * This class is a link between one Activity (such as day trip or Weelend
 * activity, and her creator - as bussines.
 * @author Moshe Nahari & Haim Milikovski
 */
public class ActivityAdapter implements ITravelData{
    private int id;
    private int activityId;
    private int bussinesId;

    /**
     *First CTOR
     * Reseting the IDs
     *
     * @param activity for linking
     * @param bussiness for linking.
     */
    public ActivityAdapter(int id0,Activity activity,Bussiness bussiness)
    {
        id=id0;
        activityId = activity.getId();
        bussinesId = bussiness.getId();
    }

    /**
     * CTOR
     * @param aId activity ID
     * @param bId bussines ID
     */
    public ActivityAdapter(int id0,int aId,int bId)
    {
        id=id0;
        activityId=aId;
        bussinesId=bId;
    }

    //Get methods

    public void setId(int id) {
        this.id = id;
    }

    public int getID(){return id;}
    public int getActivityID(){return  activityId;}
    public int getBussinessID(){return bussinesId;}

    //Set methods

    public void setActivityID(int id){activityId=id;}
    public void setBussinessID(int id){bussinesId=id;}

    @Override
    public Object[] getRowData() {
        return new Object[]
                {
                        getID(),
                        getActivityID(),
                        getBussinessID()
                };
    }
}
