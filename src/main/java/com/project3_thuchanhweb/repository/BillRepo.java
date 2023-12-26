package com.project3_thuchanhweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project3_thuchanhweb.dto.BillStatisticDTO;
import com.project3_thuchanhweb.entity.Bill;
import java.util.Date;


public interface BillRepo extends JpaRepository<Bill, Integer> {
	// thong ke so luong bill theo thang va nam
		@Query("SELECT new com.project3_thuchanhweb.dto.BillStatisticDTO(count(b.id), MONTH(b.buyDate), YEAR(b.buyDate)) " +
			       "FROM Bill b " +
			       "GROUP BY MONTH(b.buyDate), YEAR(b.buyDate)")
		public List<BillStatisticDTO> billStatistic();
		
	// tim don theo ngay mua
		@Query("SELECT b from Bill b WHERE b.buyDate >:buyDate")
		public List<Bill> findByBuyDate(@Param("buyDate") Date buyDate);
}
