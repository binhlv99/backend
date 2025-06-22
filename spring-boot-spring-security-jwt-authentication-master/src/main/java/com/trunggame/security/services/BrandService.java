package com.trunggame.security.services;

import java.util.List;

import com.trunggame.models.Brand;
import com.trunggame.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllCompanies() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, Brand brand) {
        Brand existingBrand = brandRepository.findById(id).orElse(null);
        if (existingBrand != null) {
            existingBrand.setName(brand.getName());
            existingBrand.setAddress(brand.getAddress());
            existingBrand.setPhoneNumber(brand.getPhoneNumber());
            existingBrand.setEmailAddress(brand.getEmailAddress());
            existingBrand.setWebsite(brand.getWebsite());
            existingBrand.setDescription(brand.getDescription());
            return brandRepository.save(existingBrand);
        } else {
            return null;
        }
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

}
