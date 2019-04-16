package com.example.jobtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<DataList> data;
    ICustomClick iNavigation;

    public RecyclerAdapter(Context context, ArrayList<DataList> data,ICustomClick iNavigation) {
        this.context = context;
        this.data = data;
        this.iNavigation=iNavigation;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iNavigation.onCustomClick(data.get(holder.getAdapterPosition()),view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int i) {
holder.jobTitle.setText(data.get(i).getJobTitle());
holder.jobLocation.setText(data.get(i).getJobLocation());
String status=data.get(i).getStatus();
if(status.equals("Open")){
    holder.jobStatus.setBackgroundResource(R.drawable.correct);

}

else{

    holder.jobStatus.setBackgroundResource(R.drawable.closes);
}
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle;
        TextView jobLocation;
        ImageView jobStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle=itemView.findViewById(R.id.jobTitle);
            jobLocation=itemView.findViewById(R.id.jobLocation);
            jobStatus=itemView.findViewById(R.id.StatusIcon);

        }
    }
}
