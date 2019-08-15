package com.example.android.diamondcell;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailSalesActivity extends AppCompatActivity {

    private TextView tvKode, tvNama, tvAlamat, tvHp, tvJabatan, tvJenisKelamin, tvAgama,
            tvTempatLahir, tvTanggalLahir, tvEmail, tvStatus;
    private ImageButton btnCallHp, btnEmail;
    private Button btnKembali;
    private ImageView imgSales;
    public static final String SALES_EXTRA = "SalesInstans";
    private Sales mSales;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sales);
        // hindari landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSales = this.getIntent().getParcelableExtra(SALES_EXTRA);
        definisikanWidget();
        isiNilaiDefaultWidget(mSales);
        tambahkanListenerKeWidget();
    } // END ONCREATE METHOD


    private void definisikanWidget(){
        tvKode = findViewById(R.id.tv_kode_sales);
        tvNama = findViewById(R.id.tv_nama_sales);
        tvAlamat = findViewById(R.id.tv_alamat_sales);
        tvHp = findViewById(R.id.tv_hp_sales);
        tvJabatan = findViewById(R.id.tv_jabatan_sales);
        tvJenisKelamin = findViewById(R.id.tv_kelamin_sales);
        tvAgama = findViewById(R.id.tv_agama_sales);
        tvTempatLahir = findViewById(R.id.tv_tempat_lahir_sales);
        tvTanggalLahir = findViewById(R.id.tv_tanggal_lahir_sales);
        tvEmail = findViewById(R.id.tv_email_sales);
        tvStatus = findViewById(R.id.tv_status_sales);
        btnCallHp = findViewById(R.id.btn_hp_call);
        btnEmail = findViewById(R.id.btn_email);
        btnKembali = findViewById(R.id.btn_kembali);
        imgSales = findViewById(R.id.img_sales);
    }
    private void isiNilaiDefaultWidget(Sales sales){
        if (sales!=null){
            Glide.with(this)
                    .load(sales.getmFoto())
                    .apply(new RequestOptions())
                    .override(imgSales.getWidth(),imgSales.getHeight())
                    .into(imgSales);
            tvKode.setText(sales.getmKode());
            tvNama.setText(sales.getmNama());
            tvAlamat.setText(sales.getmAlamat());
            tvHp.setText(sales.getmTelp());
            tvJabatan.setText(sales.getJabatan().getmNama());
            tvJenisKelamin.setText(sales.getmJenisKelaminAsString());
            tvAgama.setText(sales.getmAgamaAsString());
            tvTempatLahir.setText(sales.getmTempatLahir());
            tvTanggalLahir.setText(sales.getmTanggalLahirAsString());
            tvEmail.setText(sales.getmEmail());
            tvStatus.setText(sales.getmStatusAktifAsString());
            btnCallHp.setEnabled(!(tvHp.getText().toString().isEmpty()));
            btnEmail.setEnabled(!(tvEmail.getText().toString().isEmpty()));
        }else {
            tvKode.setText("");
            tvNama.setText("");
            tvAlamat.setText("");
            tvHp.setText("");
            tvJabatan.setText("");
            tvJenisKelamin.setText("");
            tvAgama.setText("");
            tvTempatLahir.setText("");
            tvTanggalLahir.setText("");
            tvEmail.setText("");
            tvStatus.setText("");
            btnCallHp.setEnabled(false);
            btnEmail.setEnabled(false);
            tampilkanToast("Gagal Load Data");
        }
    }
    private void tambahkanListenerKeWidget(){
        btnCallHp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:"+mSales.getmTelp()));//change the number
                startActivity(callIntent);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ mSales.getmEmail()});
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void tampilkanToast(String Message){
        Toast.makeText(this,Message,Toast.LENGTH_LONG).show();
    }
} // END CLASS
