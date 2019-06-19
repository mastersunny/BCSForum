package com.inflack.bcsforum;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.model.NotificationDTO;
import com.inflack.bcsforum.rest.ApiClient;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<NotificationDTO> notificationDTOS;
    private OnItemSelectListener listener;
    private Context context;

    public NotificationListAdapter(Context context, List<NotificationDTO> notificationDTOS) {
        this.context = context;
        this.notificationDTOS = notificationDTOS;
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
        NotificationDTO notificationDTO = notificationDTOS.get(position);
        MainHolder mainHolder = (MainHolder) viewHolder;

        if (!TextUtils.isEmpty(notificationDTO.getImage())) {
            Glide.with(context).load(ApiClient.BASE_URL + "storage" + "/" + notificationDTO.getImage())
                    .into(mainHolder.img_profile);
        }
        mainHolder.tv_message.setText(notificationDTO.getMessage());

    }

    @Override
    public int getItemCount() {
        return notificationDTOS == null ? 0 : notificationDTOS.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_profile)
        CircularImageView img_profile;

        @BindView(R.id.tv_message)
        TextView tv_message;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
