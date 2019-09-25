package com.victor.wang.bigCrab.sf.dto;


import java.util.List;

public class WaybillDto {
	private String mailNo;
	private int expressType;
	private int payMethod;
	private String returnTrackingNo;
	private String monthAccount;
	private String orderNo;
	private String zipCode;
	private String destCode;
	private String payArea;
	private String deliverCompany;
	private String deliverName;
	private String deliverMobile;
	private String deliverTel;
	private String deliverProvince;
	private String deliverCity;
	private String deliverCounty;
	private String deliverAddress;
	private String deliverShipperCode;
	private String consignerCompany;
	private String consignerName;
	private String consignerMobile;
	private String consignerTel;
	private String consignerProvince;
	private String consignerCity;
	private String consignerCounty;
	private String consignerAddress;
	private String consignerShipperCode;
	private String logo;
	private String sftelLogo;
	private String topLogo;
	private String topsftelLogo;
	private String custLogo;
	private String totalFee;
	private String appId;
	private String appKey;
	private String electric;
	private List<CargoInfoDto> cargoInfoDtoList;
	private String insureValue;
	private String insureFee;
	private String codValue;
	private String codMonthAccount;
	private boolean encryptMobile = false;
	private boolean encryptCustName = false;
	private List<RlsInfoDto> rlsInfoDtoList;
	private String mainRemark;
	private String childRemark;
	private String returnTrackingRemark;

	public WaybillDto() {
	}

	public String getMailNo() {
		return this.mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getReturnTrackingNo() {
		return this.returnTrackingNo;
	}

	public void setReturnTrackingNo(String returnTrackingNo) {
		this.returnTrackingNo = returnTrackingNo;
	}

	public String getDeliverCompany() {
		return this.deliverCompany;
	}

	public void setDeliverCompany(String deliverCompany) {
		this.deliverCompany = deliverCompany;
	}

	public String getDeliverName() {
		return this.deliverName;
	}

	public void setDeliverName(String deliverName) {
		this.deliverName = deliverName;
	}

	public String getDeliverMobile() {
		return this.deliverMobile;
	}

	public void setDeliverMobile(String deliverMobile) {
		this.deliverMobile = deliverMobile;
	}

	public String getDeliverTel() {
		return this.deliverTel;
	}

	public void setDeliverTel(String deliverTel) {
		this.deliverTel = deliverTel;
	}

	public String getDeliverProvince() {
		return this.deliverProvince;
	}

	public void setDeliverProvince(String deliverProvince) {
		this.deliverProvince = deliverProvince;
	}

	public String getDeliverCity() {
		return this.deliverCity;
	}

	public void setDeliverCity(String deliverCity) {
		this.deliverCity = deliverCity;
	}

	public String getDeliverCounty() {
		return this.deliverCounty;
	}

	public void setDeliverCounty(String deliverCounty) {
		this.deliverCounty = deliverCounty;
	}

	public String getDeliverAddress() {
		return this.deliverAddress;
	}

	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}

	public String getDeliverShipperCode() {
		return this.deliverShipperCode;
	}

	public void setDeliverShipperCode(String deliverShipperCode) {
		this.deliverShipperCode = deliverShipperCode;
	}

	public String getConsignerCompany() {
		return this.consignerCompany;
	}

	public void setConsignerCompany(String consignerCompany) {
		this.consignerCompany = consignerCompany;
	}

	public String getConsignerName() {
		return this.consignerName;
	}

	public void setConsignerName(String consignerName) {
		this.consignerName = consignerName;
	}

	public String getConsignerMobile() {
		return this.consignerMobile;
	}

	public void setConsignerMobile(String consignerMobile) {
		this.consignerMobile = consignerMobile;
	}

	public String getConsignerTel() {
		return this.consignerTel;
	}

	public void setConsignerTel(String consignerTel) {
		this.consignerTel = consignerTel;
	}

	public String getConsignerProvince() {
		return this.consignerProvince;
	}

	public void setConsignerProvince(String consignerProvince) {
		this.consignerProvince = consignerProvince;
	}

	public String getConsignerCity() {
		return this.consignerCity;
	}

	public void setConsignerCity(String consignerCity) {
		this.consignerCity = consignerCity;
	}

	public String getConsignerCounty() {
		return this.consignerCounty;
	}

	public void setConsignerCounty(String consignerCounty) {
		this.consignerCounty = consignerCounty;
	}

	public String getConsignerAddress() {
		return this.consignerAddress;
	}

	public void setConsignerAddress(String consignerAddress) {
		this.consignerAddress = consignerAddress;
	}

	public String getConsignerShipperCode() {
		return this.consignerShipperCode;
	}

	public void setConsignerShipperCode(String consignerShipperCode) {
		this.consignerShipperCode = consignerShipperCode;
	}

	public String getInsureValue() {
		return this.insureValue;
	}

	public void setInsureValue(String insureValue) {
		this.insureValue = insureValue;
	}

	public String getCodValue() {
		return this.codValue;
	}

	public void setCodValue(String codValue) {
		this.codValue = codValue;
	}

	public String getMonthAccount() {
		return this.monthAccount;
	}

	public void setMonthAccount(String monthAccount) {
		this.monthAccount = monthAccount;
	}

	public List<CargoInfoDto> getCargoInfoDtoList() {
		return this.cargoInfoDtoList;
	}

	public void setCargoInfoDtoList(List<CargoInfoDto> cargoInfoDtoList) {
		this.cargoInfoDtoList = cargoInfoDtoList;
	}

	public int getExpressType() {
		return this.expressType;
	}

	public int getPayMethod() {
		return this.payMethod;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public String getDestCode() {
		return this.destCode;
	}

	public String getPayArea() {
		return this.payArea;
	}

	public void setExpressType(int expressType) {
		this.expressType = expressType;
	}

	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	public void setPayArea(String payArea) {
		this.payArea = payArea;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSftelLogo() {
		return this.sftelLogo;
	}

	public void setSftelLogo(String sftelLogo) {
		this.sftelLogo = sftelLogo;
	}

	public String getElectric() {
		return this.electric;
	}

	public void setElectric(String electric) {
		this.electric = electric;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getTopLogo() {
		return this.topLogo;
	}

	public void setTopLogo(String topLogo) {
		this.topLogo = topLogo;
	}

	public String getTopsftelLogo() {
		return this.topsftelLogo;
	}

	public void setTopsftelLogo(String topsftelLogo) {
		this.topsftelLogo = topsftelLogo;
	}

	public String getCodMonthAccount() {
		return this.codMonthAccount;
	}

	public void setCodMonthAccount(String codMonthAccount) {
		this.codMonthAccount = codMonthAccount;
	}

	public String getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public boolean isEncryptMobile() {
		return this.encryptMobile;
	}

	public void setEncryptMobile(boolean encryptMobile) {
		this.encryptMobile = encryptMobile;
	}

	public boolean isEncryptCustName() {
		return this.encryptCustName;
	}

	public void setEncryptCustName(boolean encryptCustName) {
		this.encryptCustName = encryptCustName;
	}

	public String getInsureFee() {
		return this.insureFee;
	}

	public void setInsureFee(String insureFee) {
		this.insureFee = insureFee;
	}

	public String getCustLogo() {
		return this.custLogo;
	}

	public void setCustLogo(String custLogo) {
		this.custLogo = custLogo;
	}

	public List<RlsInfoDto> getRlsInfoDtoList() {
		return this.rlsInfoDtoList;
	}

	public void setRlsInfoDtoList(List<RlsInfoDto> rlsInfoDtoList) {
		this.rlsInfoDtoList = rlsInfoDtoList;
	}

	public String getReturnTrackingRemark() {
		return this.returnTrackingRemark;
	}

	public void setReturnTrackingRemark(String returnTrackingRemark) {
		this.returnTrackingRemark = returnTrackingRemark;
	}

	public String getMainRemark() {
		return this.mainRemark;
	}

	public void setMainRemark(String mainRemark) {
		this.mainRemark = mainRemark;
	}

	public String getChildRemark() {
		return this.childRemark;
	}

	public void setChildRemark(String childRemark) {
		this.childRemark = childRemark;
	}
}
