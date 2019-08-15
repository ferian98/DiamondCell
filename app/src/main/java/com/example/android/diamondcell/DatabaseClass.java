package com.example.android.diamondcell;

import android.os.AsyncTask;

import java.util.ArrayList;

public class DatabaseClass<V>{
    public V mObjek;

    private final String URL_DATABASE="https://bla";
    private final String ID="1234ROOT01";
    private final String PASSWORD="12345ASDFQWERT";
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
            kueri=kueri+isitabel.get(i).getmName()+"','";
        }
        kueri=kueri+isitabel.get(indekstertinggi).getmName()+"')";
        parameters.add(new KeyValuePair("kueri",kueri));
        WriteAsync writeAsync=new WriteAsync(URL_DATABASE,parameters,afterGetResponseListenerWrite);
        writeAsync.execute();
    }
    public void fetchAll(String namaTabel, AfterGetResponseListener<V> afterGetResponseListener){
        String kueri="Select * from "+namaTabel;
        parameters.add(new KeyValuePair("kueri",kueri));
        ReadAsync readAsync=new ReadAsync(URL_DATABASE,parameters,afterGetResponseListener);
        readAsync.execute();
    }
    public void fetchByCondition(String namaTabel,String seleksiDataSyarat,AfterGetResponseListener<V> afterGetResponseListener){
        String kueri="Select * from "+namaTabel;
        parameters.add(new KeyValuePair("kueri",kueri));
        ReadAsync readAsync= new ReadAsync(URL_DATABASE,parameters,afterGetResponseListener);
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
        parameters.add(new KeyValuePair("kueri",kueri));
        WriteAsync writeAsync=new WriteAsync(URL_DATABASE,parameters,afterGetResponseListenerWrite);
        writeAsync.execute();
    }
    public void delete(String namaTabel,String seleksiData,AfterGetResponseListenerWrite afterGetResponseListenerWrite){
        String kueri="Delete from "+namaTabel+" where "+ seleksiData;
        parameters.add(new KeyValuePair("kueri",kueri));
        WriteAsync writeAsync= new WriteAsync(URL_DATABASE,parameters,afterGetResponseListenerWrite);
        writeAsync.execute();
    }
    public void queryWrite(String Kueri,AfterGetResponseListenerWrite afterGetResponseListenerWrite){
        parameters.add(new KeyValuePair("kueri",Kueri));
        WriteAsync writeAsync= new WriteAsync(URL_DATABASE,parameters,afterGetResponseListenerWrite);
        writeAsync.execute();
    }
    public void queryRead(String Kueri,AfterGetResponseListener<V> afterGetResponseListener){
        parameters.add(new KeyValuePair("kueri",Kueri));
        ReadAsync readAsync= new ReadAsync(URL_DATABASE,parameters,afterGetResponseListener);
        readAsync.execute();
    }
    private class ReadAsync extends AsyncTask<Void,Void,V>{
        private String DatabaseUrl;

        private ArrayList<KeyValuePair> parameters;
        private AfterGetResponseListener<V> afterGetResponseListener;

        public ReadAsync(String databaseUrl ,ArrayList<KeyValuePair> parameters, AfterGetResponseListener<V> afterGetResponseListener) {
            DatabaseUrl = databaseUrl;
            this.parameters = parameters;
            this.afterGetResponseListener = afterGetResponseListener;
        }

        @Override
        protected V doInBackground(Void... strings) {
            String responseJson = "";
            try {
                responseJson=KoneksiKeJaringan.getHttpResponse(DatabaseUrl,parameters);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return afterGetResponseListener.afterGetResponse(responseJson);
        }

        @Override
        protected void onPostExecute(V v) {
            super.onPostExecute(v);
            afterGetResponseListener.updateUIThread(v);
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
    public interface AfterGetResponseListener<T> {
        public T afterGetResponse(String responseJSON);
        public void updateUIThread(T response);
    }
    public interface AfterGetResponseListenerWrite {
        public void updateUIThread(Boolean response);
    }
}
