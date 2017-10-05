package com.antonhellbegmail.assignment2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-10-04.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    private ArrayList<TextMessage> messageList;
    private ChatFragment chatFragment;

    public ChatAdapter(ChatFragment fragment){
        this.chatFragment = fragment;
        messageList = new ArrayList<>();
    }

    public void setData(ArrayList<TextMessage> messageList){
        this.messageList = messageList;
        notifyDataSetChanged();
    }


    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout3, parent, false);
        ChatAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        holder.tvMessage.setText(messageList.get(position).getText());
        holder.tvMemName.setText(messageList.get(position).getMember());
        if(!(messageList.get(position).getImageId().equals(""))){
            holder.customImageView.init(messageList.get(position).getImageId(), messageList.get(position).getPort(), chatFragment);

        }else{
            holder.customImageView.setImageBitmap(null);
        }


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rv;
        TextView tvMessage;
        TextView tvMemName;
        CustomImageView customImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            tvMessage = (TextView) itemView.findViewById(R.id.tvTextMesssage);
            tvMemName = (TextView) itemView.findViewById(R.id.tvMemName);
            customImageView = (CustomImageView) itemView.findViewById(R.id.imCustom);
        }

    }
}
