package com.trunggame.security.services.impl;

import com.trunggame.dto.*;
import com.trunggame.models.*;
import com.trunggame.repository.*;
import com.trunggame.repository.impl.ProductRepositoryCustom;
import com.trunggame.repository.impl.PackageRepositoryImpl;
import com.trunggame.repository.impl.PostRespositoryCustom;
import com.trunggame.security.services.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoeOutsolesRepository shoeOutsolesRepository;

    @Autowired
    private ShoeTopsRepository shoeTopsRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ProductRepositoryCustom productRepositoryCustom;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PackageRepositoryImpl packageRepositoryCustom;

    @Autowired
    private PostRespositoryCustom postRespositoryCustom;

    @Override
    @Transactional
    public BaseResponseDTO<?> createGame(ProductInputDTO input) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        var currentUser = userRepository.findByUsername(userDetails.getUsername());

        validateInput(input);

        var file = fileRepository.findFirstByUniqId(input.getImageId());

        // Create game builder
        var game = Product.builder()
                .name(input.getName())
                .description(input.getDescription())
                .categoryId(input.getCategoryId())
                .imageId(input.getImageId())
                .thumbnail(input.getImageId())
                .type(input.getType())
                .status(Product.Status.ACTIVE)
                .brandName(input.getBrandName())
                .thumbnail(input.getThumbnail())
                .contentEn(input.getContentEn())
                .contentVi(input.getContentVi())
                .descriptionEn(input.getDescriptionEn())
                .marketType(input.getMarketType())
                .youtubeLink(input.getYoutubeLink())
                .gamePriority(input.getGamePriority())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        var gameEntity = productRepository.save(game);
        gameEntity.setPreviewUrl(file.get().getPreviewUrl());

        file.get().setOwnerId(game.getId());
        fileRepository.save(file.get());

//        // Create price builder
//        var price = Price.builder()
//                .price(input.getPrice())
//                .promotionPrice(input.getPromotionPrice())
//                .promotionPercent(input.getPromotionPercent())
//                .status(ECommonStatus.ACTIVE)
//                .gameId(gameEntity.getId())
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now()).build();
//
//        priceRepository.save(price);

        // Create brand builder
        var brand = Brand.builder()
                .name(input.getBrandName()).build();

        brandRepository.save(brand);

        // Create company builder
        var marketType = Size.builder()
                .name(input.getBrandName()).build();

        sizeRepository.save(marketType);


        // save tags and game
        if (input.getTags() != null && !input.getTags().isEmpty()) {
            var tags = input.getTags();
            if (tags.size() > 10) {
                return new BaseResponseDTO<>("Tag's size is must be smaller than equal 10", 400, 400, null);
            }
            List<ShoeTops> shoeTops = new ArrayList<>();
            for (var tagName : input.getTags()) {
                var tag = shoeOutsolesRepository.findFirstByName(tagName);
//                if (tag.isPresent()) {
//                    shoeTops.add(ShoeTops.builder().smartTagId(tag.get().getId()).gameId(gameEntity.getId()).build());
//                } else {
//                    return new BaseResponseDTO<>("Tag's name does not exist: " + tagName, 400, 400, null);
//                }
            }

            // Batch save smart tag game
            shoeTopsRepository.saveAll(shoeTops);


        }

        return new BaseResponseDTO<>("Success", 200, 200, gameEntity);

    }

    @Override
    @Transactional
    public BaseResponseDTO<?> updateGame(ProductInputDTO input) {
        if (input.getId() == null) {
            return new BaseResponseDTO<>("Id can not be null", 400, 400, null);
        }

        var gameEntity = productRepository.findById(input.getId());
        if (gameEntity.isPresent()) {
            validateInput(input);

            if (!Objects.equals(gameEntity.get().getImageId(), input.getImageId()) || !Objects.equals(gameEntity.get().getThumbnail(), input.getThumbnail())) {
                // Delete old files
                List<String> uniqIds = Arrays.asList(gameEntity.get().getImageId(), gameEntity.get().getThumbnail());
                fileRepository.updateFileLinkToGame(uniqIds);
            }

            // Delete all tags
            var smartTagGameOpt = shoeTopsRepository.findByGameId(input.getId());
            shoeTopsRepository.deleteAllById(smartTagGameOpt.get().stream().map(s -> s.getId()).collect(Collectors.toList()));


            var file = fileRepository.findFirstByUniqId(input.getImageId());

            var gameUpdate = gameEntity.get();
            gameEntity.get().setName(input.getName());
            gameEntity.get().setDescription(input.getDescription());
            gameEntity.get().setCategoryId(input.getCategoryId());
            gameEntity.get().setImageId(input.getImageId());
            gameEntity.get().setThumbnail(input.getThumbnail());
            gameEntity.get().setType(input.getType());
            gameEntity.get().setBrandName(input.getBrandName());
            gameEntity.get().setContentEn(input.getContentEn());
            gameEntity.get().setContentVi(input.getContentVi());
            gameEntity.get().setMarketType(input.getMarketType());
            gameEntity.get().setGamePriority(input.getGamePriority());
            gameEntity.get().setYoutubeLink(input.getYoutubeLink());
            gameEntity.get().setDescriptionEn(input.getDescriptionEn());
            gameEntity.get().setStatus(input.getStatus());
            var gameSaved = productRepository.saveAndFlush(gameEntity.get());
            gameSaved.setPreviewUrl(file.get().getPreviewUrl());

            file.get().setOwnerId(gameUpdate.getId());
            fileRepository.save(file.get());

//            // Create price builder
//            var price = Price.builder()
//                    .price(input.getPrice())
//                    .promotionPrice(input.getPromotionPrice())
//                    .promotionPercent(input.getPromotionPercent())
//                    .status(ECommonStatus.ACTIVE)
//                    .gameId(gameSaved.getId())
//                    .createdAt(LocalDateTime.now())
//                    .updatedAt(LocalDateTime.now()).build();
//
//            priceRepository.save(price);


            // save tags and game
            if (input.getTags() != null && !input.getTags().isEmpty()) {
                var tags = input.getTags();
                if (tags.size() > 10) {
                    return new BaseResponseDTO<>("Tag's size is must be smaller than equal 10", 400, 400, null);
                }
                List<ShoeTops> shoeTops = new ArrayList<>();
                for (var tagName : input.getTags()) {
                    var tag = shoeOutsolesRepository.findFirstByName(tagName);
//                    if (tag.isPresent()) {
//                        shoeTops.add(ShoeTops.builder().smartTagId(tag.get().getId()).gameId(gameSaved.getId()).build());
//                    } else {
//                        return new BaseResponseDTO<>("Tag's name does not exist: " + tagName, 400, 400, null);
//                    }
                }

                // Batch save smart tag game
                shoeTopsRepository.saveAll(shoeTops);
            }

//            // update server group
//            if (input.getServerGroups() != null) {
//                if (input.getServerGroups().size() > 0) {
//                    // find all old server game group
//                    var odlGameServerGroup = gameServerGroupRepository.findAllByGameId(gameSaved.getId());
//                    gameServerGroupRepository.deleteAll(odlGameServerGroup);
//
//
//                }
//            }


            return new BaseResponseDTO<>("Success", 200, 200, gameSaved);

        }
        return new BaseResponseDTO<>("Game not found", 400, 400, null);
    }

    @Override
    public List<ProductInformationDTO> getListGame() {
        var games = productRepositoryCustom.getAllInformation();
        for (var game : games) {
//            var gamePackages = gamePackageRepository.findAllByGameId(game.getId());
//            game.setGamePackages(gamePackages);
            List <PackageDTO> listPackage = packageRepositoryCustom.getPackageByGameId(game.getId());
            if(listPackage.size()==0){
                listPackage = new ArrayList<>();
            }
            game.setGamePackages(listPackage);
            var gameServerGroups = colorRepository.findAllByGameId(game.getId());
            game.setServer(gameServerGroups);

            // get game tag
//            var gameTagSmart = smartTagGameRepository.findTagByGameId(game.getId());
//            var tagList = new ArrayList<String>();
//            for(var tag : gameTagSmart) {
//                tagList.add(tag.getName());
//            }
        }
        return games;
    }

    @Override
    public LoadDataDTO getLoadData() {

        var games = this.productRepositoryCustom.getActiveGame();
        for (var game : games) {
            List <PackageDTO> listPackage = packageRepositoryCustom.getPackageByGameId(game.getId());
            if(listPackage.size()==0){
                listPackage = new ArrayList<>();
            }
            game.setGamePackages(listPackage);
            var gameServerGroups = colorRepository.findAllByGameId(game.getId());
            game.setServer(gameServerGroups);
        }
        var newGame = this.productRepositoryCustom.getNewGamge();

        for (var game : newGame) {
            List <PackageDTO> listPackage = packageRepositoryCustom.getPackageByGameId(game.getId());
            if(listPackage.size()==0){
                listPackage = new ArrayList<>();
            }
            game.setGamePackages(listPackage);
            var gameServerGroups = colorRepository.findAllByGameId(game.getId());
            game.setServer(gameServerGroups);
        }

        return LoadDataDTO.builder()
                .listGame(games)
                .newGames(newGame)
                .banners(postRespositoryCustom.getAllActiveBanner())
                .posts(postRepository.findByStatus(Post.Status.ACTIVE))
                .newPackage(packageRepositoryCustom.getNewPackage())
                .topSale(packageRepositoryCustom.getTopSale())
                .bestSale(packageRepositoryCustom.getBestSale())
                .packages(packageRepositoryCustom.getAllActivePackage())
                .build();
    }

    @Override
    public BaseResponseDTO<?> deleteGame(ProductInputDTO orderDTO) {
        var game = productRepository.findById(orderDTO.getId());
        if(game.isPresent()) {
            var gameObject = game.get();
            gameObject.setStatus(orderDTO.getStatus());
            productRepository.save(gameObject);
            return new BaseResponseDTO<>("Success", 200,200,null);
        }
        return new BaseResponseDTO<>("No content", 400,400,null);
    }

    public Query createFindGameTemp(String search, Pageable pageable) {
        Map<String, Object> param = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.append(" select * from game g where 1 = 1 ");
        if (StringUtils.isNotBlank(search)) {
            String lowerSearch = search.trim().toLowerCase();
            builder.append(" and (lower(g.name) like :search ");
            param.put("search", "%" + lowerSearch + "%");
        }

        builder.append("LIMIT :limit OFFSET :offset ");
        param.put("limit", pageable.getPageSize());
        param.put("offset", pageable.getOffset());

        var query = entityManager.createNativeQuery(builder.toString(), Product.class);
        for (Map.Entry<String, Object> item : param.entrySet()) {
            query.setParameter(item.getKey(), item.getValue());
        }
        return query;
    }

    private void validateInput(ProductInputDTO input) {
        if (Objects.isNull(input)) {
            throw new RuntimeException("Input to create game is null");
        }

        var categoryOPT = categoryRepository.findById(input.getCategoryId());
        if (categoryOPT.isEmpty()) {
            throw new RuntimeException("Category does exist");
        }

        var imageOTP = fileRepository.findFirstByUniqId(input.getImageId());

        if (imageOTP.isEmpty()) {
            throw new RuntimeException("Image's id  does exist");
        }
    }
}
