package com.example.fletch.databaseproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.fletch.databaseproject.Model.Enseignant.EnseignantDataAccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EnseignantDataAccessInstrumentedTest {

    private SchemaHelper mDataHelper;
    SQLiteDatabase db;
    Context appContext;

    @Before
    public void setup(){
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        String testDbName = "testData.db";
        appContext.deleteDatabase(testDbName);
        mDataHelper = new SchemaHelper(InstrumentationRegistry.getTargetContext());
        mDataHelper.DBNAME = testDbName;

        // Ouverture de la database pour écriture
        db = mDataHelper.getWritableDatabase();
    }

    @Test
    public void useAppContext() {
        assertEquals("com.example.fletch.databaseproject", appContext.getPackageName());
    }

    @Test
    public void isOpen(){
        assert(db.isOpen());
    }

    @Test
    public void testInitialData(){
        //('Arnold'),('Gérald'),('Erménésime'),('Gontran'

        Cursor cEnseignant = db.query("Enseignant",null,null,null,null,
                null,null);

        int valueId = 0;
        String[] noms = {
          "Arnold", "Gérald", "Erménésime", "Gontran"
        };

        while(cEnseignant.moveToNext()) {
            int id = cEnseignant.getInt(cEnseignant.getColumnIndex("enseignant_id"));
            String nom = cEnseignant.getString(cEnseignant.getColumnIndex("nom"));

            assertTrue(id == valueId+1);
            assertTrue(nom.equals(noms[valueId]));

            valueId++;
        }
    }

    @Test
    public void testEnseignantDataAccessQueryAll(){
        EnseignantDataAccess dataAccess = new EnseignantDataAccess(mDataHelper);
        Cursor cEnseignant =  dataAccess.queryAll();

        int valueId = 0;
        String[] noms = {
                "Arnold", "Gérald", "Erménésime", "Gontran"
        };

        while(cEnseignant.moveToNext()) {
            int id = cEnseignant.getInt(cEnseignant.getColumnIndex("enseignant_id"));
            String nom = cEnseignant.getString(cEnseignant.getColumnIndex("nom"));

            assertTrue(id == valueId+1);
            assertTrue(nom.equals(noms[valueId]));

            valueId++;
        }

    }


}
