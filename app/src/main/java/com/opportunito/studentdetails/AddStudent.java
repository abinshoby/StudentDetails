//created by Abin shoby R3
//used to add students to database

package com.opportunito.studentdetails;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.support.design.widget.Snackbar;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import java.util.Objects;

public class AddStudent extends AppCompatActivity {
String[]branches={"Select Branch","B-ARCH","CSE","CIVIL","CHEMICAL","MECH","EC","MECH-PRO","EEE"};   //list of branches
    EditText namee;
    RadioGroup gendergr;
    CheckBox android;
    CheckBox web;
    CheckBox design;
    Spinner branchsel;
    SQLiteDatabase studdatabase;
    Intent def;                     //default intent activity=Mainactivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,branches);
        branchsel=(Spinner) findViewById(R.id.branche);
        namee=(EditText)findViewById(R.id.namee);//name box
        gendergr=(RadioGroup)findViewById(R.id.gendgr) ;
        android=(CheckBox)findViewById(R.id.android);
        web=(CheckBox)findViewById(R.id.web);
        design=(CheckBox)findViewById(R.id.design);
        def=new Intent(AddStudent.this,MainActivity.class);
        studdatabase=openOrCreateDatabase("student",MODE_PRIVATE,null);
        studdatabase.execSQL("CREATE TABLE IF NOT EXISTS studdetails("+
        "name TEXT NOT NULL PRIMARY KEY,"+
        "branch TEXT NOT NULL,"+
        "gender TEXT NOT NULL,"+
        "interests TEXT NOT NULL);");

        branchsel.setAdapter(adapter);
        branchsel.setDropDownVerticalOffset(100);//sets offset for drop menu from selection point

    }

    public void regdata(View view) {                        //register the data
        SharedPreferences preferences;
        String name=namee.getText().toString();
        String branch=branchsel.getSelectedItem().toString();
        int gendid=gendergr.getCheckedRadioButtonId();
        String gender="";

        RadioButton selected;
        if(gendid!=-1) {

             selected = (RadioButton) findViewById(gendid);
             gender=selected.getText().toString();
        }

        String Interest="";
        if(android.isChecked()){
            Interest+=android.getText().toString();
        }
        if(web.isChecked()){
            Interest+=","+web.getText().toString();
        }
        if(design.isChecked()){
            Interest+=","+design.getText().toString();
        }
        int count=0;    //counter for no of records
        int flag=0;
        AlertDialog.Builder builder;
        builder=new AlertDialog.Builder(AddStudent.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);
        builder.setTitle("Record not saved!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                studdatabase.close();                   //close database and start mainactivity on error
                startActivity(def);
                AddStudent.super.onBackPressed();

            }
        });

        if(name.matches("") || branch.matches("Select Branch")|| gender.matches("")){   //check for empty data
            builder.setMessage("Please enter valid information");
            builder.show();


        }
        else {
            Cursor studset = studdatabase.rawQuery("SELECT * FROM studdetails", null);        //check for redundancy
            studset.moveToFirst();
            if (studset.isAfterLast()) {
                flag = 0;
            }
            else {
                long numRows = DatabaseUtils.longForQuery(studdatabase, "SELECT COUNT(*) FROM studdetails", null);
                int i = 0;
                while (i < numRows) {
                    if (Objects.equals(studset.getString(0), name)) {
                        flag = 1;
                        break;
                    }
                    studset.moveToNext();
                    i++;
                }
            }
            studset.close();
            if (flag == 0) {
                studdatabase.execSQL("INSERT INTO studdetails VALUES(" +
                        "'" + name + "'" + "," + "'" + branch + "'" + "," + "'" + gender + "'" + "," + "'" + Interest + "'" + ");");
                preferences = getSharedPreferences("student", MODE_PRIVATE);
                count = preferences.getInt("register_count", 0);
                count++;
                preferences.edit().putInt("register_count", count).apply();      //increase the no of records
                Snackbar.make(findViewById(R.id.submit),"Record no:" + preferences.getInt("register_count", 0)+"  saved sucessfully..",Snackbar.LENGTH_LONG).show();

            }
            else
            {
                builder.setMessage("Your name is already registered!");
                builder.show();
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        studdatabase.close();//always close database on exit from addstudent activity to avoid error
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(def);//start main activity on exit
    }
}
