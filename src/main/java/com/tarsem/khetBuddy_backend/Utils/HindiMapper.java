package com.tarsem.khetBuddy_backend.Utils;


import java.util.Map;

public class HindiMapper {

    private static final Map<String, String> cropMap = Map.of(
            "Maize", "मक्का",
            "Wheat", "गेहूं",
            "Rice", "धान"
    );

    private static final Map<String, String> stateMap = Map.of(
            "Punjab", "पंजाब",
            "Himachal Pradesh", "हिमाचल प्रदेश"
    );

    private static final Map<String, String> districtMap = Map.ofEntries(

            // Punjab Districts
            Map.entry("Amritsar", "अमृतसर"),
            Map.entry("Ludhiana", "लुधियाना"),
            Map.entry("Patiala", "पटियाला"),
            Map.entry("Jalandhar", "जालंधर"),
            Map.entry("Bathinda", "बठिंडा"),
            Map.entry("Mohali", "मोहाली"),
            Map.entry("Sangrur", "संगरूर"),
            Map.entry("Firozpur", "फिरोजपुर"),
            Map.entry("Gurdaspur", "गुरदासपुर"),
            Map.entry("Hoshiarpur", "होशियारपुर"),

            // Himachal Pradesh
            Map.entry("Una", "ऊना")

    );

    public static String crop(String crop) {
        return cropMap.getOrDefault(crop, crop);
    }

    public static String state(String state) {
        return stateMap.getOrDefault(state, state);
    }

    public static String district(String district) {
        return districtMap.getOrDefault(district, district);
    }

    public static String location(String district) {
        return district(district);
    }
}