package com.majang.ui.calculate;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.majang.Adapter.FuiRecyclerViewAdapter;
import com.majang.Hai;
import com.majang.databinding.FragmentCalcBinding;

import java.util.ArrayList;
import java.util.Objects;

public class CalcFragment extends Fragment {

    private FragmentCalcBinding binding;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected FuiRecyclerViewAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected ArrayList<Integer> mDataset = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CalcViewModel homeViewModel =
                new ViewModelProvider(this).get(CalcViewModel.class);

        binding = FragmentCalcBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.calcButton.setOnClickListener(v -> {
            int fan = Integer.parseInt(Objects.requireNonNull(binding.textInputLayout.getEditText()).getText().toString());
            Log.d("fan", String.valueOf(fan));
            int[] ans = Hai.fuCalculate(40, fan);
            Log.d("ans", String.format("%d %d", ans[0], ans[1]));
            new AlertDialog.Builder(getContext())
                    .setTitle("計算結果")
                    .setMessage(String.format("莊家得點: %d\n閒家得點: %d", ans[0], ans[1]))
                    .setPositiveButton("OK", null)
                    .show();

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}