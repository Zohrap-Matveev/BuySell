package am.example.buysell.services;

import am.example.buysell.exceptions.ProductNotFoundException;
import am.example.buysell.models.Product;
import am.example.buysell.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService{

    private final ProductRepository productRepository;

    public List<Product> listProducts(String title){
        if(title != null){
            return productRepository.findByTitle(title);
        }
        return productRepository.findAll();
    }

    public void saveProduct(Product product){
        log.info("Saving new {}", product);
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product getProductById(long id){
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException :: new);
    }
}
