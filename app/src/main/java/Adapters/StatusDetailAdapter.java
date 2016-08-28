package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mymla.development.hsb.R;

import java.util.ArrayList;

import Models.StatusDetail;

/**
 * Created by Development on 8/13/2016.
 */

public class StatusDetailAdapter extends RecyclerView.Adapter<StatusDetailAdapter.StatusViewHolder>  {
    private ArrayList<StatusDetail> status;
    private int rowLayout;
    private Context context;

    public StatusDetailAdapter(ArrayList<StatusDetail> status,int rowLayout, Context context) {

        this.status = status;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public StatusDetailAdapter.StatusViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new StatusViewHolder(view);
    }



    @Override
    public void onBindViewHolder(StatusViewHolder holder, final int position) {

        holder.StatusDetailsMessage.setText(status.get(position).getMessage());
        holder.statusDetail.setText("Status : "+status.get(position).getStatus());
        holder.dateDetail.setText((status.get(position).getDate().substring(0,10)));

    }


    @Override
    public int getItemCount() {
        return status.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout status_detail_layout;
        TextView statusDetail,dateDetail;
        TextView StatusDetailsMessage;



        public StatusViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            status_detail_layout = (LinearLayout) v.findViewById(R.id.status_detail_layout);
            statusDetail = (TextView) v.findViewById(R.id.statusDetail);
            dateDetail = (TextView) v.findViewById(R.id.dateDetail);
            StatusDetailsMessage = (TextView) v.findViewById(R.id.statusMessage);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
