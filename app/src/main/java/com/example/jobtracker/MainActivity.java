package com.example.jobtracker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.util.Calendar;

import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity implements INavigation{
private Database database;
private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=new Database(getApplicationContext());
    }

    @Override
    public void toJobList(View view) {
        Navigation.findNavController(view).navigate(R.id.action_splashScreen_to_jobList);
    }

    @Override
    public void toAddJobList(View view) {
        Navigation.findNavController(view).navigate(R.id.action_jobList_to_addJob);
    }

    @Override
    public void goBackToJobList(View view) {
        Navigation.findNavController(view).navigate(R.id.action_addJob_to_jobList);
    }

    @Override
    public void goToInfo(View view, DataList dataList) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("info",dataList);
        Navigation.findNavController(view).navigate(R.id.action_jobList_to_info2,bundle);
    }

    @Override
    public void gobacktoInfo(View view) {
        Navigation.findNavController(view).navigate(R.id.action_info2_to_jobList2);
    }


    public Database getDatabase() {
        return database;
    }


}
