package com.example.pokedex_trial1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pokedex_trial1.Fragments.FragmentPokemonDetails;
import com.example.pokedex_trial1.Fragments.FragmentSearchItem;
import com.example.pokedex_trial1.Fragments.FragmentSearchLocation;

public class SearchActivity extends AppCompatActivity {

    Spinner spSearchType;
    ImageButton ibSearch;
    EditText etSearchId;
    FrameLayout flSearchContainer;

    // 0 = pokemon, 1 = item, 2 = location
    int searchCategory;

    ArrayAdapter<CharSequence> searchTypeAdapter;

    String idString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search");

        spSearchType = findViewById(R.id.spSearchType);
        ibSearch = findViewById(R.id.ibSearch);
        etSearchId = findViewById(R.id.etSearchId);
        flSearchContainer = findViewById(R.id.flSearchContainer);

        searchTypeAdapter = ArrayAdapter.createFromResource(this, R.array.SearchType, android.R.layout.simple_spinner_item);
        searchTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSearchType.setAdapter(searchTypeAdapter);

        spSearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                searchCategory = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void searchSelectedThing(View view) {
        closeKeyboard();
        idString = etSearchId.getText().toString();
        if (idString.isEmpty() || idString.equals("0")) {
            Toast.makeText(this, "Enter a valid ID!", Toast.LENGTH_SHORT).show();
        } else {
            switch (searchCategory) {
                case 0:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flSearchContainer, new FragmentPokemonDetails(getApplicationContext(), idString)).commit();
                    break;
                case 1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flSearchContainer, new FragmentSearchItem(getApplicationContext(), idString)).commit();
                    break;
                case 2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.flSearchContainer, new FragmentSearchLocation(getApplicationContext(), idString)).commit();
                    break;
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
