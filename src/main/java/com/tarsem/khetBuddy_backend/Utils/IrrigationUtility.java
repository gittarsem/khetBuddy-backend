package com.tarsem.khetBuddy_backend.Utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class IrrigationUtility {
    public static int calculateLastWeekIrrigationMm(
            double dailyAvgHours,
            double fieldSize,
            String fieldUnit,
            String pumpType
    ) {
        double weeklyHours = dailyAvgHours * 7;

// Normalize inputs
        String unit = fieldUnit == null ? "" : fieldUnit.trim().toUpperCase();
        String pump = pumpType == null ? "" : pumpType.trim().toUpperCase();

        double areaM2;
        switch (unit) {
            case "ACRE": areaM2 = fieldSize * 4047; break;
            case "HECTARE": areaM2 = fieldSize * 10000; break;
            case "M2":
            case "SQM":
            case "SQUARE_METER": areaM2 = fieldSize; break;
            default: throw new IllegalArgumentException("Invalid field unit: " + fieldUnit);
        }

        double flowRate = switch (pump) {
            case "SMALL" -> 5000;
            case "LARGE" -> 20000;
            case "MEDIUM" -> 10000;
            default -> throw new IllegalArgumentException("Invalid pump type: " + pumpType);
        };

        double totalLiters = weeklyHours * flowRate;
        double mm = totalLiters / areaM2;

        if (mm > 150) {
            throw new IllegalArgumentException("Unrealistic irrigation value: " + mm);
        }

        return (int)mm;
    }

    public static String getCropStage(String crop, LocalDate sowingDate) {

        if (sowingDate == null) {
            throw new IllegalArgumentException("Sowing date cannot be null");
        }

        long days = ChronoUnit.DAYS.between(sowingDate, LocalDate.now());

        if (days < 0) {
            throw new IllegalArgumentException("Sowing date cannot be in future");
        }

        crop = crop == null ? "" : crop.trim().toLowerCase();

        // Unified stage resolver (ONLY allowed stages)
        java.util.function.Function<long[], String> resolveStage = (r) -> {
            if (days <= r[0]) return "Seedling";
            if (days <= r[1]) return "Vegetative";
            if (days <= r[2]) return "Flowering";
            return "Ripening";
        };

        switch (crop) {

            case "maize":
                return resolveStage.apply(new long[]{10, 30, 60});

            case "rice":
                return resolveStage.apply(new long[]{15, 45, 75});

            case "wheat":
                return resolveStage.apply(new long[]{15, 40, 90});

            default:
                return "Vegetative";
        }
    }
}
