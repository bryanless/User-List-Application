package com.ss.week1_0706012010002.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ss.week1_0706012010002.R;
import com.ss.week1_0706012010002.model.OnCardClickListener;
import com.ss.week1_0706012010002.model.User;

import java.util.ArrayList;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.UserViewHolder> {

    private ArrayList<User> userList;
    private OnCardClickListener cardListener;

    public UserRVAdapter(ArrayList<User> userList, OnCardClickListener cardListener) {
        this.userList = userList;
        this.cardListener = cardListener;
    }

    @NonNull
    @Override
    public UserRVAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_user, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRVAdapter.UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.cardUser_textView_fullName.setText(user.getFullname());
        holder.cardUser_textView_age.setText(String.valueOf(user.getAge()));
        holder.cardUser_textView_address.setText(user.getAddress());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView cardUser_textView_fullName, cardUser_textView_age, cardUser_textView_address;

        public UserViewHolder(@NonNull View userView) {
            super(userView);

            cardUser_textView_fullName = itemView.findViewById(R.id.cardUser_textView_fullname);
            cardUser_textView_age = itemView.findViewById(R.id.cardUser_textView_age);
            cardUser_textView_address = itemView.findViewById(R.id.cardUser_textView_address);

            userView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
