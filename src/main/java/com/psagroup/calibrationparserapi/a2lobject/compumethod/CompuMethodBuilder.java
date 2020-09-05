package com.psagroup.calibrationparserapi.a2lobject.compumethod;

import com.psagroup.calibrationparserapi.a2lobject.Builder;


public class CompuMethodBuilder implements Builder {
    private CompuMethod comp = new CompuMethod();

    public CompuMethod build() {
        CompuMethod compToReturn = comp;
        this.comp = new CompuMethod();
        return compToReturn;
    }

    public void label(String label) {
        this.comp.setLabel(label);
    }

    public void description(String description) {
        this.comp.setDescription(description);
    }

    public void format(String format) {
        this.comp.setFormat(format);
    }

    public void unit(String unit) {
        this.comp.setUnit(unit);
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

    public void coeffs(String coeffs) {
        String[] coeffArray = coeffs.split(" ");
        if (coeffArray.length > 1) {
            if (coeffArray[0].trim().equals("COEFFS")) {
                double tab[] = new double[6];
                for (int i = 0; i < coeffArray.length - 1; i++) {
                    tab[i] = Double.parseDouble(coeffArray[i + 1]);
                }
                this.comp.setCoeffs(tab);
            } else {
                System.err.printf("Problème de coherence entre le type RATFUNC de la compMethod %s et le mot clé %s", comp.getLabel(), coeffArray[0]);
            }
        }
    }

    public void refcomputab(String comptab) {
        String[] split = comptab.split(" ");
        if (split.length > 1) {
            if (split[0].trim().equals("COMPU_TAB_REF")) {
                this.comp.setRefcomputab(split[1].trim());
            } else {
                System.err.printf("Problème de coherence entre le type COMPU_TAB_REF de la compMethod %s et le mot clé %s", comp.getLabel(), split[0]);
            }
        }
    }

    /**
     * @return the comp
     */
    public CompuMethod getComp() {
        return comp;
    }

    /**
     * @param comp the comp to set
     */
    public void setComp(CompuMethod comp) {
        this.comp = comp;
    }


}
