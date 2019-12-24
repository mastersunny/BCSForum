package com.inflack.bcsforum;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyDialogFragment extends DialogFragment {

    public String TAG = "MyDialogFragment";
    private Unbinder unbinder;
    private static boolean addPhone = false;
    private static boolean addEmail = false;

    @BindView(R.id.edt_phone_no)
    EditText edt_phone_no;

    @BindView(R.id.edt_email)
    EditText edt_email;

    @BindView(R.id.edt_phone_layout)
    LinearLayout edt_phone_layout;

    @BindView(R.id.edt_email_layout)
    LinearLayout edt_email_layout;

    static AddPhoneEmailUpdateListener listener;

    public static void show(Context context, boolean phnAdd, boolean emailAdd, AddPhoneEmailUpdateListener list) {
        addPhone = phnAdd;
        addEmail = emailAdd;
        listener = list;
        AppCompatActivity activity = (AppCompatActivity) context;
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(MyDialogFragment.class.getName());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(ft, MyDialogFragment.class.getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_dialog_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 20);
        getDialog().getWindow().setBackgroundDrawable(inset);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        initLayout();
    }

    private void initLayout() {
        if (addPhone) {
            edt_phone_layout.setVisibility(View.VISIBLE);
            edt_email_layout.setVisibility(View.GONE);
        } else if (addEmail) {
            edt_email_layout.setVisibility(View.VISIBLE);
            edt_phone_layout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_update})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                updatePhoneOrEmail();
                break;

        }
    }

    private void updatePhoneOrEmail() {
        if (addPhone) {
            listener.updatePhoneOrEmail(edt_phone_no.getText().toString().trim(),
                    "");
        } else if (addEmail) {
            listener.updatePhoneOrEmail("",
                    edt_email.getText().toString().trim());
        }
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setLayout(width, height);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
