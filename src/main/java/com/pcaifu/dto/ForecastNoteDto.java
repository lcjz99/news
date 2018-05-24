package com.pcaifu.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

@ApiModel(value = "发布预测参数")
public class ForecastNoteDto implements Serializable {

	private static final long serialVersionUID = -2797224723516463993L;

	@ApiModelProperty(name = "noteId", required = false, value = "笔记id", dataType = "String")
	private String noteId;

	@NotBlank
	@ApiModelProperty(name = "forcastId", required = true, value = "预测记录ID", dataType = "String")
	private String forcastId;

	@NotBlank
	@ApiModelProperty(name = "optType", required = true, value = "操作类型", dataType = "String")
	private String optType;

	@NotBlank
	@ApiModelProperty(name = "noteContent", required = true, value = "笔记内容", dataType = "String")
	private String noteContent;

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getForcastId() {
		return forcastId;
	}

	public void setForcastId(String forcastId) {
		this.forcastId = forcastId;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

}
