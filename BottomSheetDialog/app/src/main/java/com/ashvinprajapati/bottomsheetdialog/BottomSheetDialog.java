package com.ashvinprajapati.bottomsheetdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_bottom_sheet_dialog, container, false);

        Button button1 = view.findViewById(R.id.button_1);
        Button button2 = view.findViewById(R.id.button_2);

        button1.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Button 1 Clicked", Toast.LENGTH_SHORT).show();
            dismiss();
        });
        button2.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Button 2 Clicked", Toast.LENGTH_SHORT).show();
            dismiss();
        });
        return view;
    }
}
