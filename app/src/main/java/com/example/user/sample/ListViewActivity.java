package com.example.user.sample;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2017-11-20.
 */

public class ListViewActivity extends AppCompatActivity {

    String REQUEST_CODE;

    TextView btnSearch;
    ListView listView;
    TextView txtSelect;
    Spinner spnSelect1, spnSelect2;

    ArrayAdapter spnAdapter1, spnAdapter2;

    TownItem[] TownItems;
    ListViewAdapter adapter;

    SQLiteDatabase db;

    String key1, key2;
    int itemNum;

    private void set() {
        Intent intent = getIntent();
        REQUEST_CODE = intent.getStringExtra("REQUEST_CODE");

        if(REQUEST_CODE.equals("city"))
            spnAdapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.state_spinner, android.R.layout.simple_spinner_item);
        else if(REQUEST_CODE.equals("program")) {
            spnAdapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.program_spinner, android.R.layout.simple_spinner_item);

            spnSelect2.setVisibility(View.GONE);
        }
        spnSelect1.setAdapter(spnAdapter1);

        spnSelect1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                key1 = String.valueOf(parent.getItemAtPosition(position));

                if(REQUEST_CODE.equals("city"))
                    setCitySpinner(key1);
                else if(REQUEST_CODE.equals("program"))
                    txtSelect.setText(key1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCitySpinner(String key) {
        if(key1.equals("전체보기")) {
            txtSelect.setText("전체보기");
        }
        else {
            spnSelect2.setVisibility(View.VISIBLE);

            if(key.equals("서울특별시"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.seoul_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("대전광역시"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.daejeon_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("대구광역시"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.daegu_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("울산광역시"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.ulsan_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("부산광역시"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.busan_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("광주광역시"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gwangju_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("세종특별자치시"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sejong_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("경기도"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gyeonggi_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("강원도"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gangwon_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("충청남도"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.chungnam_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("충청북도"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.chungbuk_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("경상북도"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gyeongbuk_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("경상남도"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gyeongnam_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("전라북도"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jeonbuk_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("전라남도"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jeonnam_spinner, android.R.layout.simple_spinner_item);
            else if(key.equals("제주특별자치도"))
                spnAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jeju_spinner, android.R.layout.simple_spinner_item);

            spnSelect2.setAdapter(spnAdapter2);

            spnSelect2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    key2 = String.valueOf(parent.getItemAtPosition(position));

                    txtSelect.setText(key1 + " " + key2);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = (ListView) findViewById(R.id.listView);
        spnSelect1 = (Spinner) findViewById(R.id.spnSelect1);
        spnSelect2 = (Spinner) findViewById(R.id.spnSelect2);
        txtSelect = (TextView) findViewById(R.id.txtSelect);

        set();

        btnSearch = (TextView) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openOrCreateDatabase("data.db", MODE_PRIVATE, null);

                if(REQUEST_CODE.equals("city"))
                    findDatabase(key1, key2);
                else if(REQUEST_CODE.equals("program"))
                    findDatabase(key1);


                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String townName = TownItems[position].getName();
                        String state = TownItems[position].getState();
                        String city = TownItems[position].getCity();

                        Intent myIntent = new Intent(getApplicationContext(), TownActivity.class);

                        myIntent.putExtra("TOWN_NAME", townName);
                        myIntent.putExtra("TOWN_STATE", state);
                        myIntent.putExtra("TOWN_CITY", city);

                        startActivity(myIntent);
                    }
                });
            }
        });

    }

    class ListViewAdapter extends BaseAdapter {
        ArrayList<TownItem> items = new ArrayList<TownItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        private void addItem(TownItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TownItemView view = new TownItemView(getApplicationContext());

            TownItem item = items.get(position);
            view.setName(item.getName());
            view.setCity(item.getState(), item.getCity());
            view.setActivity(item.getActivity());

            return view;
        }
    }

    private void findDatabase(String key) {
        String sql;

        if(db != null) {

            if (!key.equals("전체보기"))
                sql = "select townName, state, city, activity from town where program = \"" + key + "\";";
            else
                sql = "select townName, state, city, activity from town";

            setListView(db.rawQuery(sql, null));
        }
    }

    private void findDatabase(String key1, String key2) {
        String sql;

        if (!key1.equals("전체보기")) {
            if (!key2.equals("전체보기"))
                sql = "select townName, state, city, activity from town where state = \"" + key1 + "\" AND " + "city = \"" + key2 + "\";";
            else
                sql = "select townName, state, city, activity from town where state = \"" + key1 + "\";";
        }
        else
            sql = "select townName, state, city, activity from town";


        setListView(db.rawQuery(sql, null));
    }

    private void setListView(Cursor cursor) {
        itemNum = cursor.getCount();

        adapter = new ListViewAdapter();
        TownItems = new TownItem[itemNum];

        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();

            TownItems[i] = new TownItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            adapter.addItem(TownItems[i]);
        }
    }
}
