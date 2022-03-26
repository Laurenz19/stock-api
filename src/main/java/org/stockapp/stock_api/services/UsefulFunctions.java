package org.stockapp.stock_api.services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UsefulFunctions {
	
	public String formatDate(Date date) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		return simpleDateFormat.format(date);
	}
	
	public int new_stock(int ancien_stock, int qteEntree, int qteSortie) {
		return ancien_stock + qteEntree - qteSortie;
	}
}
