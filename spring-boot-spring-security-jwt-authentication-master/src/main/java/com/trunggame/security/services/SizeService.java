package com.trunggame.security.services;

import com.trunggame.models.Size;
import com.trunggame.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    public List<Size> getAllSize() {
        return sizeRepository.findAll();
    }

    public Optional<Size> getSizeById(Long id) {
        return sizeRepository.findById(id);
    }

    public Size createSize(Size size) {
        return sizeRepository.save(size);
    }

    public Size updateSize(Long id, Size sizeDetails) {
        Optional<Size> optionalMarketType = sizeRepository.findById(id);
        if (optionalMarketType.isPresent()) {
            Size size = optionalMarketType.get();
            size.setName(sizeDetails.getName());
            size.setDescription(sizeDetails.getDescription());
            return sizeRepository.save(size);
        } else {
            return null; // or throw an exception
        }
    }

    public void deleteSize(Long id) {
        sizeRepository.deleteById(id);
    }
}
