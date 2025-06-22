package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.models.Size;
import com.trunggame.security.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/size")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping
    public List<Size> getAllSize() {
        return sizeService.getAllSize();
    }

    @GetMapping("/{id}")
    public  BaseResponseDTO<?> getSizeById(@PathVariable Long id) {
        Optional<Size> optionalSize = sizeService.getSizeById(id);
        if (optionalSize.isPresent()) {
            return new BaseResponseDTO<>("Success", 200,200,optionalSize.get());
        } else {
            return new BaseResponseDTO<>("No content", 400,400,optionalSize.get());
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createSize(@RequestBody Size size) {
        return new BaseResponseDTO<>("Success", 200,200, sizeService.createSize(size));
    }

    @PutMapping("/{id}")
    public BaseResponseDTO<?> updateSize(@PathVariable Long id, @RequestBody Size sizeDetails) {
        Size updatedSize = sizeService.updateSize(id, sizeDetails);
        if (updatedSize != null) {
            return new BaseResponseDTO<>("Success", 200,200,null);
        } else {
            return new BaseResponseDTO<>("No content", 403,403,null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteMSize(@PathVariable Long id) {
        sizeService.deleteSize(id);
        return new BaseResponseDTO<>("Success", 200,4200,null);
    }
}
