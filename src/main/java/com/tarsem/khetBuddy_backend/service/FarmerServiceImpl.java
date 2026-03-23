package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.Exception.ResourceNotFoundException;
import com.tarsem.khetBuddy_backend.Exception.UnAuthorisedException;
import com.tarsem.khetBuddy_backend.dto.FarmerUpdateProfileRequestDTO;
import com.tarsem.khetBuddy_backend.dto.FarmerUpdateProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.ProfilePicDTO;
import com.tarsem.khetBuddy_backend.model.FarmerDetails;
import com.tarsem.khetBuddy_backend.model.UserEntity;
import com.tarsem.khetBuddy_backend.model.UserPrincipal;
import com.tarsem.khetBuddy_backend.repo.FarmerDetailsRepo;
import com.tarsem.khetBuddy_backend.service.Interfaces.FarmerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class FarmerServiceImpl implements FarmerService {
   private final ModelMapper modelMapper;
   private final FarmerDetailsRepo farmerDetailsRepo;
    @Override
    public FarmerUpdateProfileResponseDTO farmerDetailsUpdate(FarmerUpdateProfileRequestDTO requestDTO, String imageUrl) {
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user=userPrincipal.getUser();
        if(user==null) throw new RuntimeException("User not Exist");
        FarmerDetails farmer=modelMapper.map(requestDTO,FarmerDetails.class);
        farmer.setProfileImage(imageUrl);
        farmer.setUserEntity(user);
        farmerDetailsRepo.save(farmer);
        return modelMapper.map(farmer, FarmerUpdateProfileResponseDTO.class);

    }

    @Override
    public ProfilePicDTO getProfileImage(Long farmerId)  {
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user=userPrincipal.getUser();
        FarmerDetails farmerDetails=farmerDetailsRepo.findById(farmerId).orElseThrow(
                ()-> new ResourceNotFoundException("Farmer does not exist")
        );
        if(farmerDetails.getUserEntity()==null ||
                !user.getId().equals(farmerDetails.getUserEntity().getId())){
            throw new UnAuthorisedException("Farmer does not have access");
        }
        return new ProfilePicDTO(
                farmerDetails.getFirstName(), farmerDetails.getProfileImage()
        );
    }

    @Override
    public ProfilePicDTO updateProfilePic(Long farmerId, String imageUrl) {
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user=userPrincipal.getUser();
        FarmerDetails farmerDetails=farmerDetailsRepo.findById(farmerId).orElseThrow(
                ()-> new ResourceNotFoundException("Farmer does not exist")
        );
        if(farmerDetails.getUserEntity()==null ||
                !user.getId().equals(farmerDetails.getUserEntity().getId())){
            throw new UnAuthorisedException("Farmer does not have access");
        }

        farmerDetails.setProfileImage(imageUrl);
        farmerDetailsRepo.save(farmerDetails);
        return new ProfilePicDTO(
                farmerDetails.getFirstName(), farmerDetails.getProfileImage()
        );
    }

    @Override
    public FarmerUpdateProfileResponseDTO farmerUpdateDetailsUpdate(FarmerUpdateProfileRequestDTO requestDTO) {
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user=userPrincipal.getUser();
        if(user==null) throw new RuntimeException("User not Exist");
        FarmerDetails farmer=modelMapper.map(requestDTO,FarmerDetails.class);
        farmer.setUserEntity(user);
        farmerDetailsRepo.save(farmer);
        return modelMapper.map(farmer, FarmerUpdateProfileResponseDTO.class);

    }


}

