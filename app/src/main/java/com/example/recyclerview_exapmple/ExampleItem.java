package com.example.recyclerview_exapmple;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ExampleItem {
    public static String getAmount;
    private String mAmount;
    private String mType;
    private String mDate;
    private String mDesc;
    private String mCurrency;

    public ExampleItem(String  amount , String type , String date , String desc , String currency){
        mAmount = amount;
        mType = type;
        mDate = date;
        mDesc = desc;
        mCurrency = currency;
    }

    public String getAmount(){ return mAmount;}
    public String getType(){
        return mType;
    }
    public String getmDesc(){ return mDesc;}
    public String getmDate(){ return mDate;}
    public String getmCurrency(){return mCurrency;}

    public void changeExampleItem(String Amount , String Type , String Date , String Description , String Currency){
        mAmount = Amount;
        mType = Type;
        mDate = Date;
        mDesc = Description;
        mCurrency = Currency;
    }
    
}
