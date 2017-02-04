package com.company.vishnu.starchaters.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.company.vishnu.starchaters.R;
import com.company.vishnu.starchaters.Util;
import com.company.vishnu.starchaters.database.Models.Contact;
import com.company.vishnu.starchaters.database.Models.Device;
import com.company.vishnu.starchaters.database.Models.Message;


/**
 * Created by vishnu on 9/9/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_LABEL = "database.DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "chatApplication";
    private static DatabaseHelper singleton;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (context.getResources().getBoolean(R.bool.developer_mode)) {
            Log.w(LOG_LABEL, " :: [[[[ Recreating the database in dev mode ]]]] :  ");
            this.truncateAllTables();
        }
    }

    public static DatabaseHelper getInstance(final Context context) {
        if (singleton == null) {
            singleton = new DatabaseHelper(context.getApplicationContext());
        }
        return singleton;
    }

    public synchronized void close() {
        super.close();
        Log.w(LOG_LABEL, "Database is closed");
    }

    public boolean truncateAllTables() {
        Log.w(LOG_LABEL, " Truncating all the tables in the database");
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = true;
        try {
            db.execSQL("DELETE FROM " + Device.TABLE_NAME);
            db.execSQL("DELETE FROM " + Contact.TABLE_NAME);
            db.execSQL("DELETE FROM " + Message.TABLE_NAME);
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
            result = false;
        }
        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w(LOG_LABEL, "creating database, Existing contents will be wiped out");
        db.execSQL("DROP TABLE IF EXISTS " + Device.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contact.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Message.TABLE_NAME);

        db.execSQL(Device.CREATE_TABLE);
        db.execSQL(Contact.CREATE_TABLE);
        db.execSQL(Message.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_LABEL, "upgrading database. Existing content will be lost. ["
                + oldVersion + "] -> [" + newVersion + "]");
        this.upgradeDatabase(db);
    }

    private boolean upgradeDatabase(SQLiteDatabase db) {
        Log.w(LOG_LABEL, "upgrading database. Existing contents will be wiped out !");
        boolean result = false;
        try {
            db.execSQL("DROP TABLE IF EXISTS " + Device.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Contact.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Message.TABLE_NAME);

            db.execSQL(Device.CREATE_TABLE);
            db.execSQL(Contact.CREATE_TABLE);
            db.execSQL(Message.CREATE_TABLE);
            result = true;
        } catch (Exception e) {
            Util.logException(e, LOG_LABEL);
        }
        return result;
    }
}
