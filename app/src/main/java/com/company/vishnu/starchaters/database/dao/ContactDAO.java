package com.company.vishnu.starchaters.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.company.vishnu.starchaters.Util;
import com.company.vishnu.starchaters.database.DatabaseHelper;
import com.company.vishnu.starchaters.database.Models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishnu on 11/9/15.
 */
public class ContactDAO {
    private static final String LOG_LABEL = "database.dao.ContactDAO";
    private static ContactDAO singleton;
    private final Context context;
    private final DatabaseHelper dbHelper;


    private ContactDAO(Context context) {
        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public static ContactDAO getInstance(Context context) {
        if (singleton == null) {
            singleton = new ContactDAO(context.getApplicationContext());
        }
        return singleton;
    }

    public synchronized Cursor getContactCursor() {
        Cursor cursor = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    Contact.TABLE_NAME,
                    Contact.FIELDS,
                    null, null, null, null, null, null
            );
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }

        return cursor;
    }

    public synchronized Contact getContactById(String id) {
        Cursor cursor = null;
        Contact item = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    Contact.TABLE_NAME,
                    Contact.FIELDS,
                    Contact.COL_ID + " IS ? ",
                    new String[]{id},
                    null, null, null, null
            );
            if (cursor.moveToFirst()) {
                item = new Contact(cursor);
            }
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                Util.logException(e, LOG_LABEL);
            }
        }

        return item;
    }

    public synchronized boolean putContact(final Contact contact) {
        boolean success = false;
        int result = 0;
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();

            if (contact.id != null) {
                result += db.update(
                        Contact.TABLE_NAME,
                        contact.getContent(),
                        Contact.COL_ID + " IS ? ",
                        new String[]{contact.id}
                );
            }

            if (result > 0) {
                success = true;
            } else {
                final long rowId = db.insert(
                        Contact.TABLE_NAME,
                        null,
                        contact.getContent()
                );

                if (rowId > -1) {
                    success = true;
                }
            }
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }

        return success;
    }

    public synchronized Contact getContact() {
        Cursor contactCursor = getContactCursor();
        Contact contact = null;
        try {
            if (contactCursor != null && contactCursor.moveToLast()) {
                contact = new Contact(contactCursor);
            }
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        } finally {
            try {
                if (contactCursor != null) {
                    contactCursor.close();
                }
            } catch (Exception e) {
                Util.logException(e, LOG_LABEL);
            }
        }
        return contact;
    }

    public synchronized boolean deleteContact(final String contactId) {
        int result = -1;
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            result = db.delete(
                    Contact.TABLE_NAME,
                    Contact.COL_ID + " IS ? ",
                    new String[]{String.valueOf(contactId)}
            );
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }

        boolean success = false;
        if (result > 0) {
            success = true;
        }
        return success;
    }

    public synchronized Cursor getContact(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    Contact.TABLE_NAME,
                    projection,
                    selection, selectionArgs, null, null, sortOrder, null
            );
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }

        return cursor;
    }

    public synchronized List<Contact> getContactArrayList() {
        Cursor cursor = null;
        List<Contact> contactList = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    Contact.TABLE_NAME,
                    Contact.FIELDS,
                    null, null, null, null, null, null
            );
            if(cursor.moveToFirst()){
                contactList = new ArrayList<>();
                do{
                    Contact contact = new Contact(cursor);
                    contactList.add(contact);
                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }

        return contactList;
    }
}
