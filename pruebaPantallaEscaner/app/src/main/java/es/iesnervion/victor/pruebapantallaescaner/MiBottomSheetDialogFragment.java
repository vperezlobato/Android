package es.iesnervion.victor.pruebapantallaescaner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MiBottomSheetDialogFragment extends BottomSheetDialogFragment {

    static MiBottomSheetDialogFragment newInstance() {
        return new MiBottomSheetDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dialogfragment, container, false);
        return v;
    }
}
