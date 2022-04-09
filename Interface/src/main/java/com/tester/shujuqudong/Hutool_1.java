package com.tester.shujuqudong;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.*;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Hutool_1 {

    //获取CSV文件某行的数据
    @Test
    public JSONObject csv1(String[] strings, int read)  {
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





    //获取单行或多行内容
    @Test
    public void excel1() throws FileNotFoundException {
        //获取单行内容
        //获取第一行内容
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\lxq\\Desktop\\后端2/test2.xlsx");
        //获取第一行内容
        List<Map<String, Object>> mapList =reader.read(0,1,2);
        System.out.println(mapList);

    }



//获取单个单元格内容
    @Test
    public void excel2() throws FileNotFoundException {
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\lxq\\Desktop\\后端2/test2.xlsx");
        //获取第四行第三列单元格内容
        Object cellValue =reader.readCellValue(2,3);
        System.out.println(cellValue);


    }


    //获取单个sheet内容
    @Test
    public void excel3() throws FileNotFoundException {
        //通过sheet编号获取
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("C:\\Users\\lxq\\Desktop\\后端2/test2.xlsx"), 0);
        //通过sheet名获取
        ExcelReader reader2 = ExcelUtil.getReader(FileUtil.file("C:\\Users\\lxq\\Desktop\\后端2/test2.xlsx"), "sheet1");

        List<Map<String,Object>> readAll = reader.readAll();

        System.out.println(readAll);

    }




}
