package com.example.sqtest;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
//references to buttons and other controls on the layout
    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    CheckBox cb_activeUser;
    ListView lv_userList;
    ArrayAdapter customerArrayAdapter;
    DataBaseTest dataBaseTest;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        cb_activeUser = findViewById(R.id.cb_active);
        lv_userList = findViewById(R.id.lv_userList);

        dataBaseTest = new DataBaseTest(MainActivity.this);

        ShowUserOnListView(dataBaseTest);

        //button listeners for the add and view all buttons
        btn_add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                CustomerModel userModel;
                try {
                   userModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), cb_activeUser.isChecked());

                    //Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                    userModel = new CustomerModel(-1, "error", 0, false);
                }

                boolean success = dataBaseTest.addOne(userModel);

                Toast.makeText(MainActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                ShowUserOnListView(dataBaseTest);
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            DataBaseTest dataBaseTest = new DataBaseTest(MainActivity.this);


            public void onClick(View v){
                ShowUserOnListView(dataBaseTest);

                //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();
           }
        });
        lv_userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedUser = (CustomerModel) parent.getItemAtPosition(position);
            dataBaseTest.deleteOne(clickedUser);
            ShowUserOnListView(dataBaseTest);
            Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void ShowUserOnListView(DataBaseTest dataBaseTest1) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, this.dataBaseTest.getEveryone());
        lv_userList.setAdapter(customerArrayAdapter);
    }
}