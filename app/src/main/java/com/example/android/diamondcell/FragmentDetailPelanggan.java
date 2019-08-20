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

public class FragmentDetailPelanggan extends Fragment {

    private TextView tvKode, tvNama, tvTglInput, tvAlamat, tvTelp, tvHp, tvEmail, tvStatus;
    private ImageButton btnCallTelp, btnCallHp, btnEmail;
    private Button btnKembali;
    private ImageView imgPelanggan;
    public static final String PELANGGAN_EXTRA = "pelanggan_instans";

    private Pelanggan mPelanggan;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_pelanggan, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        v = getView();

        // hindari landscape mode
        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // TODO: ambil objek pelanggan
        mPelanggan = null; //getIntent().getParcelableExtra(PELANGGAN_EXTRA);
        
        definisikanWidget();
        isiNilaiDefaultWidget(mPelanggan);
        tambahkanListenerKeWidget();

    }

    private void definisikanWidget(){
        tvKode = v.findViewById(R.id.tv_kode_pelanggan);
        tvNama = v.findViewById(R.id.tv_nama_pelanggan);
        tvTglInput = v.findViewById(R.id.tv_tanggal_input_pelanggan);
        tvAlamat = v.findViewById(R.id.tv_alamat_pelanggan);
        tvTelp = v.findViewById(R.id.tv_telepon_pelanggan);
        tvHp = v.findViewById(R.id.tv_hp_pelanggan);
        tvEmail = v.findViewById(R.id.tv_email_pelanggan);
        tvStatus = v.findViewById(R.id.tv_status_pelanggan);
        imgPelanggan = v.findViewById(R.id.img_pelanggan);
        btnCallTelp = v.findViewById(R.id.btn_telepon_call);
        btnCallHp = v.findViewById(R.id.btn_hp_call);
        btnEmail = v.findViewById(R.id.btn_email);
        btnKembali = v.findViewById(R.id.btn_kembali);
    }

    private void isiNilaiDefaultWidget(Pelanggan pelanggan){
        if (pelanggan!=null){
            tvKode.setText(pelanggan.getmKode());
            tvNama.setText(pelanggan.getmNama());
            tvTglInput.setText(pelanggan.getTglMasukAsString());
            tvAlamat.setText(pelanggan.getmAlamat());
            tvTelp.setText(pelanggan.getmTelp());
            tvHp.setText(pelanggan.getmHandphone());
            tvEmail.setText(pelanggan.getmEmail());
            tvStatus.setText(pelanggan.getmStatusAktifAsString());
            Glide.with(this)
                    .load(pelanggan.getmFoto())
                    .apply(new RequestOptions())
                    .override(imgPelanggan.getWidth(),imgPelanggan.getHeight())
                    .into(imgPelanggan);
            btnCallTelp.setEnabled(!(tvTelp.getText().toString().isEmpty()));
            btnCallHp.setEnabled(!(tvHp.getText().toString().isEmpty()));
            btnEmail.setEnabled(!(tvEmail.getText().toString().isEmpty()));
        }else {
            tvKode.setText("");
            tvNama.setText("");
            tvTglInput.setText("");
            tvAlamat.setText("");
            tvTelp.setText("");
            tvHp.setText("");
            tvEmail.setText("");
            tvStatus.setText("");
            btnCallTelp.setEnabled(false);
            btnCallHp.setEnabled(false);
            btnEmail.setEnabled(false);
            tampilkanToast("Gagal load data");
        }
    }
    private void tambahkanListenerKeWidget(){
        btnCallHp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:"+mPelanggan.getmHandphone()));//change the number
                startActivity(callIntent);
            }
        });
        btnCallTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:"+mPelanggan.getmTelp()));//change the number
                startActivity(callIntent);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ mPelanggan.getmEmail()});
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