package com.zfg.org.myexample.model;

public class TimeModel {

	private int type;
	private int subType;
	private String name;

	public TimeModel(int type, int subType, String name) {
		this.type = type;
		this.subType = subType;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
