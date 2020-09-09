package com.psagroup.calibrationparserapi.a2lobject.axis;

import com.psagroup.calibrationparserapi.a2lobject.PrimaryObject;

import com.psagroup.calibrationparserapi.parser.ulp.SRecord;
//import lombok.Data;

public class AxisDescr extends PrimaryObject {
	AxisType type;
	String address, inputquantity ;
	String refCompuMethod;
	int nbpoints;
	float lower, upper;
	
	String format, extended_limits, refAxisPts;
	
	double[] fixAxisPar;

	public String getInputquantity(){ return inputquantity;}

	void setType(AxisType axisType){
		this.type = axisType;
	}

	void setAddress(String address){
		this.address = address;
	}

	void setInputquantity(String inputquantity) {
		this.inputquantity = inputquantity;
	}

	 void setRefCompuMethod(String refCompuMethod){
		this.refCompuMethod = refCompuMethod;
	 }

	 void setNbpoints(int nbpoints) {
		this.nbpoints = nbpoints;
	 }

	 void setLower(float lower) {
		this.lower = lower;
	 }

	 void setUpper(float upper) {
		this.upper = upper;
	 }

	 void setFormat(String format) {
		this.format = format;
	 }

	 void setExtended_limits(String extended_limits){
		this.extended_limits = extended_limits;
	 }

	 void setRefAxisPts(String refAxisPts) {this.refAxisPts = refAxisPts;}

	 void setFixAxisPar(double[] fixAxisPar) {this.fixAxisPar = fixAxisPar;}

	public AxisType getType() {
		return type;
	}

	public int getNbpoints() {
		return nbpoints;
	}

	public double[] getFixAxisPar() {
		return fixAxisPar;
	}

	public String getRefCompuMethod() {
		return refCompuMethod;
	}

	public String getRefAxisPts() {
		return refAxisPts;
	}
}
