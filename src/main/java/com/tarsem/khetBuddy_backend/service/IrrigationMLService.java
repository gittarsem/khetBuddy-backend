package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.IrrigationMLRequestDTo;
import com.tarsem.khetBuddy_backend.dto.MLDayPlan;
import com.tarsem.khetBuddy_backend.dto.MLImmediateResult;
import com.tarsem.khetBuddy_backend.dto.MLScheduleResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class IrrigationMLService {

    private final WebClient webClient;

    public IrrigationMLService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://irrigation-recommendation-api.onrender.com")
                .build();
    }

    public MLImmediateResult getImmediateRecommendation(IrrigationMLRequestDTo request) {
        try {
            @Nullable Map response = webClient.post()
                    .uri("/recommend")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return mapImmediateResponse(response);

        } catch (Exception e) {
            throw new RuntimeException("ML recommend API failed: " + e.getMessage());
        }
    }

    public MLScheduleResult getSchedule(IrrigationMLRequestDTo request) {
        try {
            @Nullable Map response = webClient.post()
                    .uri("/schedule")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return mapScheduleResponse(response);

        } catch (Exception e) {
            throw new RuntimeException("ML schedule API failed: " + e.getMessage());
        }
    }

    private MLImmediateResult mapImmediateResponse(Map<String, Object> res) {

        if (res == null) {
            throw new RuntimeException("Empty response from ML recommend API");
        }

        MLImmediateResult result = new MLImmediateResult();

        result.setIrrigateToday(getBoolean(res, "irrigate_today"));
        result.setEmergency(getBoolean(res, "emergency_irrigation"));
        result.setIrrigationMm(getDouble(res, "irrigation_amount_mm"));
        result.setReason((String) res.getOrDefault("reason", "No reason provided"));

        return result;
    }

    // =========================
    // 🔄 MAPPING: SCHEDULE
    // =========================
    private MLScheduleResult mapScheduleResponse(Map<String, Object> res) {

        if (res == null) {
            throw new RuntimeException("Empty response from ML schedule API");
        }

        MLScheduleResult result = new MLScheduleResult();

        result.setEmergency(getBoolean(res, "emergency_irrigation"));
        result.setTotalIrrigationMm(getDouble(res, "total_irrigation_planned_mm"));

        List<MLDayPlan> days = new ArrayList<>();

        List<Map<String, Object>> scheduleList =
                (List<Map<String, Object>>) res.get("schedule");

        if (scheduleList != null) {
            for (Map<String, Object> d : scheduleList) {

                MLDayPlan day = new MLDayPlan();

                day.setDay(getInt(d, "day"));
                day.setDate((String) d.getOrDefault("date", ""));

                day.setIrrigate(getBoolean(d, "irrigate"));
                day.setIrrigationMm(getDouble(d, "amount_mm"));
                day.setRainExpected(getDouble(d, "rain_expected_mm"));
                day.setReason((String) d.getOrDefault("reason", ""));

                days.add(day);
            }
        }

        result.setDays(days);

        return result;
    }

    // =========================
    // 🧰 SAFE PARSING HELPERS
    // =========================
    private double getDouble(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? ((Number) val).doubleValue() : 0.0;
    }

    private boolean getBoolean(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null && (Boolean) val;
    }

    private int getInt(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? ((Number) val).intValue() : 0;
    }
}
