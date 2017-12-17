package com.example.a1505197.contactlist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a1505197.contactlist.Contacts;

/**
 * Created by 1505197 on 11/4/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts_table";
    private static final String col0 = "ID";
    private static final String col1 = "NAME";
    private static final String col2 = "PHONE_NUMBER";
    private static final String col3 = "DEVICE";
    private static final String col4 = "EMAIL";
    private static final String col5 = "PROFILE_PHOTO";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " +
                TABLE_NAME + " ( " +
                col0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col1 + " TEXT, " +
                col2 + " TEXT, " +
                col3 + " TEXT, " +
                col4 + " TEXT, " +
                col5 + " TEXT )";
               db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean addContact(Contacts contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, contact.getName());
        contentValues.put(col2, contact.getPhonenumber());
        contentValues.put(col3, contact.getDevice());
        contentValues.put(col4, contact.getEmail());
        contentValues.put(col5, contact.getProfileImage());
        long result=db.insert(TABLE_NAME,null,contentValues);
         if(result==-1)
         {
             return false;
         }
         else
         {
             return true;
         }

    }
    public Cursor getAllContacts(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
    }
    public boolean updateContact(Contacts contact,int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, contact.getName());
        contentValues.put(col2, contact.getPhonenumber());
        contentValues.put(col3, contact.getDevice());
        contentValues.put(col4, contact.getEmail());
        contentValues.put(col5, contact.getProfileImage());
        int update=db.update(TABLE_NAME,contentValues,col0+"=?",new String[]{String.valueOf(id)});
    if(update!=-1)
    {
        return false;
    }
    else
    {
        return true;
    }
    }
    public Cursor getContactID(Contacts contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="SELECT * FROM "+TABLE_NAME +
                " WHERE"+col1+"='"+contact.getName()+"''"+
                " AND"+col2+"='"+contact.getPhonenumber()+"'";
        return db.rawQuery(sql,null);
    }
}
