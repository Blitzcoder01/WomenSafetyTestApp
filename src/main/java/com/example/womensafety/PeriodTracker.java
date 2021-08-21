package com.example.womensafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


import com.allyants.notifyme.NotifyMe;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class PeriodTracker extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;
    TextView editText_input,editText_output;
    CalendarView calender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_tracker);
        Button btnNotify = findViewById(R.id.remind);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }

        editText_input = findViewById(R.id.input_date);
        editText_output = findViewById(R.id.output_date);
        calender = findViewById(R.id.calendarView);

        final String c= editText_output.getText().toString();

        calender.setOnDateChangeListener(
                new CalendarView
                        .OnDateChangeListener() {
                    @Override

                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String Date = dayOfMonth + "-" + (month + 1) + "-" + year;

                        editText_input.setText(Date);
                        if (dayOfMonth >= 1 && dayOfMonth <= 31) {
                            int dayGap = 28;
                            int nextDate = dayOfMonth + dayGap;
                            if (nextDate > 30) {
                                int periodDate = nextDate - 30;
                                String Date_new
                                        = periodDate + "-"
                                        + (month + 2) + "-" + year;

                                editText_output.setText(Date_new);
                            } else {
                                int periodDate = nextDate;
                                String Date_new
                                        = periodDate + "-"
                                        + (month + 2) + "-" + year;

                                editText_output.setText(Date_new);

                            }

                        }
                    }

                });
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getFragmentManager(), "Datepickerdialog");
                String message= "Reminder Set Successfully" +
                        "Thank you..";
                Toast.makeText(getApplicationContext(), "Reminder set for upcoming Period date,stay tuned, Stay safe", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,monthOfYear);
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,second);
        Intent intent = new Intent(getApplicationContext(),PeriodTracker.class);
        intent.putExtra("test","I am a String");
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title("Women Safety App")
                .content("Alert")
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                .time(now)
                .addAction(intent,"Snooze",false)
                .key("test")
                .addAction(new Intent(),"Dismiss",true,false)
                .addAction(intent,"Done")
                .large_icon(R.mipmap.ic_launcher_round)
                .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
                .build();
    }
}