package com.psagroup.calibrationparserapi.a2lobject.characteristic;

import com.psagroup.calibrationparserapi.a2lobject.Builder;
import com.psagroup.calibrationparserapi.a2lobject.axis.AxisDescr;


public class CharacteristicBuilder implements Builder {
    Characteristic charac = new Characteristic();

    @Override
    public Characteristic build() {
        Characteristic characToReturn = charac;
        this.charac = new Characteristic();
        return characToReturn;
    }


    public void label(String label) {
        charac.setLabel(label);
    }

    public void description(String description) {
        charac.setDescription(description);
    }

    public void address(String address) {
        charac.setAddress(address);
    }

    public void charType(String chartype) {
        if ("VALUE".equals(chartype)) {
            charac.setChartype(CharType.VALUE);
            //charac.value = new float[1][0];
        } else if ("CURVE".equals(chartype)) {
            charac.setChartype(CharType.CURVE);
        } else if ("MAP".equals(chartype)) {
            charac.setChartype(CharType.MAP);
        } else if ("VAL_BLK".equals(chartype)) {
            charac.setChartype(CharType.VAL_BLK);
        } else if ("CUBOID".equals(chartype)) {
            charac.setChartype(CharType.CUBOID);
        } else if ("ASCII".equals(chartype)) {
            charac.setChartype(CharType.ASCII);
        } else if ("CUBE_4".equals(chartype)) {
            charac.setChartype(CharType.CUBE_4);
        } else if ("CUBE_5".equals(chartype)) {
            charac.setChartype(CharType.CUBE_5);
        } else {
            System.err.println("Unknown type: " + chartype + " for characteristic: " + charac.getLabel());
        }
    }

    public void maxdiff(String maxdiff) {
        charac.setMaxdiff(maxdiff);
    }

    public void upper(String upper) {
        charac.setUpper(Double.parseDouble(upper));
    }

    public void lower(String lower) {
        charac.setLower(Double.parseDouble(lower));
    }

    public void format(String format) {
        if (format.split(" ").length > 1) {
            if (format.split(" ")[0].trim().equals("FORMAT")) {
                charac.setFormat(format.split(" ")[1]);
            }
        }
    }

    public void number(String number) {
        if (number.split("\\s+").length > 1) {
            if (number.split("\\s+")[0].trim().equals("NUMBER") || number.split("\\s+")[0].trim().equals("MATRIX_DIM")) {
                charac.setNumber(Integer.parseInt(number.split("\\s+")[1]));
            }
        }
    }

    public void extended_limits(String extended_limits) {
        if (extended_limits.split(" ").length > 1) {
            if (extended_limits.split(" ")[0].trim().equals("EXTENDED_LIMITS")) {
                charac.setExtended_limits((extended_limits.split(" ")[1]) + " : " + (extended_limits.split(" ")[2]));
            }
        }
    }

    public void refCompuMethod(String refCompuMethod) {
        charac.setRefComputMethod(refCompuMethod);
    }

    public void refRecordLayout(String refRecordLayout) {
        charac.setRefRecordLayout(refRecordLayout);
    }

    public void addAxis(AxisDescr axis) {
        charac.getAxises().add(axis);
    }

    public void displayCharac() {
        System.out.println(charac.toString());
    }


}
