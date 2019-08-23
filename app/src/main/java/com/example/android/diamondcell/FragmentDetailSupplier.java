package com.example.android.diamondcell;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentDetailSupplier extends Fragment {

    private TextView tvKode, tvNama, tvAlamat, tvTelp, tvHp, tvEmail, tvKontak, tvStatus;
    private ImageButton btnCallTelp, btnCallHp, btnEmail;
    private Button btnKembali;
    public static final String SUPPLIER_EXTRA = "supplier_instans";

    private Supplier supplier;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_supplier, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        // hindari landscape mode
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //setTitle("Detail Supplier");

        tvKode = v.findViewById(R.id.tv_kode_sup);
        tvNama = v.findViewById(R.id.tv_nama_sup);
        tvAlamat = v.findViewById(R.id.tv_alamat_sup);
        tvTelp = v.findViewById(R.id.tv_telepon_sup);
        tvHp = v.findViewById(R.id.tv_hp_sup);
        tvEmail = v.findViewById(R.id.tv_email_sup);
        tvKontak = v.findViewById(R.id.tv_kontak_sup);
        tvStatus = v.findViewById(R.id.tv_status_sup);
        btnCallTelp = v.findViewById(R.id.btn_telepon_call);
        btnCallHp = v.findViewById(R.id.btn_hp_call);
        btnEmail = v.findViewById(R.id.btn_email);
        btnKembali = v.findViewById(R.id.btn_kembali);

        btnCallTelp.setEnabled(false);
        btnCallHp.setEnabled(false);
        btnEmail.setEnabled(false);

        // TODO: Ambil Objek Supplier
        supplier = null; // getIntent().getParcelableExtra(SUPPLIER_EXTRA);

        if (supplier != null) {
            // isi text view
            tvKode.setText(supplier.getKode());
            tvNama.setText(supplier.getNama());
            tvAlamat.setText(supplier.getAlamat());
            tvTelp.setText(supplier.getTelp());
            tvHp.setText(supplier.getHp());
            tvEmail.setText(supplier.getEmail());
            tvKontak.setText(supplier.getKontak());
            if (supplier.isActive()) {
                tvStatus.setText("AKTIF");
            } else {
                tvStatus.setText("TIDAK AKTIF");
            }

            // aktifkan button
            if (!(tvTelp.getText().toString().trim().equals(""))) {
                btnCallTelp.setEnabled(true);
                btnCallTelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendExplicitIntent(true, tvTelp.getText().toString());
                    }
                });
            }
            if (!tvHp.getText().toString().trim().equals("")) {
                btnCallHp.setEnabled(true);
                btnCallHp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendExplicitIntent(true, tvHp.getText().toString());
                    }
                });
            }
            if (!tvEmail.getText().toString().trim().equals("")) {
                btnEmail.setEnabled(true);
                btnEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendExplicitIntent(false, tvEmail.getText().toString());
                    }
                });
            }
            btnKembali.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tutupFragment();
                }
            });
        } else {
            tvKode.setText("");
            tvNama.setText("");
            tvAlamat.setText("");
            tvHp.setText("");
            tvTelp.setText("");
            tvEmail.setText("");
            tvKontak.setText("");
            tvStatus.setText("");

            Toast.makeText(getContext(), "Gagal load data", Toast.LENGTH_LONG);
            tutupFragment();
        } // END IF

    } // END ON ACTIVITY CREATED

    /**
     * Membuat sebuah intent eksplisit. Metode ini hanya meng-support
     * intent eksplisit untuk menelpon dan mengirim e-mail
     * @param isPhoneCall
     * @param uriValue: seperti nomor telp, atau alamat e-mail
     */
    private void sendExplicitIntent(Boolean isPhoneCall, String uriValue){
        Intent i;
        if(isPhoneCall){ // call
            i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("tel:"+uriValue));
            startActivity(i);
        } else { // send email
           i = new Intent(Intent.ACTION_SEND);
           i.setType("plain/text");
           i.putExtra(Intent.EXTRA_EMAIL, uriValue);
           i.putExtra(Intent.EXTRA_SUBJECT, "");
           i.putExtra(Intent.EXTRA_TEXT, "");
           startActivity(i);
        }
    } // END METHOD

    /**
     * Metode untuk menutup fragment
     */
    private void tutupFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

} // END CLASS
