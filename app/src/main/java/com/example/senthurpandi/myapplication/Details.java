package com.example.senthurpandi.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Senthurpandi on 20-Nov-16.
 */

public class Details extends Activity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        String eMail= getIntent().getStringExtra("Email");
        String name= getIntent().getStringExtra("Username");
        String pno= getIntent().getStringExtra("Contactnumber");
        String time= getIntent().getStringExtra("Time");
        Bitmap bitmap=getIntent().getParcelableExtra("Image");
        TextView mEmail,mName,mPno,mTime;
        ImageView userImage=(ImageView) findViewById(R.id.profileImg);
        mEmail= (TextView) findViewById(R.id.welcomeEMail);
        mName= (TextView) findViewById(R.id.welcomeName);
        mPno= (TextView) findViewById(R.id.welcomePhoneNo);
        mTime= (TextView) findViewById(R.id.resTime);
        userImage.setImageBitmap(bitmap);
        mEmail.setText(eMail);
        mName.setText(name);
        mPno.setText(pno);
        mTime.setText(time);

    }

    }
