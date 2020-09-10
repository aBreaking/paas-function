package com.sitech.esb.hb;


public class ExcelStyle {

	private Long colm_style_id;
	
	private String colm_style_name;
	
	private Long colm_style_parentId;
	
	private String colm_style_display;
	
	private String colm_style_default;
	
	private SrvType  colm_style_type;
	
	private String colm_style_flag;
	
	private Long colm_style_position;
	
	private String colm_cont_type;
	
	private String colm_style_template;
	
	public Long getColm_style_position() {
		return colm_style_position;
	}

	public void setColm_style_position(Long colmStylePosition) {
		colm_style_position = colmStylePosition;
	}

	public String getColm_style_flag() {
		return colm_style_flag;
	}

	public void setColm_style_flag(String colmStyleFlag) {
		colm_style_flag = colmStyleFlag;
	}

	public ExcelStyle() {
		super();
	}

	public SrvType getColm_style_type() {
		return colm_style_type;
	}

	public void setColm_style_type(SrvType colmStyleType) {
		colm_style_type = colmStyleType;
	}

	

	public String getColm_cont_type() {
		return colm_cont_type;
	}

	public void setColm_cont_type(String colmContType) {
		colm_cont_type = colmContType;
	}

	public Long getColm_style_id() {
		return colm_style_id;
	}

	public void setColm_style_id(Long colmStyleId) {
		colm_style_id = colmStyleId;
	}

	public String getColm_style_name() {
		return colm_style_name;
	}

	public void setColm_style_name(String colmStyleName) {
		colm_style_name = colmStyleName;
	}

	public Long getColm_style_parentId() {
		return colm_style_parentId;
	}

	public void setColm_style_parentId(Long colmStyleParentId) {
		colm_style_parentId = colmStyleParentId;
	}

	public String getColm_style_display() {
		return colm_style_display;
	}

	public void setColm_style_display(String colmStyleDisplay) {
		colm_style_display = colmStyleDisplay;
	}

	public String getColm_style_default() {
		return colm_style_default;
	}

	public void setColm_style_default(String colmStyleDefault) {
		colm_style_default = colmStyleDefault;
	}
	
	public String getColm_style_template() {
		return colm_style_template;
	}

	public void setColm_style_template(String colm_style_template) {
		this.colm_style_template = colm_style_template;
	}

	public static void main(String[] args) {
		String str = "class java.lang.String";
		
		System.out.println(str.substring(str.lastIndexOf('.')+1,str.length()));
	}
}
