package com.example.chatappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.datepicker.MaterialCalendar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookActivtiy extends AppCompatActivity {

    DatePicker datePicker;
    Calendar calendar;
    Button b3;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_activtiy);
        calendar=Calendar.getInstance();
        b3=findViewById(R.id.button3);
        datePicker=findViewById(R.id.calendar);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref= FirebaseDatabase.getInstance().getReference().child("doctor").child("appointments");
                calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                final String date=new StringBuilder(datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear()).toString();
                final String date1=new StringBuilder(datePicker.getDayOfMonth()+""+datePicker.getMonth()+""+datePicker.getYear()).toString();
                ref.child(date1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(getApplicationContext(),"value exist",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"value don't exist",Toast.LENGTH_SHORT).show();
                            ref.child(date1).push().setValue(date);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}