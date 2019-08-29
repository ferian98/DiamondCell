package com.example.android.diamondcell;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class MenuDataMasterFragment extends Fragment {
    private static final String ARG_USER_ID = "ID";
    private static final String ARG_HAK_AKSES = "HAK_AKSES";

    // TODO: Rename and change types of parameters
    private String mUserID;
    private String mHakAkses;


    public MenuDataMasterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserID = getArguments().getString(ARG_USER_ID);
            mHakAkses = getArguments().getString(ARG_HAK_AKSES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_data_master, container, false);
        Button buttonSales = view.findViewById(R.id.btn_sales);
        Button buttonPelanggan = view.findViewById(R.id.btn_pelanggan);
        Button buttonSupplier = view.findViewById(R.id.btn_supplier);

        buttonSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation
                        .findNavController(v)
                        .navigate(MenuDataMasterFragmentDirections.actionMenuDataMasterFragmentToFragmentMasterSales());
            }
        });

        buttonPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation
                        .findNavController(v)
                        .navigate(MenuDataMasterFragmentDirections.actionMenuDataMasterFragmentToFragmentMasterPelanggan());
            }
        });

        buttonSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation
                        .findNavController(v)
                        .navigate(MenuDataMasterFragmentDirections.actionMenuDataMasterFragmentToFragmentMasterSupplier());
            }
        });

        return view;
    }

}
