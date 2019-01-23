package com.example.fletch.databaseproject.Model.Etudiant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fletch.databaseproject.Model.Table.EtudiantTable;

public class EtudiantDataAccess {

    private SQLiteDatabase db;

    public EtudiantDataAccess(SQLiteOpenHelper helper) {
        db = helper.getWritableDatabase();
    }

    public long insert(String nom, Integer formation){
        ContentValues values = new ContentValues();
        values.put(EtudiantTable.NOM, nom);
        values.put(EtudiantTable.COURS, formation);
        return db.insert(EtudiantTable.TABLE_NAME, null,
                values);
    }

    public Cursor queryAll(){
        String[] allColumns = { EtudiantTable.ID, EtudiantTable.NOM, EtudiantTable.COURS};
        Cursor c1 = db.query(EtudiantTable.TABLE_NAME,
                allColumns, null, null, null, null, null);
        return c1;
    }
}
