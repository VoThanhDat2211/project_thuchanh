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

import com.project3_thuchanhweb.dto.CategoryDTO;
import com.project3_thuchanhweb.dto.PageDTO;
import com.project3_thuchanhweb.dto.SearchDTO;
import com.project3_thuchanhweb.entity.Category;
import com.project3_thuchanhweb.repository.CategoryRepo;

import lombok.extern.slf4j.Slf4j;

public interface CategoryService {
	void create(CategoryDTO categoryDTO);

	void updateName(CategoryDTO categoryDTO);

	void delete(int id);

	CategoryDTO getbyId(int id);

	PageDTO<List<CategoryDTO>> searchByName(SearchDTO searchDTO);
}

@Service
@Slf4j
class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRe;

	@Override
	@Transactional
	public void create(CategoryDTO categoryDTO) {
		log.info("categoryDTO: " + categoryDTO);
		Category c = new ModelMapper().map(categoryDTO, Category.class);
		log.info("category: " + c);
		categoryRe.save(c);

	}

	@Transactional
	@Override
	public void updateName(CategoryDTO categoryDTO) {
		Category c = categoryRe.findById(categoryDTO.getId()).orElse(null);
		if (c != null) {
			c.setName(categoryDTO.getName());
			categoryRe.save(c);
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		Category c = categoryRe.findById(id).orElse(null);
		if (c != null) {
			categoryRe.deleteById(id);
		}

	}

	@Override
	public CategoryDTO getbyId(int id) {
		Category category = categoryRe.findById(id).orElse(null);
		if (category != null) {
			CategoryDTO categoryDTO = convert(category);
			return categoryDTO;
		}
		return null;
	}

	@Override
	public PageDTO<List<CategoryDTO>> searchByName(SearchDTO searchDTO) {
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

		Page<Category> page = null;
		if (searchDTO.getKeyword() == null) {
			page = (Page<Category>) categoryRe.findAll();
		} else {
			page = categoryRe.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		}

		PageDTO<List<CategoryDTO>> pageDTOs = new PageDTO<List<CategoryDTO>>();
		pageDTOs.setTotalPages(page.getTotalPages());
		pageDTOs.setTotalElements(page.getTotalElements());

		List<CategoryDTO> categoryDTOs = page.get().map(c -> convert(c)).collect(Collectors.toList());
		pageDTOs.setData(categoryDTOs);

		return pageDTOs;
	}

	private CategoryDTO convert(Category c) {
		return new ModelMapper().map(c, CategoryDTO.class);
	}

}
