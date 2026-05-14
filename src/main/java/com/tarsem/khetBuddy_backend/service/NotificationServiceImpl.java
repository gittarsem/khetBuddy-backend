package com.tarsem.khetBuddy_backend.service;


import com.tarsem.khetBuddy_backend.Utils.HindiMapper;
import com.tarsem.khetBuddy_backend.client.WhatsAppClient;
import com.tarsem.khetBuddy_backend.dto.yield.YieldMlResponse;
import com.tarsem.khetBuddy_backend.entity.Farm;
import com.tarsem.khetBuddy_backend.entity.FarmerDetails;
import com.tarsem.khetBuddy_backend.service.Interfaces.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final WhatsAppClient client;

    public NotificationServiceImpl(WhatsAppClient client) {
        this.client = client;
    }

    @Override
    public void sendWelcomeFarmer(FarmerDetails farmer) {
        client.sendTemplate(
                farmer.getPhoneNo(),
                "khetbuddy_welcome_farmer_hi",
                List.of(
                        farmer.getFirstName()
                )
        );
    }
    @Override
    public void sendWelcomeFarm(Farm farm) {
        String location=HindiMapper.location(
                farm.getDistrict()
        );

        client.sendTemplate(
                farm.getUserEntity().getFarmerDetails().getPhoneNo(),
                "khetbuddy_welcome_farm_hi",
                List.of(
                        farm.getUserEntity().getFarmerDetails().getFirstName(),
                        location
                )
        );
    }

    @Override
    public void sendYield(Farm farm, YieldMlResponse response) {
        String crop = HindiMapper.crop(response.getCropType());

        String location = HindiMapper.location(
                farm.getDistrict()

        );

        String expected = String.valueOf(response.getYieldPerHectare().getExpected());

        String range = response.getYieldPerHectare().getLower()
                + "–" +
                response.getYieldPerHectare().getHigher();

        client.sendTemplate(
                farm.getUserEntity().getFarmerDetails().getFirstName(),
                "yield_prediction_hi",
                List.of(
                        farm.getUserEntity().getFarmerDetails().getFirstName(),
                        crop,
                        location,
                        expected,
                        range
                )
        );
    }
}