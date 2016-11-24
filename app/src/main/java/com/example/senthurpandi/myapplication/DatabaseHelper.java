package com.example.senthurpandi.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Senthurpandi on 19-Nov-16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME ="userdetail.db";
    private static final String TABLE_NAME ="userdetails";
    private static final String COLUMN_ID ="id";
    private static final String COLUMN_NAME ="name";
    private static final String COLUMN_EMAIL ="email";
    private static final String COLUMN_PASS ="pass";
    private static final String COLUMN_PNO ="pno";
    private static final String COLUMN_TIME ="time";
    private static final String COLUMN_QNO ="qno";
    private static final String COLUMN_ANSWER ="answer";
    private static final String COLUMN_IMAGE ="image";
    SQLiteDatabase db;
    private static final String TABLE_CREATE ="create table userdetails (id integer primary key not null ,"+
            "name text not null, email text not null ,pass text not null, pno integer not null,time text not null,qno integer not null,answer text not null,image blob);";
    public DatabaseHelper(Context context)
    {
        super(context ,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db=db;
    }

    //method check the user in database
    public Contact isUser(String email){
        db= this .getReadableDatabase();
        Contact co=new Contact();
        String query ="select email,qno,answer,id from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        String eMail;
        co.setName("no");
        if(cursor.moveToFirst())
        {
            do{
                eMail=cursor.getString(0);
                if(eMail.equalsIgnoreCase(email))
                {
                    co.setEmail(email);
                    co.setName("yes");

                    co.setQno(cursor.getString(1));
                    co.setAnswer(cursor.getString(2));
                    co.setId(cursor.getInt(3));
                    break;
                }
            }while (cursor.moveToNext());
        }
        return co;
    }

    //method to change user password in database
    public void changePassword(int  id, String pass){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_PASS,pass);
        db.update(TABLE_NAME,values,COLUMN_ID+ " = "+id,null);
    }

    //Method for adding the new user to database
    public void insertContact(Contact c){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        String query ="select * from userdetails";
        Cursor cursor =db.rawQuery(query,null);
        int count=cursor.getCount();

        values.put(COLUMN_ID,count);

        values.put(COLUMN_EMAIL,c.getEmail());
        values.put(COLUMN_PASS,c.getPass());
        values.put(COLUMN_NAME,c.getName());
        values.put(COLUMN_TIME,c.getTime());
        values.put(COLUMN_PNO,c.getPno());
        values.put(COLUMN_QNO,c.getQno());
        values.put(COLUMN_IMAGE,c.getImage());
        values.put(COLUMN_ANSWER,c.getAnswer());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }



//Method for searching the user in database
    public Contact searchPass(String lEmail,String lpass){
        db= this .getReadableDatabase();
        Contact co=new Contact();
        String query ="select email,pass,name,pno,time,image from "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        String eMail,pass,name,pNo,time;
        byte[] image=null;
        pass="";
        pNo="";
        time="";
        name="not";
        if(cursor.moveToFirst())
        {
            do{
                eMail=cursor.getString(0);
                if(eMail.equalsIgnoreCase(lEmail))
                { pass=cursor.getString(1);

                    if(pass.equals(lpass))
                    {
                        name=cursor.getString(2);
                        pNo=cursor.getString(3);
                        time=cursor.getString(4);
                        image=cursor.getBlob(5);
                    }

                    break;
                }
            }while (cursor.moveToNext());
            co.setEmail(eMail);
            co.setImage(image);
            co.setName(name);
            co.setPno(pNo);
            co.setTime(time);
        }return co;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }
}
