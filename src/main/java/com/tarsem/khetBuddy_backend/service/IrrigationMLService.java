package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.IrrigationMLRequestDTo;
import com.tarsem.khetBuddy_backend.dto.MLDayPlan;
import com.tarsem.khetBuddy_backend.dto.MLImmediateResult;
import com.tarsem.khetBuddy_backend.dto.MLScheduleResult;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
        return mapImmediateResponse(callApi("/recommend", request));
    }

    public MLScheduleResult getSchedule(IrrigationMLRequestDTo request) {
        return mapScheduleResponse(callApi("/schedule", request));
    }

    private @Nullable Map callApi(String uri, Object body) {
        try {
            return webClient.post()
                    .uri(uri)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("ML API failed: " + e.getMessage());
        }
    }

    private MLImmediateResult mapImmediateResponse(Map<String, Object> res) {
        if (res == null) throw new RuntimeException("Empty ML response");

        MLImmediateResult r = new MLImmediateResult();
        r.setIrrigateToday(bool(res, "irrigate_today"));
        r.setEmergency(bool(res, "emergency_irrigation"));
        r.setIrrigationMm(dbl(res, "irrigation_amount_mm"));
        r.setReason((String) res.getOrDefault("reason", ""));
        return r;
    }

    private MLScheduleResult mapScheduleResponse(Map<String, Object> res) {
        if (res == null) throw new RuntimeException("Empty ML response");

        MLScheduleResult r = new MLScheduleResult();
        r.setEmergency(bool(res, "emergency_irrigation"));
        r.setTotalIrrigationMm(dbl(res, "total_irrigation_planned_mm"));

        List<MLDayPlan> days = ((List<Map<String, Object>>) res.getOrDefault("schedule", List.of()))
                .stream()
                .map(d -> {
                    MLDayPlan p = new MLDayPlan();
                    p.setDay(intVal(d, "day"));
                    p.setDate((String) d.getOrDefault("date", ""));
                    p.setIrrigate(bool(d, "irrigate"));
                    p.setIrrigationMm(dbl(d, "amount_mm"));
                    p.setRainExpected(dbl(d, "rain_expected_mm"));
                    p.setReason((String) d.getOrDefault("reason", ""));
                    return p;
                })
                .toList();

        r.setDays(days);
        return r;
    }

    private double dbl(Map<String, Object> m, String k) {
        Object v = m.get(k);
        return v == null ? 0 : ((Number) v).doubleValue();
    }

    private boolean bool(Map<String, Object> m, String k) {
        Object v = m.get(k);
        return v != null && (Boolean) v;
    }

    private int intVal(Map<String, Object> m, String k) {
        Object v = m.get(k);
        return v == null ? 0 : ((Number) v).intValue();
    }
}
