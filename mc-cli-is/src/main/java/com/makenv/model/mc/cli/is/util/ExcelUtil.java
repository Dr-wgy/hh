package com.makenv.model.mc.cli.is.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by wgy on 2017/3/22.
 */
public class ExcelUtil {

    private static  Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static <T>List<T> excelToBean(Class<T> clazz,Sheet sheet) {

        Map<String,Integer> indexNameMapping = new LinkedHashMap<>();

        List<T> list = new ArrayList<T>();

        if(Objects.isNull(sheet)) {

            logger.error("sheet can not be null");

            System.exit(1);
        }

        Field fields[] = clazz.getDeclaredFields();

        int rowNum = sheet.getLastRowNum();

        if(rowNum == 0) {

            logger.error("the linenums can't be one line or less");

            System.exit(1);

        }

        Row row = sheet.getRow(0);

        designMapping(row,fields,indexNameMapping);

        for (int currRowNum = 1; currRowNum <= rowNum;currRowNum++) {

            Row currRow = sheet.getRow(currRowNum);

            T t = null;

            try {
                t = clazz.newInstance();

                for(Field field:fields) {

                    String fieldName = field.getName();

                    int columnIndex = indexNameMapping.get(fieldName);

                    Cell cell = currRow.getCell(columnIndex);

                    field.setAccessible(true);

                    setterField(cell,field,t,currRowNum,columnIndex);
                }

            } catch (Exception e) {

                e.printStackTrace();

            }

            list.add(t);

        }

        return list;
    }

    private static <T> void setterField(Cell cell, Field field, T t, Integer currRowNum, int columnIndex) {

        try {

            if (cell != null) {

                switch (cell.getCellType()) {

                    case Cell.CELL_TYPE_STRING:

                        Object value = checkField(field, cell.getStringCellValue(), currRowNum, columnIndex);

                        field.set(t, value);

                        break;

                    case Cell.CELL_TYPE_NUMERIC:

                        Object value1 = checkField(field, cell.getNumericCellValue(), currRowNum, columnIndex);

                        field.set(t, value1);

                        break;

                    case Cell.CELL_TYPE_BOOLEAN:

                        Object value2 = checkField(field, cell.getBooleanCellValue(), currRowNum, columnIndex);

                        field.set(t, value2);

                        break;

                    case Cell.CELL_TYPE_BLANK:

                        System.out.println(cell.getStringCellValue());

                        Object value3 = checkField(field, cell.getStringCellValue(), currRowNum, columnIndex);

                        field.set(t, value3);

                        break;

                    default:

                        logger.error("excel dataType error");

                        //throw new DataTypeException("excel dataType error");

                        System.exit(1);

                }
            }
        } catch (IllegalAccessException e) {

            logger.error("system is error",e);

            System.exit(1);
        }
    }

    private static Object  checkField(Field field,Object obj,Integer row,Integer col) {

            if(obj != null) {

                if(!checkType(field,obj)) {

                    if( (double.class.equals(obj.getClass()) || Double.class.equals(obj.getClass())) && String.class.equals(field.getType())) {

                        Double doubleValue = Double.parseDouble(String.valueOf(obj));

                        obj = String.valueOf(doubleValue.intValue());
                    }

                    else if((int.class.equals(field.getType()) || Integer.class.equals(field.getType())) &&
                            (double.class.equals(obj.getClass()) || Double.class.equals(obj.getClass()))) {

                        try {

                            obj = ((Double)obj).intValue();
                        }
                        catch (Exception e) {

                            logger.error(field.getName() +"is mismatch type ",e);
                        }

                    }

                    else {

                        logger.error("type is mismatch");

                        System.exit(1);
                    }
                }
            }

            return obj;
    }

    private static boolean checkType(Field field, Object obj) {

        if(Double.class.equals(field.getType()) || double.class.equals(field.getType())) {

            return Double.class.equals(obj.getClass()) || double.class.equals(obj.getClass());

        }

        return field.getType().equals(obj.getClass());
    }

    private static void designMapping(Row row, Field[] fields, Map<String, Integer> indexNameMapping) {

        for(Field field:fields) {

            String fieldName = field.getName();

            int columnIndex = getColumnIndexByname(row,fieldName);

            if(columnIndex == 99999) {

                logger.error("the cloumn is not exsits");

                System.exit(1);

            }

            indexNameMapping.put(fieldName,columnIndex);
        }
    }

    private static int getColumnIndexByname(Row firstRow, String fieldName) {

        int cellSize = firstRow.getLastCellNum();

        for(int currCell = 0 ; currCell < cellSize ; currCell ++) {

            if(fieldName.equals(firstRow.getCell(currCell).getStringCellValue())) {

                return currCell;
            }
        }

        return 99999;
    }
}
