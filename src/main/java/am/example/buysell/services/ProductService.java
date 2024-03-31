package am.example.buysell.services;

import am.example.buysell.exceptions.ProductNotFoundException;
import am.example.buysell.models.Image;
import am.example.buysell.models.Product;
import am.example.buysell.models.User;
import am.example.buysell.repositories.ProductRepository;
import am.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService{

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Product> listProducts(String title){
        if(title != null){
            return productRepository.findByTitle(title);
        }
        return productRepository.findAll();
    }

    @Transactional
    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException{
        product.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        log.info("Saving new Product. Title: {}; Author email : {}", product.getTitle(), product.getUser().getEmail());
        Product productFromDb = productRepository.save(product);
        if (!product.getImages().isEmpty()) {
            productFromDb.setPreviewImageId(product.getImages().get(0).getId());
            productRepository.save(productFromDb);
        }
    }

    @Transactional
    public User getUserByPrincipal(Principal principal){
        if(principal == null) {
            return new User();
        }
        return userRepository.findByEmail(principal.getName());
    }

    @Transactional
    public Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product getProductById(long id){
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException :: new);
    }
}
