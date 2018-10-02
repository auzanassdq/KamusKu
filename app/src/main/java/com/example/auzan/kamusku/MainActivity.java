package com.example.auzan.kamusku;

import android.database.SQLException;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;

import com.example.auzan.kamusku.Adapter.ListKamusAdapter;
import com.example.auzan.kamusku.Database.KamusHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ListKamusAdapter listKamusAdapter;
    ArrayList<Kamus> kamus;
    KamusHelper kamusHelper;
    boolean isIndonesia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        kamusHelper = new KamusHelper(this);
        listKamusAdapter = new ListKamusAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listKamusAdapter);

        isIndonesia = true;
        getData(isIndonesia, "");
    }

    private void getData(boolean lang, String query){
        try {
            kamusHelper.open();

            kamus = query.isEmpty() ?
                    kamusHelper.getAllData(lang) :
                    kamusHelper.getDataByWord(query, lang);

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            kamusHelper.close();
        }
        listKamusAdapter.replaceAll(kamus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.mn_search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                kamus.clear();
                getData(isIndonesia, query);
                listKamusAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    getDefaultData();
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_indonesia:
                isIndonesia = true;
                getData(isIndonesia, "");
                break;
            case R.id.mn_english:
                isIndonesia = false;
                getData(isIndonesia, "");
                break;
        }
        getDefaultData();
        return super.onOptionsItemSelected(item);
    }

    private void getDefaultData() {
        kamus.clear();
        getData(isIndonesia, "");
        listKamusAdapter.notifyDataSetChanged();
    }
}
