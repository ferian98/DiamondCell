package com.example.android.diamondcell;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class FragmentInputSales extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Member untuk date time picker
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private SimpleDateFormat dateFormatter;

    //Member widget user interface
    private TextView tvDateResult;
    private Button btnDatePicker,btnKembali,btnSimpan,btnFoto;
    private EditText edtKode,edtNama,edtAlamat
            ,edtNomorHandphone,edtTempatLahir,edtTanggalLahir,edtEmail;
    private Spinner spnStatus,spnAgama,spnJenisKelamin,spnJabatan;
    // TODO: Rename and change types of parameters
    private Sales mParam1;
    public static Uri SalesPhotoURI;

    public FragmentInputSales() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInputSales.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInputSales newInstance(Parcelable param1, String param2) {
        FragmentInputSales fragment = new FragmentInputSales();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmen_input_sales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        definisikanWidget(view);
        persiapkanUntukDateTimePicker();
        isiNilaiDefaultWidget(mParam1);
        tambahkanListenerKeWidget();
    }

    /** Metode dibawah ini merupakan metode buatan sendiri **/
    private void definisikanWidget(View view){
        edtKode=view.findViewById(R.id.edtKode);
        edtNama=view.findViewById(R.id.edtNama);
        edtAlamat=view.findViewById(R.id.edtAlamat);
        edtNomorHandphone=view.findViewById(R.id.edtHp);
        spnJabatan=view.findViewById(R.id.spnJabatan);
        edtTanggalLahir=view.findViewById(R.id.edtTempatLahir);
        tvDateResult = view.findViewById(R.id.txtTanggalLahir);
        btnDatePicker = view.findViewById(R.id.btnDatePicker);
        edtEmail=view.findViewById(R.id.edtEmail);
        spnStatus=view.findViewById(R.id.spnStatus);
        spnJenisKelamin=view.findViewById(R.id.spnJenisKel);
        spnAgama=view.findViewById(R.id.spnAgama);
        btnSimpan=view.findViewById(R.id.btnSimpan);
        btnKembali=view.findViewById(R.id.btnKembali);
        btnFoto=view.findViewById(R.id.btnFoto);
    }

    private void isiNilaiDefaultWidget(Sales sales){
        //Todo: Selesaikan isi nilai default
        if (sales==null){
            edtKode.setText("");
            edtNama.setText("");
            edtAlamat.setText("");
            edtNomorHandphone.setText("");
            SalesPhotoURI=null;
        }
    }

    private void tambahkanListenerKeWidget(){
        //Todo: tambahkan listener ke widget
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

            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesUploadFoto();
            }
        });
    }

    private void prosesSimpan(){
        //Todo: sesuaikan dengan pojo
    }
    private void prosesUpdate(){
        //Todo: sesuaikan dengan pojo
    }
    private void prosesUploadFoto(){
        //Todo: definisikan proses upload foto
    }
    private void tambahkanItemJabatan(){

    }
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
    private void persiapkanUntukDateTimePicker(){
        //Pertama kali dibuat calendar akan menunjuk pada tanggal hari ini
        Date date=new Date();
        calendar= Calendar.getInstance();
        calendar.setTime(date);
    }
    private void tambahkanJabatanPadaSpinner(){
        //Todo: definisikan jabatan pada spinner
        JabatanAdapter jabatanAdapter = null;
        spnJabatan.setAdapter(jabatanAdapter);
    }

    private class JabatanAdapter extends ArrayAdapter<Jabatan>{

        public JabatanAdapter(@NonNull Context context,@NonNull List<Jabatan> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row=getLayoutInflater().inflate(R.layout.item_jabatan,parent,false);
            TextView tvKode= row.findViewById(R.id.txtKode);
            TextView tvNama= row.findViewById(R.id.txtNama);
            tvKode.setText(getItem(position).getmKode());
            tvNama.setText(getItem(position).getmNama());
            return row;
        }
    }
}
