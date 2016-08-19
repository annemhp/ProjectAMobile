package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.development.mymla.R;
import com.example.development.mymla.StatusDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Models.Status;
import Models.StatusDetail;

/**
 * Created by Development on 8/13/2016.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder>  {
    private List<Status> status;
    private int rowLayout;
    private Context context;

    public StatusAdapter(List<Status> status, int rowLayout, Context context) {
        this.status = status;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public StatusAdapter.StatusViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new StatusViewHolder(view);
    }



    @Override
    public void onBindViewHolder(StatusViewHolder holder, final int position) {


        holder.StatusComplaintNo.setText(status.get(position).getComplaintNo());
        holder.statusDate.setText(status.get(position).getDate().toString().substring(4,10));
        holder.statusTime.setText(status.get(position).getDate().toString().substring(10,19));

        holder.statusSubject.setText(status.get(position).getSubject());
        if(status.get(position).getStatus().equals("resolved")) {
            holder.statusSts.setText(status.get(position).getStatus());
            holder.statusSts.setTextColor(Color.parseColor("#45b445"));
            holder.statusImage.setImageResource(R.drawable.statussolved);
        }
        else if(status.get(position).getStatus().equals("inprogress")) {
            holder.statusSts.setText(status.get(position).getStatus());
            holder.statusSts.setTextColor(Color.parseColor("#ea870d"));
            holder.statusImage.setImageResource(R.drawable.statuspending);
        }
        else{
            holder.statusSts.setText(status.get(position).getStatus());

            holder.statusImage.setImageResource(R.drawable.statusonhold);
        }
    }


    @Override
    public int getItemCount() {
        return status.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout statusMasterLayout;
        ImageView statusImage;
        TextView statusDate,statusTime;
        TextView StatusComplaintNo;
        TextView statusSubject;
        TextView statusSts;



        public StatusViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            statusMasterLayout = (LinearLayout) v.findViewById(R.id.status_layout);
            statusImage = (ImageView) v.findViewById(R.id.statusImage);
            statusDate = (TextView) v.findViewById(R.id.statusDate);
            statusTime = (TextView) v.findViewById(R.id.statusTime);
            StatusComplaintNo = (TextView) v.findViewById(R.id.statusComplaintno);
            statusSubject = (TextView) v.findViewById(R.id.statusSubject);
            statusSts = (TextView) v.findViewById(R.id.statusSts);

        }

        @Override
        public void onClick(View v) {
            if((status.get(getAdapterPosition()).updates) != null) {
                ArrayList<StatusDetail> statusDetail = new ArrayList<StatusDetail>(status.get(getAdapterPosition()).getUpdates().values());
                Intent i = new Intent(context, StatusDetailActivity.class);
                i.putParcelableArrayListExtra("ProductId", statusDetail);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
            else
            {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getRootView().getContext());
                builder1.setMessage("There are no updates for this Complaint.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        }
    }
}
