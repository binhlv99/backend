package com.trunggame.controllers;

import com.trunggame.constant.ConstantUtils;
import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.TagDeleteDTO;
import com.trunggame.models.ShoeOutsoles;
import com.trunggame.repository.ShoeOutsolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tag")
public class ShoeOutsolesController {

    @Autowired
    ShoeOutsolesRepository shoeOutsolesRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createTag(@RequestBody ShoeOutsoles input) {

        return getBaseResponseDTO(input, false);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updateTag(@RequestBody ShoeOutsoles input) {

        return getBaseResponseDTO(input,true);
    }

    private BaseResponseDTO<?> getBaseResponseDTO(@RequestBody ShoeOutsoles input, boolean isUpdate) {
        var existedShoeOutsoles = shoeOutsolesRepository.findFirstByName(input.getName());
        if(existedShoeOutsoles.isPresent()) {
            return new BaseResponseDTO<>("Error: Tag is already taken!", 400, 400,null);
        }

        input.setStatus(ConstantUtils.ACTIVE);
        if (!isUpdate) {
            input.setCreatedAt(LocalDateTime.now());
            input.setUpdatedAt(LocalDateTime.now());
        } else {
            input.setUpdatedAt(LocalDateTime.now());
        }
        var entity = shoeOutsolesRepository.save(input);

        return new BaseResponseDTO<>("Success", 200, 200,entity);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteTag(@RequestBody TagDeleteDTO input) {
        if(input.getIds().size() > 0) {
            shoeOutsolesRepository.deleteAllByIdIn(input.getIds());
        }
        return new BaseResponseDTO<>("Success", 200, 200,null);
    }


    @GetMapping("/{id}")
    public BaseResponseDTO<?> getTagDetail(@PathVariable Long id) {
        return new BaseResponseDTO<>("Success", 200, 200, shoeOutsolesRepository.findById(id));
    }


    @GetMapping("/all")
    public BaseResponseDTO<?> getAllTagDetail() {
        return new BaseResponseDTO<>("Success", 200, 200, shoeOutsolesRepository.findAll());
    }
}
