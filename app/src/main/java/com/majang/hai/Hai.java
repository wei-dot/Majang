package com.majang.hai;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author jerem
 */
public class Hai {

    int num;
    int type;
    boolean red = false;

    public Hai(int num, int type) {
        this.num = num;
        this.type = type;
    }

    public Hai(String hai) {
        if (hai.length() == 1) {
            switch (hai) {
                case "中":
                    this.num = 5;
                    this.type = 0;
                    break;
                case "發":
                    this.num = 6;
                    this.type = 0;
                    break;
                case "白":
                    this.num = 7;
                    this.type = 0;
                    break;
            }
        } else {
            if (hai.substring(1, 2).contains("風")) {
                switch (hai.substring(0, 1)) {
                    case "東":
                        this.num = 1;
                        this.type = 0;
                        break;
                    case "南":
                        this.num = 2;
                        this.type = 0;
                        break;
                    case "西":
                        this.num = 3;
                        this.type = 0;
                        break;
                    case "北":
                        this.num = 4;
                        this.type = 0;
                        break;
                }
            } else {
                switch (hai.substring(1, 2)) {
                    case "條":
                        this.type = 1;
                        break;
                    case "筒":
                        this.type = 2;
                        break;
                    case "萬":
                        this.type = 3;
                        break;
                }
                switch (hai.substring(0, 1)) {
                    case "一":
                        this.num = 1;
                        break;
                    case "二":
                        this.num = 2;
                        break;
                    case "三":
                        this.num = 3;
                        break;
                    case "四":
                        this.num = 4;
                        break;
                    case "五":
                        this.num = 5;
                        break;
                    case "六":
                        this.num = 6;
                        break;
                    case "七":
                        this.num = 7;
                        break;
                    case "八":
                        this.num = 8;
                        break;
                    case "九":
                        this.num = 9;
                        break;
                }
            }
        }
    }

    public static ArrayList<Hai> sort(Hai[] unsorted) {

        ArrayList<Hai> type0 = new ArrayList<>();
        ArrayList<Hai> type1 = new ArrayList<>();
        ArrayList<Hai> type2 = new ArrayList<>();
        ArrayList<Hai> type3 = new ArrayList<>();

        for (int i = 0; i < unsorted.length; i++) {
            if (unsorted[i].type == 0) {
                type0.add(unsorted[i]);
            } else if (unsorted[i].type == 1) {
                type1.add(unsorted[i]);
            } else if (unsorted[i].type == 2) {
                type2.add(unsorted[i]);
            } else if (unsorted[i].type == 3) {
                type3.add(unsorted[i]);
            }
        }

        type0.sort(new HaiNumSort());
        type1.sort(new HaiNumSort());
        type2.sort(new HaiNumSort());
        type3.sort(new HaiNumSort());

        ArrayList<Hai> sorted = new ArrayList<>();
        sorted.addAll(type3);
        sorted.addAll(type2);
        sorted.addAll(type1);
        sorted.addAll(type0);

        return sorted;
    }

    public static ArrayList<Hai> sort(ArrayList<Hai> unsorted) {
        ArrayList<Hai> type0 = new ArrayList<>();
        ArrayList<Hai> type1 = new ArrayList<>();
        ArrayList<Hai> type2 = new ArrayList<>();
        ArrayList<Hai> type3 = new ArrayList<>();

        for (int i = 0; i < unsorted.size(); i++) {
            if (unsorted.get(i).type == 0) {
                type0.add(unsorted.get(i));
            } else if (unsorted.get(i).type == 1) {
                type1.add(unsorted.get(i));
            } else if (unsorted.get(i).type == 2) {
                type2.add(unsorted.get(i));
            } else if (unsorted.get(i).type == 3) {
                type3.add(unsorted.get(i));
            }
        }

        type0.sort(new HaiNumSort());
        type1.sort(new HaiNumSort());
        type2.sort(new HaiNumSort());
        type3.sort(new HaiNumSort());

        ArrayList<Hai> sorted = new ArrayList<>();
        sorted.addAll(type3);
        sorted.addAll(type2);
        sorted.addAll(type1);
        sorted.addAll(type0);

        return sorted;
    }

    public static String ron(ArrayList<Hai> hands, Hai tin, ArrayList<Hai> minGantsu, ArrayList<Hai> anGantsu,
                             ArrayList<Hai> furu, Hai[] cTora, Hai[] cUraTora, boolean tsumo) {
        int fu = 20;//符
        int fan = 0;//翻數
        String ans = "";
        ArrayList<Hai> full = new ArrayList<>();
        ArrayList<Hai> gantsu = new ArrayList<>();
        gantsu.addAll(minGantsu);
        gantsu.addAll(anGantsu);
        full.addAll(hands);
        full.addAll(furu);
        for (int i = 0; i < gantsu.size(); i++) {
            for (int j = 0; j < 3; j++) {
                full.add(gantsu.get(i));
            }
        }
        full.add(tin);
        full = Hai.sort(full);

        if (!Hai.isLegal(hands, tin)) {
            return "無法和牌";
        } else {
            if (Hai.isKokushi(full)) {
                fan = 13;
                ans = "國士無雙 \n";
            } else if (Hai.isKokushi_ad(hands, tin)) {
                fan = 13;
                ans = "國士無雙十三面 \n";
            } else if (Hai.isDaisan(full)) {
                fan = 13;
                ans = "大三元 \n";
            } else if (Hai.isSuenku(hands, tin, true)) {
                fan = 13;
                ans = "四暗刻 \n";
            } else if (Hai.isZuisu(hands, tin)) {
                fan = 13;
                ans = "字一色 \n";
            } else if (Hai.isRyuisu(hands, tin)) {
                fan = 13;
                ans = "綠一色 \n";
            } else if (Hai.isSaoSu_Shi(full)) {
                fan = 13;
                ans = "小四喜 \n";
            } else if (Hai.isDaiSu_Shi(full)) {
                fan = 13;
                ans = "大四喜 \n";
            } else if (Hai.isChinro(full)) {
                fan = 13;
                ans = "清老頭 \n";
            } else if (Hai.isChuren(hands, tin)) {
                fan = 13;
                ans = "九蓮寶燈 \n";
            } else if (Hai.isChuren_ad(hands, tin)) {
                fan = 13;
                ans = "純正九蓮寶燈 \n";
            } else if (Hai.isSugantsu(gantsu)) {
                fan = 13;
                ans = "四槓子 \n";
            } else if (Hai.isChitoi(full)) {
                fu = 25;
                fan = 2;
                ans = "七對子 2翻\n";
            }
            if (fan == 13) {
                return ans;
            }
            if (Hai.isChinisu(hands, tin)) {
                fan += 6;
                ans = "清一色  6翻\n" + ans;
            }
            if (Hai.isHonisu(hands, tin)) {
                if (furu.isEmpty() && minGantsu.isEmpty()) {
                    fan += 3;
                    ans = "混一色 3翻\n" + ans;
                } else {
                    fan += 2;
                    ans = "混一色 2翻\n" + ans;
                }
            }
            if (Hai.isJunchan(hands, tin)) {
                if (furu.isEmpty() && minGantsu.isEmpty()) {
                    fan += 3;
                    ans = "純全帶么 3翻\n" + ans;
                } else {
                    fan += 2;
                    ans = "純全帶么 2翻\n" + ans;
                }
            }
            if (Hai.isJunchan(hands, tin)) {
                fan += 3;
                ans = "二盃口 3翻\n" + ans;
            }
            if (Hai.isSanse(hands, tin)) {
                if (furu.isEmpty() && minGantsu.isEmpty()) {
                    fan += 2;
                    ans = "三色同順 2翻\n" + ans;
                } else {
                    fan += 1;
                    ans = "三色同順 1翻\n" + ans;
                }
            }
            if (Hai.isSantonke(hands, tin)) {
                fan += 2;
                ans = "三色同刻 2翻\n" + ans;
            }
            if (Hai.isIchi(hands, tin)) {
                if (furu.isEmpty() && minGantsu.isEmpty()) {
                    fan += 2;
                    ans = "一氣通貫 2翻\n" + ans;
                } else {
                    fan += 1;
                    ans = "一氣通貫 1翻\n" + ans;
                }
            }
            if (Hai.isToitoi(full)) {
                fan += 2;
                ans = "對對和 2翻\n" + ans;
            }
            if (Hai.isSanenke(hands, tin)) {
                fan += 2;
                ans = "三暗刻 2翻\n" + ans;
            }
            if (Hai.isSangantsu(gantsu)) {
                fan += 2;
                ans = "三槓子 2翻\n" + ans;
            }
            if (Hai.isChanta(full)) {
                if (furu.isEmpty() && minGantsu.isEmpty()) {
                    fan += 2;
                    ans = "混全帶么 2翻\n" + ans;
                } else {
                    fan += 1;
                    ans = "混全帶么 1翻\n" + ans;
                }
            }
            if (Hai.isHonro(full)) {
                fan += 2;
                ans = "混老頭 2翻\n" + ans;
            }
            if (Hai.isSaosan(full)) {
                fan += 2;
                ans = "小三元 2翻\n" + ans;
            }
            if (Hai.isTanyao(full)) {
                fan += 1;
                ans = "斷么九 1翻\n" + ans;
            }
            if (Hai.isIpe(full)) {
                fan += 1;
                ans = "一杯口 1翻\n" + ans;
            }
            if (Hai.hasGreen(full)) {
                fan += 1;
                ans = "役牌:發 1翻\n" + ans;
            }
            if (Hai.hasRed(full)) {
                fan += 1;
                ans = "役牌:中 1翻\n" + ans;
            }
            if (Hai.hasWhite(full)) {
                fan += 1;
                ans = "役牌:白 1翻\n" + ans;
            }
            int tr = Hai.countDora(full, cTora);
            fan += tr;
            ans = "寶牌 " + tr + "翻\n" + ans;
            tr = Hai.countDora(full, cUraTora);
            fan += tr;
            ans = "裡寶牌 " + tr + "翻\n" + ans;
            if (fan >= 13) {
                ans = ans + "累計役滿";
            }
            fu = Hai.fuCount(full, hands, tin, furu, minGantsu, anGantsu, tsumo);
            if (fan < 5) {
                ans = fu + "符\n" + ans;
            }
            System.out.println("fu=" + fu + " fan=" + fan + ",[" + Hai.fuCalculate(fu, fan)[0] + "," + Hai.fuCalculate(fu, fan)[1] + "]");
            ans = ans + Hai.grade(fu, fan) + "\n" + "莊家得點:" + Hai.fuCalculate(fu, fan)[0] + "\n閒家得點:" + Hai.fuCalculate(fu, fan)[1];

        }

        return ans;
    }

    //符數台數計算
    public static int[] fuCalculate(int fu, int fan) {
        int[] points = new int[2];//0是莊家 1是閒家
        int a = fu / 10;
        int[][] host_p
                = {{0, 0, 0, 1500, 2000, 2400, 2900, 3400, 3900, 4400, 4800, 5300},
                {0, 0, 2000, 2900, 3900, 4800, 5800, 6800, 7700, 8700, 9600, 10600},
                {0, 0, 3900, 5800, 7700, 9600, 11600, 12000, 12000, 12000, 12000, 12000},
                {0, 0, 7700, 11600, 12000, 12000, 12000, 12000, 12000, 12000, 12000, 12000}};
        int[][] gust_p
                = {{0, 0, 0, 1000, 1300, 1600, 2000, 2300, 2600, 2900, 3200, 3600},
                {0, 0, 1300, 2000, 2600, 3200, 3900, 4500, 5200, 5800, 6400, 7100},
                {0, 0, 2600, 3900, 5200, 6400, 7700, 8000, 8000, 8000, 8000, 8000},
                {0, 0, 5200, 7700, 8000, 8000, 8000, 8000, 8000, 8000, 8000, 8000}};
        int[] host_chitoi = {0, 0, 2400, 4800, 9600};
        int[] gust_chitoi = {0, 0, 1600, 3200, 6400};
        if (fan < 5 && fu != 25) {
            points[0] = host_p[fan - 1][a];
            points[1] = gust_p[fan - 1][a];

        } else if (fu == 25) {
            points[0] = host_chitoi[fan];
            points[1] = gust_chitoi[fan];
        } else {
            if (fan == 5) {
                points[0] = 12000;
                points[1] = 8000;
            } else if (fan == 6 || fan == 7) {
                points[0] = 18000;
                points[1] = 12000;
            } else if (fan >= 8 && fan <= 10) {
                points[0] = 24000;
                points[1] = 16000;
            } else if (fan == 11 || fan == 12) {
                points[0] = 36000;
                points[1] = 24000;
            } else {
                points[0] = 48000;
                points[1] = 32000;
            }
        }

        return points;
    }

    public static int[] res(int a, int b) {
        int[] c = {a, b};
        return c;
    }

    //得點評價
    public static String grade(int fu, int fan) {
        if ((fu >= 70 && fan == 3) || (fu >= 40 && fan == 4) || (fan == 5)) {
            return "滿貫";
        } else if (fan == 6 || fan == 7) {
            return "跳滿";
        } else if (fan >= 8 && fan <= 10) {
            return "倍滿";
        } else if (fan == 11 || fan == 12) {
            return "三倍滿";
        } else if (fan >= 13) {
            return "役滿";
        }
        return "";
    }

    //計算符數
    public static int fuCount(ArrayList<Hai> full, ArrayList<Hai> hands, Hai tin, ArrayList<Hai> furu,
                              ArrayList<Hai> minGantsu, ArrayList<Hai> anGantsu, boolean tsumo) {
        int total = 20;
        ArrayList<Hai> temps = Hai.sort(full);
        Hai[][] com = new Hai[4][3];

        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        int now = 0;
        for (int i = 0; i < eyes.size(); i++) {
            total = 20;
            now = 0;
            P = Hai.removeHai(temps, eyes.get(i), 2);
            if (eyes.get(i).isEqual(new Hai("白")) || eyes.get(i).isEqual(new Hai("發")) || eyes.get(i).isEqual(new Hai("中"))) {
                total += 2;
            }
            P = Hai.sort(P);
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                    now++;
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num, temp.type);
                    com[now][2] = new Hai(temp.num, temp.type);
                    now++;
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.size() == 0) {
                if (!tsumo && furu.isEmpty() && minGantsu.isEmpty()) {
                    total += 10;
                }
                for (int j = 0; j < com.length; j++) {
                    if (com[j][0] != null) {
                        Hai tp = com[j][0];
                        if (Hai.countHai(hands, tp) >= 3) {
                            if (!(tp.num == 1 || tp.num == 9) && tp.type != 0) {
                                total += 4;
                            } else {
                                total += 8;
                            }
                        } else if (Hai.countHai(furu, tp) >= 3) {
                            if ((tp.num != 1 || tp.num != 9) && tp.type != 0) {
                                total += 2;
                            } else {
                                total += 4;
                            }
                        }
                    }
                    for (Hai tp : minGantsu) {
                        if ((tp.num != 1 || tp.num != 9) && tp.type != 0) {
                            total += 8;
                        } else {
                            total += 16;
                        }
                    }
                    for (Hai tp : anGantsu) {
                        if ((tp.num != 1 || tp.num != 9) && tp.type != 0) {
                            total += 16;
                        } else {
                            total += 32;
                        }
                    }
                }
                if (Hai.tinHai(full, tin).size() == 1) {
                    total += 2;
                }
            }
        }
        int t = total / 10;
        total = t * 10 + 10;
        return total;
    }

    //聽啥牌
    public static ArrayList<Hai> tinHai(ArrayList<Hai> full, Hai tin) {
        ArrayList<Hai> result = new ArrayList<>();
        ArrayList<Hai> temp = Hai.sort(full);
        temp = Hai.removeHai(temp, tin, 1);
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 9; j++) {
                if (Hai.isLegal(temp, new Hai(j, i))) {
                    result.add(new Hai(j, i));
                }
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (Hai.isLegal(temp, new Hai(0, i))) {
                result.add(new Hai(0, i));
            }
        }
        result = Hai.sort(result);
        return result;

    }

    //確認七對子
    public static boolean isChitoi(ArrayList<Hai> full) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(full);
        //符合2222222牌型
        for (int i = 0; i < temps.size(); i += 2) {
            if (!(temps.get(i).isEqual(temps.get(i + 1)))) {
                return false;
            }
        }
        //確認沒有4張相同牌
        for (int i = 0; i < temps.size(); i++) {
            int cnt = 0;
            for (int j = 0; j < temps.size(); j++) {
                if (temps.get(i).isEqual(temps.get(j))) {
                    cnt++;
                }
                if (cnt > 2) {
                    return false;
                }
            }
        }
        return true;
    }

    //確認國士無雙
    public static boolean isKokushi(ArrayList<Hai> full) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(full);
        Hai[] thirteenType = {new Hai("一萬"), new Hai("九萬"), new Hai("一筒"), new Hai("九筒"), new Hai("一條"), new Hai("九條"),
                new Hai("東風"), new Hai("南風"), new Hai("西風"), new Hai("北風"), new Hai("中"), new Hai("發"), new Hai("白")};
        boolean[] checks = new boolean[thirteenType.length];
        boolean forteen = false;
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < thirteenType.length; j++) {
                if (temps.get(i).isEqual(thirteenType[j])) {
                    if (checks[j] == true) {
                        forteen = true;
                    } else {
                        checks[j] = true;
                    }
                }
            }
        }
        for (int j = 0; j < checks.length; j++) {
            if (!checks[j]) {
                return false;
            }
        }
        return forteen;
    }

    //確認國士無雙十三面
    public static boolean isKokushi_ad(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> target = new ArrayList<>();
        String[] a = {"一筒", "九筒", "一萬", "九萬", "一條", "九條", "東風", "南風", "北風", "西風", "中", "發", "白"};
        for (int i = 0; i < a.length; i++) {
            target.add(new Hai(a[i]));
        }
        target = Hai.sort(target);

        for (int i = 0; i < hands.size(); i++) {
            if (!hands.get(i).isEqual(target.get(i))) {
                return false;
            }
        }
        for (int j = 0; j < target.size(); j++) {
            if (target.get(j).isEqual(tin)) {
                return true;
            }
        }
        return false;
    }

    //確認大三元
    public static boolean isDaisan(ArrayList<Hai> full) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(full);
        int red = 0;
        int green = 0;
        int white = 0;
        for (int i = 0; i < temps.size(); i++) {
            if (temps.get(i).isEqual(new Hai("中"))) {
                red += 1;
            } else if (temps.get(i).isEqual(new Hai("發"))) {
                green += 1;
            } else if (temps.get(i).isEqual(new Hai("白"))) {
                white += 1;
            } else {
                continue;
            }
        }
        return red >= 3 && green >= 3 && white >= 3;
    }

    //確認大四喜
    public static boolean isDaiSu_Shi(ArrayList<Hai> full) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(full);
        int e = 0;
        int w = 0;
        int n = 0;
        int s = 0;
        for (int i = 0; i < temps.size(); i++) {
            if (temps.get(i).isEqual(new Hai("東風"))) {
                e += 1;
            } else if (temps.get(i).isEqual(new Hai("南風"))) {
                s += 1;
            } else if (temps.get(i).isEqual(new Hai("西風"))) {
                w += 1;
            } else if (temps.get(i).isEqual(new Hai("北風"))) {
                n += 1;
            } else {
                continue;
            }
        }
        return e >= 3 && w >= 3 && s >= 3 && n >= 3;
    }

    //確認小四喜
    public static boolean isSaoSu_Shi(ArrayList<Hai> full) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(full);

        int[] winds = new int[4];
        int total = 0;
        for (int i = 0; i < winds.length; i++) {
            winds[i] = Hai.countHai(temps, new Hai(i + 1, 0));
            if (winds[i] > 3) {
                winds[i] = 3;
            }
            total += winds[i];
        }
        return total == 11;
    }

    //確認清老頭
    public static boolean isChinro(ArrayList<Hai> full) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(full);

        for (int i = 0; i < temps.size(); i++) {
            if (!(temps.get(i).type != 0 && (temps.get(i).num == 1 || temps.get(i).num == 9))) {
                return false;
            }
        }
        return true;
    }

    //確認字一色
    public static boolean isZuisu(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        for (int i = 0; i < temps.size(); i++) {
            if (temps.get(i).type != 0) {
                return false;
            }
        }
        return true;
    }

    //確認九蓮寶燈
    public static boolean isChuren(ArrayList<Hai> hands, Hai tin) {
        int type = Hai.isPureType(hands, tin);
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        int[] target = {0, 3, 1, 1, 1, 1, 1, 1, 1, 3};
        if (type != -1) {
            for (int i = 1; i < target.length; i++) {
                if (Hai.countHai(temps, new Hai(i, type)) < target[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    //確認純正九蓮寶燈
    public static boolean isChuren_ad(ArrayList<Hai> hands, Hai tin) {
        int type = Hai.isPureType(hands, tin);
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        ArrayList<Hai> target = new ArrayList<>();
        int[] a = {1, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9};
        for (int i = 0; i < a.length; i++) {
            target.add(new Hai(a[i], type));
        }
        for (int i = 0; i < temps.size(); i++) {
            if (!temps.get(i).isEqual(target.get(i))) {
                return false;
            }
        }
        return tin.type == type;

    }

    //確認綠一色
    public static boolean isRyuisu(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        ArrayList<Hai> check = new ArrayList<>();
        check.add(new Hai("二條"));
        check.add(new Hai("三條"));
        check.add(new Hai("四條"));
        check.add(new Hai("六條"));
        check.add(new Hai("八條"));
        check.add(new Hai("發"));

        for (int i = 0; i < temps.size(); i++) {
            if (!Hai.isContains(check, temps.get(i))) {
                return false;
            }
        }
        return true;
    }

    //確認四暗刻
    public static boolean isSuenku(ArrayList<Hai> hands, Hai tin, boolean tsumo) {
        int current = 0;
        ArrayList<Hai> P = new ArrayList<>();
        P = Hai.sort(hands);
        int sz = P.size();
        while (true) {
            if (Hai.countHai(P, P.get(current)) >= 3) {
                Hai temp = P.get(current);
                P = Hai.removeHai(P, new Hai(temp.num, temp.type), 3);
            } else {
                current++;
            }
            sz = P.size();
            if (current >= sz) {
                break;
            }
        }
        if (P.size() == 4) {
            return Hai.isContains(P, tin) && tsumo;
        } else if (P.size() == 1) {
            return Hai.isContains(P, tin);
        } else {
            return false;
        }
    }

    //確認四槓子
    public static boolean isSugantsu(ArrayList<Hai> gantsu) {
        return gantsu.size() == 4;
    }

    //確認三槓子
    public static boolean isSangantsu(ArrayList<Hai> gantsu) {
        return gantsu.size() == 3;
    }

    //確認清一色
    public static boolean isChinisu(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        return Hai.isPureType(temps, tin) != (-1);
    }

    //確認混一色
    public static boolean isHonisu(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        int type = temps.get(0).type;
        for (int i = 0; i < temps.size(); i++) {
            if (!(temps.get(i).type == type || temps.get(i).type == 0)) {
                return false;
            }
        }
        return true;
    }

    //確認純全帶么
    public static boolean isJunchan(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        Hai[][] com = new Hai[4][3];

        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        int now = 0;
        for (int i = 0; i < eyes.size(); i++) {
            now = 0;
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num + 1, temp.type);
                    com[now][2] = new Hai(temp.num + 2, temp.type);
                    now++;
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num, temp.type);
                    com[now][2] = new Hai(temp.num, temp.type);
                    now++;
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.size() == 0) {
                for (int k = 0; k < com.length; k++) {
                    if (com[k][0].num == 1 || com[k][2].num == 9) {
                        continue;
                    } else {
                        return false;
                    }
                }
            } else {
            }
        }
        return true;

    }

    //確認二盃口
    public static boolean isLanpe(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        Hai[][] com = new Hai[4][3];

        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        int now = 0;
        for (int i = 0; i < eyes.size(); i++) {
            now = 0;
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num + 1, temp.type);
                    com[now][2] = new Hai(temp.num + 2, temp.type);
                    now++;
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num, temp.type);
                    com[now][2] = new Hai(temp.num, temp.type);
                    now++;
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.size() == 0) {
                if (com[0][0].isEqual(com[1][0]) && com[0][1].isEqual(com[1][1]) && com[0][2].isEqual(com[1][2])
                        && com[2][0].isEqual(com[3][0]) && com[2][1].isEqual(com[3][1]) && com[2][2].isEqual(com[3][2])) {
                    return true;
                }
            } else {
            }
        }
        return false;
    }

    //確認三色同順
    public static boolean isSanse(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        Hai[][] com = new Hai[4][3];

        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        int now = 0;
        for (int i = 0; i < eyes.size(); i++) {
            now = 0;
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num + 1, temp.type);
                    com[now][2] = new Hai(temp.num + 2, temp.type);
                    now++;
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num, temp.type);
                    com[now][2] = new Hai(temp.num, temp.type);
                    now++;
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.size() == 0) {
                int[] nums = new int[3];
                boolean[] types = new boolean[4];
                for (int j = 0; j < com.length; j++) {
                    nums[0] = com[j][0].num;
                    nums[1] = com[j][1].num;
                    nums[2] = com[j][2].num;
                    for (int k = 0; k < com.length; k++) {
                        if (com[k][0].num == nums[0] && com[k][1].num == nums[1] && com[k][2].num == nums[2]) {
                            types[com[k][0].type] = true;
                        }
                    }
                    if (types[1] && types[2] && types[3]) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    //確認三色同刻
    public static boolean isSantonke(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        Hai[][] com = new Hai[4][3];

        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        int now = 0;
        for (int i = 0; i < eyes.size(); i++) {
            now = 0;
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num + 1, temp.type);
                    com[now][2] = new Hai(temp.num + 2, temp.type);
                    now++;
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num, temp.type);
                    com[now][2] = new Hai(temp.num, temp.type);
                    now++;
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.size() == 0) {
                int ke = 0;
                boolean[] checks = new boolean[4];
                for (int j = 0; j < com.length; j++) {
                    ke = com[j][0].num;
                    for (int k = 0; k < com.length; k++) {
                        if (com[k][0].num == ke && com[k][1].num == ke && com[k][2].num == ke) {
                            checks[com[k][0].type] = true;
                        }
                    }
                    if (checks[1] && checks[2] && checks[3]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //確認一氣通貫
    public static boolean isIchi(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        Hai[][] com = new Hai[4][3];

        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        int now = 0;
        for (int i = 0; i < eyes.size(); i++) {
            now = 0;
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num + 1, temp.type);
                    com[now][2] = new Hai(temp.num + 2, temp.type);
                    now++;
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num, temp.type);
                    com[now][2] = new Hai(temp.num, temp.type);
                    now++;
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.size() == 0) {
                for (int j = 0; j < com.length; j++) {
                    int tp = com[j][0].type;
                    boolean[] checks = new boolean[10];
                    for (int k = 0; k < com.length; k++) {
                        for (int t = 0; t < com[k].length; t++) {
                            if (com[k][t].type == tp) {
                                checks[com[k][t].num] = true;
                            }
                        }
                    }
                    if (checks[1] && checks[2] && checks[3]
                            && checks[4] && checks[5] && checks[6]
                            && checks[7] && checks[8] && checks[9]) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    //確認對對胡
    public static boolean isToitoi(ArrayList<Hai> full) {
        ArrayList<Hai> temps = Hai.sort(full);
        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        for (int i = 0; i < eyes.size(); i++) {
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            int sz = P.size();
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 3);
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.size() == 0) {
                return true;
            }
        }
        return false;
    }

    //確認三暗刻
    public static boolean isSanenke(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);

        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        int cnt = 0;
        for (int i = 0; i < eyes.size(); i++) {
            //去除雀頭
            cnt = 0;
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            //去除連續三數 ex:一萬二萬三萬
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            //去除三張相同
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                    cnt++;
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (cnt == 3) {
                return true;
            }
        }
        return false;
    }

    //確認混全帶么
    public static boolean isChanta(ArrayList<Hai> full) {
        ArrayList<Hai> temps = Hai.sort(full);
        Hai[][] com = new Hai[4][3];

        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))
                        && (temps.get(i).type == 0 || temps.get(i).num == 1 || temps.get(i).num == 9)) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        int now = 0;
        for (int i = 0; i < eyes.size(); i++) {
            now = 0;
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num + 1, temp.type);
                    com[now][2] = new Hai(temp.num + 2, temp.type);
                    now++;
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num, temp.type);
                    com[now][2] = new Hai(temp.num, temp.type);
                    now++;
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.size() == 0) {
                for (int j = 0; j < com.length; j++) {
                    if (!(com[j][0].type == 0 || com[j][0].num == 1 || com[j][2].num == 9)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    //確認混老頭
    public static boolean isHonro(ArrayList<Hai> full) {
        for (int i = 0; i < full.size(); i++) {
            if (!(full.get(i).num == 1 || full.get(i).num == 9 || full.get(i).type == 0)) {
                return false;
            }
        }
        return true;
    }

    //確認小三元
    public static boolean isSaosan(ArrayList<Hai> full) {
        return Hai.countHai(full, new Hai("白")) + Hai.countHai(full, new Hai("發")) + Hai.countHai(full, new Hai("中")) == 8;
    }

    //確認中
    public static boolean hasRed(ArrayList<Hai> full) {
        return Hai.countHai(full, new Hai("中")) == 3;
    }

    //確認中
    public static boolean hasGreen(ArrayList<Hai> full) {
        return Hai.countHai(full, new Hai("發")) == 3;
    }

    //確認中
    public static boolean hasWhite(ArrayList<Hai> full) {
        return Hai.countHai(full, new Hai("白")) == 3;
    }

    //確認斷么九
    public static boolean isTanyao(ArrayList<Hai> full) {
        for (int i = 0; i < full.size(); i++) {
            if (full.get(i).num == 1 || full.get(i).num == 9 || full.get(i).type == 0) {
                return false;
            }
        }
        return true;
    }

    //確認平和
    public static boolean isPinfu(int fu) {
        return fu == 20;
    }

    //確認一盃口
    public static boolean isIpe(ArrayList<Hai> full) {
        ArrayList<Hai> temps = Hai.sort(full);
        Hai[][] com = new Hai[4][3];

        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))
                        && (temps.get(i).type == 0 || temps.get(i).num == 1 || temps.get(i).num == 9)) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        int now = 0;
        for (int i = 0; i < eyes.size(); i++) {
            now = 0;
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num + 1, temp.type);
                    com[now][2] = new Hai(temp.num + 2, temp.type);
                    now++;
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                    com[now][0] = new Hai(temp.num, temp.type);
                    com[now][1] = new Hai(temp.num, temp.type);
                    com[now][2] = new Hai(temp.num, temp.type);
                    now++;
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.size() == 0) {
                for (int j = 0; j < com.length; j++) {
                    for (int k = 0; k < com.length; k++) {
                        if (j != k && com[j][0].isEqual(com[k][0]) && com[j][1].isEqual(com[k][1]) && com[j][2].isEqual(com[k][2])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //確認牌型是否已經和
    public static boolean isLegal(ArrayList<Hai> hands, Hai tin) {
        ArrayList<Hai> temps = new ArrayList<>();
        temps = Hai.sort(hands);
        temps.add(tin);
        temps = Hai.sort(temps);
        if(Hai.isKokushi(temps)){
            return true;
        }
        /*
        将手牌排序后，找到n个雀头(V)
        对于每个雀头，都执行下面的判断逻辑
        1.去除雀头，得到剩余手牌P(V)
        2.对P按照牌进行分组后排序(V)
        3.对于排序后的牌，如果是连续三张牌，则将这三张牌作为一个顺子(V)
        4.将顺子的三张牌从P中删除(V)
        5.重复2-3-4步骤，直到无顺子(V)
        6.对P进行分组(V)
        7.如果某牌数量是3，则将这三张牌作为一个刻子(V)
        8.将刻子从P中删除(V)
        重复6-7-8步骤，直到无刻子(V)
        如果P现在无牌，则表示此时能够和牌，如果有牌，表示不能和牌
         */
        ArrayList<Hai> eyes = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.size(); j++) {
                if (i != j && temps.get(i).isEqual(temps.get(j)) && !(Hai.isContains(eyes, temps.get(i)))) {
                    eyes.add(temps.get(i));
                }
            }
        }
        ArrayList<Hai> P = new ArrayList<>();
        for (int i = 0; i < eyes.size(); i++) {
            //去除雀頭
            P = Hai.removeHai(temps, eyes.get(i), 2);
            P = Hai.sort(P);
            //去除連續三數 ex:一萬二萬三萬
            int current = 0;
            int sz = P.size();
            while (P.size() != 0) {
                if (Hai.isContains(P, new Hai(P.get(current).num + 1, P.get(current).type))
                        && Hai.isContains(P, new Hai(P.get(current).num + 2, P.get(current).type)) && P.get(current).type != 0) {
                    Hai temp = P.get(current);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 1, temp.type), 1);
                    P = Hai.removeHai(P, new Hai(temp.num + 2, temp.type), 1);
                } else {
                    current++;
                }
                sz = P.size();
                if (current >= sz) {
                    break;
                }
            }
            //去除三張相同
            int current2 = 0;
            while (P.size() != 0) {
                if (Hai.countHai(P, P.get(current2)) >= 3) {
                    Hai temp = P.get(current2);
                    P = Hai.removeHai(P, new Hai(temp.num, temp.type), 4);
                } else {
                    current2++;
                }
                sz = P.size();
                if (current2 >= sz) {
                    break;
                }
            }
            if (P.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    //確認是否僅包含某種色牌
    public static int isPureType(ArrayList<Hai> a, Hai b) {
        int type = a.get(0).type;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).type != type) {
                return -1;
            }
        }
        if (b.type != type) {
            return -1;
        }
        return type;
    }

    //確認是否有包含某牌
    public static boolean isContains(ArrayList<Hai> a, Hai b) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).isEqual(b)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContains(Hai[] a, Hai b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i].isEqual(b)) {
                return true;
            }
        }
        return false;
    }

    //回傳某牌的數量
    public static int countHai(ArrayList<Hai> a, Hai b) {
        int cnt = 0;
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).isEqual(b)) {
                cnt++;
            }
        }
        return cnt;
    }

    //回傳寶牌數量
    public static int countDora(ArrayList<Hai> full, Hai[] tora) {
        int total = 0;
        for (int i = 0; i < full.size(); i++) {
            for (int j = 0; j < tora.length; j++) {
                if (full.get(i).isEqual(tora[j])) {
                    total += 1;
                }
            }
        }
        return total;
    }

    //去除某張牌
    public static ArrayList<Hai> removeHai(ArrayList<Hai> a, Hai b, int times) {
        if (!Hai.isContains(a, b)) {
            return a;
        }
        ArrayList<Hai> P = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).isEqual(b) && times != 0) {
                times -= 1;
            } else {
                P.add(a.get(i));
            }
        }
        return P;
    }

    //轉換懸賞牌->寶牌
    public static Hai[] ToraChange(Hai[] Tora) {
        Hai[] changed = new Hai[Tora.length];
        for (int i = 0; i < Tora.length; i++) {
            if (Tora[i] != null) {
                int a = 0;
                if (Tora[i].type != 0) {
                    a = Tora[i].num + 1;
                    if (a == 10) {
                        a = 1;
                    }
                } else {
                    if (Tora[i].num <= 4) {
                        a = Tora[i].num + 1;
                        if (a == 5) {
                            a = 1;
                        }
                    } else {
                        a = Tora[i].num + 1;
                        if (a == 8) {
                            a = 5;
                        }
                    }
                }
                changed[i] = new Hai(a, Tora[i].type);
            }
        }
        return changed;
    }

    public String getType() {
        String[] wind = {"空", "東風", "南風", "西風", "北風", "中", "發", "白"};
        if (type == 0) {
            return wind[num];
        } else if (type == 1) {
            return "條";
        } else if (type == 2) {
            return "筒";
        } else {
            return "萬";
        }
    }

    public void turnRed() {
        if (this.num == 5 && type != 0) {
            this.red = true;
        }
    }

    public boolean isEqual(Hai a) {
        return this.num == a.num && this.type == a.type;
    }

    @Override
    public String toString() {
        String[] word = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] wind = {"空", "東風", "南風", "西風", "北風", "中", "發", "白"};
        if (type == 0) {
            return wind[num];
        } else {
            return word[num] + this.getType();
        }
    }
}

class HaiNumSort implements Comparator<Hai> {

    @Override
    public int compare(Hai a, Hai b) {
        return a.num - b.num;
    }
}
