package Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymla.development.hsb.R;
import com.mymla.development.hsb.StatusDetailActivity;

import java.util.ArrayList;
import java.util.List;

import Models.Status;
import Models.StatusDetail;

/**
 * Created by Development on 8/13/2016.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public void onBindViewHolder(StatusViewHolder holder, final int position) {


        holder.StatusComplaintNo.setText(status.get(position).getComplaintNo());
        holder.statusDate.setText(status.get(position).getDate().toString());
        holder.statusSubject.setText(status.get(position).getSubject());

        if (status.get(position).getStatus().equals("resolved")) {
            holder.statusMessage.setText(status.get(position).getStatus());
            Drawable resolved = ContextCompat.getDrawable(context, R.drawable.icon_resolved);
            holder.statusMessage.setCompoundDrawablesWithIntrinsicBounds(null, resolved, null, null);
            holder.statusMasterLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.green_color));
            holder.statusMessage.setTextColor(ContextCompat.getColor(context, R.color.green_color));

        } else if (status.get(position).getStatus().equals("inprogress")) {
            holder.statusMessage.setText(status.get(position).getStatus());
            Drawable inprogress = ContextCompat.getDrawable(context, R.drawable.icon_inprogress);
            holder.statusMessage.setCompoundDrawablesWithIntrinsicBounds(null, inprogress, null, null);
            holder.statusMasterLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.orange_color));
            holder.statusMessage.setTextColor(ContextCompat.getColor(context, R.color.orange_color));

        } else {
            holder.statusMessage.setText(status.get(position).getStatus());
            Drawable open = ContextCompat.getDrawable(context, R.drawable.icon_open);
            holder.statusMessage.setCompoundDrawablesWithIntrinsicBounds(null, open, null, null);
            holder.statusMasterLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.red_color));
            holder.statusMessage.setTextColor(ContextCompat.getColor(context, R.color.red_color));

        }
    }


    @Override
    public int getItemCount() {
        return status.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View statusMasterLayout;
        TextView statusDate, statusMessage;
        TextView StatusComplaintNo;
        TextView statusSubject;


        public StatusViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            statusMasterLayout = (View) v.findViewById(R.id.indicator);
            statusMessage = (TextView) v.findViewById(R.id.status_text);
            StatusComplaintNo = (TextView) v.findViewById(R.id.statusComplaintno);
            statusSubject = (TextView) v.findViewById(R.id.statusSubject);
            statusDate = (TextView) v.findViewById(R.id.statusDate);
        }

        @Override
        public void onClick(View v) {

            Status currentIssue = (status.get(getAdapterPosition()));
            ArrayList<StatusDetail> statusDetail = new ArrayList<StatusDetail>();
            if (currentIssue.getUpdates() != null) {
                statusDetail = new ArrayList<StatusDetail>(currentIssue.getUpdates().values());
            }


            Intent i = new Intent(context, StatusDetailActivity.class);
            i.putExtra("Department", currentIssue.department);
            i.putExtra("Place", currentIssue.place);


            i.putExtra("Name", currentIssue.name);
            i.putExtra("ImageRef", currentIssue.getImageUri() != null ? currentIssue.getImageUri() : "");
            i.putExtra("Problem", currentIssue.getProblem());
            i.putParcelableArrayListExtra("Updates", statusDetail);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
    }
}
