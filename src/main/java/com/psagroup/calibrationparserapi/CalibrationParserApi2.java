package com.psagroup.calibrationparserapi;

import com.psagroup.calibrationparserapi.service.ParserService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// parse a2l and ulp file to text format
public class CalibrationParserApi2 {
    public static void main(String[] args) {
        // parse args
        if(args.length <= 3) {
            System.out.println("args not less than 3");
            return;
        }
        String a2lFilePath = args[1];
        String ulpFilePath = args[2];
        String outputFilePath = args[3];

        System.out.println(a2lFilePath);
        System.out.println(ulpFilePath);
        System.out.println(outputFilePath);

        // read file
        File f_a2l = new File(a2lFilePath);
        if(!f_a2l.exists()) {
            System.out.println("a2l file not found!");
            return ;
        }
        File f_ulp = new File(ulpFilePath);
        if(!f_ulp.exists()) {
            System.out.println("ulp file not found!");
            return;
        }
        File f_output = new File(outputFilePath);
        if(!f_output.exists()) {
            System.out.println("output file not found!");
            return;
        }

        ParserService parserService = new ParserService();
        parserService.parseA2LFile(f_a2l);
        parserService.parseUlpFile(f_ulp);

        List<String> list = new ArrayList<String>();
        Map<String, String> map = parserService.getRecordData();
        parserService.assignValues(list, map);
    }
}
