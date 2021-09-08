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
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.marco.calcola_sconto.R;
import com.marco.calcola_sconto.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    FloatingActionButton mAddResultFab, mAddSavingFab, mAddDefaultFab;
    ExtendedFloatingActionButton mAddFab;
    TextView addResultText, addSavingActionText;

    Boolean isAllFabsVisible;

    double prezzo, percentuale, prezzoFinale, risparmio;
    EditText txtPrezzo, txtPercentuale;
    TextView txtRisultatoFinale, txtRisparmio;


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAddFab = view.findViewById(R.id.add_fab);
        mAddResultFab = view.findViewById(R.id.fab_copy_result);
        mAddSavingFab = view.findViewById(R.id.fab_copy_saving);
        mAddDefaultFab = view.findViewById(R.id.fab_copy_default);
        addResultText = view.findViewById(R.id.copy_result_action_text);
        addSavingActionText = view.findViewById(R.id.copy_saving_action_text);


        // Now set all the FABs and all the action name
        // texts as GONE
        mAddResultFab.setVisibility(View.GONE);
        mAddSavingFab.setVisibility(View.GONE);
        addResultText.setVisibility(View.GONE);
        addSavingActionText.setVisibility(View.GONE);
        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are
        // invisible
        isAllFabsVisible = false;
        // Set the Extended floating action button to
        // shrinked state initially
        mAddFab.shrink();

        SharedPreferences sharedPreferences1 =
                PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean checkbox1 = sharedPreferences1.getBoolean("check_box_preference_shortcut", false);

        SharedPreferences sharedPreferences2 =
                PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean checkbox2 = sharedPreferences2.getBoolean("check_box_preference_discount", false);

        txtPrezzo = view.findViewById(R.id.EditText_prezzo);
        txtPercentuale = view.findViewById(R.id.EditText_percentuale);
        txtRisultatoFinale = view.findViewById(R.id.TextView_risultato);
        txtRisparmio = view.findViewById(R.id.textViewDiscount);

        view.findViewById(R.id.button_calcola).setOnClickListener(v -> {

            if (!txtPrezzo.getText().toString().equals("") && !txtPercentuale.getText().toString().equals("")) {
                prezzo = Double.parseDouble(txtPrezzo.getText().toString());
                percentuale = Double.parseDouble(txtPercentuale.getText().toString());
            } else Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
            if (percentuale > 100) {
                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                percentuale = 100;
            }
            percentuale = 100 - percentuale;
            percentuale = percentuale / 100;
            prezzoFinale = percentuale * prezzo;

            String prezzofinaleString = arrotonda(prezzoFinale) + getResources().getString(R.string.symbol);

            txtRisultatoFinale.setText(prezzofinaleString);

            risparmio = prezzo - prezzoFinale;

            String risparmioString = getResources().getString(R.string.savings) + arrotonda(risparmio) + getResources().getString(R.string.symbol);

            txtRisparmio.setText(risparmioString);

            if (checkbox1) {
                Snackbar.make(view, R.string.copy_snackbar, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipdata = ClipData.newPlainText("", txtRisultatoFinale.getText().toString());
                clipboardManager.setPrimaryClip(clipdata);
            }
        });


        view.findViewById(R.id.button_azzera).setOnClickListener(v -> {
            txtRisultatoFinale.setText(R.string.default_result);
            txtPercentuale.setText("");
            txtPrezzo.setText("");
            txtRisparmio.setText(R.string.savings);
            percentuale = 0;
            prezzo = 0;
            prezzoFinale = 0;
            risparmio = 0;
        });


        if (!checkbox1) {
            mAddFab.setVisibility(View.VISIBLE);
            mAddFab.setOnClickListener(v -> {

                if (!isAllFabsVisible) {
                    if (checkbox2) {
                        mAddSavingFab.show();
                        addSavingActionText.setVisibility(View.VISIBLE);
                    }

                    mAddResultFab.show();
                    addResultText.setVisibility(View.VISIBLE);
                    mAddFab.extend();

                    isAllFabsVisible = true;
                } else {
                    mAddSavingFab.hide();
                    mAddResultFab.hide();
                    addSavingActionText.setVisibility(View.GONE);
                    addResultText.setVisibility(View.GONE);
                    mAddFab.shrink();
                    isAllFabsVisible = false;
                }

                mAddResultFab.setOnClickListener(v1 -> {
                    Snackbar.make(view, R.string.copy_snackbar, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipdata = ClipData.newPlainText("", txtRisultatoFinale.getText().toString());
                    clipboardManager.setPrimaryClip(clipdata);
                });

                mAddSavingFab.setOnClickListener(v12 -> {
                    Snackbar.make(view, R.string.copy_snackbar, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipdata = ClipData.newPlainText("", txtRisparmio.getText().toString());
                    clipboardManager.setPrimaryClip(clipdata);
                });

            });
        } else {
            view.findViewById(R.id.add_fab).setVisibility(View.GONE);
        }

        if (!checkbox2) {
            view.findViewById(R.id.textViewDiscount).setVisibility(View.GONE);

            mAddFab.setVisibility(View.GONE);
            mAddDefaultFab.setVisibility(View.VISIBLE);
            mAddDefaultFab.setOnClickListener(v -> {
                Snackbar.make(view, R.string.copy_snackbar, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                ClipboardManager clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipdata = ClipData.newPlainText("", txtRisultatoFinale.getText().toString());
                clipboardManager.setPrimaryClip(clipdata);
            });


        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Arrotonda
    public static double arrotonda(double arrotonda) {
        arrotonda = Math.round(arrotonda * 100);
        arrotonda = arrotonda / 100;
        return arrotonda;
    }


}