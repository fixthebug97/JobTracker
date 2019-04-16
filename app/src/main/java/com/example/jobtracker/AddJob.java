package com.example.jobtracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddJob extends Fragment  {
    Database db;
    EditText jobTitle;
    EditText jobDate;
    EditText jobStatus;
    EditText jobLocation;
    EditText jobURL;
    Spinner spinner;
    Button calender;
    Button add;
    private String status;
    private String jobtitle;
    private String joblocation;
    private String joburl;
    private String jobdate;
    private String st;
    DatePickerDialog datePickerDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.addjob,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = ((MainActivity) getActivity()).getDatabase();
        calender=view.findViewById(R.id.calender);
        spinner=view.findViewById(R.id.AddJObStatus);
        jobTitle=view.findViewById(R.id.AddJobTitle);

        jobDate=view.findViewById(R.id.AddJobDate);

        jobStatus=view.findViewById(R.id.AddStatus);
        jobLocation=view.findViewById(R.id.AddJobLocation);
        jobURL=view.findViewById(R.id.AddJObURL);
        add=view.findViewById(R.id.Addjobs);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(),R.array.statusArray,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setCalender();
        setStatus();
        AddData();
        setTime();
    }



    public void setCalender(){


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c=Calendar.getInstance();
                final int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                final int day=c.get(Calendar.DAY_OF_MONTH);


datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {


    @Override
    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
        jobDate.setText((mmonth+1)+"/"+mday+"/"+myear);

    }
},month,day,year);
datePickerDialog.show();
datePickerDialog.updateDate(year,month,day);
            }
        });
    }

    public void setTime(){

        Calendar calendar = Calendar.getInstance();
       SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
     jobdate = dateFormat.format(calendar.getTime());
        jobDate.setText(jobdate);
    }



    public void setStatus(){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status=adapterView.getItemAtPosition(i).toString();
               jobStatus.setText(status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void AddData(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobtitle = jobTitle.getText().toString();
                joblocation = jobLocation.getText().toString();
                joburl = jobURL.getText().toString();
                jobdate = jobDate.getText().toString();
                st = jobStatus.getText().toString();

                if (jobtitle.isEmpty() || joblocation.isEmpty() || jobdate.isEmpty() || joburl.isEmpty() || st.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill out the fields", Toast.LENGTH_SHORT).show();

                } else {

                    DataList dataList = new DataList();
                    dataList.setJobTitle(jobtitle);
                    dataList.setJobLocation(joblocation);
                    dataList.setJobURL(joburl);
                    dataList.setJobDate(jobdate);
                    dataList.setStatus(st);
                    long task_is_inserted = db.insertData(dataList);

                    if (task_is_inserted > 0) {

                        Toast.makeText(getActivity(), "Job Added Successfully", Toast.LENGTH_SHORT).show();
                        Clear();
                        ((INavigation) getActivity()).goBackToJobList(view);
                    }

                    else {

                        Toast.makeText(getActivity(), "Job insertion failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void Clear(){

        jobTitle.setText("");
        jobLocation.setText("");
        jobURL.setText("");
        jobDate.setText("");
        jobStatus.setText("open");
    }


}
