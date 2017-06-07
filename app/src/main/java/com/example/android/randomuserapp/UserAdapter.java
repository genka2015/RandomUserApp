package com.example.android.randomuserapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter{

    private ArrayList<User> users;
    private Context context;

    public UserAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // check if it hasnt been inflated yet
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        User currentUser = (User) getItem(position);
        holder.userInfo.setText(currentUser.getName() + " " + currentUser.getAddress() + " " + currentUser.getEmail());

        return convertView;
    }

    private class ViewHolder{
        TextView userInfo;

        public ViewHolder(View view){
            userInfo = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
