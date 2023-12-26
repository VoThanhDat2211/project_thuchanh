package com.project3_thuchanhweb.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project3_thuchanhweb.dto.Product_colorDTO;
import com.project3_thuchanhweb.entity.Product_color;
import com.project3_thuchanhweb.repository.Product_colorRepo;

public interface Product_colorService {
	void create(Product_colorDTO pcDTO);

	void update(Product_colorDTO pcDTO);

	void delete(int id);
}

@Service
class Product_colorServiceImpl implements Product_colorService {

	@Autowired
	Product_colorRepo pcRe;

	@Override
	@Transactional
	public void create(Product_colorDTO pcDTO) {
		Product_color pc = new ModelMapper().map(pcDTO, Product_color.class);
		pcRe.save(pc);

	}

	@Override
	public void update(Product_colorDTO pcDTO) {
		Product_color pc = pcRe.findById(pcDTO.getId()).orElse(null);
		if (pc != null) {
			pc.setQuantity(pcDTO.getQuantity());
			pcRe.save(pc);
		}

	}

	@Override
	public void delete(int id) {
		Product_color pc = pcRe.findById(id).orElse(null);
		if (pc != null) {
			pcRe.deleteById(id);
		}

	}

}
