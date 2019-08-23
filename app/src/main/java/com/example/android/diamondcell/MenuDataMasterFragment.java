package com.example.android.diamondcell;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuDataMasterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuDataMasterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuDataMasterFragment extends Fragment {
    private static final String ARG_USER_ID = "ID";
    private static final String ARG_HAK_AKSES = "HAK_AKSES";

    // TODO: Rename and change types of parameters
    private String mUserID;
    private String mHakAkses;

    private OnFragmentInteractionListener mListener;

    public MenuDataMasterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 ID dari user yang sedang login.
     * @param param2 Hak akses user yang sedang login.
     * @return A new instance of fragment MenuDataMasterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuDataMasterFragment newInstance(String param1, String param2) {
        MenuDataMasterFragment fragment = new MenuDataMasterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, param1);
        args.putString(ARG_HAK_AKSES, param2);
        fragment.setArguments(args);
        return fragment;
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
                onButtonPressed(FragmentMasterSales.newInstance("", ""));
            }
        });

        buttonPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(FragmentMasterPelanggan.newInstance("", ""));
            }
        });

        buttonSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(FragmentMasterSupplier.newInstance("", ""));
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Fragment fragment) {
        if (mListener != null) {
            mListener.onDataMasterFragmentInteraction(fragment);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDataMasterFragmentInteraction(Fragment fragment);
    }
}
