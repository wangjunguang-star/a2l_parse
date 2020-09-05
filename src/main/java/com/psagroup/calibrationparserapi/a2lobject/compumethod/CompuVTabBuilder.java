package com.psagroup.calibrationparserapi.a2lobject.compumethod;

import com.psagroup.calibrationparserapi.a2lobject.Builder;

import java.io.BufferedReader;
import java.io.IOException;

public class CompuVTabBuilder implements Builder {
    private CompuVTab comp = new CompuVTab();

    public CompuVTab build() {
        CompuVTab compToReturn = comp;
        this.comp = new CompuVTab();
        return compToReturn;
    }

    public void label(String label) {
        this.comp.setLabel(label);
    }

    public void description(String description) {
        this.comp.setDescription(description);
    }

    public void type(String functiontype) {
        if ("IDENTICAL".equals(functiontype)) {
            this.comp.setType(ConversionType.IDENTICAL);
        } else if ("LINEAR".equals(functiontype)) {
            this.comp.setType(ConversionType.LINEAR);
        } else if ("RAT_FUNC".equals(functiontype)) {
            this.comp.setType(ConversionType.RAT_FUNC);
        } else if ("TAB_INTP".equals(functiontype)) {
            this.comp.setType(ConversionType.TAB_INTP);
        } else if ("TAB_NOINTP".equals(functiontype)) {
            this.comp.setType(ConversionType.TAB_NOINTP);
        } else if ("TAB_VERB".equals(functiontype)) {
            this.comp.setType(ConversionType.TAB_VERB);
        } else if ("FORM".equals(functiontype)) {
            this.comp.setType(ConversionType.FORM);
        }
    }

    public void nbofStrings(String nbofStrings) {
        this.comp.setNbofStrings(Integer.parseInt(nbofStrings));
    }

    public void enu(BufferedReader reader) throws IOException {
        String line = reader.readLine().trim();
        while (line.split("\\s+")[0].matches("\\d+")) {
            this.comp.getEnu().put(Double.parseDouble(line.split("\\s+")[0]), line.substring(line.split("\\s+")[0].length() + 1));
            line = reader.readLine().trim();
        }
    }

    public void enuLR(String[] pair) throws IOException {
        this.comp.getEnu().put(Double.parseDouble(pair[0]), pair[1]);
    }


    /**
     * @return the comp
     */
    public CompuVTab getComp() {
        return comp;
    }


}
