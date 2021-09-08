package com.marco.calcola_sconto.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.marco.calcola_sconto.BuildConfig;
import com.marco.calcola_sconto.R;

public class GalleryFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery,container,false);
    }



    public  void  onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        CardView cardView1 = view.findViewById(R.id.cardView1);
        cardView1.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://github.com/o0Mardev/Calcola_Sconto/tree/master/app/src/main/res"));
            startActivity(intent);
        });



        CardView cardView2 = view.findViewById(R.id.cardView2);
        cardView2.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://github.com/o0Mardev/Calcola_Sconto/pulls"));
            startActivity(intent);

        });
        TextView textViewVersion = view.findViewById(R.id.textViewVersion);
        textViewVersion.setText(BuildConfig.VERSION_NAME);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
}