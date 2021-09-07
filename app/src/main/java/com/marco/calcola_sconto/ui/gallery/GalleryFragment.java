package com.marco.calcola_sconto.ui.gallery;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.marco.calcola_sconto.BuildConfig;
import com.marco.calcola_sconto.R;
import com.marco.calcola_sconto.databinding.FragmentGalleryBinding;

import org.w3c.dom.Text;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    String versionName = BuildConfig.VERSION_NAME;
    TextView textViewVersion;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }



    public  void  onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        textViewVersion =  view.findViewById(R.id.textViewVersion);

        textViewVersion.setText(getResources().getString(R.string.version) + versionName);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    
}