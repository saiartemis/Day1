package com.example.zhangxian.day1;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
    private String phone_1;
    private String phone_2;
    private String smsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Button button_call = findViewById(R.id.button_call);
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone_1 = ((EditText)findViewById(R.id.phone_1)).getText().toString().trim();
                if (phone_1 == null || phone_1.equals("")) {
                    Toast.makeText(MainActivity.this, "电话为空", Toast.LENGTH_SHORT).show();
                } else {
                    performCodeWithPermission("需要打电话的权限", new PermissionCallback() {
                        @Override
                        public void hasPermission() {
                            callPhone(phone_1);
                        }

                        @Override
                        public void noPermission() {
                            Toast.makeText(MainActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                        }
                    }, Manifest.permission.CALL_PHONE);
                }

            }
        });

        Button button_reset1 = findViewById(R.id.button_reset1);
        button_reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText)findViewById(R.id.phone_1)).setText("");
            }
        });

        Button button_sms = findViewById(R.id.button_sms);
        button_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone_2 = ((EditText)findViewById(R.id.phone_2)).getText().toString().trim();
                smsText = ((EditText)findViewById(R.id.sms_1)).getText().toString().trim();
                if(phone_2 == null || phone_2.equals("")||smsText == null || smsText.equals(""))
                {
                    Toast.makeText(MainActivity.this, "电话为空", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    performCodeWithPermission("需要发短信的权限", new PermissionCallback() {
                        @Override
                        public void hasPermission() {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phone_2,null,smsText,null,null);
                            Toast.makeText(MainActivity.this,"发送短消息成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void noPermission() {
                            Toast.makeText(MainActivity.this, "没有权限", Toast.LENGTH_SHORT).show();
                        }
                    },Manifest.permission.SEND_SMS);
                }
            }
        });

        Button button_reset2 = findViewById(R.id.button_reset2);
        button_reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText)findViewById(R.id.phone_2)).setText("");
                ((EditText)findViewById(R.id.sms_1)).setText("");
            }
        });

    }

    private void callPhone(String phone) {
        // 1.创建一个意图对象
        Intent intent = new Intent();
        // 2.设置动作
        intent.setAction(Intent.ACTION_CALL);
        // 3.指定动作的数据
        intent.setData(Uri.parse("tel://" + phone));
        // 4.开启一个界面，根据程序员指定的行为来做事情
        startActivity(intent);
    }
}
