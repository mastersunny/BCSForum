package com.inflack.bcsforum;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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

    LinearLayout layout1;

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

        layout1 = findViewById(R.id.layout1);
        layout1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.member_name:
                Intent intent = new Intent(context, MemberListActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
            case R.id.img_cancel:
                dismiss();
                break;
            case R.id.layout1:
                Uri uri = Uri.parse("https://m.me/join/AbbAGxSTwKRcivB9"); // missing 'http://' will cause crashed
                intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
                break;

        }
    }


}
