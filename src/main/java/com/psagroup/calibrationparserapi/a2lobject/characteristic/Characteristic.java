package com.psagroup.calibrationparserapi.a2lobject.characteristic;


import java.awt.*;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.JsonNode;
import com.psagroup.calibrationparserapi.a2lobject.PrimaryObject;
import com.psagroup.calibrationparserapi.a2lobject.axis.AxisDescr;

import com.psagroup.calibrationparserapi.parser.hex.MemoryRegions;
//import lombok.Data;


public class Characteristic extends PrimaryObject {
	String label,address,description, maxdiff, format, extended_limits;
	double upper, lower ;
	int number;
	CharType chartype;
	
	String refComputMethod;
	String refRecordLayout;
	
	LinkedList<AxisDescr> axises = new LinkedList<>();
	
	JsonNode result;

	public void getCharacteristicInfo(){
		System.out.println("Characteristic Info: ");
		String info_str = String.format("label:%s , address:%s , description:%s , maxdiff:%s , format:%s , extended_limits:%s"
		,label,address,description, maxdiff, format, extended_limits);

		String info_str1 = String.format("upper:%f , lower:%f , number:%d , chartype:%s , refComputMethod:%s , refRecordLayout:%s "
				, upper, lower , number , chartype , refComputMethod , refRecordLayout);

		System.out.println(info_str);
		System.out.println(info_str1);
	}


	public void setLabel(String label) {
		this.label = label;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setChartype(CharType value) { this.chartype = value; }

	public String getLabel() {
		return this.label;
	}

	public void setMaxdiff(String maxdiff) {
		this.maxdiff = maxdiff;
	}

	public void setUpper(double parseDouble) {
		this.upper = parseDouble;
	}

	public void setLower(double parseDouble) {
		this.lower = parseDouble;
	}

	public void setFormat(String s) {
		this.format = s;
	}

	public void setNumber(int parseInt) {
		this.number = parseInt;
	}

	public void setExtended_limits(String s) {
		this.extended_limits = s;
	}

	public void setRefComputMethod(String refCompuMethod) {
		this.refComputMethod = refCompuMethod;
	}

	public void setRefRecordLayout(String refRecordLayout) {
		this.refRecordLayout = refRecordLayout;
	}

	public void setResult(JsonNode result) {
		this.result = result;
	}

	public LinkedList<AxisDescr> getAxises() {
		return axises;
	}

	public CharType getChartype() {
		return chartype;
	}

	public String getRefComputMethod() {
		return refComputMethod;
	}

	public String getRefRecordLayout() { return refRecordLayout; }

	public String getAddress() { return address; }

	public int getNumber() {
		return number;
	}

	public JsonNode getResult() {
		if(result != null) {
			return result;
		}
		return null;
	}

	public String getDescription() { return description;}

	public double getLower() { return lower ; }

	public double getUpper() { return upper; }

	public String getExtended_limits() {
		return extended_limits;
	}

	public String getFormat() {
		return format;
	}

	public String getMaxdiff() {
		return maxdiff;
	}
}
