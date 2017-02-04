package com.company.vishnu.starchaters;

import android.util.Log;

import java.util.Random;

/**
 * Created by vishnu on 9/9/15.
 */
public class Util {
    public static final String TAG = "starchaters";
    private static final String LOG_LABEL = "Util";
    private static final int MAX_LENGTH = 8;

    public static void logException(Throwable e, String logLabel) {
        Log.e(LOG_LABEL, " [[[ " + e.getMessage() + " ]]]");
        for (StackTraceElement el : e.getStackTrace()) {
            Log.e(LOG_LABEL, " :: " + el.toString());
        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public static String replaceExtension(String name, String newExtension) {
        return name.replaceFirst("\\..*", newExtension);
    }
}
