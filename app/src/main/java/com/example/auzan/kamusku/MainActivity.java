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
import android.widget.Toast;

import com.example.auzan.kamusku.Adapter.ListKamusAdapter;
import com.example.auzan.kamusku.Database.KamusHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ListKamusAdapter listKamusAdapter;
    ArrayList<Kamus> kamus;
    KamusHelper kamusHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        kamusHelper = new KamusHelper(this);
        listKamusAdapter = new ListKamusAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(listKamusAdapter);
        kamusHelper.open();

        ArrayList<Kamus> kamus = kamusHelper.getAllData();
        kamusHelper.close();
        listKamusAdapter.addItem(kamus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.mn_search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /*
            Gunakan method ini ketika search selesai atau OK
             */

            @Override
            public boolean onQueryTextSubmit(String query) {
//                kamus.clear();
//                kamus.addAll(kamusHelper.getDataByWord(query));
//                listKamusAdapter.notifyDataSetChanged();
                return false;
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            @Override
            public boolean onQueryTextChange(String newText) {
//                if (newText.isEmpty()) {
//                    getDefaultData();
//                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mn_indonesia:
//                isIndonesia = true;
//                language();
//                break;
//            case R.id.mn_english:
//                isIndonesia = false;
//                language();
//                break;
//            default:
//                isIndonesia = true;
//        }
        getDefaultData();
        return super.onOptionsItemSelected(item);
    }

    private void getDefaultData() {
        kamus.clear();
        kamus.addAll(kamusHelper.getAllData());
        listKamusAdapter.notifyDataSetChanged();
    }

    public void language(){
        kamusHelper = new KamusHelper(this);
        listKamusAdapter = new ListKamusAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(listKamusAdapter);


        kamusHelper.open();

        // Ambil semua data mahasiswa di database

        //mahasiswaModels = kamusHelper.getAllData(isEnglish);

        loadData();

        kamusHelper.close();

        listKamusAdapter.addItem(kamus);
    }

    private void loadData() {
        loadData("");
    }

    private void loadData(String search) {
        try {
            kamusHelper.open();
            if (search.isEmpty()) {
                kamus = kamusHelper.getAllData();
            } else {
                kamus = kamusHelper.getDataByWord(search);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            kamusHelper.close();
        }
        listKamusAdapter.replaceAll(kamus);
    }
}
