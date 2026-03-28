package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.exception.UnAuthorisedException;
import com.tarsem.khetBuddy_backend.dto.farmer.FarmerProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.farmer.FarmerUpdateProfileRequestDTO;
import com.tarsem.khetBuddy_backend.dto.farmer.FarmerUpdateProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.farmer.ProfilePicDTO;
import com.tarsem.khetBuddy_backend.entity.FarmerDetails;
import com.tarsem.khetBuddy_backend.entity.UserEntity;
import com.tarsem.khetBuddy_backend.repo.FarmerDetailsRepo;
import com.tarsem.khetBuddy_backend.service.Interfaces.FarmerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.tarsem.khetBuddy_backend.Utils.AppUtils.giveMeCurrentUser;


@Service
@Slf4j
@AllArgsConstructor
public class FarmerServiceImpl implements FarmerService {
   private final ModelMapper modelMapper;
   private final FarmerDetailsRepo farmerDetailsRepo;
    @Override
    public FarmerUpdateProfileResponseDTO farmerDetailsUpdate(FarmerUpdateProfileRequestDTO requestDTO, String imageUrl) {
        UserEntity user=giveMeCurrentUser();
        if(user==null) throw new RuntimeException("User not Exist");
        FarmerDetails farmer=modelMapper.map(requestDTO,FarmerDetails.class);
        farmer.setProfileImage(imageUrl);
        farmer.setUserEntity(user);
        farmerDetailsRepo.save(farmer);
        return modelMapper.map(farmer, FarmerUpdateProfileResponseDTO.class);

    }

    @Override
    public ProfilePicDTO getProfileImage()  {
        UserEntity user=giveMeCurrentUser();
        FarmerDetails farmerDetails=farmerDetailsRepo.findByUserEntity(user);
        if(farmerDetails.getUserEntity()==null ||
                !user.getId().equals(farmerDetails.getUserEntity().getId())){
            throw new UnAuthorisedException("Farmer does not have access");
        }
        return new ProfilePicDTO(
                farmerDetails.getFirstName(), farmerDetails.getProfileImage()
        );
    }

    @Override
    @Transactional
    public ProfilePicDTO updateProfilePic(String imageUrl) {
        UserEntity user=giveMeCurrentUser();
        FarmerDetails farmerDetails=farmerDetailsRepo.findByUserEntity(user);
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
    @Transactional
    public FarmerUpdateProfileResponseDTO farmerUpdateDetailsUpdate(FarmerUpdateProfileRequestDTO dto) {
        UserEntity user=giveMeCurrentUser();
        FarmerDetails farmer = farmerDetailsRepo.findByUserEntity(user);
        if (dto.getFirstName() != null) {
            farmer.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            farmer.setLastName(dto.getLastName());
        }

        if (dto.getPhoneNo() != null) {
            farmer.setPhoneNo(dto.getPhoneNo());
        }
        farmerDetailsRepo.save(farmer);
        return modelMapper.map(farmer, FarmerUpdateProfileResponseDTO.class);
    }

    @Override
    public @Nullable FarmerProfileResponseDTO getFarmerDetails() {
        UserEntity user=giveMeCurrentUser();
        FarmerDetails farmerDetails= farmerDetailsRepo.findByUserEntity(user);
        FarmerProfileResponseDTO farmerDTO=modelMapper.map(farmerDetails, FarmerProfileResponseDTO.class);
        farmerDTO.setEmail(user.getUsername());
        return farmerDTO;
    }


}

