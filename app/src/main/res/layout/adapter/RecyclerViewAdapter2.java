package com.example.kismaatproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kismaatproject.LoadingScreen4;
import com.example.kismaatproject.R;

import java.util.ArrayList;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {

    public Context context;
    public ArrayList<String> lotterycode2;
    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private String ticktotal;
    private String amount;
    private String requiredValue[];
    private int x;
    private int index1;
    private int index2;
    private ArrayList<String> contestnamevalue=new ArrayList<String>();


    public RecyclerViewAdapter2(ArrayList<String> lotterycode,String currentUpi1,String currentUsername1,String currentName1,String currentPassword1,String userId1,String tickettotal,String gpayamount) {
        lotterycode2=lotterycode;
        currentUpi=currentUpi1;
        currentUsername=currentUsername1;
        currentPassword=currentPassword1;
        currentName=currentName1;
        userId=userId1;
        ticktotal=tickettotal;
        amount=gpayamount;
        requiredValue=new String[lotterycode2.size()];
    }

    @NonNull
    @Override
    public RecyclerViewAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        for(x=0;x<lotterycode2.size();x++)
        {
            requiredValue[x]=(String)lotterycode2.get(x);
            index1=requiredValue[x].indexOf("c");
            index2=requiredValue[x].indexOf("N");
            contestnamevalue.add(requiredValue[x].substring(index1+1,index2));
        }
        holder.lotterycode.setText((String)lotterycode2.get(position));
        holder.contestname.setText("Contest"+(String)contestnamevalue.get(position));
    }

    @Override
    public int getItemCount() {
        return lotterycode2.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{


        public TextView lotterycode;
        public ImageView ticketlogodownload;
        public TextView contestname;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();

            ticketlogodownload=(ImageView) itemView.findViewById(R.id.ticketlogodownload);
            lotterycode=(TextView) itemView.findViewById(R.id.lotterycode);
            contestname=(TextView)itemView.findViewById(R.id.contestname);

            ticketlogodownload.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                int position=this.getAdapterPosition();
                Intent result=new Intent(context, LoadingScreen4.class);
                result.putExtra("currentName",currentName);
                result.putExtra("currentPhoneNumber", currentUpi);
                result.putExtra("currentUserName", currentUsername);
                result.putExtra("currentPassword", currentPassword);
                result.putExtra("userId",userId);
                result.putExtra("tickettotal",ticktotal);
                result.putExtra("lotterycode",(String)lotterycode2.get(position));
                result.putExtra("gpayamount",amount);
                context.startActivity(result);
            }
            catch (Exception e)
            {
                e.getStackTrace();
            }
        }
    }
}


