package com.project3_thuchanhweb.jobSchedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project3_thuchanhweb.service.BillService;
import com.project3_thuchanhweb.service.EmailService;

@Component
public class JobSchedule {
	@Autowired
	BillService billSe;
	
	@Autowired
	EmailService emailSe;
	
	@Scheduled(fixedDelay = 1000 * 60 * 5)
	public void sendEmail() {
		
		Date dateCreateBill = new Date();
		dateCreateBill.setTime(dateCreateBill.getTime() - 1000 * 60 * 5);
		
		boolean check = billSe.searchByBuyDate(dateCreateBill);
		if(check == true) {
			emailSe.sendJobSchedule("vdat224187@gmail.com");
		}
	}
}
