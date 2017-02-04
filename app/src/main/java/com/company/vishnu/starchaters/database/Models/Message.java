package com.company.vishnu.starchaters.database.Models;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by vishnu on 19/9/15.
 */
public class Message {
    private static final String LOG_LABEL = "database.model.Message";

    public static final String TABLE_NAME = "Message";

    public static final String COL_ID = "ID";
    public static final String COL_FROM = "FROMADDRESS";
    public static final String COL_TO = "TOADDRESS";
    public static final String COL_BODY = "BODY";
    public static final String COL_TYPE = "TYPE";
    public static final String COL_THUMBNAIL = "THUMBNAIL";
    public static final String COL_DATE_AND_TIME = "DATE_AND_TIME";
    public static final String COL_SENT_STATUS = "SENT_STATUS";
    public static final String COL_READ_STATUS = "READ_STATUS";

    public static final String[] FIELDS = {COL_ID, COL_FROM, COL_TO, COL_BODY, COL_TYPE,
            COL_THUMBNAIL, COL_DATE_AND_TIME, COL_SENT_STATUS, COL_READ_STATUS};

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " TEXT NOT NULL, "
            + COL_FROM + " TEXT NOT NULL, "
            + COL_TO + " TEXT NOT NULL, "
            + COL_BODY + " TEXT NOT NULL, "
            + COL_TYPE + " TEXT NOT NULL, "
            + COL_THUMBNAIL + " TEXT NOT NULL, "
            + COL_DATE_AND_TIME + " TEXT NOT NULL, "
            + COL_SENT_STATUS + " TEXT NOT NULL, "
            + COL_READ_STATUS + " TEXT NOT NULL "
            + ")";

    public String id = "";
    public String from = "";
    public String to = "";
    public String body = "";
    public String type = "";
    public String thumbnail = "";
    public String dateAndTime = "";
    public String sentStatus = "false";
    public String readStatus = "false";

    public Message() {
        super();
    }

    public Message(Cursor cursor) {
        this.id = cursor.getString(0);
        this.from = cursor.getString(1);
        this.to = cursor.getString(2);
        this.body = cursor.getString(3);
        this.type = cursor.getString(4);
        this.thumbnail = cursor.getString(5);
        this.dateAndTime = cursor.getString(6);
        this.sentStatus = cursor.getString(7);
        this.readStatus = cursor.getString(8);
    }

    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        values.put(COL_ID, this.id);
        values.put(COL_FROM, this.from);
        values.put(COL_TO, this.to);
        values.put(COL_BODY, this.body);
        values.put(COL_TYPE, this.type);
        values.put(COL_THUMBNAIL, this.thumbnail);
        values.put(COL_DATE_AND_TIME, this.dateAndTime);
        values.put(COL_SENT_STATUS, this.sentStatus);
        values.put(COL_READ_STATUS, this.readStatus);
        return values;
    }
}
