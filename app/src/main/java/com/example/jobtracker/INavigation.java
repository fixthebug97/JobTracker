package com.example.jobtracker;

import android.view.View;

public interface INavigation {
    void toJobList(View view);
    void toAddJobList(View view);
    void goBackToJobList(View view);
    void goToInfo(View view, DataList dataList);
    void gobacktoInfo(View view);

}
