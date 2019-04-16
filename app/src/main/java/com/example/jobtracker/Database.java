package com.example.jobtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static String DATABASE="JobList";
    private Context context;

    public Database(Context context) {
        super(context, DATABASE, null, 1);
        this.context=context;
        //Toast.makeText(context, "DataBase Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tblJob (id integer primary key autoincrement,jobTitle varchar(50),jobLocation varchar(50),jobDate varchar(50),jobURL varchar(50),status varchar(50))");
        //Toast.makeText(context, "Table Created Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public long insertData(DataList dataList){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("jobTitle",dataList.getJobTitle());
        contentValues.put("jobLocation",dataList.getJobLocation());
        contentValues.put("jobDate",dataList.getJobDate());
        contentValues.put("jobURL",dataList.getJobURL());
        contentValues.put("status",dataList.getStatus());
        long result=sqLiteDatabase.insert("tblJob",null,contentValues);

        return result;

    }
    public ArrayList<DataList> getAllTask(){

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String[] columns={"id","jobTitle","jobLocation","jobDate","jobURL","status"};
        Cursor cursor=sqLiteDatabase.query("tblJob",columns,null,null,null,null,null);
        int idIndex;
        int jobTitleIndex;
        int jobLocationIndex;
        int jobDateIndex;
        int jobURLIndex;
        int statusIndex;

        int id;
        String jobTitle;
        String jobLocation;
        String jobDate;
        String jobURL;
        String status;


        ArrayList<DataList> dataLists=new ArrayList<>();

        while (cursor.moveToNext()){
            idIndex=cursor.getColumnIndex("id");
            jobTitleIndex=cursor.getColumnIndex("jobTitle");
            jobLocationIndex=cursor.getColumnIndex("jobLocation");
            jobDateIndex=cursor.getColumnIndex("jobDate");
            jobURLIndex=cursor.getColumnIndex("jobURL");
            statusIndex=cursor.getColumnIndex("status");


            id=Integer.parseInt(cursor.getString(idIndex));
            jobTitle=cursor.getString(jobTitleIndex);
            jobLocation=cursor.getString(jobLocationIndex);
            jobDate=cursor.getString(jobDateIndex);
            jobURL=cursor.getString(jobURLIndex);
            status=cursor.getString(statusIndex);


            DataList dataList=new DataList();
            dataList.setId(id);
           dataList.setJobTitle(jobTitle);
           dataList.setJobLocation(jobLocation);
           dataList.setJobDate(jobDate);
           dataList.setJobURL(jobURL);
           dataList.setStatus(status);

            dataLists.add(dataList);
        }

        return dataLists;
    }

    public ArrayList<OpenStatus> getOpenJobs()

    {
        String st="Open";
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String query = "SELECT id FROM tblJob WHERE status ='"+st+"'" ;
        Cursor  cursor = sqLiteDatabase.rawQuery(query,null);
ArrayList<OpenStatus> data=new ArrayList<>();
        int id;
        int idIndex;
       while (cursor.moveToNext()){
           idIndex=cursor.getColumnIndex("id");
           id=Integer.parseInt(cursor.getString(idIndex));
           OpenStatus openStatus=new OpenStatus();
           openStatus.setId(id);
           data.add(openStatus);
       }
return data;
    }


    public ArrayList<CLoseStatus> getCLOSEJobs()

    {
        String st="Close";
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String query = "SELECT id FROM tblJob WHERE status ='"+st+"'" ;
        Cursor  cursor = sqLiteDatabase.rawQuery(query,null);
        ArrayList<CLoseStatus> data=new ArrayList<>();
        int id;
        int idIndex;
        while (cursor.moveToNext()){
            idIndex=cursor.getColumnIndex("id");
            id=Integer.parseInt(cursor.getString(idIndex));
            CLoseStatus cLoseStatus=new CLoseStatus();
            cLoseStatus.setId(id);
            data.add(cLoseStatus);
        }
        return data;
    }


    public long delete(int id){

        SQLiteDatabase db=this.getWritableDatabase();
        long del;
        del = db.delete("tblJob","id=?",new String[]{String.valueOf(id)});
        return del;

    }
    public long update( int id, String jobTitle, String jobLocation, String jobDate, String jobURL, String status){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put("id",id);
        c.put("jobTitle",jobTitle);
        c.put("jobLocation",jobLocation);
        c.put("jobDate",jobDate);
        c.put("jobURL",jobURL);
        c.put("status",status);
        long result=db.update("tblJob",c,"id=?",new String[]{String.valueOf(id)});
        return result;

    }



}
