package com.example.user.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Random;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class Splashscreen extends Activity {

    String townName, state, city, program, activity, facility, address, president, callNumber, homepage, management;
    Double latitude, longitude;

    SQLiteDatabase db;

    Thread splashTread;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        imageView = (ImageView)findViewById(R.id.splash);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        int[] ids = new int[]{R.drawable.s_img};

        Random randomGenerator = new Random();

        int rg= randomGenerator.nextInt(ids.length);

        this.imageView.setImageDrawable(getResources().getDrawable(ids[rg]));

        splashTread = new Thread() {
            @Override
            public void run() {
                try {

                    int waited = 0;
                    // Splash screen pause time

                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }

                    Intent intent = new Intent(Splashscreen.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                    Splashscreen.this.finish();

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();
        try {
            createDatabase();

            int row = 0, column = 0;

            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("data.xls");
            Workbook workbook = Workbook.getWorkbook(inputStream);

            Sheet sh = workbook.getSheet("Sheet1");
            row = sh.getRows();
            column = sh.getColumns();

            for(int r = 1; r < row; r++) {
                for(int c = 0; c < column; c++) {
                    Cell sheetCell = sh.getCell(c,r);

                    switch(c) {
                        case 0:
                            townName = sheetCell.getContents();
                            break;
                        case 1:
                            state = sheetCell.getContents();
                            break;
                        case 2:
                            city = sheetCell.getContents();
                            break;
                        case 3:
                            program = sheetCell.getContents();
                            break;
                        case 4:
                            activity = sheetCell.getContents();
                            break;
                        case 5:
                            facility = sheetCell.getContents();
                            break;
                        case 6:
                            address = sheetCell.getContents();
                            break;
                        case 7:
                            president = sheetCell.getContents();
                            break;
                        case 8:
                            callNumber = sheetCell.getContents();
                            break;
                        case 9:
                            homepage = sheetCell.getContents();
                            break;
                        case 10:
                            management = sheetCell.getContents();
                            break;
                        case 11:
                            latitude = Double.valueOf(sheetCell.getContents());
                            break;
                        case 12:
                            longitude = Double.valueOf(sheetCell.getContents());
                            break;
                    }
                }

                insertData(townName, state, city, program, activity, facility, address, president, callNumber, homepage, management, latitude, longitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createDatabase() {
        db = openOrCreateDatabase("data.db", MODE_PRIVATE, null);

        if(db != null) {
            String sql = "create table town (townNum integer PRIMARY KEY autoincrement, townName text, state text, city text, program text, activity text, facility text, address text, president text, callNumber text, homepage text, management text, latitude real, longitude real);";
            db.execSQL(sql);
        }
    }

    private void insertData(String townName, String state, String city, String program, String activity, String facility, String address, String president, String callNumber, String homepage, String management, double latitude, double longitude) {
        if(db != null) {
            String sql = "insert into town (townName, state, city, program, activity, facility, address, president, callNumber, homepage, management, latitude, longitude) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            Object[] params = {townName, state, city, program, activity, facility, address, president, callNumber, homepage, management, latitude, longitude};

            db.execSQL(sql, params);
        }
    }
}