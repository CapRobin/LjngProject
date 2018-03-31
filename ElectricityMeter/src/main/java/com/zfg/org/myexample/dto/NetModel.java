package com.zfg.org.myexample.dto;

public class NetModel {

	private String day;
	private float eat;
	private float sport;
	
	public NetModel(String day,float eat,float sport){
		this.day = day;
		this.eat = eat;
		this.sport = sport;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public float getEat() {
		return eat;
	}

	public void setEat(float eat) {
		this.eat = eat;
	}

	public float getSport() {
		return sport;
	}

	public void setSport(float sport) {
		this.sport = sport;
	}

	public float sub(float basic){
		return eat - sport - basic;
	}
}
