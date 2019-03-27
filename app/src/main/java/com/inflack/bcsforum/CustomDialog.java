package com.inflack.bcsforum;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity context;

    LinearLayout member_name;

    ImageView img_cancel;

    public CustomDialog(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        member_name = findViewById(R.id.member_name);
        member_name.setOnClickListener(this);

        img_cancel = findViewById(R.id.img_cancel);
        img_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.member_name:
                Log.d("TAG", "safd");
                Intent intent = new Intent(context, MemberListActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
            case R.id.img_cancel:
                dismiss();
                break;

        }
    }


}
