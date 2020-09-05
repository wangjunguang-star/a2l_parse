package com.psagroup.calibrationparserapi.parser.a2l;

import com.psagroup.calibrationparserapi.a2lobject.axis.AxisPts;
import com.psagroup.calibrationparserapi.a2lobject.characteristic.Characteristic;
import com.psagroup.calibrationparserapi.a2lobject.compumethod.CompuMethod;
import com.psagroup.calibrationparserapi.a2lobject.compumethod.CompuVTab;
import com.psagroup.calibrationparserapi.a2lobject.recordlayout.RecordLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

public class A2lParser {

    private final Logger logger = LoggerFactory.getLogger(A2lParser.class);

    private InputStream isA2l;
    public List<Characteristic> caracList;
    public Map<String, RecordLayout> recordLayoutMap;
    public Map<String, CompuMethod> compuMethodMap;
    public Map<String, CompuVTab> compuVTabMap;
    public Map<String, AxisPts> axisPtsMap;


    // 构造函数，城初始化只需要一个File参数
    public A2lParser(InputStream isA2l) {
        this.isA2l = isA2l;
        this.caracList = new LinkedList<>();
        this.recordLayoutMap = new HashMap<>();
        this.compuMethodMap = new HashMap<>();
        this.axisPtsMap = new HashMap<>();
    }

    public void parse() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(isA2l)); // 读取一行
        String currentLine;
        // 实例化各个解析器
        CharacteristicParser pCharac = new CharacteristicParser();
        CompuMethodParser pComp = new CompuMethodParser();
        RecordLayoutParser pRecord = new RecordLayoutParser();
        CompuVTabParser pCompVTab = new CompuVTabParser();
        AxisPtsParser pAxisPts = new AxisPtsParser();
        long i = 0;
        try {
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().startsWith("/begin")) {
                    pCharac.parse(reader, currentLine);
                    pComp.parse(reader, currentLine);
                    pRecord.parse(reader, currentLine);
                    pCompVTab.parse(reader, currentLine);
                    pAxisPts.parse(reader, currentLine);
                }
                i++;
            }
        } catch (Exception e) {
            logger.error("Fatal error during value assignation  at line : " + i);
            e.printStackTrace();
        }
        compuMethodMap = pComp.getCompList();
        caracList = pCharac.getCaracList();
        recordLayoutMap = pRecord.getRecordList();
        compuVTabMap = pCompVTab.getCompVTabList();
        axisPtsMap = pAxisPts.getAxisPtsList();

        /**
         * for debug
         */
//        for(String key : recordLayoutMap.keySet()) {
//            System.out.print ("key : " + key+" ; value : ");
//            recordLayoutMap.get(key).printRecordLayoutInfo();
//            recordLayoutMap.get(key).printRecordLayoutInfo();
//        }

        reader.close();
    }


}
