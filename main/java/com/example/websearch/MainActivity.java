package com.example.websearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    Button mButtonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSend = (Button)findViewById(R.id.buttonSend);
        mButtonSend.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)){
            mButtonSend.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }

    }

    public void webSearch(View view){
        String searchFor = ((EditText) findViewById(R.id.editText)) .getText() .toString();
        Intent viewSearch = new Intent(Intent.ACTION_WEB_SEARCH);
        viewSearch.putExtra(SearchManager.QUERY, searchFor);
        startActivity(viewSearch);
    }

    private boolean checkPermission(String permission){
        int permissionCheck =
                ContextCompat.checkSelfPermission(this, permission);
        return (permissionCheck == PackageManager.PERMISSION_GRANTED);
    }

    public void dialPhone(View view){
        if(checkPermission("android.permission.CALL_PHONE")){
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:07951194262"));
            startActivity(intent);
        }
    }

    public void send(View view){
        String phoneNumber = ((EditText)findViewById(R.id.editTextNumber)).getText() .toString();
        String msg = ((EditText)findViewById(R.id.editTextMsg)).getText().toString();
        if (phoneNumber==null || phoneNumber.length()==0 || msg==null || msg.length()==0){
            return;
        }
        if (checkPermission2(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
            Toast.makeText(MainActivity.this, "Your Message has been sent!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "No Permission", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission2(String permission2){
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission2);
        return (permissionCheck == PackageManager.PERMISSION_GRANTED);
    }



    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mButtonSend.setEnabled(true);
                }
                return;
            }
        }
    }


}
