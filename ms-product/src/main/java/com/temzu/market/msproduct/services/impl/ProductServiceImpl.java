package com.temzu.market.msproduct.services.impl;

import com.temzu.market.corelib.exceptions.ResourceNotFoundException;
import com.temzu.market.msproduct.dao.services.CategoryDao;
import com.temzu.market.msproduct.dao.services.ProductDao;
import com.temzu.market.msproduct.dao.repositories.specification.ProductSpecifications;
import com.temzu.market.msproduct.dao.entities.Product;
import com.temzu.market.routinglib.dtos.ProductCreateDto;
import com.temzu.market.routinglib.dtos.ProductDto;
import com.temzu.market.msproduct.mappers.ProductMapper;
import com.temzu.market.msproduct.services.ProductService;
import com.temzu.market.routinglib.dtos.ProductUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductDao productDao;
  private final ProductMapper productMapper;
  private final CategoryDao categoryDao;

  @Override
  public Page<ProductDto> findPage(
      MultiValueMap<String, String> params,
      Integer page,
      Integer pageSize
  ) {
    Specification<Product> specification = ProductSpecifications.build(params);
    return productDao
        .findPage(specification, page, pageSize)
        .map(productMapper::toProductDto);
  }

  @Override
  public ProductDto findById(Long id) {
    return productMapper.toProductDto(productDao.findById(id));
  }

  @Transactional
  @Override
  public ProductDto save(ProductCreateDto productCreateDto) {
    Product createdProd = productMapper.toProduct(productCreateDto);
    createdProd.setCategory(categoryDao.findByTitle(productCreateDto.getCategoryTitle()));
    return productMapper.toProductDto(productDao.saveOrUpdate(createdProd));
  }

  @Transactional
  @Override
  public ProductDto update(ProductUpdateDto productUpdateDto) {
    if (!productDao.existById(productUpdateDto.getId())) {
      throw ResourceNotFoundException.byId(productUpdateDto.getId(), Product.class);
    }
    Product updatedProd = productMapper.toProduct(productUpdateDto);
    updatedProd.setCategory(categoryDao.findByTitle(productUpdateDto.getCategoryTitle()));
    return productMapper.toProductDto(productDao.saveOrUpdate(updatedProd));
  }

  @Transactional
  @Override
  public void deleteById(Long id) {
    productDao.deleteById(id);
  }
}
