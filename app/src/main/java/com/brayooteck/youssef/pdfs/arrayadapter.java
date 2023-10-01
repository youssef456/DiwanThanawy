package com.brayooteck.youssef.pdfs;



public class arrayadapter {

    private String mName;
    private int mSize;
    private String mUrl;
    private int mAnsp;
    private boolean mValue;



    public arrayadapter(String name, String url, int size ,int ansp,boolean value) {
        mName = name;
        mSize =size;
        mUrl = url;
        mAnsp = ansp;
        mValue =value;
    }





    public  String getName() {
        return mName;
    }


    public  int getSize() {
        return mSize;
    }


    public String getUrl() {
        return mUrl;
    }


    public boolean getValue() {
        return mValue;
    }


    public int getAnsp() {
        return mAnsp;
    }
}