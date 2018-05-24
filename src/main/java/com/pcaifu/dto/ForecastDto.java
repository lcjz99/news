package com.pcaifu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

@ApiModel(value = "发布预测参数")
public class ForecastDto implements Serializable {

	private static final long serialVersionUID = 7181139752198637787L;

	@NotBlank
	@ApiModelProperty(name = "userId", required = true, value = "用户id", dataType = "String")
	private String userId;

	@NotBlank
	@ApiModelProperty(name = "nickName", required = true, value = "用户昵称", dataType = "String")
	private String nickName;

	@NotBlank
	@ApiModelProperty(name = "mobile", required = true, value = "户手机号", dataType = "String")
	private String mobile;

	@NotBlank
	@ApiModelProperty(name = "fromType", required = true, value = "预测来源", dataType = "String")
	private String fromType;

	@NotBlank
	@ApiModelProperty(name = "stockCode", required = true, value = "股票代码", dataType = "String")
	private String stockCode;

	@NotBlank
	@ApiModelProperty(name = "stockName", required = true, value = "股票名称", dataType = "String")
	private String stockName;

	@NotBlank
	@ApiModelProperty(name = "periodType", required = true, value = "预测时长", dataType = "String")
	private String periodType;

	@NotBlank
	@ApiModelProperty(name = "forecastType", required = true, value = "预测趋势", dataType = "String")
	private String forecastType;

	@NotBlank
	@ApiModelProperty(name = "expectDate", required = true, value = "预计开奖日期", dataType = "String")
	private String expectDate;

	@NotBlank
	@ApiModelProperty(name = "currentValue", required = true, value = "当前价格", dataType = "String")
	private String currentValue;

	@ApiModelProperty(name = "forecastValue", required = false, value = "预测价格", dataType = "String")
	private String forecastValue;

	@ApiModelProperty(name = "forecastCond", required = true, value = "预测依据", dataType = "String")
	private String forecastCond;

	@ApiModelProperty(name = "refArticleId", required = false, value = "参考文章ID", dataType = "String")
	private String refArticleId;

	@ApiModelProperty(name = "forecastNote", required = false, value = "交易笔记", dataType = "String")
	private String forecastNote;

	@ApiModelProperty(name = "pkSerial", required = false, value = "pk场次", dataType = "String")
	private String pkSerial;

	@ApiModelProperty(name = "isNew", required = false, value = "是否新用户", dataType = "String")
	private String isNew;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getForecastType() {
		return forecastType;
	}

	public void setForecastType(String forecastType) {
		this.forecastType = forecastType;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(String expectDate) {
		this.expectDate = expectDate;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getForecastValue() {
		return forecastValue;
	}

	public void setForecastValue(String forecastValue) {
		this.forecastValue = forecastValue;
	}

	public String getForecastCond() {
		return forecastCond;
	}

	public void setForecastCond(String forecastCond) {
		this.forecastCond = forecastCond;
	}

	public String getRefArticleId() {
		return refArticleId;
	}

	public void setRefArticleId(String refArticleId) {
		this.refArticleId = refArticleId;
	}

	public String getForecastNote() {
		return forecastNote;
	}

	public void setForecastNote(String forecastNote) {
		this.forecastNote = forecastNote;
	}

	public String getPkSerial() {
		return pkSerial;
	}

	public void setPkSerial(String pkSerial) {
		this.pkSerial = pkSerial;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

}
