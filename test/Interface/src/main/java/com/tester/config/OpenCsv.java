package com.tester.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.*;
import org.json.JSONObject;

import java.io.File;


public class OpenCsv {

    public JSONObject csv(String[] strings, int read)  {
        CsvBaseReader reader = CsvUtil.getReader();

        File file = FileUtil.file("C:/Users/lxq/Desktop/dd/test.csv");
        CsvData data = reader.read(file);
        CsvRow readAll = data.getRow(read-1);
        JSONObject jsonObject = new JSONObject();

        int len1=strings.length;
        int len2=readAll.size();
        int maxLength = Math.max(len1,len2);
        for (int i=0;i<maxLength;i++){
            jsonObject.put(strings[i], readAll.get(i));

        }

        return jsonObject;

    }



    public CsvRow csv(int read)  {
        CsvBaseReader reader = CsvUtil.getReader();

        File file = FileUtil.file("C:/Users/lxq/Desktop/dd/test.csv");
        CsvData data = reader.read(file);
        CsvRow readAll = data.getRow(read-1);

        return readAll;
    }
}
