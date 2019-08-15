package com.example.android.diamondcell;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailSupplierActivity extends AppCompatActivity {

    private TextView tvKode, tvNama, tvAlamat, tvTelp, tvHp, tvEmail, tvKontak, tvStatus;
    private ImageButton btnCallTelp, btnCallHp, btnEmail;
    private Button btnKembali;
    public static final String SUPPLIER_EXTRA = "supplier_instans";

    private Supplier supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_supplier);

        // hindari landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //
        setTitle("Detail Supplier");

        tvKode = findViewById(R.id.tv_kode_sup);
        tvNama = findViewById(R.id.tv_nama_sup);
        tvAlamat = findViewById(R.id.tv_alamat_sup);
        tvTelp = findViewById(R.id.tv_telepon_sup);
        tvHp = findViewById(R.id.tv_hp_sup);
        tvEmail = findViewById(R.id.tv_email_sup);
        tvKontak = findViewById(R.id.tv_kontak_sup);
        tvStatus = findViewById(R.id.tv_status_sup);
        btnCallTelp = findViewById(R.id.btn_telepon_call);
        btnCallHp = findViewById(R.id.btn_hp_call);
        btnEmail = findViewById(R.id.btn_email);
        btnKembali = findViewById(R.id.btn_kembali);

        btnCallTelp.setEnabled(false);
        btnCallHp.setEnabled(false);
        btnEmail.setEnabled(false);

        // Ambil Objek Supplier
         supplier = this.getIntent().getParcelableExtra(SUPPLIER_EXTRA);

        if (supplier != null){
            // isi text view
            tvKode.setText(supplier.getKode());
            tvNama.setText(supplier.getNama());
            tvAlamat.setText(supplier.getAlamat());
            tvTelp.setText(supplier.getTelp());
            tvHp.setText(supplier.getHp());
            tvEmail.setText(supplier.getEmail());
            tvKontak.setText(supplier.getKontak());
            if(supplier.isActive()){
                tvStatus.setText("AKTIF");
            } else {
                tvStatus.setText("TIDAK AKTIF");
            }

            // aktifkan button
            if (!(tvTelp.getText().toString().trim().equals(""))){
                btnCallTelp.setEnabled(true);
                btnCallTelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendExplicitIntent(true, tvTelp.getText().toString());
                    }
                });
            }
            if (!tvHp.getText().toString().trim().equals("")){
                btnCallHp.setEnabled(true);
                btnCallHp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendExplicitIntent(true, tvHp.getText().toString());
                    }
                });
            }
            if (!tvEmail.getText().toString().trim().equals("")){
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
                    finish();
                }
            });

        } else {
            Toast.makeText(this, "Gagal load data", Toast.LENGTH_LONG);
            finish();
        } // END IF

    } // END ON CREATE

    /**
     * Membuat sebuah intent eksplisit. Metode ini hanya meng-support
     * intent eksplisit untuk menelpon dan mengirim e-mail
     * @param isPhoneCall
     * @param uriValue: seperti nomor telp, atau alamat e-mail
     */
    private void sendExplicitIntent(Boolean isPhoneCall, String uriValue){
        Intent i;
        if(isPhoneCall){ // call
            i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:"+uriValue));
            if(getIntent().resolveActivity(getPackageManager()) != null){
                startActivity(i);
            }
        } else { // send email
           i = new Intent(Intent.ACTION_SEND);
           i.setType("plain/text");
           i.putExtra(Intent.EXTRA_EMAIL, uriValue);
           i.putExtra(Intent.EXTRA_SUBJECT, "");
           i.putExtra(Intent.EXTRA_TEXT, "");
            if(getIntent().resolveActivity(getPackageManager()) != null){
                startActivity(i);
            }
        }
    } // END METHOD

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

} // END CLASS
