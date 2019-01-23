package com.example.fletch.databaseproject.Model.EnseignantEtudiant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fletch.databaseproject.Model.Enseignant.Enseignant;
import com.example.fletch.databaseproject.Model.Table.EnseignantEtudiantTable;
import com.example.fletch.databaseproject.Model.Table.EnseignantTable;

import java.util.ArrayList;

public class EnseignantEtudiantDataAccess {
    private SQLiteDatabase db;

    public EnseignantEtudiantDataAccess(SQLiteOpenHelper helper) {
        db = helper.getWritableDatabase();
    }


    public Cursor queryAll(){
        String[] allColumns = { EnseignantEtudiantTable.ENSEIGNANT_ID, EnseignantEtudiantTable.ETUDIANT_ID};
        Cursor c1 = db.query(EnseignantTable.TABLE_NAME,
                allColumns, null, null, null, null, null);
        return c1;
    }

    /**
     * Récupère les données des tables Enseignant et Enseignant_Etudiant
     *
     * @param etudiantId
     * @return
     */
    public Cursor getByEtudiantWithEnseignantData(int etudiantId){
        String[] allColumns = { EnseignantEtudiantTable.ENSEIGNANT_ID, EnseignantEtudiantTable.ETUDIANT_ID};
        /*
        SELECT * FROM Enseignant_Etudiant
        INNER JOIN Enseignant ON Enseignant.id = Enseignant_Etudiant.Enseignant_id
        WHERE Etudiant_id = ?
         */
        Cursor c1 = db.rawQuery("SELECT * FROM " + EnseignantEtudiantTable.TABLE_NAME + " " +
                "INNER JOIN "+ EnseignantTable.TABLE_NAME + " ON " + EnseignantTable.TABLE_NAME + "." +  EnseignantTable.ID + " = " +
                        EnseignantEtudiantTable.TABLE_NAME +"." + EnseignantEtudiantTable.ENSEIGNANT_ID +
                " WHERE " + EnseignantEtudiantTable.ETUDIANT_ID  + "=?",
                new String[]{String.valueOf(etudiantId)});
        return c1;
    }

    /**
     * Lie l'étudiant avec les enseignants fournis en liste
     *
     * @param etudiantId
     * @param enseignantArraylist
     */
    public void saveByEtudiant(int etudiantId, ArrayList<Enseignant> enseignantArraylist){
        //Efface tous les enregistrements liés à l'étudiant
        db.delete(EnseignantEtudiantTable.TABLE_NAME,EnseignantEtudiantTable.ETUDIANT_ID+" = ?" ,
                new String[]{String.valueOf(etudiantId)});
        //Enregistre les nouveaux enseignants
        for (Enseignant enseignant:enseignantArraylist
             ) {
            ContentValues values = new ContentValues();
            values.put(EnseignantEtudiantTable.ENSEIGNANT_ID,enseignant.getId());
            values.put(EnseignantEtudiantTable.ETUDIANT_ID,1); // On travail toujours avec l'étudiant 1 pour l'exemple
            db.insert(EnseignantEtudiantTable.TABLE_NAME,"",
                    values);
        }
    }

}
