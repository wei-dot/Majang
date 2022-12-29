package com.majang.ui.home;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.majang.Constants;
import com.majang.Hai;
import com.majang.R;
import com.majang.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ArrayList<String> myPai = new ArrayList<>();
    ArrayList<String> tin = new ArrayList<>();
    ArrayList<String> minGantsu = new ArrayList<>();
    ArrayList<String> anGantsu = new ArrayList<>();
    ArrayList<String> furu = new ArrayList<>();
    ArrayList<String> cTora = new ArrayList<>();
    ArrayList<String> cUraTora = new ArrayList<>();
    boolean isTsumo = false;
    private static int countLimit = 4;
    private static int paiLimit = 14;
    String isSelected = "";
    ArrayList<String> views = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.bottomBar.deleteAllButton.setOnClickListener(v -> {
            binding.bottomBar.myLinearLayout.removeAllViews();
            switch (isSelected) {
                case "手牌":
                    myPai.clear();
                    break;
                case "裡寶牌":
                    cUraTora.clear();
                    break;
                case "寶牌":
                    cTora.clear();
                    break;
                case "明槓":
                    minGantsu.clear();
                    break;
                case "暗槓":
                    anGantsu.clear();
                    break;
            }
        });
        binding.bottomBar.nextButton.setOnClickListener(v -> {
            if (myPai.size() >= 13 && tin.size() >= 1 && cTora.size() >= 1) {
                new MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Rounded)
                        .setTitle("自摸")
                        .setMessage("是否自摸")

                        .setPositiveButton("是", (dialog, which) -> {
                            isTsumo = true;
//                            myPai.removeAll(furu);
                            String ans = Hai.ron(ConvertToHai(myPai), new Hai(tin.get(0)), ConvertToHai(minGantsu),
                                    ConvertToHai(anGantsu), ConvertToHai(furu),
                                    ConvertToHai(cTora).toArray(new Hai[cTora.size()]),
                                    ConvertToHai(cUraTora).toArray(new Hai[cUraTora.size()]), isTsumo);
                            new MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Rounded)
                                    .setTitle("和了")
                                    .setMessage(ans)
                                    .setPositiveButton("確定", null)
                                    .show();
                            myPai.clear();
                            tin.clear();
                            minGantsu.clear();
                            anGantsu.clear();
                            furu.clear();
                            cTora.clear();
                            cUraTora.clear();
                            binding.bottomBar.myLinearLayout.removeAllViews();
                        })
                        .setNegativeButton("否", (dialog, which) -> {
                            isTsumo = false;
//                            myPai.removeAll(furu);
                            Log.d("test",furu.toString());
                            Log.d("test",myPai.toString());
                            String ans = Hai.ron(ConvertToHai(myPai), new Hai(tin.get(0)), ConvertToHai(minGantsu),
                                    ConvertToHai(anGantsu), ConvertToHai(furu),
                                    ConvertToHai(cTora).toArray(new Hai[cTora.size()]),
                                    ConvertToHai(cUraTora).toArray(new Hai[cUraTora.size()]), isTsumo);
                            new MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Rounded)
                                    .setTitle("和了")
                                    .setMessage(ans)
                                    .setPositiveButton("確定", null)
                                    .show();
                            myPai.clear();
                            tin.clear();
                            minGantsu.clear();
                            anGantsu.clear();
                            furu.clear();
                            cTora.clear();
                            cUraTora.clear();
                            binding.bottomBar.myLinearLayout.removeAllViews();
                        })
                        .show();
            } else {
                new MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Rounded)
                        .setTitle("錯誤")
                        .setMessage("手牌數量不足")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        Spinner spinner = binding.optionSpinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, Constants.items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        MaterialButton info = binding.bottomBar.infoButton;
        info.setOnClickListener(v -> new AlertDialog.Builder(
                getContext())
                .setTitle("不計以下役種:")
                .setMessage("一翻:立直、一發、門清自摸和、嶺上開花、搶槓、海底撈月、河底撈魚、赤寶牌\n" +
                        "兩翻:雙立直\n" +
                        "五翻:流局滿貫\n" +
                        "役滿:天和、地和 \n" +
                        "這些都是和牌局的手牌比較無關的要計算要通靈，所以不計")
                .setPositiveButton("確定", null)
                .show());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isSelected = parent.getItemAtPosition(position).toString();
                binding.bottomBar.myLinearLayout.removeAllViews();
                LinearLayout linearLayout = binding.bottomBar.myLinearLayout;
                try {
                    switch (isSelected) {
                        case "手牌":
                            views = myPai;
                            countLimit = 4;
                            paiLimit = 13;
                            break;
                        case "銃牌":
                            views = tin;
                            countLimit = 1;
                            paiLimit = 1;
                            break;
                        case "明槓":
                            views = minGantsu;
                            countLimit = 1;
                            paiLimit = 14;
                            break;
                        case "暗槓":
                            views = anGantsu;
                            countLimit = 1;
                            paiLimit = 14;
                            break;
                        case "副露":
                            views = furu;
                            countLimit = 4;
                            paiLimit = 14;
                            break;
                        case "寶牌":
                            views = cTora;
                            countLimit = 4;
                            paiLimit = 14;
                            break;
                        case "裡寶牌":
                            views = cUraTora;
                            countLimit = 4;
                            paiLimit = 14;
                            break;
                    }
                    for (String s : views) {
                        View majang_item1 = addItem(getTypes(s), s, linearLayout);
                        majang_item1.setOnClickListener(v1 -> {
                            linearLayout.removeView(majang_item1);
                            views.remove(s);
                            Log.d("test", views.toString());
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String majang_types;
        try {
            for (String s : requireActivity().getAssets().list("majang_icon")) {
                majang_types = s;
                TreeMap<Integer, String> test = new TreeMap<>();
                LinearLayout linearLayout1;
                for (String s1 : requireActivity().getAssets().list("majang_icon/" + majang_types)) {
                    test.put(Constants.category.indexOf(s1.substring(0, 1)), s1);
                }
                switch (majang_types) {
                    case "tiao":
                        linearLayout1 = binding.tiaoLinearLayout;
                        break;
                    case "wan":
                        linearLayout1 = binding.wanLinearLayout;
                        break;
                    case "tong":
                        linearLayout1 = binding.tongLinearLayout;
                        break;
                    default:
                        linearLayout1 = binding.ziLinearLayout;
                        break;

                }
                for (String s1 : test.values()) {
                    View majang_item = addItem(majang_types, s1, linearLayout1);
                    LinearLayout linearLayout = binding.bottomBar.myLinearLayout;
                    final String Majang_types = majang_types;
                    majang_item.setOnClickListener(v -> {
                        try {
                            View majang_item1;
                            int sum = Collections.frequency(views, s1);
                            if (sum < countLimit && views.size() < paiLimit) {
                                views.add(s1);
                                majang_item1 = addItem(Majang_types, s1, linearLayout);
                                majang_item1.setOnClickListener(v1 -> {
                                    linearLayout.removeView(majang_item1);
                                    views.remove(s1);
                                });
                            }
                            Log.d("test", views.toString());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private View addItem(String majang_types, String s1, LinearLayout linearLayout) throws IOException {
        View majang_item = getLayoutInflater().inflate(R.layout.majang_item, linearLayout, false);
        ImageView imageView = majang_item.findViewById(R.id.majang_icon);
        TextView textView = majang_item.findViewById(R.id.majang_name);
        InputStream inputStream = requireActivity().getAssets().open(String.format("majang_icon/%s/%s", majang_types, s1));
        imageView.setImageDrawable(Drawable.createFromStream(inputStream, null));
        imageView.requestLayout();
        imageView.getLayoutParams().height = 100;
        imageView.getLayoutParams().width = 100;
        textView.setText(s1);
        majang_item.setTag("unselected");
        majang_item.setPadding(10, 10, 10, 10);
        linearLayout.addView(majang_item);
        return majang_item;
    }

    private ArrayList<Hai> ConvertToHai(ArrayList<String> views) {
        ArrayList<Hai> haiArrayList = new ArrayList<>();
        for (String s : views) {
            haiArrayList.add(new Hai(s));
        }
        return haiArrayList;
    }

    private String getTypes(String value) {
        String types = "";
        if (value.length() > 1) {
            switch (value.substring(1, 2)) {
                case "條":
                    types = "tiao";
                    break;
                case "萬":
                    types = "wan";
                    break;
                case "筒":
                    types = "tong";
                    break;
            }
        } else {
            types = "zi";
        }
        return types;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}