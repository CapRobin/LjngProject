package com.zfg.org.myexample.dto;

public class DataModel {

	public int sex;
	public String code;
	public String question;
	public int method_question;
	public int index;
	public String answer;
	public String value_mid;
	public String unit;

	public boolean isCheck() {
		if ("true".equals(answer)) {
			return true;
		}
		return false;
	}
}
