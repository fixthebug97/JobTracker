package com.example.jobtracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Info extends Fragment {
    EditText jobTitle;
    EditText jobDate;
    EditText jobStatus;
    EditText jobLocation;
    EditText jobURL;
    private String status;
    private String jobtitle;
    private String joblocation;
    private String joburl;
    private String jobdate;
    private int jobID;
    FloatingActionButton update;
    Spinner spinner;
    Button calender;
    Button updateData;
    Button deleteJOb;
    Button navigate;
    DatePickerDialog datePickerDialog;
    Database db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.info,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = ((MainActivity) getActivity()).getDatabase();
        jobTitle=view.findViewById(R.id.AddJobTitle);
        jobDate=view.findViewById(R.id.AddJobDate);
        jobStatus=view.findViewById(R.id.AddStatus);
        jobLocation=view.findViewById(R.id.AddJobLocation);
        jobURL=view.findViewById(R.id.AddJObURL);
        update=view.findViewById(R.id.Update);
        updateData=view.findViewById(R.id.UpdateJobs);
        calender=view.findViewById(R.id.calender);
        spinner=view.findViewById(R.id.AddJObStatus);
        deleteJOb=view.findViewById(R.id.DeleteJobs);
        navigate=view.findViewById(R.id.navigate);
        Bundle bundle=getArguments();
     DataList dataList= (DataList) bundle.getSerializable("info");
     jobtitle=dataList.getJobTitle();
     joblocation=dataList.getJobLocation();
     jobdate=dataList.getJobDate();
     joburl=dataList.getJobURL();
     status=dataList.getStatus();
     jobID=dataList.getId();
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(),R.array.statusArray,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setColor(getResources().getColor(R.color.textoff));
        SetData();
        setCalender();
        update();
        setStatus();
        UpdateData();
        deleteJobs();
        navigate();
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

    public void SetData(){

        jobTitle.setText(jobtitle);
        jobLocation.setText(joblocation);
        jobDate.setText(jobdate);
        jobURL.setText(joburl);
        jobStatus.setText(status);
    }

    public void update(){

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate.setVisibility(View.GONE);
                setColor(getResources().getColor(R.color.textoff));

                ((View)update).setVisibility(View.GONE);
                jobTitle.setEnabled(true);
                jobTitle.setCursorVisible(true);
                jobTitle.setGravity(Gravity.CENTER);
                jobTitle.setBackgroundResource(R.drawable.edittext);


                jobLocation.setEnabled(true);
                jobLocation.setCursorVisible(true);
                jobLocation.setGravity(Gravity.CENTER);
                jobLocation.setBackgroundResource(R.drawable.edittext);


                jobDate.setEnabled(true);
                jobDate.setCursorVisible(true);
                jobDate.setGravity(Gravity.CENTER);
                jobDate.setBackgroundResource(R.drawable.edittext);


                jobURL.setEnabled(true);
                jobURL.setCursorVisible(true);
                jobURL.setGravity(Gravity.CENTER);
                jobURL.setBackgroundResource(R.drawable.edittext);

                jobStatus.setEnabled(true);
                jobStatus.setCursorVisible(true);
                jobStatus.setGravity(Gravity.CENTER);
                jobStatus.setBackgroundResource(R.drawable.edittext);

                spinner.setVisibility(View.VISIBLE);

                calender.setVisibility(View.VISIBLE);

                updateData.setVisibility(View.VISIBLE);
                deleteJOb.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setCalender(){


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c=Calendar.getInstance();
                final int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                int day=c.get(Calendar.DAY_OF_MONTH);


                datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {
                        jobDate.setText(mday+"/"+(mmonth+1)+"/"+myear);
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });
    }

    public void UpdateData(){

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if(jobTitle.getText().toString().isEmpty() || jobLocation.getText().toString().isEmpty() || jobDate.getText().toString().isEmpty() || jobURL.getText().toString().isEmpty() || jobStatus.getText().toString().isEmpty() ){

    Toast.makeText(getActivity(), "Please fill out the field", Toast.LENGTH_SHORT).show();
}

else {
    long is_updated = db.update(jobID, jobTitle.getText().toString(), jobLocation.getText().toString(), jobDate.getText().toString(), jobURL.getText().toString(), jobStatus.getText().toString());
    if (is_updated > 0) {
        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
        ((View) update).setVisibility(View.VISIBLE);
        navigate.setVisibility(View.VISIBLE);

        setColor(getResources().getColor(R.color.textoff));
        jobTitle.setEnabled(false);
        jobTitle.setCursorVisible(false);
        jobTitle.setBackgroundResource(0);
        jobTitle.setGravity(Gravity.LEFT);


        jobLocation.setEnabled(false);
        jobLocation.setCursorVisible(false);
        jobLocation.setBackgroundResource(0);
        jobLocation.setGravity(Gravity.LEFT);

        jobDate.setEnabled(false);
        jobDate.setCursorVisible(false);
        jobDate.setBackgroundResource(0);
        jobDate.setGravity(Gravity.LEFT);


        jobURL.setEnabled(false);
        jobURL.setCursorVisible(false);
        jobURL.setBackgroundResource(0);
        jobURL.setGravity(Gravity.LEFT);


        jobStatus.setEnabled(false);
        jobStatus.setCursorVisible(false);
        jobStatus.setBackgroundResource(0);
        jobStatus.setGravity(Gravity.LEFT);

        spinner.setVisibility(View.GONE);

        calender.setVisibility(View.GONE);

        updateData.setVisibility(View.GONE);
        deleteJOb.setVisibility(View.GONE);
    } else {

        Toast.makeText(getActivity(), "Not Updated", Toast.LENGTH_SHORT).show();
    }
}
            }
        });
    }

    public void setColor(int color){
        jobTitle.setTextColor(color);
        jobLocation.setTextColor(color);
        jobDate.setTextColor(color);
        jobURL.setTextColor(color);
        jobStatus.setTextColor(color);


    }

    public void deleteJobs(){

        deleteJOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long is_deleted=db.delete(jobID);
                if(is_deleted>0){

                    Toast.makeText(getActivity(), "Job Deleted", Toast.LENGTH_SHORT).show();
                    ((INavigation)getActivity()).gobacktoInfo(view);
                }
            }
        });
    }
public void navigate(){

        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jobURL.getText().toString()));
                startActivity(browserIntent);
            }
        });
}



}
