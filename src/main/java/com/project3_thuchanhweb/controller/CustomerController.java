package com.project3_thuchanhweb.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project3_thuchanhweb.dto.BillDTO;
import com.project3_thuchanhweb.dto.PageDTO;
import com.project3_thuchanhweb.dto.ProductDTO;
import com.project3_thuchanhweb.dto.ResponseDTO;
import com.project3_thuchanhweb.dto.SearchDTO;
import com.project3_thuchanhweb.dto.UserDTO;
import com.project3_thuchanhweb.service.CustomerService;



@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerSe;

	@PostMapping("/create-user-role")
	public ResponseDTO<Void> createUserRole(@RequestBody UserDTO userDTO,Principal p) throws AdminRoleException {
		customerSe.createUserRole(p,userDTO);
		return ResponseDTO.<Void>builder().status(200).msg("create role successful").build();
	}

	@PostMapping("/create-bill")
	public ResponseDTO<Void> createBill(@RequestBody BillDTO billDTO, Principal p) {
		customerSe.CreateBill(billDTO, p);
		return ResponseDTO.<Void>builder().status(200).msg("create bill successful").build();
	}

	@GetMapping("/search-product")
	public ResponseDTO<PageDTO<List<ProductDTO>>> searchProduct(SearchDTO searchDTO) {
		PageDTO<List<ProductDTO>> products = customerSe.searchByProductName(searchDTO);
		return ResponseDTO.<PageDTO<List<ProductDTO>>>builder().status(200).data(products).build();
	}
	
	@GetMapping("/get-bill")
	public ResponseDTO<List<BillDTO>> getBill(Principal p){
		List<BillDTO> bills = customerSe.getBillByUser(p);
		
		return ResponseDTO.<List<BillDTO>>builder().status(200).data(bills).build();
		}
	
}
