package com.example.policeandthief;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RoomAdaptor extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<RoomItem> roomList = null;
    private ArrayList<RoomItem> listview;

    public RoomAdaptor(Context context, List<RoomItem> roomList) {
        this.context = context;
        this.roomList = roomList;
        inflater = LayoutInflater.from(context);
        this.listview = new ArrayList<RoomItem>();
        this.listview.addAll(roomList);
    }

    public void setBoardList(List<RoomItem> roomList) {
        this.roomList = roomList;
    }

    @Override
    public int getCount(){
        return roomList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final RoomAdaptor.ViewHolder holder;
        final RoomItem potion = roomList.get(position);

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            holder = new RoomAdaptor.ViewHolder();
            convertView = inflater.inflate(R.layout.activity_room_list, null);

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            holder.tv1 = (TextView) convertView.findViewById(R.id.txt_room_name) ;
            holder.tv2 = (TextView) convertView.findViewById(R.id.txt_id) ;
            holder.tv3= (TextView) convertView.findViewById(R.id.txt_c_persons) ;
            convertView.setTag(holder);
        }
        else{
            holder = (RoomAdaptor.ViewHolder) convertView.getTag();
        }

        holder.tv1.setText(potion.getRoomName());
        holder.tv2.setText(potion.getMasterId());
        holder.tv3.setText(potion.getPersons() + "/2");

        return convertView;
    }

    public class ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public RoomItem getItem(int position) {
        return roomList.get(position) ;
    }

}
