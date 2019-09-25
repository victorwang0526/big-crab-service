package com.victor.wang.bigCrab.sf.dto;

import java.math.BigDecimal;

public class CargoInfoDto {
	private String cargo;
	private Integer parcelQuantity;
	private Integer cargoCount;
	private String cargoUnit;
	private BigDecimal cargoWeight;
	private BigDecimal cargoAmount;
	private BigDecimal cargoTotalWeight;
	private String remark;
	private String sku;

	public CargoInfoDto() {
	}

	public String getCargo() {
		return this.cargo;
	}

	public Integer getParcelQuantity() {
		return this.parcelQuantity;
	}

	public Integer getCargoCount() {
		return this.cargoCount;
	}

	public String getCargoUnit() {
		return this.cargoUnit;
	}

	public BigDecimal getCargoWeight() {
		return this.cargoWeight;
	}

	public BigDecimal getCargoAmount() {
		return this.cargoAmount;
	}

	public BigDecimal getCargoTotalWeight() {
		return this.cargoTotalWeight;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public void setParcelQuantity(Integer parcelQuantity) {
		this.parcelQuantity = parcelQuantity;
	}

	public void setCargoCount(Integer cargoCount) {
		this.cargoCount = cargoCount;
	}

	public void setCargoUnit(String cargoUnit) {
		this.cargoUnit = cargoUnit;
	}

	public void setCargoWeight(BigDecimal cargoWeight) {
		this.cargoWeight = cargoWeight;
	}

	public void setCargoAmount(BigDecimal cargoAmount) {
		this.cargoAmount = cargoAmount;
	}

	public void setCargoTotalWeight(BigDecimal cargoTotalWeight) {
		this.cargoTotalWeight = cargoTotalWeight;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}

