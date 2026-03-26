package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.model.Farm;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IrrigationUserResponseService {


    public IrrigationAdviceDTO buildImmediateResponse(IrrigationPlanRequestDTO req,MLImmediateResult ml, Farm farm) {

        double hours = calculateHours(ml.getIrrigationMm(), farm,req);
        int cycles = (int) Math.ceil(hours);

        IrrigationAdviceDTO dto = new IrrigationAdviceDTO();
        dto.setIrrigateToday(ml.isIrrigateToday());
        dto.setHoursRequired(round(hours));
        dto.setCycles(cycles);

        dto.setMessage(
                ml.isIrrigateToday()
                        ? (ml.isEmergency()
                        ? "Urgent irrigation needed. Run for " + round(hours) + " hours (" + cycles + " cycles)."
                        : "Irrigate for " + round(hours) + " hours (" + cycles + " cycles). " + ml.getReason())
                        : "No irrigation needed today. " + ml.getReason()
        );

        return dto;
    }


    private double calculateHours(double mm, Farm farm, IrrigationPlanRequestDTO req) {

        double area = req.getField_unit().equals("HECTARE")
                ? farm.getTotalLand() * 10000
                : req.getField_unit().equals("ACRE")
                ? farm.getTotalLand() * 4047
                : farm.getTotalLand();

        double flow = req.getPump_type().equals("SMALL")
                ? 10000
                : req.getPump_type().equals("LARGE")
                ? 40000
                : 20000;

        return (mm * area) / flow;
    }

    private double round(double v) {
        return Math.round(v * 10.0) / 10.0;
    }
}