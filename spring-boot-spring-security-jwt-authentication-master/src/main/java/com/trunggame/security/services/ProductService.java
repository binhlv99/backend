package com.trunggame.security.services;


import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.ProductInformationDTO;
import com.trunggame.dto.ProductInputDTO;
import com.trunggame.dto.LoadDataDTO;

import java.util.List;

public interface ProductService {

    BaseResponseDTO<?> createGame(ProductInputDTO input);

    BaseResponseDTO<?> updateGame(ProductInputDTO input);

    List<ProductInformationDTO> getListGame();

    LoadDataDTO getLoadData();

    BaseResponseDTO<?> deleteGame(ProductInputDTO productInputDTO);

}
