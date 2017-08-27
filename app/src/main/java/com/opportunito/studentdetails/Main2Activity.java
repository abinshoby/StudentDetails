//created by Abin Shoby ,R3
//detailed view of students details
package com.opportunito.studentdetails;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
TextView namev;
    TextView branchv;
    TextView genderv;
    TextView interestv;
    SQLiteDatabase studdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        studdatabase=openOrCreateDatabase("student",MODE_PRIVATE,null);
        Bundle bundle=getIntent().getExtras();
        namev=(TextView)findViewById(R.id.namev) ;
        branchv=(TextView)findViewById(R.id.branchv) ;
        genderv=(TextView)findViewById(R.id.genderv) ;
        interestv=(TextView)findViewById(R.id.Interestv) ;
        String nameppbranch=bundle.getString("name");       //retrieve data from list item selected
        String[] namebra=nameppbranch.split("\n");          //split name and branch
        String name=namebra[0];
        Cursor studset=studdatabase.rawQuery("SELECT * FROM studdetails WHERE name=?",new String[] {name});
        studset.moveToFirst();
        if(studset!=null && !studset.isAfterLast()) {     //retrieval of data
            namev.setText(studset.getString(0));
            branchv.setText(studset.getString(1));
            genderv.setText(studset.getString(2));
            interestv.setText(studset.getString(3));
            studset.close();
            studdatabase.close();
        }


    }
}
