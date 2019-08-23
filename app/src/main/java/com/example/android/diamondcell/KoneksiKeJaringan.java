package com.example.android.diamondcell;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class KoneksiKeJaringan {
    private static final String DEBUG_TAG = KoneksiKeJaringan.class.getSimpleName();;

    public static String getHttpResponse(String BaseAddress, ArrayList<KeyValuePair> keyValuePairs) throws Exception{
        String urladdress;
        Uri.Builder builderbase;
        Uri uri;
        URL url;
        HttpURLConnection connection;
        int responseCode;
        BufferedReader reader;
        InputStream inputStream;
        String line;
        StringBuffer buffer;
        String result;

        //Persiapkan String untuk urladress
        if (BaseAddress==null){
            return null;
        }
        urladdress = BaseAddress;
        builderbase = Uri.parse(urladdress).buildUpon();
        if (keyValuePairs.size()>0){
            for (KeyValuePair keyvaluepair: keyValuePairs) {
                builderbase=builderbase.appendQueryParameter(keyvaluepair.getmName(),
                        keyvaluepair.getmValue());
            }

        }else {
            Log.d(DEBUG_TAG, "The response is: size is 0" );
        }
        uri = builderbase.build();
        urladdress=uri.toString();
        Log.d(DEBUG_TAG, "The address is: "+urladdress );
        //Buat Url Objek untuk memulai koneksi beserta keterangan tambahannya
        url = new URL(urladdress);
        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(4000);
        connection.setRequestMethod("GET");
        connection.connect();

        //Dapatkan Respons
        responseCode = connection.getResponseCode();
        Log.d(DEBUG_TAG, "The response is: " + responseCode);
        inputStream=connection.getInputStream();
        if (inputStream==null){
            result=null;
        }

        //Buffer respons ke String
        reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        buffer=new StringBuffer();
        while ((line=reader.readLine())!=null){
            buffer.append(line+"\n");
        };
        if (buffer.length()==0){
            result= null;
        }
        result=buffer.toString();

        //Tutup semua koneksi
        if (connection!=null){
            connection.disconnect();
        }
        if (reader!=null){
            reader.close();
        }
        return result;
    }
    public static Boolean getHttpResponseForWrite(String BaseAddress, ArrayList<KeyValuePair> keyValuePairs) throws Exception{
        String urladdress;
        Uri.Builder builderbase;
        Uri uri;
        URL url;
        HttpURLConnection connection;
        int responseCode;
        BufferedReader reader;
        InputStream inputStream;
        String line;
        StringBuffer buffer;
        Boolean result = true;

        //Persiapkan String untuk urladress
        if (BaseAddress==null){
            return null;
        }
        urladdress = BaseAddress;
        builderbase = Uri.parse(urladdress).buildUpon();
        if (keyValuePairs.size()>0){
            for (KeyValuePair keyvaluepair: keyValuePairs) {
                builderbase=builderbase.appendQueryParameter(keyvaluepair.getmName(),
                        keyvaluepair.getmValue());
            }

        }else {
            Log.d(DEBUG_TAG, "The response is: size is 0" );
        }
        uri = builderbase.build();
        urladdress=uri.toString();
        Log.d(DEBUG_TAG, "The address is: "+urladdress );
        //Buat Url Objek untuk memulai koneksi beserta keterangan tambahannya
        url = new URL(urladdress);
        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(4000);
        connection.setRequestMethod("GET");
        connection.connect();

        //Dapatkan Respons
        responseCode = connection.getResponseCode();
        Log.d(DEBUG_TAG, "The response is: " + responseCode);
        inputStream=connection.getInputStream();
        if (inputStream==null){
            result=null;
        }

        //Tutup semua koneksi
        if (connection!=null){
            connection.disconnect();
        }
        if (result==null){
            return false;
        }else {
            return true;
        }
    }


}
