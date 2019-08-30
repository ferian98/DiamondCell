package com.example.android.diamondcell;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseClass<V>{
    public V mObjek;

    private final String URL_DATABASE="https://prototypal-leakages.000webhostapp.com/test.php?";
    private final String ID="root123";
    private final String PASSWORD="123";
    private ArrayList<KeyValuePair> parameters;
    private void KoneksiKeDatabase(){
        parameters=new ArrayList<>();
        parameters.add(new KeyValuePair("id",this.ID));
        parameters.add(new KeyValuePair("password",this.PASSWORD));
    }

    public DatabaseClass() {
        KoneksiKeDatabase();
    }

    public void save(String namaTabel,ArrayList<KeyValuePair> isitabel,AfterGetResponseListenerWrite afterGetResponseListenerWrite){
        String kueri;
        int indekstertinggi;
        kueri= "Insert into "+namaTabel+" (";
        indekstertinggi=isitabel.size()-1;
        for (int i=0;i<indekstertinggi;i++) {
            kueri=kueri+isitabel.get(i).getmName()+",";
        }
        kueri=kueri+isitabel.get(indekstertinggi).getmName()+") values('";

        for (int i=0;i<indekstertinggi;i++) {
            kueri=kueri+isitabel.get(i).getmValue()+"','";
        }
        kueri=kueri+isitabel.get(indekstertinggi).getmName()+"')";
        Log.e("Database Class Kueri", "save: " +kueri );
        parameters.add(new KeyValuePair("kueri",kueri));
        WriteAsync writeAsync=new WriteAsync(URL_DATABASE,parameters,afterGetResponseListenerWrite);
        writeAsync.execute();
    }
    public void fetchAll(String namaTabel, AfterGetResponseListenerRead<V> afterGetResponseListenerRead){
        String kueri="Select * from "+namaTabel;
        parameters.add(new KeyValuePair("kueri",kueri));
        ReadAsync readAsync=new ReadAsync(URL_DATABASE,parameters, afterGetResponseListenerRead);
        readAsync.execute();
    }
    public void fetchByCondition(String namaTabel, String seleksiDataSyarat, AfterGetResponseListenerRead<V> afterGetResponseListenerRead){
        String kueri="Select * from "+namaTabel;
        parameters.add(new KeyValuePair("kueri",kueri));
        ReadAsync readAsync= new ReadAsync(URL_DATABASE,parameters, afterGetResponseListenerRead);
        readAsync.execute();
    }
    public void update(String namaTabel, ArrayList<KeyValuePair> isitabel,
                       String seleksiData,AfterGetResponseListenerWrite afterGetResponseListenerWrite){
        String kueri;
        int indekstertinggitabel;
        indekstertinggitabel=isitabel.size()-1;
        kueri="Update "+namaTabel+" set ";
        for (int i=0;i<indekstertinggitabel;i++){
            kueri=kueri+isitabel.get(i).getmName()+"='"
            +isitabel.get(i).getmValue()+"',";
        }
        kueri=kueri+isitabel.get(indekstertinggitabel).getmName()+"='"+
                isitabel.get(indekstertinggitabel).getmValue()+"'";
        kueri=kueri+" where "+seleksiData;
        Log.e("Database Class Kueri", "update: " +kueri );
        parameters.add(new KeyValuePair("kueri",kueri));
        WriteAsync writeAsync=new WriteAsync(URL_DATABASE,parameters,afterGetResponseListenerWrite);
        writeAsync.execute();
    }
    public void delete(String namaTabel,String seleksiData,AfterGetResponseListenerWrite afterGetResponseListenerWrite){
        String kueri="Delete from "+namaTabel+" where "+ seleksiData;
        parameters.add(new KeyValuePair("kueri",kueri));
        WriteAsync writeAsync= new WriteAsync(URL_DATABASE,parameters,afterGetResponseListenerWrite);
        Log.e("Database Class Kueri", "save: " +kueri );
        writeAsync.execute();
    }
    public void queryWrite(String Kueri,AfterGetResponseListenerWrite afterGetResponseListenerWrite){
        parameters.add(new KeyValuePair("kueri",Kueri));
        Log.e("Database Class Kueri", "queryWrite: " +Kueri );
        WriteAsync writeAsync= new WriteAsync(URL_DATABASE,parameters,afterGetResponseListenerWrite);
        writeAsync.execute();
    }
    public void queryRead(String Kueri, AfterGetResponseListenerRead<V> afterGetResponseListenerRead){
        parameters.add(new KeyValuePair("kueri",Kueri));
        Log.e("Database Class Kueri", "queryRead: " +Kueri );
        ReadAsync readAsync= new ReadAsync(URL_DATABASE,parameters, afterGetResponseListenerRead);
        readAsync.execute();
    }
    private class ReadAsync extends AsyncTask<Void,Void,ArrayList<V>>{
        private String DatabaseUrl;

        private ArrayList<KeyValuePair> parameters;
        private AfterGetResponseListenerRead<V> afterGetResponseListenerRead;

        public ReadAsync(String databaseUrl ,ArrayList<KeyValuePair> parameters, AfterGetResponseListenerRead<V> afterGetResponseListenerRead) {
            DatabaseUrl = databaseUrl;
            this.parameters = parameters;
            this.afterGetResponseListenerRead = afterGetResponseListenerRead;
        }

        @Override
        protected ArrayList<V> doInBackground(Void... strings) {
            String responseJson = "";
            try {
                responseJson=KoneksiKeJaringan.getHttpResponse(DatabaseUrl,parameters);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return afterGetResponseListenerRead.afterGetResponse(responseJson);
        }

        @Override
        protected void onPostExecute(ArrayList<V> v) {
            super.onPostExecute(v);
            afterGetResponseListenerRead.updateUIThread(v);
        }
    }
    private class WriteAsync extends AsyncTask<Void,Void,Boolean>{
        private String DatabaseUrl;
        private ArrayList<KeyValuePair> parameters;
        private AfterGetResponseListenerWrite afterGetResponseListener;

        public WriteAsync(String databaseUrl, ArrayList<KeyValuePair> parameters, AfterGetResponseListenerWrite afterGetResponseListener) {
            DatabaseUrl = databaseUrl;
            this.parameters = parameters;
            this.afterGetResponseListener = afterGetResponseListener;
        }

        public WriteAsync(String databaseUrl, ArrayList<KeyValuePair> parameters) {
            DatabaseUrl = databaseUrl;
            this.parameters = parameters;
            this.afterGetResponseListener= new AfterGetResponseListenerWrite() {
                @Override
                public void updateUIThread(Boolean response) {

                }
            };
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean result = false;
            try {
                result= KoneksiKeJaringan.getHttpResponseForWrite(DatabaseUrl,parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            afterGetResponseListener.updateUIThread(aBoolean);
        }
    }
    public interface AfterGetResponseListenerRead<T> {
        public ArrayList<T> afterGetResponse(String responseJSON);
        public void updateUIThread(ArrayList<T> response);
    }
    public interface AfterGetResponseListenerWrite {
        public void updateUIThread(Boolean response);
    }
}
