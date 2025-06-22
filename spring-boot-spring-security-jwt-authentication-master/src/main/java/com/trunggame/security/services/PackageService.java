package com.trunggame.security.services;


import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.PackageDTO;

import java.util.List;

public interface PackageService {

     BaseResponseDTO<?> createPackage(PackageDTO input);
     BaseResponseDTO<?> updatePackage(PackageDTO input);
     public List<PackageDTO> getAllPackage();
}
