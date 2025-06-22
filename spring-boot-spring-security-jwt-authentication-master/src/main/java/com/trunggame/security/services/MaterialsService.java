package com.trunggame.security.services;

import com.trunggame.dto.MaterialsDTO;
import com.trunggame.models.Materials;
import com.trunggame.repository.MaterialsRepository;
import com.trunggame.repository.impl.MaterialsCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class MaterialsService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private MaterialsRepository repository;

    @Autowired
    private MaterialsCustomRepository materialsCustomRepository;

    public Materials save(Materials materials) {
        return repository.save(materials);
    }

    public Optional<Materials> findById(Long id) {
        return repository.findById(id);
    }

    public List<Materials> findAll() {
        Query query = entityManager.createNativeQuery(
                "select sg.*, parentSG.name as parentName from materials as sg  " +
                        "            join materials as parentSG on sg.parent_id = parentSG.id;", "ServerGroupMapping");

        List<Materials> results = query.getResultList();
        return results;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public  List<MaterialsDTO>  getAllMaterialsByParentId() {
        return materialsCustomRepository.getAllMaterials();
    }
}
