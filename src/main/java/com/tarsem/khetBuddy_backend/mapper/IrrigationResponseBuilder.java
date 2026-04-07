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
        int cycles = calculateCycles(hours);

        IrrigationAdviceDTO dto = new IrrigationAdviceDTO();
        dto.setIrrigateToday(ml.isIrrigateToday());
        dto.setHoursRequired(round(hours));
        dto.setCycles(cycles);

        dto.setMessage(
                ml.isIrrigateToday()
                        ? (ml.isEmergency()
                        ? "Urgent irrigation needed. Run " + round(hours) + " hrs (~" + cycles + " cycles)."
                        : "Irrigate " + round(hours) + " hrs (~" + cycles + " cycles). " + ml.getReason())
                        : "No irrigation needed. " + ml.getReason()
        );

        return dto;
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
            int cycles = calculateCycles(hours);

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
                p.setAction("Irrigate");
                p.setHours(round(hours));
                p.setCycles(cycles);
                p.setNote("Irrigate " + round(hours) + " hrs");

                totalHours += hours;
                irrigationDays++;
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

        double hours = (mm * areaM2) / flowRate;

        if (hours > 24) {
            throw new IllegalArgumentException("Unrealistic irrigation hours: " + hours);
        }

        return hours;
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