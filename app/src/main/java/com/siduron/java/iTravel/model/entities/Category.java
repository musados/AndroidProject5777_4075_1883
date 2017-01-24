package com.siduron.java.iTravel.model.entities;

/**
 * Created by musad on 19/01/2017.
 */

/**
 * This enum is types of the activities
 * For e.g. Tracking, hotels, short route or tour.
 * @author Moshe Nahari & Haim Milikovski
 *
 */
public enum Category {
    SHORT_TRACK,DAY_TRIP, GUIDED_TOUR,HOTEL,WEEKEND,FAMILIS_TRIP;

    public static Category fromInt(int value) {
        switch (value) {
            case 0:
                return SHORT_TRACK;
            case 1:
                return DAY_TRIP;
            case 2:
                return GUIDED_TOUR;
            case 3:
                return HOTEL;
            case 4:
                return WEEKEND;
            case 5:
                return FAMILIS_TRIP;
            default:
                return SHORT_TRACK;
        }
    }
}
