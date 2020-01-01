package com.decathlon.test.exchange;

import java.util.List;

public class Rates {

	private ResultInfo resultsInfo;
	private List<Rate> rates;

	
	public ResultInfo getResultsInfo() {
		return resultsInfo;
	}

	public void setResultsInfo(ResultInfo resultsInfo) {
		this.resultsInfo = resultsInfo;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> ratesList) {
		this.rates = ratesList;
	}
	
}
