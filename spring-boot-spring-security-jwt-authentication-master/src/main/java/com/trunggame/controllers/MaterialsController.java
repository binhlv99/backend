package com.trunggame.controllers;
import com.trunggame.dto.MaterialsDTO;
import com.trunggame.models.Materials;
import com.trunggame.security.services.MaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/server-groups")
public class MaterialsController {

    @Autowired
    private MaterialsService service;

    @PostMapping
    public ResponseEntity<Materials> create(@RequestBody MaterialsDTO input) {
        Materials sG = new Materials();
        sG.setName(input.getName());
        sG.setCreatedAt(new Date());
        if(input.getParentId() != null) {
            sG.setParentId(input.getParentId());
         }else {
            sG.setParentId(0L);

        }
        Materials savedMaterials = service.save(sG);
        return new ResponseEntity<>(savedMaterials, HttpStatus.CREATED);
    }

    @GetMapping("/group-by-parent")
    public ResponseEntity<Map<String, List<MaterialsDTO>>> groupByParentId() {
        List<MaterialsDTO> Materialss = service.getAllMaterialsByParentId();
        Map<String, List<MaterialsDTO>> groupedMaterialss = Materialss.stream()
                .collect(Collectors.groupingBy(MaterialsDTO::getParentName));
        return new ResponseEntity<>(groupedMaterialss, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materials> findById(@PathVariable Long id) {
        Optional<Materials> optionalMaterials = service.findById(id);
        return optionalMaterials
                .map(materials -> new ResponseEntity<>(materials, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materials> update(@PathVariable Long id, @RequestBody Materials materials) {
        Optional<Materials> optionalMaterials = service.findById(id);
        return optionalMaterials
                .map(existingMaterials -> {
                    existingMaterials.setName(materials.getName());
                    Materials updatedMaterials = service.save(existingMaterials);
                    return new ResponseEntity<>(updatedMaterials, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Materials> optionalMaterials = service.findById(id);
        return optionalMaterials
                .map(materials -> {
                    service.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
