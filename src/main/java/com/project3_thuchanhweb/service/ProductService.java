package com.project3_thuchanhweb.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project3_thuchanhweb.dto.PageDTO;
import com.project3_thuchanhweb.dto.ProductDTO;
import com.project3_thuchanhweb.dto.SearchDTO;
import com.project3_thuchanhweb.entity.Product;
import com.project3_thuchanhweb.repository.ProductRepo;

public interface ProductService {
	void create(ProductDTO productDTO);

	void updateName(ProductDTO productDTO);

	void delete(int id);

	ProductDTO getbyId(int id);

	PageDTO<List<ProductDTO>> searchByName(SearchDTO searchDTO);
}

@Service
class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepo productRe;

	@Override
	@Transactional
	public void create(ProductDTO productDTO) {
		Product c = new ModelMapper().map(productDTO, Product.class);
		productRe.save(c);

	}

	@Transactional
	@Override
	public void updateName(ProductDTO productDTO) {
		Product c = productRe.findById(productDTO.getId()).orElse(null);
		if (c != null) {
			c.setName(productDTO.getName());
			productRe.save(c);
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		Product c = productRe.findById(id).orElse(null);
		if (c != null) {
			productRe.deleteById(id);
		}

	}

	@Override
	public ProductDTO getbyId(int id) {
		Product p = productRe.findById(id).orElse(null);
		if (p != null) {
			ProductDTO productDTO = convert(p);
			return productDTO;
		}
		return null;
	}

	@Override
	public PageDTO<List<ProductDTO>> searchByName(SearchDTO searchDTO) {
		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(10);
		}

		Sort sortBy = Sort.by("name").ascending();
		if (StringUtils.hasText(searchDTO.getSortField())) {
			sortBy = Sort.by(searchDTO.getSortField()).ascending();
		}

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);

		Page<Product> page = null;
		if (searchDTO.getKeyword() == null) {
			page = (Page<Product>) productRe.findAll();
		} else {
			page = productRe.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		}

		PageDTO<List<ProductDTO>> pageDTOs = new PageDTO<List<ProductDTO>>();
		pageDTOs.setTotalPages(page.getTotalPages());
		pageDTOs.setTotalElements(page.getTotalElements());

		List<ProductDTO> productDTOs = page.get().map(c -> convert(c)).collect(Collectors.toList());
		pageDTOs.setData(productDTOs);

		return pageDTOs;
	}

	private ProductDTO convert(Product p) {
		return new ModelMapper().map(p, ProductDTO.class);
	}

}
