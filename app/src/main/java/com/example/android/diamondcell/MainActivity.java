package com.example.android.diamondcell;

import android.net.Uri;
import android.os.Bundle;


import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        DashboardFragment.OnFragmentInteractionListener,
        MenuDataMasterFragment.OnFragmentInteractionListener,
        FragmentMasterSupplier.OnFragmentInteractionListener,
        FragmentMasterSales.OnFragmentInteractionListener,
        FragmentMasterPelanggan.OnFragmentInteractionListener {

    //Tag yang akan digunakan untuk memanggil ulang fragment
    //Belum diimplementasikan
    private final String FRAGMENT_DASHBOARD_TAG = "DASHBOARD";
    private final String FRAGMENT_DATAMASTER_TAG = "DATA_MASTER";
    private final String FRAGMENT_MASTER_SUPPLIER = "MASTER_SUPPLIER";
    private final String FRAGMENT_MASTER_SALES = "MASTER_SALES";
    private final String FRAGMENT_MASTER_PELANGGAN = "MASTER_PELANGGAN";

    private Fragment mDashboardFragment, mMenuDataMasterFragment, mMasterPelangganFragment;
    private Fragment mMasterSalesFragment, mMasterSupplierFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDashboardFragment = DashboardFragment.newInstance("", "");
        mMenuDataMasterFragment = MenuDataMasterFragment.newInstance("", "");
        mMasterPelangganFragment = FragmentMasterPelanggan.newInstance("", "");
        mMasterSalesFragment = FragmentMasterSales.newInstance("", "");
        mMasterSupplierFragment = FragmentMasterSupplier.newInstance("", "");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mDashboardFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        // Menambahkan fungsi untuk menutup navigation drawer dengan tombol back
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate dan menambahkan action bar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFramgent(mDashboardFragment);
        } else if (id == R.id.nav_data_master) {
            replaceFramgent(mMenuDataMasterFragment);
        } else if (id == R.id.nav_pembelian) {
            //TODO:Pindah ke fragment pembelian
        } else if (id == R.id.nav_penjualan) {
            //TODO:Pindah ke fragment penjualan
        } else if (id == R.id.nav_transaksi_lain) {
            //TODO:Pindah ke fragment transaksi lain
        } else if (id == R.id.nav_utility) {
            //TODO:Pindah ke fragment utility
        } else {
            //TODO:Pindah ke activity login
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDataMasterFragmentInteraction(Fragment fragment) {
        replaceFramgent(fragment);
    }

    @Override
    public void onDashboardFragmentInteraction(Uri uri) {
        //TODO:Implement Fragment interaction
    }

    @Override
    public void onFragmentPelangganInteraction(Uri uri) {
        //TODO: Implement Fragment Interaction
    }

    @Override
    public void onFragmentSalesInteraction(Uri uri) {
        //TODO: Implement Fragment Interaction
    }

    @Override
    public void onFragmentSupplierInteraction(Uri uri) {
        //TODO: Implement Fragment Interaction
    }

    public void replaceFramgent(Fragment fragmentClass) {
        //Meng-handle pergantian fragment pada MainActivity
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment oldFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        //Menggantikan fragment yang ada di dalam frame layout dengan fragment yang sesuai
        if (fragmentClass instanceof DashboardFragment)
            transaction.replace(R.id.fragment_container, mDashboardFragment);
        else if (fragmentClass instanceof MenuDataMasterFragment)
            transaction.replace(R.id.fragment_container, mMenuDataMasterFragment);
        else if (fragmentClass instanceof FragmentMasterSupplier)
            transaction.replace(R.id.fragment_container, mMasterSupplierFragment);
        else if (fragmentClass instanceof FragmentMasterPelanggan)
            transaction.replace(R.id.fragment_container, mMasterPelangganFragment);
        else if (fragmentClass instanceof  FragmentMasterSales)
            transaction.replace(R.id.fragment_container, mMasterSalesFragment);

        //Menambahkan transaksi ke backstack hanya jika ada perpindahan fragment
        if (!fragmentClass.getClass().equals(oldFragment.getClass()))
            transaction.addToBackStack(null);
        transaction.commit();
    }

    //TODO: Cari cara lebih efisien untuk menampilkan fragment kalau ada
    //TODO: Ubah item navigation drawer yang di-highlight jika terjadi perpindahan fragment
    //TODO: Atasi masalah fragment yang menumpuk-numpuk
    //TODO: Ganti kembali metode replace ke metode ini ketika masalah sudah diatasi
    public void displayFragment(Fragment fragment) {
        //Metode untuk menampilkan dan menyembunyikan fragment lain
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        //Menyembunyikan fragment yang berada di fragment container sekarang ini
        if (currentFragment instanceof DashboardFragment)
            transaction.hide(mDashboardFragment);
        else if (currentFragment instanceof MenuDataMasterFragment)
            transaction.hide(mMenuDataMasterFragment);
        else if (currentFragment instanceof FragmentMasterSales)
            transaction.hide(mMasterSalesFragment);
        else if (currentFragment instanceof FragmentMasterPelanggan)
            transaction.hide(mMasterPelangganFragment);
        else if (currentFragment instanceof  FragmentMasterSupplier)
            transaction.hide(mMasterSupplierFragment);

        //Menampilkan fragment yang diinginkan jika sudah ada kalau tidak tambahkan fragment ke fragment manager
        if (fragment instanceof DashboardFragment) {
            if (mDashboardFragment.isAdded())
                transaction.show(mDashboardFragment);
            else
                transaction.add(R.id.fragment_container, mDashboardFragment);
        } else if (fragment instanceof MenuDataMasterFragment) {
            if(mMenuDataMasterFragment.isAdded())
                transaction.show(fragment);
            else
                transaction.add(R.id.fragment_container, mMenuDataMasterFragment);
        } else if (fragment instanceof FragmentMasterSupplier) {
            if (mMasterSupplierFragment.isAdded())
                transaction.show(mMasterSupplierFragment);
            else
                transaction.add(R.id.fragment_container, mMasterSupplierFragment);
        } else if (fragment instanceof FragmentMasterPelanggan) {
            if (mMasterPelangganFragment.isAdded())
                transaction.show(mMasterPelangganFragment);
            else
                transaction.add(R.id.fragment_container, mMasterPelangganFragment);
        } else if (fragment instanceof FragmentMasterSales) {
            if (mMasterSalesFragment.isAdded())
                transaction.show(mMasterSalesFragment);
            else
                transaction.add(R.id.fragment_container, mMasterSalesFragment);
        }

        //Tambah ke transaksi ke backstack hanya jika terjadi perpindahan fragment
        if(!currentFragment.getClass().equals(fragment.getClass()))
            transaction.addToBackStack(null);
        transaction.commit();
    }
}
