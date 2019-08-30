package com.example.android.diamondcell;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Supplier implements Parcelable {
    private String mKode;
    private String mNama;
    private String mAlamat;
    private String mTelp;
    private String mHp;
    private String mEmail;
    private String mKontak;
    private boolean mStatus = false;
    private ArrayList<KeyValuePair> mPasanganKolomNilai;
    private static final String NAMATABEL="tbsuplier";
    private DatabaseClass<Supplier> mDatabase;
    public Supplier() {
    }

    public Supplier(String mKode) {
        this.mKode = mKode;
        //Todo: Dapatkan suplier berdasarkan kode
        buatParameterTabelPelanggan();
    }

    public Supplier (String kode, String nama, String alamat, String telp, String hp, String email, String kontak, boolean status) {
        setKode(kode);
        setNama(nama);
        setAlamat(alamat);
        setTelp(telp);
        setHp(hp);
        setEmail(email);
        setKontak(kontak);
        setStatus(status);
        buatParameterTabelPelanggan();
    }

    protected Supplier(Parcel in) {
        mKode = in.readString();
        mNama = in.readString();
        mAlamat = in.readString();
        mTelp = in.readString();
        mHp = in.readString();
        mEmail = in.readString();
        mKontak = in.readString();
        mStatus = in.readByte() != 0;
        buatParameterTabelPelanggan();
    }

    public static final Creator<Supplier> CREATOR = new Creator<Supplier>() {
        @Override
        public Supplier createFromParcel(Parcel in) {
            return new Supplier(in);
        }

        @Override
        public Supplier[] newArray(int size) {
            return new Supplier[size];
        }
    };

    public String getKode() {
        return mKode;
    }

    public void setKode(String kode) {
        this.mKode = kode;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        this.mNama = nama;
    }

    public String getAlamat() {
        return mAlamat;
    }

    public void setAlamat(String alamat) {
        this.mAlamat = alamat;
    }

    public String getTelp() {
        return mTelp;
    }

    public void setTelp(String telp) {
        this.mTelp = telp;
    }

    public String getHp() {
        return mHp;
    }

    public void setHp(String hp) {
        this.mHp = hp;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getKontak() {
        return mKontak;
    }

    public void setKontak(String kontak) {
        this.mKontak = kontak;
    }

    public boolean isActive() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        this.mStatus = status;
    }

    public String getStatusAsString(){
        if (mStatus){
            return "Aktif";
        }else if (!mStatus){
            return "Tidak Aktif";
        }else return "Unkown";
    }
    public void save(final UpdateOnUIThreadWrite<Supplier> metodeAfterSave) {
        //TODO:Implement method
        mDatabase.save(NAMATABEL, mPasanganKolomNilai, new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeAfterSave.updateOnUIThread(getInstance());
            }
        });
    }

    public void update(final UpdateOnUIThreadWrite<Supplier> metodeAfterUpdate) {
        //TODO:Implement method and add parameter
        mDatabase.update(NAMATABEL,mPasanganKolomNilai, mPasanganKolomNilai.get(0).getmName() + "="
                + mPasanganKolomNilai.get(0).getmValue(), new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeAfterUpdate.updateOnUIThread(getInstance());
            }
        });
    }

    public void delete(final UpdateOnUIThreadWrite<Supplier> metodeAfterDelete) {
        //TODO:Implement method and add parameter
        mDatabase.delete(NAMATABEL,mPasanganKolomNilai.get(0).getmName() + "="
                + mPasanganKolomNilai.get(0).getmValue(), new DatabaseClass.AfterGetResponseListenerWrite() {
            @Override
            public void updateUIThread(Boolean response) {
                metodeAfterDelete.updateOnUIThread(getInstance());
            }
        });
    }

    public static void fetch(final UpdateOnUIThreadRead<Supplier> metodeAfterFetch) {
        final DatabaseClass<Supplier> pelangganDatabaseClass= new DatabaseClass<>();
        pelangganDatabaseClass.fetchAll(NAMATABEL, new DatabaseClass.AfterGetResponseListenerRead<Supplier>() {
            @Override
            public ArrayList<Supplier> afterGetResponse(String responseJSON) {
                //Todo: parsing JSON
                try {
                    ArrayList<Supplier> result= new ArrayList<>();
                    JSONArray jsonArray= new JSONArray(responseJSON);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonSuplier=jsonArray.getJSONObject(i);
                        Supplier supplier=new Supplier(
                                jsonSuplier.getString("kode"),
                                jsonSuplier.getString("nama"),
                                jsonSuplier.getString("alamat"),
                                jsonSuplier.getString("telp"),
                                jsonSuplier.getString("hp"),
                                jsonSuplier.getString("email"),
                                jsonSuplier.getString("kontak"),
                                (jsonSuplier.getString("kontak").equals("Aktif"))? true:false
                                );
                        result.add(supplier);
                    }
                    return result;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void updateUIThread(ArrayList<Supplier> response) {
                metodeAfterFetch.updateOnUIThread(response);
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(mKode);
        parcel.writeString(mNama);
        parcel.writeString(mAlamat);
        parcel.writeString(mTelp);
        parcel.writeString(mHp);
        parcel.writeString(mEmail);
        parcel.writeString(mKontak);
        parcel.writeByte((byte) (mStatus ? 1 : 0));
    }

    private void buatParameterTabelPelanggan(){
        mPasanganKolomNilai= new ArrayList<>();
        mPasanganKolomNilai.add(new KeyValuePair("kode",mKode));
        mPasanganKolomNilai.add(new KeyValuePair("nama",mNama));
        mPasanganKolomNilai.add(new KeyValuePair("alamat",mAlamat));
        mPasanganKolomNilai.add(new KeyValuePair("telp",mTelp));
        mPasanganKolomNilai.add(new KeyValuePair("hp",mHp));;
        mPasanganKolomNilai.add(new KeyValuePair("email",mEmail));
        mPasanganKolomNilai.add(new KeyValuePair("status",getStatusAsString()));
        mPasanganKolomNilai.add(new KeyValuePair("kontak",mKontak));
        mDatabase=new DatabaseClass<>();

    }
    private Supplier getInstance(){
        return this;
    }
}
