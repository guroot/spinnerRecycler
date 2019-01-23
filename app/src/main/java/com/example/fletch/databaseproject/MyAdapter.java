package com.example.fletch.databaseproject;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fletch.databaseproject.Model.Enseignant.Enseignant;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Enseignant> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View mCellView;
        public MyViewHolder(View v) {
            super(v);
            mCellView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Enseignant>  myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.enseignant, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       // holder.mTextView.setText(mDataset.get(position).getNom());
        ((TextView)holder.itemView.findViewById(R.id.textView_enseignant))
                .setText(mDataset.get(position).getNom());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
