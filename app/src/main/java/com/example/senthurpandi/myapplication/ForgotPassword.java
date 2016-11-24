package com.example.senthurpandi.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Senthurpandi on 23-Nov-16.
 */

public class ForgotPassword extends Activity {

    Toolbar title;
    Button checkEmail,changePassword;
    TextView question;
    String email,pass;
    LinearLayout linearLayout;
    Contact c;
    EditText Email,newPassword,newConfirmPassword,answerQues;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);
         linearLayout=(LinearLayout) findViewById(R.id.linear) ;
        Email=(EditText) findViewById(R.id.email) ;
        newPassword=(EditText) findViewById(R.id.new_password) ;
        newConfirmPassword=(EditText) findViewById(R.id.new_confirm_password) ;
        answerQues=(EditText) findViewById(R.id.answer);
        checkEmail=(Button) findViewById(R.id.check_email) ;
        question=(TextView) findViewById(R.id.question);
        final String[] items = new String[]{"Select question", "In what city were you born?", "What is the name of your favorite pet?","What is your mother's maiden name?","Which is your favorite web"+
                " browser?","What is the name of your first school?"};
        checkEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper helper=new DatabaseHelper(view.getContext());
                boolean cancel = false;
                View focusView = null;
                email=Email.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Email.setError("This field is required");
                    focusView = Email;
                    cancel = true;
                } else if(!isEmailValid(email)) {
                    Email.setError("This email address is invalid");
                    focusView = Email;
                    cancel = true;
                }
                if(!cancel){
                    c=helper.isUser(email);
                    if(c.getName().equals("yes")){
                        question.setText(items[Integer.parseInt(c.getQno())]);
                        linearLayout.setVisibility(View.VISIBLE);
                        checkEmail.setVisibility(View.GONE);
                    }else
                    {
                        Email.setError("Email not found");
                        focusView = Email;
                    }
                }
            }
        });
        changePassword=(Button) findViewById(R.id.changepassword);
        changePassword.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                DatabaseHelper helper=new DatabaseHelper(view.getContext());
                String answer,pass,conPass;
                answerQues.setError(null);
                newPassword.setError(null);
                newConfirmPassword.setError(null);
                answer=answerQues.getText().toString();
                pass=newPassword.getText().toString();
                conPass=newConfirmPassword.getText().toString();
                boolean cancel = false;
                View focusView = null;
                if (TextUtils.isEmpty(answer)) {
                    answerQues.setError("This field is required");
                    focusView = answerQues;
                    cancel = true;
                }
                if (TextUtils.isEmpty(pass)) {
                    newPassword.setError("This field is required");
                    focusView = newPassword;
                    cancel = true;
                }else if (!isPasswordValid(pass)) {
                    newPassword.setError("Password criteria \n" +
                            "One or more uppercase characters.\n" +
                            "One or more lowercase characters.\n" +
                            "One or more digits.\n" +
                            "One or more special characters.");
                    focusView = newPassword;
                    cancel = true;
                }
                if (TextUtils.isEmpty(conPass)) {
                    newConfirmPassword.setError("This field is required");
                    focusView = newConfirmPassword;
                    cancel = true;
                }else if(!conPass.equals(pass)){
                    newConfirmPassword.setError("Password does not match");
                    focusView = newConfirmPassword;
                    cancel = true;
                }
                if(!cancel){
                    if(answer.equalsIgnoreCase(c.getAnswer())){
                        helper.changePassword(c.getId(),pass);
                        Toast.makeText(ForgotPassword.this,"Password Changed Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else{
                        answerQues.setError("Your Answer does not match");
                        focusView = answerQues;
                    }
                }
            }
        });
    }
    private   boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


    //Method for checking password validity
    private  boolean isPasswordValid(String password) {
        Pattern pattern;
        Matcher matcher;


        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }


    }



