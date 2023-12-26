package com.project3_thuchanhweb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project3_thuchanhweb.dto.BillDTO;
import com.project3_thuchanhweb.dto.BillItemsDTO;
import com.project3_thuchanhweb.dto.BillStatisticDTO;
import com.project3_thuchanhweb.entity.Bill;
import com.project3_thuchanhweb.entity.BillItems;
import com.project3_thuchanhweb.entity.Product;
import com.project3_thuchanhweb.entity.Product_color;
import com.project3_thuchanhweb.entity.User;
import com.project3_thuchanhweb.repository.BillItemsRepo;
import com.project3_thuchanhweb.repository.BillRepo;
import com.project3_thuchanhweb.repository.Product_colorRepo;
import com.project3_thuchanhweb.repository.UserRepo;

public interface BillService {
	void create(BillDTO billDTO);

	void delete(int id);

	BillDTO getbyId(int id);

	List<BillStatisticDTO> statictis();

	boolean searchByBuyDate(Date date);
}

@Service
class BillServiceImpl implements BillService {

	@Autowired
	BillRepo billRe;

	@Autowired
	UserRepo userRe;

	@Autowired
	BillItemsRepo billItemsRe;

	@Autowired
	Product_colorRepo pcRe;

	@Autowired
	EmailService emailSe;

	@Override
	@Transactional
	public void create(BillDTO billDTO) {
		Bill bill = new Bill();
		User user = userRe.findById(billDTO.getUser().getId()).orElseThrow();

		bill.setUser(user);

		List<BillItems> billItems = new ArrayList<BillItems>();
		for (BillItemsDTO billItemsDTO : billDTO.getBillItems()) {
			BillItems billItem = new BillItems();
			billItem.setBill(bill);
			billItem.setProduct(new ModelMapper().map(billItemsDTO.getProduct(), Product.class));
			billItem.setProduct_color(new ModelMapper().map(billItemsDTO.getProduct_colorDTO(), Product_color.class));

			// kiem tra so luong san pham va cap nhat lai so luong san pham
			Product_color product_color = pcRe.findById(billItem.getProduct_color().getId())
					.orElseThrow(() -> new NoResultException("sản phẩm không tồn tại"));
			if (product_color.getQuantity() > 0 && product_color.getQuantity() >= billItemsDTO.getQuantity()) {
				billItem.setQuantity(billItemsDTO.getQuantity());
				product_color.setQuantity(product_color.getQuantity() - billItem.getQuantity());
				pcRe.save(product_color);
			} else {
				throw new IllegalStateException("Không đủ số lượng sản phẩm");
			}

			billItem.setBuyPrice(billItemsDTO.getBuyPrice());

			billItems.add(billItem);

		}

		bill.setBillItems(billItems);

		billRe.save(bill);

		// gui email sau khi tao don hang thanh cong
		emailSe.sendNotice(bill.getUser().getEmail());
	}

	@Override
	@Transactional
	public void delete(int id) {
		Bill bill = billRe.findById(id).orElse(null);
		if (bill != null) {
			billRe.deleteById(id);
		}

	}

	@Override
	public BillDTO getbyId(int id) {
		Bill bill = billRe.findById(id).orElse(null);
		if (bill != null) {
			BillDTO billDTO = convert(bill);
			return billDTO;
		}
		return null;
	}

	public List<BillStatisticDTO> statictis() {
		return billRe.billStatistic();
	}

	private BillDTO convert(Bill bill) {
		return new ModelMapper().map(bill, BillDTO.class);
	}

	@Override
	public boolean searchByBuyDate(Date date) {
		List<Bill> bills = billRe.findByBuyDate(date);
		if(!bills.isEmpty()) {
			return true;
		}
		return false;
	}

}
