package com.pucmm.segundoparcial;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProductDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(ProductData productData);

    //Delete query
    @Delete
    void delete(ProductData productData);

    //Delete all query
    @Delete
    void reset(List<ProductData> productData);

    //Update query
    @Query("UPDATE table_product SET name = :sName, amount = :sAmount, category = :sCategory WHERE ID = :sID")
    void update(int sID, String sName, int sAmount, String sCategory);

    //Get all data query
    @Query("SELECT * FROM table_product")
    List<ProductData> getAll();

    //Get all sText
    @Query("SELECT name FROM table_product")
    List<String> getAllProducts();
}
