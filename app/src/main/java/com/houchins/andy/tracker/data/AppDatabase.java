package com.houchins.andy.tracker.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Lisa on 6/2/2018.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 1;
}
