package com.siduron.java.iTravel.Model.Entities;

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
                return FAMILIS_TRIP;
            case 3:
                return GUIDED_TOUR;
            case 4:
                return HOTEL;
            case 5:
            return WEEKEND;
            default:
                return SHORT_TRACK;
        }
    }


    public static Category fromString(String key) {
        switch (key) {
            case "SHORT_TRACK":
                return SHORT_TRACK;
            case "DAY_TRIP":
                return DAY_TRIP;
            case "FAMILIS_TRIP":
                return FAMILIS_TRIP;
            case "GUIDED_TOUR":
                return GUIDED_TOUR;
            case "HOTEL":
                return HOTEL;
            case "WEEKEND":
                return WEEKEND;
            default:
                return SHORT_TRACK;
        }
    }
}
