package com.example.tupperwarestore.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class, Bag.class}, version = 2)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;

    public abstract UserDao dao();
    public abstract BagDao bagDao();

    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_database.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
