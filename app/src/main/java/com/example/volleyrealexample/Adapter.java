package com.example.volleyrealexample;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {
    List<GitObject> list;
    List<GitObject> listAll;
    Context context;

    public Adapter(List<GitObject> list, Context context) {
        this.list = list;
        listAll = new ArrayList<>(list);
        this.context = context;
    }


    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
//        holder.license.setText(list.get(position).getLicense());
        holder.id.setText(list.get(position).getId());
        holder.name.setText(list.get(position).getFull_name());
        holder.size.setText(list.get(position).getSize());
        Glide.with(context).load(list.get(position).getAvatar_url()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(),DetailsActivity.class);
            intent.putExtra("license",list.get(position).getLicense());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GitObject> filtered = new ArrayList<>();
            if(constraint.toString().isEmpty())
                filtered.addAll(listAll);
            else{
                for(GitObject git:listAll){
                    if(git.getFull_name().toLowerCase().contains(constraint.toString().toLowerCase())){ // better: contains, or startsWith!!!!!!!!!!!!!
                        filtered.add(git);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filtered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends GitObject>) results.values);
            notifyDataSetChanged();

        }
    };
    @Override
    public Filter getFilter() {
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,id,size,license;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            id = (TextView) itemView.findViewById(R.id.id_tv);
            size = (TextView) itemView.findViewById(R.id.size);
//            license = (TextView) itemView.findViewById(R.id.license);
            imageView = (ImageView) itemView.findViewById(R.id.avatar);

        }
    }
}
