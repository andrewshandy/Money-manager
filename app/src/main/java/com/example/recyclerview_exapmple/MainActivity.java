package com.example.recyclerview_exapmple;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static ArrayList<ExampleItem> mExampleList;

    private RecyclerView mRecyclerView;
    private static ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;
    private FloatingActionButton buttonCreate;
    private TextView sumTextView;
    public static final String APP_PREFERENCES = "mysettings";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        buildRecyclerView();
        setButtons();
        setSum();
    }

    public void changeItem(int position, String amount, String type, String date, String Desc , String Currency){
        ExampleItem item = mExampleList.get(position);
        item.changeExampleItem( amount,type, date, Desc , Currency);
        mAdapter.notifyItemChanged(position);
    }

    public void insertItem(int position, String amount, String type, String date, String Desc , String currency) {

        mExampleList.add(position, new ExampleItem(amount, type, date, Desc , currency));
        mAdapter.notifyItemInserted(position);
        saveData();
    }

    public void removeItem(int position) {
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
        saveData();
    }

    //    public void changeItem(int position , String text){
//        mExampleList.get(position).changeText1(text);
//        mAdapter.notifyItemChanged(position);
//    }
//        mExampleList.add(new ExampleItem(5000, "Credit" , "Bank"  ,   "20.01.1231" , "this is example DEscription"  ));
    public void createExampleList() {
        mExampleList = new ArrayList<>();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                changeItem(position , "Clicked");
            }



            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                setSum();
            }

            @Override
            public void onEditClick(int position) {
                editItem(position);
            }
        });
    }
    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

    public void setButtons(){
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , CreateNoteActivity.class);
                intent.putExtra("amount" , "");
                intent.putExtra("desc" , "");
                intent.putExtra("type" , "");
                intent.putExtra("date" , "");
                intent.putExtra("currency" , "$");
                startActivityForResult(intent , 1);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && buttonCreate.getVisibility() == View.VISIBLE) {
                    buttonCreate.hide();
                } else if (dy < 0 && buttonCreate.getVisibility() != View.VISIBLE) {
                    buttonCreate.show();
                }
            }
        });

//        buttonRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = Integer.parseInt(editTextRemove.getText().toString());
//                removeItem(position);
//                setSum();
//            }
//        });
    }
    public void editItem(int position){
        ExampleItem item = mExampleList.get(position);
        String amount = item.getAmount();
        String currency = item.getmCurrency();
        String desc = item.getmDesc();
        String date = item.getmDate();
        String type = item.getType();
        Intent intent = new Intent(MainActivity.this , CreateNoteActivity.class);
        intent.putExtra("amount" , amount);
        intent.putExtra("desc" , desc);
        intent.putExtra("type" , type);
        intent.putExtra("date" , date);
        intent.putExtra("position" , position);
        intent.putExtra("currency" , currency);
        startActivityForResult(intent , 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String amount = data.getStringExtra("amount");
                String date = data.getStringExtra("date");
                String Desc = data.getStringExtra("description");
                String type = data.getStringExtra("type");
                String currency = data.getStringExtra("currency");
                int position = mExampleList.size();
                insertItem(position , amount , type , date , Desc , currency);
                setSum();
                saveData();
            }
        }
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                String amount = data.getStringExtra("amount");
                String date = data.getStringExtra("date");
                String Desc = data.getStringExtra("description");
                String type = data.getStringExtra("type");
                String currency = data.getStringExtra("currency");
                int position = data.getIntExtra("position" , 0);
                changeItem(position , amount, type, date, Desc , currency);
                setSum();
                saveData();
            }
        }
    }
    private void setSum(){
        sumTextView = findViewById(R.id.sumTv);
        int sum = 0;
        for (ExampleItem item : mExampleList){
            if (item.getAmount() != "" || item.getAmount() != null){
                sum += Integer.parseInt(item.getAmount());
            }
        }
        sumTextView.setText(String.valueOf(sum));
    }
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences" , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json= gson.toJson(mExampleList);
        editor.putString("task list" , json);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences" , MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list" , null);
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        mExampleList= gson.fromJson(json , type);
        if (mExampleList == null){
            mExampleList = new ArrayList<>();
        }
    }

}