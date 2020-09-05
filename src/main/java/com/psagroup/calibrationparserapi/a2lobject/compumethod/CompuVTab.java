package com.psagroup.calibrationparserapi.a2lobject.compumethod;

import java.util.*;

import com.psagroup.calibrationparserapi.a2lobject.PrimaryObject;


//@Data
public class CompuVTab extends PrimaryObject {
	private String label;
	private String description;
	private ConversionType type;
	int nbofStrings;
	HashMap<Double, String> enu =  new HashMap<Double, String>();

	public void printCompuVTabInfo(){
		String info_str = String.format("label: %s, description: %s, type:%s, nbofStrings:%s", label, description, type, nbofStrings);
		System.out.println("CompuVTab Info: " + info_str);
		for(Double k : enu.keySet()){
			System.out.print(k + " " + enu.get(k));
		}
		System.out.println();
	}

	public String getLabel() {
		return label;
	}

	public ConversionType getType() {
		return type;
	}

	public int getNbofStrings() {
		return nbofStrings;
	}

	public String getDescription() {
		return description;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(ConversionType identical) {
		this.type = identical;
	}

	public void setNbofStrings(int parseInt) {
		this.nbofStrings = parseInt;
	}

	public HashMap<Double, String> getEnu() {
		return this.enu;
	}
}
