package com.marco.calcola_sconto.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;
import com.marco.calcola_sconto.R;
import com.marco.calcola_sconto.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    double prezzo, percentuale, prezzoFinale, risparmio;
    EditText txtPrezzo, txtPercentuale;
    TextView txtRisultatoFinale, txtRisparmio;

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences1 =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean checkbox1 = sharedPreferences1.getBoolean("check_box_preference_shortcut", false);

        SharedPreferences sharedPreferences2 =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean checkbox2 = sharedPreferences2.getBoolean("check_box_preference_discount",false);

        txtPrezzo = view.findViewById(R.id.EditText_prezzo);
        txtPercentuale = view.findViewById(R.id.EditText_percentuale);
        txtRisultatoFinale = view.findViewById(R.id.TextView_risultato);
        txtRisparmio =view.findViewById(R.id.textViewDiscount);

        view.findViewById(R.id.button_calcola).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!txtPrezzo.getText().toString().equals("") && !txtPercentuale.getText().toString().equals("")) {
                    prezzo = Double.parseDouble(txtPrezzo.getText().toString());
                    percentuale = Double.parseDouble(txtPercentuale.getText().toString());
                }
                if (percentuale > 100) {
                    Toast.makeText(getContext(), "Inserisci una percentuale valida", Toast.LENGTH_SHORT).show();
                    percentuale = 100;
                }
                percentuale = 100 - percentuale;
                percentuale = percentuale / 100;
                prezzoFinale = percentuale * prezzo;
                txtRisultatoFinale.setText(arrotonda(prezzoFinale) + " €");

                risparmio = prezzo - prezzoFinale;

                txtRisparmio.setText(" Risparmio: " + arrotonda(risparmio)+ " €");

                if (checkbox1 == true){
                    Snackbar.make(view, "Risultato copiato negli appunti", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipdata = ClipData.newPlainText("", txtRisultatoFinale.getText().toString());
                    clipboardManager.setPrimaryClip(clipdata);
                }
            }
        });


        view.findViewById(R.id.button_azzera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtRisultatoFinale.setText("0.00 €");
                txtPercentuale.setText("");
                txtPrezzo.setText("");
                txtRisparmio.setText(" Risparmio:");
                percentuale = 0;
                prezzo = 0;
                prezzoFinale = 0;
                risparmio = 0;
            }
        });


        if (checkbox1 == false) {
            view.findViewById(R.id.fab).setVisibility(View.VISIBLE);
            view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(view, "Risultato copiato negli appunti", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipdata = ClipData.newPlainText("", txtRisultatoFinale.getText().toString());
                    clipboardManager.setPrimaryClip(clipdata);
                }
            });
        } else {
            view.findViewById(R.id.fab).setVisibility(View.GONE);
        }

        if (checkbox2 == false) {
            view.findViewById(R.id.textViewDiscount).setVisibility(View.GONE);

        } else {
            view.findViewById(R.id.fab).setVisibility(View.VISIBLE);
        }


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Arrotonda
    public static double arrotonda(double prezzoFinale) {
        prezzoFinale = Math.round(prezzoFinale * 100);
        prezzoFinale = prezzoFinale / 100;
        return prezzoFinale;
    }


}