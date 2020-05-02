package com.cybage.myapplication.dao;

import com.cybage.myapplication.model.Records;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Records record);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insertall(List<Records> records);

    @Update
    void update(Records record);

    @Delete
    void delete(Records record);

   /* @Query("select sum(volume) as volume, quarter,id , year from record group by year")
    DataSource.Factory<Integer, Records> getAllRecords();
*/

   @Query("SELECT id, quarter ,SUM(volume) AS volume, year , SUM(case when quarter = 'Q1' then volume end) AS Q1," +
           "SUM(case when quarter = 'Q2' then volume end) AS Q2," +
           "SUM(case when quarter = 'Q3' then volume end) AS Q3," +
           "SUM(case when quarter = 'Q4' then volume end) AS Q4 FROM  record " +
           "GROUP BY year")
   DataSource.Factory<Integer, Records> getAllRecords();


    @Query("SELECT id, quarter ,SUM(volume) AS volume, year , SUM(case when quarter = 'Q1' then volume end) AS Q1," +
            "SUM(case when quarter = 'Q2' then volume end) AS Q2," +
            "SUM(case when quarter = 'Q3' then volume end) AS Q3," +
            "SUM(case when quarter = 'Q4' then volume end) AS Q4 FROM  record " +
            "GROUP BY year")
    List<Records> getAllProccesdRecords();

}
