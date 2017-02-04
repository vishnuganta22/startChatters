package com.company.vishnu.starchaters.database.Models;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by vishnu on 9/9/15.
 */
public class Device {
    private static final String LOG_LABEL = "database.model.Device";

    public static final String TABLE_NAME = "Device";

    public static final String COL_ID = "ID";
    public static final String COL_EMAIL = "EMAIL";

    public static final String[] FIELDS = {COL_ID, COL_EMAIL};

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " TEXT NOT NULL, "
            + COL_EMAIL + " TEXT NOT NULL "
            + ")";

    public String id = "";
    public String email = "";

    public Device() {
        super();
    }

    public Device(Cursor cursor) {
        this.id = cursor.getString(0);
        this.email = cursor.getString(1);
    }

    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        values.put(COL_ID, this.id);
        values.put(COL_EMAIL, this.email);
        return values;
    }
}
