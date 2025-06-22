package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.models.Brand;
import com.trunggame.security.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/companies")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public BaseResponseDTO<?> getAllCompanies() {
        return new BaseResponseDTO<>("Success", 200,200,brandService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id);
        if (brand != null) {
            return new ResponseEntity<>(brand, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public  BaseResponseDTO<?> createBrand(@RequestBody Brand brand) {
        Brand createdBrand = brandService.createBrand(brand);
        return new BaseResponseDTO<>("Success", 200,200, createdBrand);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updateBrand(@PathVariable("id") Long BrandId, @RequestBody Brand brandDetails) {
        Brand updatedBrand = brandService.updateBrand(BrandId, brandDetails);
        return updatedBrand != null ?  new BaseResponseDTO<>("Success", 200,200, updatedBrand)
                :  new BaseResponseDTO<>("No content", 403,403,null);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteBrand(@PathVariable("id") Long BrandId) {
        brandService.deleteBrand(BrandId);
        return new BaseResponseDTO<>("Success", 200,200,null);
    }
}

