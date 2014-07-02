package com.kzone.util;

import com.sun.xml.bind.v2.runtime.Coordinator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeffy on 2014/7/2 0002.
 */
public class PoiAnalysisUtil {
    public static Map<String, Double> analysis(String poiString) {
        Map<String, Double> coordinateFrame = new HashMap<String, Double>();

        int digi=16;
        int add= 10;
        int plus=7;
        int cha=36;
        int I = -1;
        int H = 0;
        String B = "";
        int J = poiString.length();
        int G = poiString.charAt(J - 1);
        poiString = poiString.substring(0, J - 1);
        J--;
        for (int E = 0; E < J; E++) {
            int D = Integer.parseInt(String.valueOf(poiString.charAt(E)), cha) - add;
            if (D >= add) {
                D = D - plus;
            }
            B += Integer.toString(D, cha);
            if (D > H) {
                I = E;
                H = D;
            }
        }
        int A = Integer.parseInt(B.substring(0, I), digi);
        int F = Integer.parseInt(B.substring(I + 1), digi);
        double L = (A + F - Integer.parseInt(String.valueOf(G))) / 2;
        double K = (F - L) / 100000;
        L /= 100000;

        coordinateFrame.put("lat", K);
        coordinateFrame.put("lng", L);
        return coordinateFrame;
    }

    public static void main(String[] args) {
        Map<String, Double> map = analysis("HHDFJGZVVIHIJG");
        System.out.println(map.get("lat"));
        System.out.println(map.get("lng"));
    }
}
