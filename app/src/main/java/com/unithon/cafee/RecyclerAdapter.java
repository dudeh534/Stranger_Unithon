package com.unithon.cafee;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Youngdo on 2016-01-19.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<Recycler_item> items;
    int item_layout;

    public RecyclerAdapter(Context context, List<Recycler_item> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, null);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recycler_item item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.workgroup_type.setText(item.getWorkgroup_type());
        holder.workplace.setText(item.getWorkplace_name());
        holder.joinuser.setText(String.valueOf(item.getJoin_user_count()));
        holder.maxuser.setText(String.valueOf(item.getMax_user_count()));
        holder.create_at.setText(item.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        TextView title, workgroup_type, workplace, joinuser, maxuser, create_at;
        RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            workgroup_type = (TextView) itemView.findViewById(R.id.workgroup_type);
            workplace = (TextView) itemView.findViewById(R.id.workplace);
            joinuser = (TextView) itemView.findViewById(R.id.join);
            maxuser = (TextView) itemView.findViewById(R.id.max);
            create_at = (TextView) itemView.findViewById(R.id.create_at);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_background);
        }
    }
}