package com.example.senthurpandi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity  {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */


    private ViewPager mViewPager;
    private TabLayout tabLayout;
    static TextView forgotPass;
    static EditText mEmail,mName,mPassword,mPno,mLogmail,mLogPassword,mConPass,mAnswer;
    static Button mLogin,mSignUp;
    static Spinner secQuestions;
    static ImageView profileImg;
    static TextView profileText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            System.exit(1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Method for checking Email validity
    private static  boolean isEmailValid(String email) {

        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }


    //Method for checking password validity
    private static boolean isPasswordValid(String password) {
        Pattern pattern;
        Matcher matcher;


        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        View rootView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            switch (getArguments().getInt(ARG_SECTION_NUMBER))
            {

                //Login event
                case 1: {
                    rootView = inflater.inflate(R.layout.log_in, container, false);
                    mLogmail=(EditText) rootView.findViewById(R.id.email);
                    forgotPass=(TextView) rootView.findViewById(R.id.forgotpass);
                    mLogPassword=(EditText) rootView.findViewById(R.id.password);
                    mLogin=(Button) rootView.findViewById(R.id.email_log_in_button);

                    forgotPass.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View v){
                            Intent intent = new Intent(getActivity(),ForgotPassword.class);
                            startActivity(intent);
                        }
                    });
                     mLogin.setOnClickListener(new View.OnClickListener() {
                      @Override
                          public void onClick(View view) {
                                DatabaseHelper helper=new DatabaseHelper(view.getContext());
                                mLogmail.setError(null);
                                mLogPassword.setError(null);
                                boolean cancel = false;
                                View focusView = null;
                                String logEmail=mLogmail.getText().toString();
                                String logPass=mLogPassword.getText().toString();
                                if (TextUtils.isEmpty(logEmail)) {
                                        mLogmail.setError("This field is required");
                                        focusView = mLogmail;
                                        cancel = true;
                                } else if (!isEmailValid(logEmail)) {
                                        mLogmail.setError("This email address is invalid");
                                        focusView = mLogmail;
                                        cancel = true;
                                }
                                if (TextUtils.isEmpty(logPass)) {
                                        mLogPassword.setError("This field is required");
                                        focusView = mLogPassword;
                                        cancel = true;
                                }
                                if (!cancel) {
                                        Contact c=new Contact();
                                        c=helper.searchPass(logEmail,logPass);
                                        if(c.getName()!=null) {
                                            if (c.getName().equals("not")) {
                                                mLogmail.setError("Invalid Email/password");
                                                focusView = mLogmail;
                                            }

                                            else {
                                                Intent i = new Intent(getActivity(), Details.class);
                                                i.putExtra("Email", c.getEmail());
                                                i.putExtra("Username", c.getName());
                                                i.putExtra("Contactnumber", c.getPno());
                                                i.putExtra("Time", c.getTime());
                                                byte[] userByteImage=c.getImage();
                                                i.putExtra("Image",BitmapFactory.decodeByteArray(userByteImage, 0, userByteImage.length));
                                                getActivity().startActivity(i);
                                            }
                                        }
                                    else{

                                            mLogmail.setError("Invalid Email/password");
                                            focusView = mLogmail;
                                        }
                                }
                            }
                      });
                         break;
                }



//Sign_up event

                case 2: {
                    rootView = inflater.inflate(R.layout.signup, container, false);
                    mName=(EditText) rootView.findViewById(R.id.new_name);
                    mPassword=(EditText) rootView.findViewById(R.id.new_password);
                    mPno=(EditText) rootView.findViewById(R.id.new_phoneno);
                    mEmail=(EditText) rootView.findViewById(R.id.new_email);

                    profileImg=(ImageView) rootView.findViewById(R.id.profileImg);
                    mConPass=(EditText) rootView.findViewById(R.id.new_confirm_password);
                    mAnswer=(EditText) rootView.findViewById(R.id.new_answer);
                    secQuestions = (Spinner)rootView.findViewById(R.id.questions);
                    profileText =(TextView) rootView.findViewById(R.id.new_Profile_text);
                    String[] items = new String[]{"Select question", "In what city were you born?", "What is the name of your favorite pet?","What is your mother's maiden name?","Which is your favorite web"+
                            " browser?","What is the name of your first school?"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
                    secQuestions.setAdapter(adapter);
                    final Bitmap defaultImg =((BitmapDrawable)profileImg.getDrawable()).getBitmap();
                    profileImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Add Photo!");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item) {
                                    if (options[item].equals("Take Photo"))
                                    {
                                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            getActivity().startActivityForResult(intent, 1);
                                        }
                                    }
                                    else if (options[item].equals("Choose from Gallery"))
                                    {

                                        Intent intent = new   Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);

                                    }
                                    else if (options[item].equals("Cancel")) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                            builder.show();


                        }
                    });

                    mSignUp=(Button) rootView.findViewById(R.id.email_sign_up_button);
                    mSignUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DatabaseHelper helper=new DatabaseHelper(view.getContext());
                            mEmail.setError(null);
                            mPassword.setError(null);
                            mName.setError(null);
                            mPno.setError(null);
                            mConPass.setError(null);
                            profileText.setError(null);
                            mAnswer.setError(null);
                            boolean cancel = false;
                            View focusView = null;
                            String newEmail = mEmail.getText().toString();
                            String newPass = mPassword.getText().toString();
                            String newName = mName.getText().toString();
                            String newPno = mPno.getText().toString();
                            String newConPass= mConPass.getText().toString();
                            String newAnswer=mAnswer.getText().toString();
                            if(defaultImg.sameAs(((BitmapDrawable)profileImg.getDrawable()).getBitmap())){
                                profileText.setError("");
                                focusView = profileText;
                                cancel = true;

                            }
                            if (TextUtils.isEmpty(newEmail)) {
                                mEmail.setError("This field is required");
                                focusView = mEmail;
                                cancel = true;
                            } else if (!isEmailValid(newEmail)) {
                                mEmail.setError("This email address is invalid");
                                focusView = mEmail;
                                cancel = true;
                            }
                            if (TextUtils.isEmpty(newName)) {
                                mName.setError("This field is required");
                                focusView = mName;
                                cancel = true;
                            }
                            if (TextUtils.isEmpty(newPass)) {
                                mPassword.setError("This field is required");
                                focusView = mPassword;
                                cancel = true;
                            } else if (!isPasswordValid(newPass)) {
                                mPassword.setError("Password criteria \n" +
                                        "One or more uppercase characters.\n" +
                                        "One or more lowercase characters.\n" +
                                        "One or more digits.\n" +
                                        "One or more special characters.");
                                focusView = mPassword;
                                cancel = true;
                            }
                            if (TextUtils.isEmpty(newConPass)) {
                                mConPass.setError("This field is required");
                                focusView = mConPass;
                                cancel = true;
                            }else if(!newConPass.equals(newPass)){
                                mConPass.setError("Password does not match");
                                focusView = mConPass;
                                cancel = true;
                            }

                            if (TextUtils.isEmpty(newPno)) {
                                mPno.setError("This field is required");
                                focusView = mPno;
                                cancel = true;
                            }
                            if(secQuestions.getSelectedItemPosition()==0){
                                ((TextView)secQuestions.getSelectedView()).setError("");
                                cancel=true;
                            }
                            if (newPno.length() < 10) {
                                mPno.setError("Enter Valid Contact number");
                                focusView = mPno;
                                cancel = true;

                            }
                            if (TextUtils.isEmpty(newAnswer)) {
                                mAnswer.setError("This field is required");
                                focusView = mAnswer;
                                cancel = true;
                            }
                            if (!cancel) {
                                String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
                                Contact c = new Contact();
                                c=helper.searchPass(newEmail,newPass);
                                if(c.getName()!=null){
                                    if(c.getName().equals("not")){
                                        c.setEmail(newEmail);
                                        c.setPass(newPass);
                                        c.setName(newName);
                                        c.setPno(newPno);
                                        c.setAnswer(newAnswer);
                                        c.setQno((String.valueOf( secQuestions.getSelectedItemPosition())));
                                        c.setTime(currentDateTime);
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        ((BitmapDrawable) profileImg.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 0, stream);
                                        c.setImage(stream.toByteArray());

                                        Toast.makeText(getActivity(),"Added Successfully!", Toast.LENGTH_SHORT).show();
                                        helper.insertContact(c);

                                    }else{
                                        Toast.makeText(getActivity(),"User Already Exists!", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    c.setEmail(newEmail);
                                    c.setPass(newPass);
                                    c.setName(newName);
                                    c.setPno(newPno);
                                    c.setAnswer(newAnswer);
                                    c.setQno((String.valueOf( secQuestions.getSelectedItemPosition())));
                                    c.setTime(currentDateTime);
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    ((BitmapDrawable) profileImg.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 0, stream);
                                    c.setImage(stream.toByteArray());
                                    Toast.makeText(getActivity(),"Added Successfully!", Toast.LENGTH_SHORT).show();
                                    helper.insertContact(c);

                                }

                            }
                        }
                    });

                    break;
                }



            }
            return rootView;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

           profileImg.setImageBitmap(photo);
        } else  if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

             Bitmap bm=null;
             if (data != null) {
                 try {
                     bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
             profileImg.setImageBitmap(bm);
         }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Login";
                case 1:
                    return "Sign Up";

            }
            return null;
        }
    }


}
