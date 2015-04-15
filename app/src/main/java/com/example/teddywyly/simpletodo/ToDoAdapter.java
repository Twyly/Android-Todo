package com.example.teddywyly.simpletodo;

import android.content.Context;
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


    private static class ViewHolder {
        TextView name;
        TextView body;
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
            viewHolder.body = (TextView) convertView.findViewById(R.id.tvHome);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(todoItem.getBody());
        viewHolder.body.setText(todoItem.getBody());

        return convertView;
    }


}
