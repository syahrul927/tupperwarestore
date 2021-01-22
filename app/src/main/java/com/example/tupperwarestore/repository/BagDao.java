package com.example.tupperwarestore.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface BagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Bag bag);

    @Delete
    void delete(Bag bag);

    @Update
    void update(Bag bag);

    @Query("DELETE FROM bag_table")
    void deleteAllBags();

    @Query("SELECT * FROM bag_table where id = :id")
    Maybe<Bag> findById(String id);


    @Query("SELECT * FROM bag_table")
    Single<List<Bag>> getAllBags();
}
