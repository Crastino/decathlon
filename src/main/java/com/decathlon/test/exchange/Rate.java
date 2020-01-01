package com.decathlon.test.exchange;

public class Rate {

	private String country;
	private String currency;
	private String isoCode;
	private String uicCode;
	private String avgRate;
	private String exchangeConvention;
	private String exchangeConventionCode;
	private String referenceDate;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getIsoCode() {
		return isoCode;
	}
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
	public String getUicCode() {
		return uicCode;
	}
	public void setUicCode(String uicCode) {
		this.uicCode = uicCode;
	}
	public String getAvgRate() {
		return avgRate;
	}
	public void setAvgRate(String avgRate) {
		this.avgRate = avgRate;
	}
	public String getExchangeConvention() {
		return exchangeConvention;
	}
	public void setExchangeConvention(String exchangeConvention) {
		this.exchangeConvention = exchangeConvention;
	}
	public String getExchangeConventionCode() {
		return exchangeConventionCode;
	}
	public void setExchangeConventionCode(String exchangeConventionCode) {
		this.exchangeConventionCode = exchangeConventionCode;
	}
	public String getReferenceDate() {
		return referenceDate;
	}
	public void setReferenceDate(String referenceDate) {
		this.referenceDate = referenceDate;
	}

}
