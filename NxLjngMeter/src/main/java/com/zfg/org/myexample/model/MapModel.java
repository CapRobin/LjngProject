package com.zfg.org.myexample.model;

import java.util.ArrayList;
import java.util.List;

public class MapModel {

	private int type;
	private String key;
	private String value;
	private float price;
	private int strength;

	private String heatLevel;

	private List<String> child;

	public MapModel(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public MapModel(int type, String key, String value) {
		this.type = type;
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public List<String> getChild() {
		return child;
	}

	public void setChild(List<String> child) {
		this.child = child;
	}

	public String getHeatLevel() {
		return heatLevel;
	}

	public void setHeatLevel(String heatLevel) {
		this.heatLevel = heatLevel;
	}
	
	public void addList(String test){
		child = new ArrayList<String>();
		child.add(test);
	}

}
