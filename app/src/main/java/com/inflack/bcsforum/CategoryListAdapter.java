package com.inflack.bcsforum;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inflack.bcsforum.model.CategoryDTO;
import com.inflack.bcsforum.model.MemberDTO;
import com.inflack.bcsforum.rest.ApiClient;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<CategoryDTO> categoryDTOS;
    private OnItemSelectListener listener;

    public CategoryListAdapter(List<CategoryDTO> categoryDTOS) {
        this.categoryDTOS = categoryDTOS;
    }

    public interface OnItemSelectListener {
        void onItemSelected(int position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item_layout, viewGroup, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        CategoryDTO categoryDTO = categoryDTOS.get(position);
        MainHolder mainHolder = (MainHolder) viewHolder;

        mainHolder.tv_name.setText(categoryDTO.getName());
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemSelected(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryDTOS == null ? 0 : categoryDTOS.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }
}
