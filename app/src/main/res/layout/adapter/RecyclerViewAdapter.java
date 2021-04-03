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

import com.example.kismaatproject.R;
import com.example.kismaatproject.UserAccountScreen3;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public Context context;
    public ArrayList<String> entryfee2;
    public ArrayList<String> winamount2;
    public ArrayList<String> tickets2;
    private String currentUpi;
    private String currentUsername;
    private String currentPassword;
    private String currentName;
    private String userId;
    private ArrayList<String> googleamount;


    public RecyclerViewAdapter(ArrayList<String> entryfee,ArrayList<String> winamount,ArrayList<String> tickets,String currentUpi1,String currentUsername1,String currentName1,String currentPassword1,String userId1,ArrayList<String> googlepayamount) {
        entryfee2=entryfee;
        winamount2=winamount;
        tickets2=tickets;
        currentUpi=currentUpi1;
        currentUsername=currentUsername1;
        currentPassword=currentPassword1;
        currentName=currentName1;
        userId=userId1;
        googleamount=googlepayamount;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.amountentry.setText((String)entryfee2.get(position));
        if(position == 0) {
            holder.amountwin.setText((String) winamount2.get(position));
            holder.amountwin2.setText((String) winamount2.get(position + 1));
            holder.amountwin3.setText((String) winamount2.get(position + 2));
            holder.amountwin4.setText((String) winamount2.get(position + 3));
        }
        if(position >  0)
        {
           if(position == 1) {
               holder.amountwin.setText((String) winamount2.get(position + 3));
               holder.amountwin2.setText((String) winamount2.get(position + 3 + 1));
               holder.amountwin3.setText((String) winamount2.get(position + 3 + 2));
               holder.amountwin4.setText((String) winamount2.get(position + 3 + 3));
           }
            if(position == 2) {
                holder.amountwin.setText((String) winamount2.get(position + 6));
                holder.amountwin2.setText((String) winamount2.get(position + 6 + 1));
                holder.amountwin3.setText((String) winamount2.get(position + 6 + 2));
                holder.amountwin4.setText((String) winamount2.get(position + 6 + 3));
            }
            if(position == 3) {
                holder.amountwin.setText((String) winamount2.get(position + 9));
                holder.amountwin2.setText((String) winamount2.get(position + 9 + 1));
                holder.amountwin3.setText((String) winamount2.get(position + 9 + 2));
                holder.amountwin4.setText((String) winamount2.get(position + 9 + 3));
            }
            if(position == 4) {
                holder.amountwin.setText((String) winamount2.get(position + 12));
                holder.amountwin2.setText((String) winamount2.get(position + 12 + 1));
                holder.amountwin3.setText((String) winamount2.get(position + 12 + 2));
                holder.amountwin4.setText((String) winamount2.get(position + 12 + 3));
            }
        }
        holder.tickava.setText((String)tickets2.get(position));
    }

    @Override
    public int getItemCount() {
        return entryfee2.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ticketlogo;
        public TextView entryfee;
        public TextView winamount;
        public TextView amountentry;
        public TextView amountwin;
        public TextView amountwin2;
        public TextView amountwin3;
        public TextView amountwin4;
        public TextView ticketav;
        public TextView tickava;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();

            ticketlogo=(ImageView)itemView.findViewById(R.id.ticketlogo);
            entryfee=(TextView) itemView.findViewById(R.id.entryfee);
            winamount=(TextView)itemView.findViewById(R.id.winamount);
            amountentry=(TextView) itemView.findViewById(R.id.amountentry);
            amountwin=(TextView) itemView.findViewById(R.id.amountwin);
            amountwin2=(TextView) itemView.findViewById(R.id.amountwin2);
            amountwin3=(TextView) itemView.findViewById(R.id.amountwin3);
            amountwin4=(TextView) itemView.findViewById(R.id.amountwin4);
            ticketav=(TextView) itemView.findViewById(R.id.ticketav);
            tickava=(TextView) itemView.findViewById(R.id.tickava);

            ticketlogo.setOnClickListener(this);


        }



        @Override
        public void onClick(View view) {
           int position=this.getAdapterPosition();
           String gpayamount=(String)googleamount.get(position);
           String ticktotal=(String)tickets2.get(position);
           try {
               Intent userscreen3=new Intent(context,UserAccountScreen3.class);
               userscreen3.putExtra("currentName",currentName);
               userscreen3.putExtra("currentPhoneNumber", currentUpi);
               userscreen3.putExtra("currentUserName", currentUsername);
               userscreen3.putExtra("currentPassword", currentPassword);
               userscreen3.putExtra("userId",userId);
               userscreen3.putExtra("googlepayamount",gpayamount);
               userscreen3.putExtra("tickettotal",ticktotal);
               context.startActivity(userscreen3);
           }
           catch (Exception e)
           {
               e.getStackTrace();
           }
        }
    }

}


