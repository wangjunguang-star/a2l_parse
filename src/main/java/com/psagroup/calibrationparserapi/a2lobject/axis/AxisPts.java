package com.psagroup.calibrationparserapi.a2lobject.axis;

import com.psagroup.calibrationparserapi.a2lobject.PrimaryObject;

//import lombok.Data;

//@Data
public class AxisPts extends PrimaryObject {
	String address,description,label,maxdiff, inputquantity, format;
	double upper, lower ;
	int nbpoints;
	boolean absolute;
	String refComputMethod;
	String refRecordLayout;

	/**
	 * for debug
	 */
	public void printAxisPtsInfo() {
		System.out.println("AxisPts Info:");
		String info_str = String.format("address:%s, description:%s, label:%s, maxdiff:%s, inputquantity:%s" +
				", format:%s, upper:%f, lower:%f, nbpoints:%d, absolute:%s, refComputMethod:%s, refRecordLayout:%s",
				address,description,label,maxdiff, inputquantity, format, upper, lower, nbpoints, absolute, refComputMethod, refRecordLayout);
		System.out.println(info_str);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) { this.label = label; }

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setMaxdiff(String maxdiff) {
		this.maxdiff = maxdiff;
	}

	public void setInputquantity(String inputquantity) {
		this.inputquantity = inputquantity;
	}

	public void setUpper(double parseDouble) {
		this.upper = parseDouble;
	}

	public void setLower(double parseDouble) {
		this.lower = parseDouble;
	}

	public void setNbpoints(int parseInt) {
		this.nbpoints = parseInt;
	}

	public void setRefComputMethod(String refComputMethod) {
		this.refComputMethod = refComputMethod;
	}

	public void setRefRecordLayout(String refRecordLayout) {
		this.refRecordLayout = refRecordLayout;
	}

	public void setFormat(String s) {
		this.format = s;
	}

	public void setAbsolute(boolean b) {
		this.absolute = b;
	}

	public int getNbpoints() {
		return nbpoints;
	}

	public String getRefRecordLayout() {
		return refRecordLayout;
	}
	public String getRefComputMethod() {
		return refComputMethod;
	}

	public String getAddress() {
		return address;
	}
}
