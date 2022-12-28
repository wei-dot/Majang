package com.majang.ui.calculate;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.majang.Constants;
import com.majang.Hai;
import com.majang.R;
import com.majang.databinding.FragmentCalcBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class CalcFragment extends Fragment {

    private FragmentCalcBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CalcViewModel homeViewModel =
                new ViewModelProvider(this).get(CalcViewModel.class);

        binding = FragmentCalcBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.calcButton.setOnClickListener(v -> {
            int fan = Integer.parseInt(Objects.requireNonNull(binding.fanInput.getEditText()).getText().toString());
            int fu = Integer.parseInt(binding.filledExposedDropdown.getText().toString());
            int[] ans = Hai.fuCalculate(fu, fan);
            new AlertDialog.Builder(getContext())
                    .setTitle("計算結果")
                    .setMessage(String.format("莊家得點: %d\n閒家得點: %d", ans[0], ans[1]))
                    .setPositiveButton("OK", null)
                    .show();

        });
        ArrayList<Integer> fui = Arrays.stream(Constants.fui).boxed().collect(Collectors.toCollection(ArrayList::new));
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, fui);
//        binding.filledExposedDropdown.setText("20", false);
        binding.filledExposedDropdown.setAdapter(adapter);
//        binding.filledExposedDropdown.setDropDownBackgroundResource(R.color.white);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}