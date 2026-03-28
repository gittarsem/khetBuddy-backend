package com.tarsem.khetBuddy_backend.mapper;

import com.tarsem.khetBuddy_backend.dto.farmer.FarmerDayPlan;
import com.tarsem.khetBuddy_backend.dto.irrigation.*;
import com.tarsem.khetBuddy_backend.entity.Farm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IrrigationResponseBuilder {

    private static final double HOURS_PER_CYCLE = 2.5;
    private static final double RAIN_SKIP_THRESHOLD = 5.0;

    public IrrigationAdviceDTO buildImmediateResponse(IrrigationPlanRequestDTO req, MLImmediateResult ml, Farm farm) {

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

        FarmerScheduleResponse res = new FarmerScheduleResponse();
        res.setPlan(plan);
        res.setSummary(new SummaryDTO(
                ml.getDays().size(),
                irrigationDays,
                round(totalHours),
                "14-day irrigation plan based on weather and crop needs"
        ));

        return res;
    }

    private double calculateHours(double mm, Farm farm, IrrigationPlanRequestDTO req) {

        String unit = normalize(req.getField_unit(), "ACRE");
        String pump = normalize(req.getPump_type(), "MEDIUM");

        double area = switch (unit) {
            case "HECTARE" -> farm.getTotalLand() * 10000;
            case "ACRE" -> farm.getTotalLand() * 4047;
            default -> farm.getTotalLand();
        };

        double flow = switch (pump) {
            case "SMALL" -> 10000;
            case "LARGE" -> 40000;
            default -> 20000;
        };

        return (mm * area) / flow;
    }

    private int calculateCycles(double hours) {
        return (int) Math.ceil(hours / HOURS_PER_CYCLE);
    }

    private String normalize(String v, String def) {
        return v == null ? def : v.trim().toUpperCase();
    }

    private double round(double v) {
        return Math.round(v * 10.0) / 10.0;
    }
}