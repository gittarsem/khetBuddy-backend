package com.tarsem.khetBuddy_backend.Utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class IrrigationUtility {
    public static double calculateLastWeekIrrigationMm(
            double dailyAvgHours,
            double fieldSize,
            String fieldUnit,
            String pumpType
    ) {

        double weeklyHours = dailyAvgHours * 7;

        double areaM2;
        switch (fieldUnit) {
            case "ACRE": areaM2 = fieldSize * 4047; break;
            case "HECTARE": areaM2 = fieldSize * 10000; break;
            default: areaM2 = fieldSize;
        }

        double flowRate;
        switch (pumpType) {
            case "SMALL": flowRate = 10000; break;
            case "LARGE": flowRate = 40000; break;
            default: flowRate = 20000;
        }

        double totalLiters = weeklyHours * flowRate;

        return totalLiters / areaM2;
    }

    public static String getCropStage(String crop, LocalDate sowingDate) {

        if (sowingDate == null) {
            throw new IllegalArgumentException("Sowing date cannot be null");
        }

        long days = ChronoUnit.DAYS.between(sowingDate, LocalDate.now());

        if (days < 0) {
            throw new IllegalArgumentException("Sowing date cannot be in future");
        }

        crop = crop.toLowerCase();

        // helper lambda
        java.util.function.Function<long[], String> stageResolver = (ranges) -> {
            if (days <= ranges[0]) return "Germination";
            if (days <= ranges[1]) return "Vegetative";
            if (days <= ranges[2]) return "Flowering";
            if (days <= ranges[3]) return "Grain Filling";
            return "Maturity";
        };

        switch (crop) {

            case "maize":
                return stageResolver.apply(new long[]{10, 30, 60, 90});

            case "rice":
                if (days <= 15) return "Nursery";
                if (days <= 45) return "Vegetative";
                if (days <= 75) return "Reproductive";
                if (days <= 110) return "Ripening";
                return "Maturity";

            case "wheat":
                if (days <= 15) return "Germination";
                if (days <= 40) return "Tillering";
                if (days <= 70) return "Stem Elongation";
                if (days <= 100) return "Heading";
                if (days <= 120) return "Ripening";
                return "Maturity";

            default:
                return "Vegetative";
        }
    }
}
