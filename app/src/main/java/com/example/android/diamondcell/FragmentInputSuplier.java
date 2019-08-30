package com.example.android.diamondcell;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentInputSuplier extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Supplier mParam1;

    //Member widget user interface
    private Button btnKembali,btnSimpan;
    private EditText edtKode,edtNama,edtAlamat,edtNomorDaerahTelpon,edtNomorTelpon
            ,edtNomorHandphone,edtEmail;
    private Spinner spnStatus;

    public FragmentInputSuplier() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragmentInputSuplier.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInputSuplier newInstance(Supplier param1) {
        FragmentInputSuplier fragment = new FragmentInputSuplier();
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
        return inflater.inflate(R.layout.fragment_input_suplier, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        definisikanWidget(view);
        isiNilaiDefaultWidget(mParam1);
        tambahkanListenerKeWidget();
    }

    private void tambahkanListenerKeWidget() {
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
                getFragmentManager().popBackStack();
            }
        });

    }

    private void isiNilaiDefaultWidget(Supplier supplier) {
        if (supplier==null){
            edtKode.setText("");
            edtKode.setEnabled(true);
            edtNama.setText("");
            edtAlamat.setText("");
            edtNomorTelpon.setText("");
            edtNomorDaerahTelpon.setText("");
            edtNomorHandphone.setText("");
            edtEmail.setText("");
        }else {
            edtKode.setText(supplier.getKode());
            edtKode.setEnabled(false);
            edtNama.setText(supplier.getNama());
            edtAlamat.setText(supplier.getAlamat());
            String[] telp= pisahkanNomorTelepon(supplier.getTelp());
            edtNomorDaerahTelpon.setText(telp[0]);
            edtNomorTelpon.setText(telp[1]);
            edtNomorHandphone.setText(supplier.getHp());
            edtEmail.setText(supplier.getEmail());
        }
    }

    private void definisikanWidget(View view) {
        edtKode=view.findViewById(R.id.edtKode);
        edtNama=view.findViewById(R.id.edtNama);
        edtAlamat=view.findViewById(R.id.edtAlamat);
        edtNomorDaerahTelpon=view.findViewById(R.id.edtDaerahTelepon);
        edtNomorTelpon=view.findViewById(R.id.edtTelepon);
        edtNomorHandphone=view.findViewById(R.id.edtHp);
        edtEmail=view.findViewById(R.id.edtEmail);
        spnStatus=view.findViewById(R.id.spnStatus);
        btnSimpan=view.findViewById(R.id.btnSimpan);
        btnKembali=view.findViewById(R.id.btnKembali);
    }

    private String satukanNomorDaerahDanTelepon(){
        return edtNomorDaerahTelpon.getText().toString()+"-"+edtNomorTelpon.getText().toString();
    }
    private String[] pisahkanNomorTelepon(String telpon){
        return telpon.split("-");
    }
    private void prosesSimpan(){
        boolean status;
        if (spnStatus.getSelectedItemPosition()==0){
            status=true;
        }else {
            status=false;
        }
        Supplier supplier=new Supplier(edtKode.getText().toString(),edtNama.getText().toString(),
                edtAlamat.getText().toString(),satukanNomorDaerahDanTelepon(),edtNomorHandphone.getText().toString()
                ,edtEmail.getText().toString(),"",status);
        supplier.save(new UpdateOnUIThreadWrite<Supplier>() {
            @Override
            public void updateOnUIThread(Supplier objek) {
            }
        });
    }
    private void prosesUpdate(){
        boolean status;
        if (spnStatus.getSelectedItemPosition()==0){
            status=true;
        }else {
            status=false;
        }
        Supplier supplier=new Supplier(edtKode.getText().toString(),edtNama.getText().toString(),
                edtAlamat.getText().toString(),satukanNomorDaerahDanTelepon(),edtNomorHandphone.getText().toString()
                ,edtEmail.getText().toString(),"",status);
        supplier.update(new UpdateOnUIThreadWrite<Supplier>() {
            @Override
            public void updateOnUIThread(Supplier objek) {

            }
        });
    }
    private void tampilkanToast(String pesan){
        Toast.makeText(getActivity(),pesan,Toast.LENGTH_SHORT).show();
    }
}
