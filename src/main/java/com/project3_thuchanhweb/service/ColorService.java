package com.project3_thuchanhweb.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project3_thuchanhweb.dto.ColorDTO;
import com.project3_thuchanhweb.entity.Color;
import com.project3_thuchanhweb.repository.ColorRepo;

public interface ColorService {
	void create(ColorDTO colorDTO);

	void delete(int id);

	ColorDTO getbyId(int id);

}

@Service
class ColorServiceImple implements ColorService {
	@Autowired
	ColorRepo colorRe;

	@Override
	public void create(ColorDTO colorDTO) {
		Color color = new ModelMapper().map(colorDTO, Color.class);
		;
		colorRe.save(color);

	}

	@Override
	public void delete(int id) {
		Color color = colorRe.findById(id).orElse(null);
		if (color != null) {
			colorRe.deleteById(id);
		}

	}

	@Override
	public ColorDTO getbyId(int id) {
		Color color = colorRe.findById(id).orElse(null);
		if (color != null) {
			ColorDTO colorDTO = convert(color);
			return colorDTO;
		}
		return null;
	}

	private ColorDTO convert(Color color) {
		return new ModelMapper().map(color, ColorDTO.class);
	}

}