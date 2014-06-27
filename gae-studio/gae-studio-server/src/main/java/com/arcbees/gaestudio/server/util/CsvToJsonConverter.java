package com.arcbees.gaestudio.server.util;

public class CsvToJsonConverter {
    public String convert(String csvData, String kind) {
        String result = "";
        String[] columns = retrieveColumns(csvData);
        String[] dataLines = retrieveDataLines(csvData);

        for (String column : columns) {
            result += column;
        }

        for(String dataLine : dataLines) {
            result += dataLine;
        }

        return result;
    }

    private String[] retrieveColumns(String csvData) {
        String firstLine = csvData.substring(0, csvData.indexOf("\n"));

        String[] columns = firstLine.split(",");

        return removeLeadingTrailingSpaces(columns);
    }

    private String[] removeLeadingTrailingSpaces(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
        }

        return array;
    }

    private String[] retrieveDataLines(String csvData) {
        String otherLines = csvData.substring(csvData.indexOf("\n"));

        String[] dataLines = otherLines.split("\n");

        return removeLeadingTrailingSpaces(dataLines);
    }
}
