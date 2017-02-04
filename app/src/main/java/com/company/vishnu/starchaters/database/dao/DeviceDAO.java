package com.company.vishnu.starchaters.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.company.vishnu.starchaters.Util;
import com.company.vishnu.starchaters.database.DatabaseHelper;
import com.company.vishnu.starchaters.database.Models.Device;

/**
 * Created by vishnu on 9/9/15.
 */
public class DeviceDAO {
    private static final String LOG_LABEL = "database.dao.DeviceDAO";
    private static DeviceDAO singleton;
    private final Context context;
    private final DatabaseHelper dbHelper;


    private DeviceDAO(Context context) {
        this.context = context;
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public static DeviceDAO getInstance(Context context) {
        if (singleton == null) {
            singleton = new DeviceDAO(context.getApplicationContext());
        }
        return singleton;
    }

    public synchronized Cursor getDeviceCursor() {
        Cursor cursor = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    Device.TABLE_NAME,
                    Device.FIELDS,
                    null, null, null, null, null, null
            );
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }

        return cursor;
    }

    public synchronized Device getDeviceById(String id) {
        Cursor cursor = null;
        Device item = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    Device.TABLE_NAME,
                    Device.FIELDS,
                    Device.COL_ID + " IS ? ",
                    new String[]{id},
                    null, null, null, null
            );
            if (cursor.moveToFirst()) {
                item = new Device(cursor);
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

    public synchronized boolean putDevice(final Device device) {
        boolean success = false;
        int result = 0;
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();

            if (device.id != null) {
                result += db.update(
                        Device.TABLE_NAME,
                        device.getContent(),
                        Device.COL_ID + " IS ? ",
                        new String[]{device.id}
                );
            }

            if (result > 0) {
                success = true;
            } else {
                final long rowId = db.insert(
                        Device.TABLE_NAME,
                        null,
                        device.getContent()
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

    public synchronized Device getDevice() {
        Cursor deviceCursor = getDeviceCursor();
        Device device = null;
        try {
            if (deviceCursor != null && deviceCursor.moveToLast()) {
                device = new Device(deviceCursor);
            }
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        } finally {
            try {
                if (deviceCursor != null) {
                    deviceCursor.close();
                }
            } catch (Exception e) {
                Util.logException(e, LOG_LABEL);
            }
        }
        return device;
    }

    public synchronized boolean deleteDevice(final String deviceId) {
        int result = -1;
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();
            result = db.delete(
                    Device.TABLE_NAME,
                    Device.COL_ID + " IS ? ",
                    new String[]{String.valueOf(deviceId)}
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

    public synchronized Cursor getDevice(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();

            cursor = db.query(
                    Device.TABLE_NAME,
                    projection,
                    selection, selectionArgs, null, null, sortOrder, null
            );
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }

        return cursor;
    }
}
