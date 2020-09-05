package com.psagroup.calibrationparserapi.a2lobject.axis;

import com.psagroup.calibrationparserapi.a2lobject.Builder;

public class AxisDescrBuilder implements Builder {
    private AxisDescr axis = new AxisDescr();

    public AxisDescr build() {
        AxisDescr axisToReturn = axis;
        this.axis = new AxisDescr();
        return axisToReturn;
    }

    public void type(String type) {
        if ("STD_AXIS".equals(type)) {
            axis.setType(AxisType.STD_AXIS);
        } else if ("COM_AXIS".equals(type)) {
            axis.setType(AxisType.COM_AXIS);
        } else if ("FIX_AXIS".equals(type)) {
            axis.setType(AxisType.FIX_AXIS);
        } else if ("CURVE_AXIS".equals(type)) {
            axis.setType(AxisType.CURVE_AXIS);
        } else if ("RES_AXIS".equals(type)) {
            axis.setType(AxisType.RES_AXIS);
        }
    }

    public void address(String address) {
        axis.setAddress(address);
    }

    public void inputquantity(String inputquantity) {
        axis.setInputquantity(inputquantity);
    }

    public void nbpoints(String nbpoints) {
        axis.setNbpoints(Integer.parseInt(nbpoints));
    }

    public void lower(String lower) {
        axis.setLower(Float.parseFloat(lower));
    }

    public void upper(String upper) {
        axis.setUpper(Float.parseFloat(upper));
    }

    public void refCompuMethod(String refCompuMethod) {
        axis.setRefCompuMethod(refCompuMethod);
    }

    public void format(String format) {
        if (format.split("\\s+").length > 1) {
            if (format.split("\\s+")[0].trim().equals("FORMAT")) {
                axis.setFormat(format.split("\\s+")[1]);
            }
        }
    }

    public void extended_limits(String extended_limits) {
        if (extended_limits.split("\\s+").length > 1) {
            if (extended_limits.split("\\s+")[0].trim().equals("EXTENDED_LIMITS")) {
                axis.setExtended_limits((extended_limits.split("\\s+")[1]) + " : " + (extended_limits.split("\\s+")[2]));
            }
        }
    }

    public void refaxispts(String refaxispts) {
        if (refaxispts.split("\\s+").length > 1) {
            if (refaxispts.split("\\s+")[0].trim().equals("AXIS_PTS_REF")) {
                axis.setRefAxisPts(refaxispts.split("\\s+")[1]);
            }
        }
    }

    public void fixaxispts(String fixaxispts) {
        double tab[] = new double[3];
        if (fixaxispts.split("\\s+").length > 1) {
            tab[0] = Double.parseDouble(fixaxispts.split("\\s+")[1]);
            tab[2] = Double.parseDouble(fixaxispts.split("\\s+")[3]);
            if ("FIX_AXIS_PAR".equals(fixaxispts.split("\\s+")[0].trim())) {
                tab[1] = Math.pow(2, Double.parseDouble(fixaxispts.split("\\s+")[2]));
            } else if ("FIX_AXIS_PAR_DIST".equals(fixaxispts.split("\\s+")[0].trim())) {
                tab[1] = Double.parseDouble(fixaxispts.split("\\s+")[2]);
            } else {
                System.err.println("Ce n'est pas encore géré");
            }
            axis.setFixAxisPar(tab);
        }

    }


}
