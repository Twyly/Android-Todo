package com.example.teddywyly.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by teddywyly on 4/15/15.
 */
public class ToDoAdapter extends ArrayAdapter<TodoItem> {

    //ViewHolder Pattern and ReuseIdentifier have same purpose
    private static class ViewHolder {
        TextView name;
        TextView rank;
    }

    public ToDoAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TodoItem todoItem = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_todo, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.rank = (TextView) convertView.findViewById(R.id.tvRank);
            viewHolder.name.setTextColor(Color.WHITE);
            viewHolder.rank.setTextColor(Color.WHITE);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(todoItem.getBody());
        switch (todoItem.getPriority()) {
            case 0:
                viewHolder.rank.setText("Garfield Status");
                convertView.setBackgroundColor(Color.rgb(187, 255, 0));
                break;
            case 1:
                viewHolder.rank.setText("Normal");
                convertView.setBackgroundColor(Color.rgb(250, 255, 0));
                break;
            case 2:
                viewHolder.rank.setText("Do Now!");
                convertView.setBackgroundColor(Color.rgb(255, 207, 0));
                break;
            default:
                viewHolder.rank.setText("Unknown");
                break;
        }
        return convertView;
    }


}
