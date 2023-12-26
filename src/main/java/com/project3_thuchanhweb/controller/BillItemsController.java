package com.project3_thuchanhweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project3_thuchanhweb.dto.BillItemsDTO;
import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.service.BillItemsService;

@RestController
@RequestMapping("/admin/billItems")
public class BillItemsController {
	@Autowired
	BillItemsService billItemsSe;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody BillItemsDTO billItemsDTO) {
		billItemsSe.create(billItemsDTO);

		return ResponseDTO.<Void>builder().status(200).msg("create successful").build();
	}

	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam int id) {
		billItemsSe.delete(id);

		return ResponseDTO.<Void>builder().status(200).msg("delete successful").build();
	}


	@GetMapping("/")
	public ResponseDTO<BillItemsDTO> getById(@RequestParam int id) {
		BillItemsDTO billItemsDTO= billItemsSe.getbyId(id);

		return ResponseDTO.<BillItemsDTO>builder().status(200).data(billItemsDTO).build();
	}

}
