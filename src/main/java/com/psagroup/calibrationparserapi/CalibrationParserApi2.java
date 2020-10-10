package com.psagroup.calibrationparserapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.psagroup.calibrationparserapi.a2lobject.characteristic.CharType;
import com.psagroup.calibrationparserapi.a2lobject.characteristic.Characteristic;
import com.psagroup.calibrationparserapi.service.ParserService;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// parse a2l and ulp file to text format
public class CalibrationParserApi2 {
    public static void main(String[] args) throws IOException {
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
        List<Characteristic> filteredCaracList = parserService.assignValues2(list, map);

        FileWriter fileWriter = new FileWriter(f_output.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fileWriter);


        for (Characteristic _c : filteredCaracList) {
            String labels = _c.getLabel();
            String RecordLayout = _c.getRefRecordLayout();
            String computMethod = _c.getRefComputMethod();
            String Address = _c.getAddress();
            Integer Number = _c.getNumber();
            JsonNode result = _c.getResult();
            String description = _c.getDescription();
            Double upper = _c.getUpper();
            Double lower = _c.getLower();
            CharType charType = _c.getChartype();

            JSONObject js = new JSONObject();
            try {
                js.put("label", labels);
                js.put("charType", charType);
                js.put("Address", Address);
                js.put("Number", Number);
                js.put("description", description);
                js.put("RecordLayout", RecordLayout);
                js.put("ComputMethod", computMethod);
                js.put("upper", upper);
                js.put("lower", lower);
                js.put("maxdiff", _c.getMaxdiff());
                js.put("extended_limits", _c.getExtended_limits());
                js.put("format", _c.getFormat());
                if (result == null) {
                    js.put("data", "{}");
                } else {
                    js.put("data", result.toString());
                }
            } catch (JSONException e) {
                //some exception handler code
                System.err.println("Json Error! " + _c.getLabel());
            }
            System.out.println("Output = " + js.toString());
            bw.write(js.toString());
            bw.newLine();
        }

        bw.close();
        System.out.println("Parse OK");

    }
}
