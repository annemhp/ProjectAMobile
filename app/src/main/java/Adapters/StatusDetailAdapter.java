package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymla.development.hsb.R;

import java.util.ArrayList;

import Models.StatusDetail;

/**
 * Created by Development on 8/13/2016.
 */

public class StatusDetailAdapter extends RecyclerView.Adapter<StatusDetailAdapter.StatusViewHolder> {
    private ArrayList<StatusDetail> status;
    private int rowLayout;
    private Context context;

    public StatusDetailAdapter(ArrayList<StatusDetail> status, int rowLayout, Context context) {
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
        holder.statusDetail.setText(status.get(position).getStatus());
        if(status.get(position).getDate() != null) {
            holder.dateDetail.setText((status.get(position).getDate().substring(0, 10)));
        }
        if(status.get(position).getStatus().equals("inprogress")){
            holder.statusDetail.setTextColor(Color.parseColor("#ff5200"));
        } else if(status.get(position).getStatus().equals("resolved")){
            holder.statusDetail.setTextColor(Color.parseColor("#00bc31"));
        }else{
            holder.statusDetail.setTextColor(Color.parseColor("#ff0000"));
        }
    }

    @Override
    public int getItemCount() {
        return status.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {
        TextView statusDetail, dateDetail, StatusDetailsMessage;

        public StatusViewHolder(View v) {
            super(v);
            statusDetail = (TextView) v.findViewById(R.id.status_header);
            dateDetail = (TextView) v.findViewById(R.id.dateDetail);
            StatusDetailsMessage = (TextView) v.findViewById(R.id.statusMessage);
        }


    }
}
