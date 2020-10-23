package com.example.recyclerview_exapmple;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter <ExampleAdapter.ExampleViewHolder>{
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick (int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewType;
        public TextView mTextViewWhom;
        public TextView mTextViewAmount;
        public TextView mTextViewDate;
        public TextView mTextViewDesc;
        public ImageView mDeleteImage;
        public ImageView mEditImage;

        public ExampleViewHolder(@NonNull View itemView ,final OnItemClickListener listener) {
            super(itemView);
            mTextViewType = itemView.findViewById(R.id.textViewType);
            mTextViewAmount = itemView.findViewById(R.id.textViewAmount);
            mTextViewDate = itemView.findViewById(R.id.textViewDate);
            mTextViewDesc = itemView.findViewById(R.id.textViewDesc);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
            mEditImage = itemView.findViewById(R.id.image_edit);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            mEditImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }
    public ExampleAdapter(ArrayList<ExampleItem> exampleList){
        mExampleList = exampleList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item ,  parent , false);
        ExampleViewHolder evh= new ExampleViewHolder(v , mListener);
        return evh;

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem =  mExampleList.get(position);
        holder.mTextViewDesc.setText(currentItem.getmDesc());
        holder.mTextViewType.setText(currentItem.getType());
        holder.mTextViewAmount.setText(currentItem.getAmount() + currentItem.getmCurrency());
        holder.mTextViewDate.setText(currentItem.getmDate());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
