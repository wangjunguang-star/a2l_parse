package com.psagroup.calibrationparserapi.a2lobject.recordlayout;

import com.psagroup.calibrationparserapi.a2lobject.PrimaryObject;

//@Data
public class RecordLayout extends PrimaryObject {
    String label;
    boolean rowdir;
    DataType fncvalue, axisptsx, axisptsy, noaxisptsx, noaxisptsy;

    public void printRecordLayoutInfo(){
        String info_str = String.format("label: %s ; rowdir: %s ; fncvalue: %s, axisptsx: %s, axisptsy: %s ; noaxisptsx: %s ; noaxisptsy:%s ",
                label, rowdir, fncvalue, axisptsx, axisptsy, noaxisptsx, noaxisptsy);
        System.out.println("RecordLayout Info : " + info_str);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setFncvalue(DataType ubyte) {
        this.fncvalue = ubyte;
    }

    public void setRowdir(boolean b) {
        this.rowdir = b;
    }

    public void setAxisptsx(DataType ubyte) { this.axisptsx = ubyte; }

    public void setAxisptsy(DataType ubyte) {
        this.axisptsy = ubyte;
    }

    public void setNoaxisptsx(DataType ubyte) {
        this.noaxisptsx = ubyte;
    }

    public void setNoaxisptsy(DataType ubyte) {
        this.noaxisptsy = ubyte;
    }

    public DataType getFncvalue() { return fncvalue; }

    public DataType getAxisptsx() { return axisptsx; }

    public DataType getAxisptsy() {
        return axisptsy;
    }

    public DataType getNoaxisptsx() {
        return noaxisptsx;
    }

    public DataType getNoaxisptsy() {
        return noaxisptsy;
    }

    public String getLabel() {
        return label;
    }

    public boolean isRowdir() {
        return rowdir;
    }
}
