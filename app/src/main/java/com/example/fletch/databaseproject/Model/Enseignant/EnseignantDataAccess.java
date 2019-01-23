package com.example.fletch.databaseproject.Model.Enseignant;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fletch.databaseproject.Model.Table.EnseignantTable;

public class EnseignantDataAccess {

    private SQLiteDatabase db;

    public EnseignantDataAccess(SQLiteOpenHelper helper) {
        db = helper.getWritableDatabase();
    }



    public Cursor queryAll(){
        String[] allColumns = { EnseignantTable.ID, EnseignantTable.NOM};
        Cursor c1 = db.query(EnseignantTable.TABLE_NAME,
                allColumns, null, null, null, null, null);
        return c1;
    }
}
