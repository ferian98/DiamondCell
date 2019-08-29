package com.example.android.diamondcell;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentInputPelanggan extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    //Member untuk date time picker
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;

    //Member widget user interface
    private TextView tvDateResult;
    private Button btnDatePicker,btnKembali,btnSimpan;
    private EditText edtKode,edtNama,edtAlamat,edtNomorDaerahTelpon,edtNomorTelpon
            ,edtNomorHandphone,edtEmail;
    private Spinner spnStatus;

    //Member Pelanggan (Defaultnya akan dibuat dari template fragment, hanya tipenya diubah)
    private Pelanggan mParam1;

    public FragmentInputPelanggan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragmentInputPelanggan.
     */
    public static FragmentInputPelanggan newInstance(@Nullable Parcelable param1) {
        FragmentInputPelanggan fragment = new FragmentInputPelanggan();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }else {
            mParam1=null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_pelanggan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        definisikanWidget(view);
        persiapkanUntukDateTimePicker();
        isiNilaiDefaultWidget(mParam1);
        tambahkanListenerKeWidget();
    }

    /**Metode di bawah ini merupakan metode buatan sendiri**/

    //Metode untuk menampilkankan dialog tanggal
    private void showDateDialog(){
        /*Untuk mendapatkan tanggal sekarang*/
        /*DatePicker dialog*/
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                /*Metode ini dipanggil saat kita selesai memilih tanggal di DatePicker*/
                /*Set Calendar untuk menampung tanggal yang dipilih*/
                calendar.set(year, monthOfYear, dayOfMonth);

                /*Update TextView dengan tanggal yang dpilih*/
                tvDateResult.setText(dateFormatter.format(calendar.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        /*Tampilkan DatePicker dialog*/
        datePickerDialog.show();
    }

    //Metode untuk mendefinisikan widget
    private void definisikanWidget(View view){
        edtKode=view.findViewById(R.id.edtKode);
        edtNama=view.findViewById(R.id.edtNama);
        edtAlamat=view.findViewById(R.id.edtAlamat);
        edtNomorDaerahTelpon=view.findViewById(R.id.edtDaerahTelepon);
        edtNomorTelpon=view.findViewById(R.id.edtTelepon);
        edtNomorHandphone=view.findViewById(R.id.edtHp);
        tvDateResult = view.findViewById(R.id.txtTanggalDaftar);
        btnDatePicker = view.findViewById(R.id.btnDatePicker);
        edtEmail=view.findViewById(R.id.edtEmail);
        spnStatus=view.findViewById(R.id.spnStatus);
        btnSimpan=view.findViewById(R.id.btnSimpan);
        btnKembali=view.findViewById(R.id.btnKembali);
    };

    private void isiNilaiDefaultWidget(Pelanggan pelanggan){
        if (pelanggan==null){
            edtKode.setText("");
            edtKode.setEnabled(true);
            edtNama.setText("");
            edtAlamat.setText("");
            edtNomorTelpon.setText("");
            edtNomorDaerahTelpon.setText("");
            edtNomorHandphone.setText("");
            tvDateResult.setText("Pilih Tanggal");
            calendar.setTime(new Date());
            edtEmail.setText("");
        }else {
            edtKode.setText(pelanggan.getmKode());
            edtKode.setEnabled(false);
            edtNama.setText(pelanggan.getmNama());
            edtAlamat.setText(pelanggan.getmAlamat());
            String[] telp= pisahkanNomorTelepon(pelanggan.getmTelp());
            edtNomorDaerahTelpon.setText(telp[0]);
            edtNomorTelpon.setText(telp[1]);
            edtNomorHandphone.setText(pelanggan.getmTelp());
            calendar.setTime(pelanggan.getmTglMasuk());
            tvDateResult.setText(pelanggan.getTglMasukAsString());
            edtEmail.setText(pelanggan.getmEmail());
        }
    }

    private void tambahkanListenerKeWidget(){
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mParam1==null){
                    prosesSimpan();
                }else {
                    prosesUpdate();
                }
            }
        });
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo: cek bisa tidak
                getFragmentManager().popBackStack();
            }
        });
    }

    private void prosesSimpan(){
        //Todo: sesuaikan dengan pojo
        boolean status;
        if (spnStatus.getSelectedItemPosition()==0){
            status=true;
        }else {
            status=false;
        }
        Pelanggan pelanggan=new Pelanggan(edtKode.getText().toString(),calendar.getTime()
                ,edtNama.getText().toString(),edtAlamat.getText().toString(),satukanNomorDaerahDanTelepon(),
                edtNomorHandphone.getText().toString(),edtEmail.getText().toString(),status,"");
        pelanggan.save(new UpdateOnUIThreadWrite<Pelanggan>() {
            @Override
            public void updateOnUIThread(Pelanggan objek) {
                tampilkanToast("Pelanggan dengan ID "+objek.getmNama()+" telah ditambahkan");
            }
        });
    }
    private void prosesUpdate(){
        //Todo: sesuaikan dengan pojo
        boolean status;
        if (spnStatus.getSelectedItemPosition()==0){
            status=true;
        }else {
            status=false;
        }
        Pelanggan pelanggan=new Pelanggan(edtKode.getText().toString(),calendar.getTime()
                ,edtNama.getText().toString(),edtAlamat.getText().toString(),satukanNomorDaerahDanTelepon(),
                edtNomorHandphone.getText().toString(),edtEmail.getText().toString(),status,"");
        pelanggan.update(new UpdateOnUIThreadWrite<Pelanggan>() {
            @Override
            public void updateOnUIThread(Pelanggan objek) {
                tampilkanToast("Pelanggan dengan ID "+objek.getmNama()+" telah diedit");
            }
        });
    }
    private String satukanNomorDaerahDanTelepon(){
        return edtNomorDaerahTelpon.getText().toString()+"-"+edtNomorTelpon.getText().toString();
    }

    private String[] pisahkanNomorTelepon(String telpon){
        return telpon.split("-");
    }

    private void persiapkanUntukDateTimePicker(){
        //Pertama kali dibuat calendar akan menunjuk pada tanggal hari ini
        Date date=new Date();
        calendar= Calendar.getInstance();
        calendar.setTime(date);
    }

    private void tampilkanToast(String pesan){
        Toast.makeText(getActivity(),pesan,Toast.LENGTH_SHORT).show();
    }
}
