package com.trunggame.security.services.impl;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.PackageDTO;
import com.trunggame.models.*;
import com.trunggame.models.Package;
import com.trunggame.repository.*;
import com.trunggame.repository.impl.PackageRepositoryImpl;
import com.trunggame.security.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryGroupRepository countryGroupRepository;

    @Autowired
    private PackageRepository gamePackageRepository;

    @Autowired
    private PackageRepositoryImpl packageRepositoryImpl;

    @Override
    @Transactional
    public BaseResponseDTO<?> createPackage(PackageDTO input) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var currentUser = userRepository.findByUsername(userDetails.getUsername());

        validateInput(input);

        var file = fileRepository.findFirstByUniqId(input.getImageId());

        // Create game builder
        var gamePackage = Package.builder()
                .name(input.getName())
                .unit(input.getUnit())
                .price(input.getPrice())
                .rating(input.getRating())
                .attribute(input.getAttribute())
                .warehouseQuantity(input.getWarehouseQuantity())
                .descriptionVi(input.getDescriptionVi())
                .descriptionEn(input.getDescriptionEn())
                .gameId(input.getGameId())
                .status(Package.Status.ACTIVE)
                .topSale(Package.TopSaleStatus.INACTIVE)
                .imageId(input.getImageId())
                .tradeCount(input.getTradeCount())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        var packageEntity = gamePackageRepository.save(gamePackage);
        packageEntity.setPreviewUrl(file.get().getPreviewUrl());

        file.get().setOwnerId(packageEntity.getId());
        fileRepository.save(file.get());


        // Save game server group
        if (input.getServer().size() > 0) {
            List<CountryGroup> countryGroups = new ArrayList<>();
            for (var gs : input.getServer()) {
                countryGroups.add(CountryGroup.builder().gameId(packageEntity.getGameId()).packageId(packageEntity.getId()).name(gs.getName()).createdAt(LocalDateTime.now()).build());
            }

            countryGroupRepository.saveAll(countryGroups);

        }

        return new BaseResponseDTO<>("Success", 200, 200, packageEntity);

    }

    @Override
    public BaseResponseDTO<?> updatePackage(PackageDTO input) {
        Optional<Package> oldPackage = gamePackageRepository.findById(input.getId());

        Package updatedPackage = oldPackage.get();
        updatedPackage.setName(input.getName());
        updatedPackage.setPrice(input.getPrice());
        updatedPackage.setUnit(input.getUnit());
        updatedPackage.setImageId(input.getImageId());
        updatedPackage.setRating(input.getRating());
        updatedPackage.setServerGroup(input.getServerGroup());
        updatedPackage.setAttribute(input.getAttribute());
        updatedPackage.setWarehouseQuantity(input.getWarehouseQuantity());
        updatedPackage.setTradeCount(input.getTradeCount());
        updatedPackage.setDescriptionVi(input.getDescriptionVi());
        updatedPackage.setDescriptionEn(input.getDescriptionEn());
        updatedPackage.setDeliveryTime(input.getDeliveryTime());
        updatedPackage.setGameId(input.getGameId());
        updatedPackage.setTradeCount(input.getTradeCount());
        updatedPackage.setTopSale(Objects.equals(input.getTopSale(), "ACTIVE") ? Package.TopSaleStatus.ACTIVE: Package.TopSaleStatus.INACTIVE);
        gamePackageRepository.save(updatedPackage);

        List<CountryGroup> gameServerOld;
        gameServerOld = countryGroupRepository.findAllByPackageId(updatedPackage.getId());
        for (var gs : gameServerOld) {
            countryGroupRepository.deleteById(gs.getId());
        }

        // Save game server group
        if (input.getServer().size() > 0) {
            List<CountryGroup> gameServerNew = new ArrayList<>();
            for (var gs : input.getServer()) {
                gameServerNew.add(CountryGroup.builder().gameId(updatedPackage.getGameId()).packageId(updatedPackage.getId()).name(gs.getName()).createdAt(LocalDateTime.now()).build());
            }
            countryGroupRepository.saveAll(gameServerNew);
        }

        return new BaseResponseDTO<>("Success", 200, 200, null);
    }

    @Override
    public List<PackageDTO> getAllPackage() {
        return this.packageRepositoryImpl.getAllPackage();
    }

    private void validateInput(PackageDTO input) {
        if (Objects.isNull(input)) {
            throw new RuntimeException("Input to create game is null");
        }

        var categoryOPT = productRepository.findById(input.getGameId());
        if (categoryOPT.isEmpty()) {
            throw new RuntimeException("Game does exist");
        }

        var imageOTP = fileRepository.findFirstByUniqId(input.getImageId());

        if (imageOTP.isEmpty()) {
            throw new RuntimeException("Image's id  does exist");
        }
    }
}
