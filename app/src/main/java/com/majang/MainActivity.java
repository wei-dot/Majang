package com.majang;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.tiao_linear_layout);

        ArrayList<String[]> majang_list = new ArrayList<>();
        //get folder name
        String majang_types;
        String majang_name;
        try {
            for (String s : getAssets().list("majang_icon")) {
                majang_types = s;
                for (String s1 : getAssets().list("majang_icon/" + majang_types)) {
                    majang_name = s1;
                    String[] array = new String[]{majang_types, majang_name};
                    majang_list.add(array);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sort
        majang_list.sort(new ChineseNameComparator());

        try{
            for(String[] s : majang_list){
                if (s[0].equals("tiao")){
                    View tiao_item = getLayoutInflater().inflate(R.layout.majang_item, linearLayout, false);
                    ImageView imageView = tiao_item.findViewById(R.id.majang_icon);
                    TextView textView = tiao_item.findViewById(R.id.majang_name);
                    InputStream inputStream = getAssets().open(String.format("majang_icon/%s/%s", s[0], s[1]));
                    imageView.setImageDrawable(Drawable.createFromStream(inputStream, null));
                    imageView.requestLayout();
                    imageView.getLayoutParams().height = 100;
                    imageView.getLayoutParams().width = 100;
                    textView.setText(s[1]);
                    linearLayout.addView(tiao_item);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
class PaiView extends androidx.appcompat.widget.AppCompatImageView {
    public PaiView(Context context) {
        super(context);
    }
}

class ChineseNumberUtil {
    public static int chineseNumber2Int(String chineseNumber){
        int result = 0;
        int temp = 1;
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {
                if (c == cnArr[j]) {
                    temp = j + 1;
                    break;
                }
            }
            if (i == chineseNumber.length() - 1) {
                result += temp;
            }
        }
        return result;
    }
}

class ChineseNameComparator implements Comparator<String[]> {
    @Override
    public int compare(String[] arg0, String[] arg1) {
        return Integer.compare(ChineseNumberUtil.chineseNumber2Int(arg0[1]),
                ChineseNumberUtil.chineseNumber2Int(arg1[1]));
    }
}