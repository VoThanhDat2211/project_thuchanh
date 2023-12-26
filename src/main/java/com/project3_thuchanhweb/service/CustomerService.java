package com.project3_thuchanhweb.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project3_thuchanhweb.controller.AdminRoleException;
import com.project3_thuchanhweb.dto.BillDTO;
import com.project3_thuchanhweb.dto.BillItemsDTO;
import com.project3_thuchanhweb.dto.PageDTO;
import com.project3_thuchanhweb.dto.ProductDTO;
import com.project3_thuchanhweb.dto.SearchDTO;
import com.project3_thuchanhweb.dto.UserDTO;
import com.project3_thuchanhweb.entity.Bill;
import com.project3_thuchanhweb.entity.BillItems;
import com.project3_thuchanhweb.entity.Product;
import com.project3_thuchanhweb.entity.Role;
import com.project3_thuchanhweb.entity.User;
import com.project3_thuchanhweb.repository.BillRepo;
import com.project3_thuchanhweb.repository.CustomerRepo;
import com.project3_thuchanhweb.repository.ProductRepo;
import com.project3_thuchanhweb.repository.RoleRepo;
import com.project3_thuchanhweb.repository.UserRepo;

public interface CustomerService {
	void createUserRole(Principal p, UserDTO userDTO) throws AdminRoleException;

	void CreateBill(BillDTO billDTO, Principal p);

	PageDTO<List<ProductDTO>> searchByProductName(SearchDTO searchDTO);

	List<BillDTO> getBillByUser(Principal p);

}

@Service
class CustomerServiceImpl implements CustomerService {

	@Autowired
	RoleRepo roleRe;

	@Autowired
	BillRepo billRe;

	@Autowired
	UserRepo userRe;

	@Autowired
	ProductRepo productRe;

	@Autowired
	CustomerRepo customerRe;

	@Override
	@Transactional
	public void createUserRole(Principal p, UserDTO userDTO) throws AdminRoleException {

		if (p == null) {
			throw new UsernameNotFoundException("user not found");
		}
		String username = p.getName();
		User user = userRe.findByUsername(username);

		if (user != null) {
			Set<String> existingRoleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

			List<Role> listRole = userDTO.getRoles().stream().map(c -> roleRe.findByName(c.getName()))
					.collect(Collectors.toList());

			for (Role role : listRole) {
				String roleAdmin = "ROLE_ADMIN";
				if (role.getName().equals(roleAdmin)) {
					throw new AdminRoleException("Admin role registration is not allowed!");
				} else if (existingRoleNames.contains(role.getName())) {
					throw new IllegalIdentifierException("Duplicate role!");
				} else {
					user.getRoles().add(role);
				}

			}

			userRe.save(user);
		}

	}

	@Override
	@Transactional
	public void CreateBill(BillDTO billDTO, Principal p) {

		Bill bill = new Bill();
		User user = new ModelMapper().map(userCurrent(p), User.class);

		bill.setUser(user);

		List<BillItems> billItems = new ArrayList<BillItems>();
		for (BillItemsDTO billItemsDTO : billDTO.getBillItems()) {
			BillItems billItem = new BillItems();
			billItem.setBill(bill);
			billItem.setProduct(new ModelMapper().map(billItemsDTO.getProduct(), Product.class));
			billItem.setQuantity(billItemsDTO.getQuantity());
			billItem.setBuyPrice(billItemsDTO.getBuyPrice());

			billItems.add(billItem);
		}

		bill.setBillItems(billItems);

		billRe.save(bill);
		billRe.save(bill);

	}

	@Override
	public PageDTO<List<ProductDTO>> searchByProductName(SearchDTO searchDTO) {
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
			page = productRe.getAll(pageRequest);
		} else {
			page = productRe.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		}

		PageDTO<List<ProductDTO>> pageDTOs = new PageDTO<List<ProductDTO>>();
		pageDTOs.setTotalPages(page.getTotalPages());
		pageDTOs.setTotalElements(page.getTotalElements());

		List<ProductDTO> productDTOs = page.get().map(c -> new ModelMapper().map(c, ProductDTO.class))
				.collect(Collectors.toList());
		pageDTOs.setData(productDTOs);

		return pageDTOs;
	}

	@Override
	public List<BillDTO> getBillByUser(Principal p) {
		UserDTO userDTO = userCurrent(p);
		List<Bill> bills = customerRe.searchBillByUser(userDTO.getUsername());

		return bills.stream().map(c -> new ModelMapper().map(c, BillDTO.class)).collect(Collectors.toList());
	}

	private UserDTO userCurrent(Principal p) {
		if (p == null) {
			throw new UsernameNotFoundException(" not found");
		}

		String username = p.getName();
		User user = userRe.findByUsername(username);

		return new ModelMapper().map(user, UserDTO.class);

	}
}
