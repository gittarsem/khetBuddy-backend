package com.tarsem.khetBuddy_backend.mapper;

import com.tarsem.khetBuddy_backend.dto.farmer.FarmerDayPlan;
import com.tarsem.khetBuddy_backend.dto.irrigation.*;
import com.tarsem.khetBuddy_backend.entity.Farm;
import com.tarsem.khetBuddy_backend.enums.FieldUnit;
import com.tarsem.khetBuddy_backend.enums.PumpType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IrrigationResponseBuilder {

    private static final double HOURS_PER_CYCLE = 2.5;
    private static final double RAIN_SKIP_THRESHOLD = 5.0;

    public IrrigationAdviceDTO buildImmediateResponse(
            IrrigationPlanRequestDTO req,
            MLImmediateResult ml,
            Farm farm
    ) {

        validateInputs(req, farm, ml.getIrrigationMm());

        double hours = calculateHours(ml.getIrrigationMm(), farm, req);
        if (hours > 24) {
            int daysRequired = (int) Math.ceil(hours / 24.0);

            return IrrigationAdviceDTO.builder()
                    .irrigateToday(true)
                    .hoursRequired(round(24))
                    .cycles(calculateCycles(24))
                    .message(
                            "Required irrigation exceeds 24 hours (" + round(hours) + " hrs). " +
                                    "Split irrigation over " + daysRequired + " days or upgrade pump to " +
                                    suggestPumpUpgrade(PumpType.from(req.getPump_type())) + "."
                    )
                    .build();
        }

        int cycles = calculateCycles(hours);

        return IrrigationAdviceDTO.builder()
                .irrigateToday(ml.isIrrigateToday())
                .hoursRequired(round(hours))
                .cycles(cycles)
                .message(
                        ml.isIrrigateToday()
                                ? (ml.isEmergency()
                                ? "Urgent irrigation needed. Run " + round(hours) + " hrs (~" + cycles + " cycles)."
                                : "Irrigate " + round(hours) + " hrs (~" + cycles + " cycles). " + ml.getReason())
                                : "No irrigation needed. " + ml.getReason()
                )
                .build();
    }
    public FarmerScheduleResponse buildFarmerSchedule(
            IrrigationPlanRequestDTO req,
            MLScheduleResult ml,
            Farm farm
    ) {

        validateInputs(req, farm, 0);

        List<FarmerDayPlan> plan = new ArrayList<>();
        double totalHours = 0;
        int irrigationDays = 0;

        for (MLDayPlan d : ml.getDays()) {

            double hours = calculateHours(d.getIrrigationMm(), farm, req);

            FarmerDayPlan p = new FarmerDayPlan();
            p.setDay(d.getDay());
            p.setDate(d.getDate());

            if (d.getRainExpected() > RAIN_SKIP_THRESHOLD) {
                p.setAction("Skip");
                p.setNote("Rain expected (" + round(d.getRainExpected()) + " mm)");
            } else if (!d.isIrrigate()) {
                p.setAction("Skip");
                p.setNote("Rest day");
            } else {
                if (hours > 24) {
                    int daysRequired = (int) Math.ceil(hours / 24.0);

                    p.setAction("Split Irrigation");
                    p.setHours(round(24));
                    p.setCycles(calculateCycles(24));
                    p.setNote("Exceeds daily limit. Spread over " + daysRequired + " days.");

                } else {
                    int cycles = calculateCycles(hours);

                    p.setAction("Irrigate");
                    p.setHours(round(hours));
                    p.setCycles(cycles);
                    p.setNote("Irrigate " + round(hours) + " hrs");

                    totalHours += hours;
                    irrigationDays++;
                }
            }

            plan.add(p);
        }

        return new FarmerScheduleResponse(
                new SummaryDTO(
                        ml.getDays().size(),
                        irrigationDays,
                        round(totalHours),
                        "14-day irrigation plan based on weather and crop needs"
                ),
                plan
        );
    }
    private double calculateHours(double mm, Farm farm, IrrigationPlanRequestDTO req) {

        if (mm < 0) throw new IllegalArgumentException("Irrigation mm cannot be negative");

        FieldUnit unit = FieldUnit.from(req.getField_unit());
        PumpType pump = PumpType.from(req.getPump_type());

        double areaM2 = convertToM2(farm.getTotalLand(), unit);
        double flowRate = getFlowRate(pump);

        if (areaM2 <= 0) throw new IllegalArgumentException("Invalid farm area");
        if (flowRate <= 0) throw new IllegalArgumentException("Invalid pump flow rate");

        return (mm * areaM2) / flowRate;
    }
    private double convertToM2(double land, FieldUnit unit) {
        return switch (unit) {
            case HECTARE -> land * 10000;
            case ACRE -> land * 4047;
            case M2 -> land;
        };
    }

    private double getFlowRate(PumpType pump) {
        return switch (pump) {
            case SMALL -> 5000;
            case MEDIUM -> 10000;
            case LARGE -> 20000;
        };
    }

    private int calculateCycles(double hours) {
        return (int) Math.ceil(hours / HOURS_PER_CYCLE);
    }

    private double round(double v) {
        return Math.round(v * 10.0) / 10.0;
    }

    private String suggestPumpUpgrade(PumpType current) {
        return switch (current) {
            case SMALL -> "MEDIUM or LARGE";
            case MEDIUM -> "LARGE";
            case LARGE -> "maximum capacity already";
        };
    }
    private void validateInputs(IrrigationPlanRequestDTO req, Farm farm, double mm) {

        if (farm == null) throw new IllegalArgumentException("Farm cannot be null");
        if (req == null) throw new IllegalArgumentException("Request cannot be null");

        if (farm.getTotalLand() <= 0) {
            throw new IllegalArgumentException("Invalid farm land size");
        }

        if (req.getDaily_avg() < 0 || req.getDaily_avg() > 12) {
            throw new IllegalArgumentException("Daily average must be between 0 and 12");
        }

        if (mm > 150) {
            throw new IllegalArgumentException("Unrealistic irrigation mm: " + mm);
        }
    }
}