package com.example.tahmid.Medicine_HelpV2;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME="MedicineDatabase.db";

    private static final int DATABASE_VERSION=1;

    public DataOpenHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
}
