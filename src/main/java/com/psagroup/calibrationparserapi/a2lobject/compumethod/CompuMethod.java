package com.psagroup.calibrationparserapi.a2lobject.compumethod;

import com.psagroup.calibrationparserapi.a2lobject.PrimaryObject;
import com.psagroup.calibrationparserapi.a2lobject.characteristic.CharType;

//@Data
public class CompuMethod extends PrimaryObject {
    private String label;
    private String description;
    private ConversionType type;
    private String format;
    private String unit;
    private String refcomputab;

    private double[] coeffs;

    public void printCompuMethodInfo() {
        //System.out.println("CompuMethod Info");
        String info_str = String.format( "label: %s, description: %s, type: %s, format: %s, unit: %s, refcomputab: %s"
                , label, description, type, format, unit, refcomputab);
        //System.out.println(info_str);
        if(coeffs != null) {
            for (double coeff : coeffs) {
                //System.out.print(coeff + " ");
            }
            System.out.println();
        }

    }

    public double rat_func(double x) {
        double top = 0;
        double bottom = 1;
        if (coeffs[4] == 0) {
            top = coeffs[5] * x - coeffs[2];
            bottom = coeffs[1];

        } else if (coeffs[4] != 0 && coeffs[5] == 0 && coeffs[1] == 0) {
            top = coeffs[2];
            bottom = coeffs[4] * x;
        }

        if (bottom == 0) {
            System.err.println("Rat Func pas adaptee pour les coeffs (denominatuer = 0) pour la COMP_METHOD " + label);
        }
        return top / bottom;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setType(ConversionType identical) {
        this.type = identical;
    }

    public void setCoeffs(double[] tab) {
        this.coeffs = tab;
    }

    public void setRefcomputab(String trim) {
        this.refcomputab = trim;
    }

    public String getLabel() {
        return this.label;
    }

    public ConversionType getType() {
        return this.type;
    }

    public String getUnit() {
        return  this.unit;
    }

    public String getRefcomputab() {
        return refcomputab;
    }

}


