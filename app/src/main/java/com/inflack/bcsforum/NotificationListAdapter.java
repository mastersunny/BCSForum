package com.inflack.bcsforum;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inflack.bcsforum.model.MemberDTO;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<MemberDTO> memberDTOS;
    private OnItemSelectListener listener;
    private Context context;

    public NotificationListAdapter(Context context, List<MemberDTO> memberDTOS) {
        this.context = context;
        this.memberDTOS = memberDTOS;
    }

    public interface OnItemSelectListener {
        void onItemSelected(int position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item_layout, viewGroup, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        MemberDTO memberDTO = memberDTOS.get(position);
//        MainHolder mainHolder = (MainHolder) viewHolder;
//        int res = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + memberDTOS.get(position).getProfilePicture(), null, null);
//        mainHolder.img_profile.setImageResource(res);
//
//        mainHolder.tv_name.setText(memberDTO.getName());
//        mainHolder.tv_id_no.setText("পরিচিতি নম্বরঃ " + memberDTOS.get(position).getIdNo());
//        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onItemSelected(position);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return memberDTOS == null ? 0 : memberDTOS.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.img_profile)
//        CircularImageView img_profile;
//
//        @BindView(R.id.tv_name)
//        TextView tv_name;
//
//        @BindView(R.id.tv_id_no)
//        TextView tv_id_no;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }
}
