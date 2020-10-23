package com.example.recyclerview_exapmple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText text;
    private Button buttonFinishCreating;
    private String type;
    private String date;
    private String amount;
    private String description;
    private String currency;
    private EditText text_amount;
    private EditText text_description;
    private EditText text_date;
    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);


        Spinner type_spinner = findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> type_adapter = ArrayAdapter.createFromResource(this , R.array.Types, android.R.layout.simple_spinner_item);
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(type_adapter);
        type_spinner.setOnItemSelectedListener(this);

        Spinner currency_spinner = findViewById(R.id.spinnerCurrency);
        ArrayAdapter<CharSequence> currency_adapter = ArrayAdapter.createFromResource(this , R.array.Сurrencies, android.R.layout.simple_spinner_item);
        currency_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency_spinner.setAdapter(currency_adapter);
        currency_spinner.setOnItemSelectedListener(this);
        Intent intent = getIntent();
        text_amount = (EditText)findViewById(R.id.etAmount);
        text_description = (EditText)findViewById(R.id.etDesc);
        text_date = (EditText)findViewById(R.id.etDate);
        amount = intent.getStringExtra("amount");
        type = intent.getStringExtra("type");
        currency = intent.getStringExtra("currency");
        date = intent.getStringExtra("date");
        description = intent.getStringExtra("desc");
        position = intent.getIntExtra("position" , 0);
        text_amount.setText(amount);
        text_date.setText(date);
        text_description.setText(description);
        type_spinner.setSelection(type_adapter.getPosition(type));
        currency_spinner.setSelection(currency_adapter.getPosition(currency));

        buttonFinishCreating = findViewById(R.id.buttonFinishCreating);
        buttonFinishCreating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCreating();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("type" , type);
                resultIntent.putExtra("date" , date);
                resultIntent.putExtra("description" , description);
                resultIntent.putExtra("amount" , amount);
                resultIntent.putExtra("position" , position);
                resultIntent.putExtra("currency" , currency);
                setResult(RESULT_OK , resultIntent);
                finish();
            }
        });
    }
    public void finishCreating(){
        amount = text_amount.getText().toString();
        description = text_description.getText().toString();
        date = text_date.getText().toString();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerType){
            type = parent.getItemAtPosition(position).toString();
        }
        if (parent.getId() == R.id.spinnerCurrency){
            currency = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}