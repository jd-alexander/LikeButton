package com.like.example.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.example.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joel on 3/3/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private Activity activity;
    private List<Integer> numbers;
    private final int max = 30;


    public ListAdapter(Activity activity) {

        this.activity = activity;
    }

    private void generateNumbers()
    {
        numbers = new ArrayList<>();

        for(int x=1;x<=max;x++)
        {
            numbers.add(x);
        }
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.list_row, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        holder.title.setText(String.valueOf(position));
        if(position%2==0)
        {
            holder.starButton.setLiked(true);
        }
        else
            holder.starButton.setLiked(false);

    }

    @Override
    public int getItemCount() {
        return max;
    }


    static class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.star_button)
        LikeButton starButton;

        ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
