package com.siduron.java.iTravel.Controller;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.siduron.java.androidproject5777_4075_4075.R;
import com.siduron.java.iTravel.Model.DataSource.Tools;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by musad on 27/01/2017.
 */

public class DateTextPicker  extends TextView implements Observer {
    Calendar calendar;

    public DateTextPicker(Context context) {
        super(context);
        init();
    }


    public DateTextPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateTextPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Date getDate() {
        return calendar.getTime();
    }

    public void setDate(Date date) {
        calendar.setTime(date);
        this.setText(Tools.dateToString(date));
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    private void init() {
        calendar = Calendar.getInstance();
        this.setFocusable(true);
        setDate(calendar.getTime());
        initClick();
        this.setBackground(getResources().getDrawable(R.drawable.white_input_background));
        this.setPadding(25, 10, 25, 10);

        this.setMinEms(12);
        this.setGravity(Gravity.CENTER);
        this.setTextSize(20);
    }

    int cyear = 0;
    int cmonth = 0;

    public int getCyear() {
        return cyear;
    }

    public int getCmonth() {
        return cmonth;
    }

    public int getCday() {
        return cday;
    }

    int cday = 0;

    private void initClick() {
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = DateTextPicker.this.getContext();

                DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        cyear = year;
                        cmonth = monthOfYear;
                        cday = dayOfMonth;
                        DateTextPicker.this.setText(year + "-" +
                                ((monthOfYear >= 10) ? (monthOfYear + 1) : "0" + (monthOfYear + 1)) + "-"
                                + ((dayOfMonth >= 10) ? dayOfMonth : "0" + dayOfMonth));
                    }
                };
                cyear = calendar.get(Calendar.YEAR);
                cmonth = calendar.get(Calendar.MONTH);
                cday = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dpd = new DatePickerDialog(context, callBack, cyear, cmonth, cday);
                dpd.show();
            }

        });
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
