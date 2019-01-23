package com.example.fletch.databaseproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SchemaHelper extends SQLiteOpenHelper {

    static private int VERSION = 5;
    static public String DBNAME = "datas.db";

    public SchemaHelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
    }

    private void insertInitialData(SQLiteDatabase db){
        db.execSQL("INSERT INTO enseignant(nom) " +
                "VALUES ('Arnold'),('Gérald'),('Erménésime'),('Gontran') ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE enseignant (enseignant_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", nom TEXT)");
        db.execSQL("CREATE TABLE cours (cours_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", nom TEXT, enseignant_id INTEGER," +
                "FOREIGN KEY(enseignant_id) REFERENCES enseignant(enseignant_id))");
        db.execSQL("CREATE TABLE etudiant (etudiant_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", nom TEXT, cours_id INTEGER," +
                "FOREIGN KEY(cours_id) REFERENCES cours(cours_id))");
        db.execSQL("CREATE TABLE Enseignant_Etudiant (Enseignant_id INTEGER, " +
                "Etudiant_id INTEGER," +
                "PRIMARY KEY (Enseignant_id,Etudiant_id)," +
                "FOREIGN KEY(Enseignant_id) REFERENCES enseignant(enseignant_id)," +
                "FOREIGN KEY(Etudiant_id) REFERENCES etudiant(etudiant_id))" +
                "");
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String  table:new String[]{"enseignant","cours","etudiant", "Enseignant_Etudiant"}
             ) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }
        onCreate(db);
    }
}
