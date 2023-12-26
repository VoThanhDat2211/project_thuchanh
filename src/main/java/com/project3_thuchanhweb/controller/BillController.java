package com.project3_thuchanhweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project3_thuchanhweb.dto.BillDTO;
import com.project3_thuchanhweb.dto.BillStatisticDTO;
import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.service.BillService;

@RestController
@RequestMapping("/admin/bill")
public class BillController {
	@Autowired
	BillService billSe;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody BillDTO billDTO) {
		billSe.create(billDTO);

		return ResponseDTO.<Void>builder().status(200).msg("create successful").build();
	}

	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam int id) {
		billSe.delete(id);

		return ResponseDTO.<Void>builder().status(200).msg("delete successful").build();
	}


	@GetMapping("/")
	public ResponseDTO<BillDTO> getById(@RequestParam int id) {
		BillDTO billDTO = billSe.getbyId(id);

		return ResponseDTO.<BillDTO>builder().status(200).data(billDTO).build();
	}
	
	@GetMapping("/statistic-by-buyDate")
	public ResponseDTO<List<BillStatisticDTO>> statictis() {
			List<BillStatisticDTO> list = billSe.statictis();

		return ResponseDTO.<List<BillStatisticDTO>>builder().status(200).data(list).build();
	}
}
