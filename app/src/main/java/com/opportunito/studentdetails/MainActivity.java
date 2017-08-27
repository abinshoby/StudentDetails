//created by Abin Shoby ,R3
//view brief info about students

package com.opportunito.studentdetails;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.opportunito.studentdetails.R.id.submit;

public class MainActivity extends AppCompatActivity {
    ListView Detview;
    Button submit;
    SQLiteDatabase studdatabase;
    Intent offs;
    TextView recf;
    boolean backpressed=false;
    ArrayList<String> al=new ArrayList<>();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recf=(TextView)findViewById(R.id.recf);
        studdatabase=openOrCreateDatabase("student",MODE_PRIVATE,null);
        submit=(Button)findViewById(R.id.submit);
        studdatabase.execSQL("CREATE TABLE IF NOT EXISTS studdetails("+
                "name TEXT NOT NULL PRIMARY KEY,"+
                "branch TEXT NOT NULL,"+
                "gender TEXT NOT NULL,"+
                "interests TEXT NOT NULL);");
        Cursor studset=studdatabase.rawQuery("SELECT * FROM studdetails",null);
        studset.moveToFirst();
        if(studset.isAfterLast()){

            recf.setVisibility(View.VISIBLE);//check for no data
            studset.close();
        }
        else{

            long numRows= DatabaseUtils.longForQuery(studdatabase,"SELECT COUNT(*) FROM studdetails",null);
            int i=0;
            while(i<numRows)
            {
                al.add(studset.getString(0)+"\n"+studset.getString(1));//add each data to list
                studset.moveToNext();
                i++;

            }
        studset.close();
        studdatabase.close();}
        Detview=(ListView)findViewById(R.id.Detview);
        adapter=new ArrayAdapter(this,R.layout.listitem,al);
        Detview.setAdapter(adapter);

        Detview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clname=al.get(position);
                Intent activity2=new Intent(MainActivity.this,Main2Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name",clname);//pass the data of clicked item
                activity2.putExtras(bundle);
                startActivity(activity2);

            }
        });
    }

    public void addstud(View view) { //action for add student button
        Intent addstudent=new Intent(MainActivity.this,AddStudent.class);
        startActivity(addstudent);
        super.onBackPressed();
    }
    public void onBackPressed(){ //double backpress for exit
        if(backpressed) {
            super.onBackPressed();

        }
        else{
           Snackbar.make(findViewById(R.id.add),"Tap again to exit", Snackbar.LENGTH_SHORT).show();
            backpressed=true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backpressed=false;

                }
            },1000);

        }
    }
    public boolean onCreateOptionsMenu(Menu menu){             //create menu
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){   // for selecting menu item
        switch (item.getItemId()){
            case R.id.official:
                offs=new Intent(MainActivity.this,OfficialSite.class); //official site activity
                startActivity(offs);
                return true;

            case R.id.credits:
                Toast.makeText(getApplicationContext(),"Credits:Abin Shoby",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
