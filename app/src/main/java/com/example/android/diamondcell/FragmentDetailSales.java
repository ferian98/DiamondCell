package com.example.android.diamondcell;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FragmentDetailSales extends Fragment {

    private TextView tvKode, tvNama, tvAlamat, tvHp, tvJabatan, tvJenisKelamin, tvAgama,
            tvTempatLahir, tvTanggalLahir, tvEmail, tvStatus;
    private ImageButton btnCallHp, btnEmail;
    private Button btnKembali;
    private ImageView imgSales;
    public static final String SALES_EXTRA = "sales_instans";

    private Sales mSales;;

    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_sales,  container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        v = getView();

        // hindari landscape mode
        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // TODO: ambil objek sales
        mSales = null; // this.getIntent().getParcelableExtra(SALES_EXTRA);

        definisikanWidget();
        isiNilaiDefaultWidget(mSales);
        tambahkanListenerKeWidget();
    } // END ONCREATE METHOD

    private void definisikanWidget(){
        tvKode = v.findViewById(R.id.tv_kode_sales);
        tvNama = v.findViewById(R.id.tv_nama_sales);
        tvAlamat = v.findViewById(R.id.tv_alamat_sales);
        tvHp = v.findViewById(R.id.tv_hp_sales);
        tvJabatan = v.findViewById(R.id.tv_jabatan_sales);
        tvJenisKelamin = v.findViewById(R.id.tv_kelamin_sales);
        tvAgama = v.findViewById(R.id.tv_agama_sales);
        tvTempatLahir = v.findViewById(R.id.tv_tempat_lahir_sales);
        tvTanggalLahir = v.findViewById(R.id.tv_tanggal_lahir_sales);
        tvEmail = v.findViewById(R.id.tv_email_sales);
        tvStatus = v.findViewById(R.id.tv_status_sales);
        btnCallHp = v.findViewById(R.id.btn_hp_call);
        btnEmail = v.findViewById(R.id.btn_email);
        btnKembali = v.findViewById(R.id.btn_kembali);
        imgSales = v.findViewById(R.id.img_sales);
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
                tutupFragment();
            }
        });
    }
    private void tampilkanToast(String Message){
        Toast.makeText(getContext(),Message,Toast.LENGTH_LONG).show();
    }

    /**
     * Metode untuk menutup fragment
     */
    private void tutupFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

} // END CLASS
