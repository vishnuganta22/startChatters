package com.company.vishnu.starchaters.database.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yantranet.signware.chatapplication.Util;
import com.yantranet.signware.chatapplication.database.DatabaseHelper;
import com.yantranet.signware.chatapplication.database.Models.Message;

/**
 * Created by vishnu on 19/9/15.
 */
public class MessageDAO {
    private static final String LOG_LABEL = "database.dao.MessageDAO";
    private final Context context;
    private final DatabaseHelper dbHelper;
    private static MessageDAO singleton;
    private String deviceMail;

    private MessageDAO(Context context) {
        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("chatApplication", Context.MODE_MULTI_PROCESS);
        if (sharedPreferences != null) {
            deviceMail = sharedPreferences.getString("deviceMail", null);
        }
    }

    public static MessageDAO getInstance(Context context) {
        if (singleton == null) {
            singleton = new MessageDAO(context);
        }
        return singleton;
    }

    public synchronized Cursor getMessageCursor() {
        Cursor cursor = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    Message.TABLE_NAME,
                    Message.FIELDS,
                    null, null, null, null, null, null
            );
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }

        return cursor;
    }

    public synchronized boolean putMessage(final Message message) {
        boolean success = false;
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            final long rowId = db.insert(
                    Message.TABLE_NAME,
                    null,
                    message.getContent()
            );

            if (rowId > -1) {
                success = true;
            }
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }
        return success;
    }

    public synchronized boolean updateMessage(final Message message) {
        boolean success = false;
        int result = 0;
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();

            if (message.id != null) {
                result += db.update(
                        Message.TABLE_NAME,
                        message.getContent(),
                        Message.COL_ID + " IS ? ",
                        new String[]{message.id}
                );
            }

            if (result > 0) {
                success = true;
            }
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }
        return success;
    }

    public synchronized int getCountUnreadFromAndTo(String from, String to){
        Cursor cursor = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.query(Message.TABLE_NAME,
                    Message.FIELDS,
                    Message.COL_FROM + " IS ? AND " + Message.COL_TO + " IS ? AND "
                            + Message.COL_READ_STATUS + " IS ? ",
                    new String[]{String.valueOf(from), String.valueOf(to), String.valueOf("true")},
                    null, null, Message.COL_DATE_AND_TIME, null
            );
        }catch (Exception e){
            Util.logException(e,LOG_LABEL);
        }
        if(cursor != null){
            return cursor.getCount();
        }
        return 0;
    }

    public synchronized Message getMessage() {
        Cursor messageCursor = getMessageCursor();
        Message message = null;
        try {
            if (messageCursor != null && messageCursor.moveToLast()) {
                message = new Message(messageCursor);
            }
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        } finally {
            try {
                if (messageCursor != null) {
                    messageCursor.close();
                }
            } catch (Exception e) {
                Util.logException(e, LOG_LABEL);
            }
        }
        return message;
    }

    public synchronized boolean deleteMessage(final String messageId) {
        int result = -1;
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            result = db.delete(
                    Message.TABLE_NAME,
                    Message.COL_ID + " IS ? ",
                    new String[]{String.valueOf(messageId)}
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

    public synchronized Cursor getMessage(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    Message.TABLE_NAME,
                    projection,
                    selection, selectionArgs, null, null, sortOrder, null
            );
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }

        return cursor;
    }

    public synchronized Cursor getMesssageFromOrTo(String mail) {
        Cursor cursor = null;
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query(Message.TABLE_NAME,
                Message.FIELDS,
                Message.COL_FROM + " IS ? OR " + Message.COL_TO + " IS ? ",
                new String[]{String.valueOf(mail), String.valueOf(mail)},
                null, null, Message.COL_DATE_AND_TIME, null
        );
        return cursor;
    }
}
