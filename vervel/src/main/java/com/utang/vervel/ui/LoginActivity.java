package com.utang.vervel.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import com.utang.vervel.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    private ProgressBar login_progress;
    private EditText password;
    private Button email_sign_in_button;
    private LinearLayout email_login_form;
    private ScrollView login_form;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        login_progress = (ProgressBar) findViewById(R.id.login_progress);
        password = (EditText) findViewById(R.id.password);
        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        email_login_form = (LinearLayout) findViewById(R.id.email_login_form);
        login_form = (ScrollView) findViewById(R.id.login_form);

        email_sign_in_button.setOnClickListener(this);
        email = (EditText) findViewById(R.id.email);
        email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                Boolean canLogin = submit();

                if (canLogin) {
                    login_progress.setVisibility(View.VISIBLE);

                    //提交数据

                    //获取登录结果
                }

                break;
        }
    }


    private Boolean submit() {
        String emailString = email.getText().toString().trim();
        if (TextUtils.isEmpty(emailString)) {
            Toast.makeText(this, "emailString不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "passwordString不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
}

