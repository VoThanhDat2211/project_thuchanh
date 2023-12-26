package com.project3_thuchanhweb.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project3_thuchanhweb.dto.BillItemsDTO;
import com.project3_thuchanhweb.entity.BillItems;
import com.project3_thuchanhweb.repository.BillItemsRepo;

public interface BillItemsService {
	void create(BillItemsDTO billItemsDTO);

	void delete(int id);

	BillItemsDTO getbyId(int id);
}

@Service
class BillItemsServiceImpl implements BillItemsService {

	@Autowired
	BillItemsRepo billItemsRe;

	@Override
	@Transactional
	public void create(BillItemsDTO billItemsDTO) {
		System.out.println("billItem price: "+billItemsDTO.getBuyPrice());
		BillItems billItems = new ModelMapper().map(billItemsDTO, BillItems.class);
		;
		billItemsRe.save(billItems);

	}


	@Override
	@Transactional
	public void delete(int id) {
		BillItems billItems  = billItemsRe.findById(id).orElse(null);
		if (billItems != null) {
			billItemsRe.deleteById(id);
		}

	}

	@Override
	public BillItemsDTO getbyId(int id) {
		BillItems billItems  = billItemsRe.findById(id).orElse(null);
		if (billItems != null) {
			BillItemsDTO billItemsDTO= convert(billItems);
			return billItemsDTO;
		}
		return null;
	}

	private BillItemsDTO  convert(BillItems billItems) {
		return new ModelMapper().map(billItems, BillItemsDTO.class);
	}

}