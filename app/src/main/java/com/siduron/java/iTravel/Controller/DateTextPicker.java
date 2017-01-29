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

import java.util.Calendar;
import java.util.Date;

/**
 * Created by musad on 27/01/2017.
 */

public class DateTextPicker  extends TextView {
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
        int cyear = calendar.get(Calendar.YEAR);
        int cmonth = calendar.get(Calendar.MONTH);
        int cday = calendar.get(Calendar.DAY_OF_MONTH);
        this.setText(cday + "/" + (cmonth + 1) + "/" + cyear);
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
                        cyear=year;
                        cmonth=monthOfYear;
                        cday=dayOfMonth;
                        DateTextPicker.this.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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
}
