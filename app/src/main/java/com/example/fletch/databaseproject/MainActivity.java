/**
 * Ceci est une expérimentation avec le widget Spinner
 * Le code ne devrait pas être utilisé tel quel mais
 * adapté selon les besoins.
 *
 * Il est démontré que le spinner peut accepter un série d'objets
 * créées à partir de la database. Lors de la séléction d'une valeur,
 * il est possible de consulter l'objet.
 *
 * N.B. Dans cet exemple, nous travaillons toujours avec l'étudiant 1
 *
 */

package com.example.fletch.databaseproject;

import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.fletch.databaseproject.Model.Enseignant.Enseignant;
import com.example.fletch.databaseproject.Model.Enseignant.EnseignantDataAccess;
import com.example.fletch.databaseproject.Model.EnseignantEtudiant.EnseignantEtudiantDataAccess;
import com.example.fletch.databaseproject.Model.Table.EnseignantEtudiantTable;
import com.example.fletch.databaseproject.Model.Table.EnseignantTable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter adapter;
    // Les enseignants électionnés (lié avec l'étudiant)
    private ArrayList<Enseignant> selectedEnseignants;
    private ArrayList<Enseignant> allEnseignant;
    private SchemaHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new SchemaHelper(this);

        // Initialise
        selectedEnseignants = new ArrayList<Enseignant>();
        allEnseignant = getAllEnseignants();

        getSelectedEnseignant();



        generateSpinner((Spinner)findViewById(R.id.spinner));
        recyclerView = findViewById(R.id.recyclerView_selection);

        setupRecyclerView(selectedEnseignants);

        ((Button)findViewById(R.id.button_save))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        save();
                    }
                });

        ((Button)findViewById(R.id.button_clear))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clear();
                    }
                });
    }

    private ArrayList<Enseignant> getAllEnseignants(){
        EnseignantDataAccess dataAccess = new EnseignantDataAccess(databaseHelper);
        Cursor c1 = dataAccess.queryAll();
        ArrayList<Enseignant> enseignants = new ArrayList<Enseignant>();
        enseignants.add(new Enseignant(0)); // Ajoute une valeur vide
        if(c1.moveToFirst()){
            do{
                Enseignant enseignant = new Enseignant();
                enseignant.setNom(c1.getString(c1.getColumnIndexOrThrow("nom")));
                enseignant.setId(Integer.parseInt(c1.getString(c1.getColumnIndexOrThrow("enseignant_id"))));
                enseignants.add(enseignant);
            }while(c1.moveToNext());
        }
        c1.close();
        return enseignants;
    }

    private void getSelectedEnseignant(){
        // Récupère les données de sqlLiteDatabse;
        EnseignantEtudiantDataAccess dataAccess = new EnseignantEtudiantDataAccess(databaseHelper);
        Cursor c1 = dataAccess.getByEtudiantWithEnseignantData(1);
        while(c1.moveToNext()){
            Enseignant enseignant = new Enseignant(c1.getInt(c1.getColumnIndexOrThrow(EnseignantEtudiantTable.ENSEIGNANT_ID)));
            enseignant.setNom(c1.getString(c1.getColumnIndexOrThrow(EnseignantTable.NOM)));
            selectedEnseignants.add(enseignant);
        }
    }

    private void setupRecyclerView(ArrayList<Enseignant> myDataset){

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(adapter);
    }


    /**
     * Ajoute un enseignant dans la RecyclerView
     * et notify que les données ont changées pour le rafraichissement
     *
     * @param enseignant
     */
    private void addSelectedEnseignant(Enseignant enseignant){
        if (!selectedEnseignants.contains(enseignant))
            selectedEnseignants.add(enseignant);
        adapter.notifyDataSetChanged();
        // S'assure que le Spinner est positionné
        // à l'enregistrement vide
        ((Spinner)findViewById(R.id.spinner))
                .setSelection(0);
    }


    // Sauvegarde le ArrayList des selectedEnseignants dans la base de données locale
    private void save(){
        EnseignantEtudiantDataAccess dataAccess = new EnseignantEtudiantDataAccess(databaseHelper);
        dataAccess.saveByEtudiant(1, selectedEnseignants);
    }

    private void clear(){
        selectedEnseignants.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     *
     * Il s'agit d'un spinner alimenté à partir des données de la base SqLite
     * De plus, lors de la sélection, il est possible de connaitre l'ENSEIGNANT_ID de la sélection
     *
     * @param spinner View Spinner
     */
    private void generateSpinner(Spinner spinner){

        Enseignant[] allSpinner = new Enseignant[allEnseignant.size()];
        allSpinner = allEnseignant.toArray(allSpinner);

        ArrayAdapter<Enseignant> spinnerAdapter = new ArrayAdapter<Enseignant>(MainActivity.this,android.R.layout.simple_spinner_item, allSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Enseignant enseignant = (Enseignant)parent.getItemAtPosition(position);
                if(enseignant.getId()!=0)
                    addSelectedEnseignant(((Enseignant)parent.getItemAtPosition(position)));
                return;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
