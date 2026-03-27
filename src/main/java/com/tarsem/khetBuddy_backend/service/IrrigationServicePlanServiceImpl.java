package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.Exception.ResourceNotFoundException;
import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.repo.FarmRepo;

import com.tarsem.khetBuddy_backend.service.Interfaces.IrrigationPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tarsem.khetBuddy_backend.Utils.IrrigationUtility.calculateLastWeekIrrigationMm;
import static com.tarsem.khetBuddy_backend.Utils.IrrigationUtility.getCropStage;

@Service
@Slf4j
public class IrrigationServicePlanServiceImpl implements IrrigationPlanService {

    @Autowired
    public FarmRepo farmRepo;

    @Autowired
    public IrrigationMLService mlService;

    @Autowired
    public IrrigationUserResponseService userResponseService;

    @Override
    public IrrigationAdviceDTO getImmediatePlan(Long farmId, IrrigationPlanRequestDTO requestDTO) {
        Farm farm=farmRepo.findById(farmId).orElseThrow(
                ()->new ResourceNotFoundException("Farm with this id does not exist")
        );
        farm.setSowing_date(requestDTO.getSowing_date());
        farmRepo.save(farm);
        IrrigationMLRequestDTo mlrequestDTO=new IrrigationMLRequestDTo();
        mlrequestDTO.setCrop(farm.getCrop());
        mlrequestDTO.setDistrict(farm.getDistrict());
        mlrequestDTO.setStage(getCropStage(farm.getCrop(),requestDTO.getSowing_date()));
        mlrequestDTO.setLastIrrigationDay(requestDTO.getLastIrrigationDay());
        mlrequestDTO.setLast_week_irrigation_mm(
                calculateLastWeekIrrigationMm(
                        requestDTO.getDaily_avg(), farm.getTotalLand(),
                        requestDTO.getField_unit(),requestDTO.getPump_type()
                )
        );
        MLImmediateResult mlresult=mlService.getImmediateRecommendation(mlrequestDTO);
        return userResponseService.buildImmediateResponse(requestDTO,mlresult,farm);

    }

    @Override
    public FarmerScheduleResponse generateSchedule(Long farmId, IrrigationPlanRequestDTO requestDTO) {
        Farm farm=farmRepo.findById(farmId).orElseThrow(
                ()->new ResourceNotFoundException("Farm with this id does not exist")
        );
        farm.setSowing_date(requestDTO.getSowing_date());
        farmRepo.save(farm);
        IrrigationMLRequestDTo mlrequestDTO=new IrrigationMLRequestDTo();
        mlrequestDTO.setCrop(farm.getCrop());
        mlrequestDTO.setDistrict(farm.getDistrict());
        mlrequestDTO.setStage(getCropStage(farm.getCrop(),requestDTO.getSowing_date()));
        mlrequestDTO.setLastIrrigationDay(requestDTO.getLastIrrigationDay());
        mlrequestDTO.setLast_week_irrigation_mm(
                calculateLastWeekIrrigationMm(
                        requestDTO.getDaily_avg(), farm.getTotalLand(),
                        requestDTO.getField_unit(),requestDTO.getPump_type()
                )
        );
        MLScheduleResult mlresult=mlService.getSchedule((mlrequestDTO));
        return userResponseService.buildFarmerSchedule(requestDTO,mlresult,farm);

    }
}
