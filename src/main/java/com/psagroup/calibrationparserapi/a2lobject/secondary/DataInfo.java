package com.psagroup.calibrationparserapi.a2lobject.secondary;

//@Data
public abstract class DataInfo extends SecondaryObject {
	protected int position;
	protected DataType datatype;
	protected ReadWay readway;
	protected boolean direct;
	
	public int getbyteperread() {
		return this.getDatatype().getNbbits();
		
	}

	private DataType getDatatype() {
		return this.datatype;
	}

}
