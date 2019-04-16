package com.example.jobtracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class JobList extends Fragment {
    private Database db;
    private FloatingActionButton AddJOb;
    private TextView totalJobs;
    private TextView totalOpen;
    private TextView totalClose;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DataList> dataLists=new ArrayList<>();
    ArrayList<OpenStatus> openList=new ArrayList<>();
    ArrayList<CLoseStatus> closeList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.joblist,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = ((MainActivity) getActivity()).getDatabase();
        recyclerView=view.findViewById(R.id.myRecycler);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new RecyclerAdapter(getActivity(), getData(), new ICustomClick() {
            @Override
            public void onCustomClick(DataList dataProvider, View view) {
                ((INavigation)getActivity()).goToInfo(view,dataProvider);
                dataLists.clear();
                openList.clear();
                closeList.clear();
            }
        });
        recyclerView.setAdapter(adapter);
        AddJOb=view.findViewById(R.id.AddJob);
        totalJobs=view.findViewById(R.id.totalApplied);
        totalOpen=view.findViewById(R.id.totalOpen);
        totalClose=view.findViewById(R.id.totalClose);
        AddJOb();
        getOpenStatus();
        getClsoeStatus();
        totalJobs.setText("Total Applied: "+dataLists.size());
        totalOpen.setText("Open Jobs: "+openList.size());
        totalClose.setText("Close Jobs: "+closeList.size());
    }

    public void AddJOb(){

        AddJOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((INavigation) getActivity()).toAddJobList(view);
                dataLists.clear();
                closeList.clear();
                openList.clear();
            }
        });
    }

    public ArrayList<DataList> getData(){
for(DataList list:db.getAllTask())
{
list.getJobTitle();
list.getJobLocation();
list.getStatus();
dataLists.add(list);
}

return dataLists;
    }


    public ArrayList<OpenStatus>getOpenStatus(){

        for(OpenStatus openlist:db.getOpenJobs())
        {
          openlist.getId();
          openList.add(openlist);
        }
        return openList;
    }

    public ArrayList<CLoseStatus>getClsoeStatus(){

        for(CLoseStatus closelist:db.getCLOSEJobs())
        {
            closelist.getId();
            closeList.add(closelist);
        }
        return closeList;
    }
}
