package com.example.buscaminas.Slider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.buscaminas.R;

public class SlideFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final int[] PAGE_TITLES =
            new int[]{ R.string.title_CerrarSesion, R.string.title_CerrarSesion, R.string.title_CerrarSesion,R.string.title_CerrarSesion};
    @StringRes
    private static final int[] PAGE_IMAGE =
            new int[]{
                    R.drawable.imagenfacil, R.drawable.imagenmedio, R.drawable.imagendificil,R.drawable.imagenexperto
            };
    private SliderViewModel sliderViewModel;

    public static SlideFragment newInstance(int index) {
        SlideFragment fragment = new SlideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sliderViewModel = ViewModelProviders.of(this).get(SliderViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        sliderViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.imagen_viewpager, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        final ImageView imageView = root.findViewById(R.id.imageView);
        sliderViewModel.getText().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer index) {
                textView.setText(PAGE_TITLES[index]);
                imageView.setImageResource(PAGE_IMAGE[index]);
            }
        });
        return root;
    }
}

