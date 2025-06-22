package com.trunggame.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.ProductInputDTO;
import com.trunggame.repository.ProductRepository;
import com.trunggame.repository.PackageRepository;
import com.trunggame.repository.impl.ProductRepositoryCustom;
import com.trunggame.security.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/games")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    ProductRepositoryCustom productRepositoryCustom;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PackageRepository gamePackageRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> createGame(@RequestBody ProductInputDTO input) {
        return productService.createGame(input);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updateGame(@RequestBody ProductInputDTO input) {
        return productService.updateGame(input);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteGames(@RequestBody ProductInputDTO productInputDTO) {
        return productService.deleteGame(productInputDTO);
    }

    @GetMapping("")
    public BaseResponseDTO<?> getListGame() {
        return new BaseResponseDTO<>("Success", 200, 200, productService.getListGame());
    }

    @GetMapping("/load-data")
    public BaseResponseDTO<?> loadData() throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString( gameService.getLoadData());
//        System.out.println(jsonString);
//        String data = Base64.getEncoder().encodeToString(jsonString.getBytes());

        return new BaseResponseDTO<>("Success", 200, 200, productService.getLoadData());
    }

    @GetMapping("/{id}")
    public BaseResponseDTO<?> getGame(@PathVariable Long id) {
        var games = productRepository.findById(id);
        return new BaseResponseDTO<>("Success", 200, 200, games.get());
    }
}
