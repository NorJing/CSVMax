package com.dy;

/**
 * Created by dy on 14/06/17.
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
    public CSVRecord hottestHourInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord largestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }

    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldestSoFar = null;
        for(CSVRecord currentRow : parser){
            coldestSoFar = getColdestOfTwo(currentRow, coldestSoFar);
        }
        return coldestSoFar;
    }

    public void printEachRow(CSVParser csvParser){
        if(csvParser == null){
            System.out.println("CSVParser is null!");
        }else{
            for(CSVRecord eachRow :  csvParser){
                System.out.println(eachRow.get("DateUTC") + " " + eachRow.get("TemperatureF"));
            }
        }
    }

    public void testHottestInDay () {
        FileResource fr = new FileResource("data/2015/weather-2015-01-01.csv");
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                " at " + largest.get("TimeEST"));
    }

    public void testColdestInDay(){
        FileResource fileResource = new FileResource("data/2015/weather-2015-01-01.csv");
        CSVRecord coldest = coldestHourInFile(fileResource.getCSVParser());
        System.out.println("coldest temperature was " + coldest.get("TemperatureF") +
                " at " + coldest.get("TimeEST"));
    }

    public CSVRecord hottestInManyDays() {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        System.out.println("hottest temperature was " + largestSoFar.get("TemperatureF") +
                " at " + largestSoFar.get("TimeEST") + " in " + largestSoFar.get("DateUTC"));
        return largestSoFar;
    }

    public CSVRecord coldestInManyDays() {
        CSVRecord coldestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            coldestSoFar = getColdestOfTwo(currentRow, coldestSoFar);
        }
        //The largestSoFar is the answer
        System.out.println("coldest temperature was " + coldestSoFar.get("TemperatureF") +
                " at " + coldestSoFar.get("DateUTC"));
        return coldestSoFar;
    }

    public CSVRecord getLowestHumilityOfTwo(CSVRecord currentRow, CSVRecord lowestHumiditiesSoFar){
        if(lowestHumiditiesSoFar == null){
            lowestHumiditiesSoFar = currentRow;
        }else {
            try{
                String item = currentRow.get("Humidity");
                if(item.contains("N/A")){

                }else {
                    double currentHumidities = Double.parseDouble(currentRow.get("Humidity"));
                    double lowestHumidities = Double.parseDouble(lowestHumiditiesSoFar.get("Humidity"));
                    if(currentHumidities < lowestHumidities){
                        lowestHumiditiesSoFar = currentRow;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return lowestHumiditiesSoFar;
    }

    public CSVRecord lowestHumiditiesInFile(CSVParser parser){
        CSVRecord lowestHumiditiesSoFar = null;
        for(CSVRecord currentRow : parser){
            lowestHumiditiesSoFar = getLowestHumilityOfTwo(currentRow, lowestHumiditiesSoFar);
        }
        return lowestHumiditiesSoFar;
    }

    public CSVRecord lowestHumidityInManyFiles(){
        CSVRecord lowestHumiditySoFar = null;
        DirectoryResource directoryResource = new DirectoryResource();
        for (File file : directoryResource.selectedFiles()){
            FileResource fileResource = new FileResource(file);
            CSVRecord currentRow = lowestHumiditiesInFile(fileResource.getCSVParser());
            // use method to compare two records
            lowestHumiditySoFar = getLowestHumilityOfTwo(currentRow, lowestHumiditySoFar);
        }
        System.out.println("Lowest humidity was " + lowestHumiditySoFar.get("Humidity") + " at " + lowestHumiditySoFar.get("DateUTC"));
        return lowestHumiditySoFar;
    }

    public String fileWithColdestTemperature(){
        DirectoryResource dr = new DirectoryResource();
        String fileName = null;
        String fullPath = null;
        for(File f : dr.selectedFiles()){
            fileName = f.getName();
            fullPath = f.getParentFile().getAbsolutePath() + "/" + fileName;
            System.out.println("Coldest day was in file " + fileName);
            // System.out.println("Full path " + fullPath);
        }
        return fullPath;
    }

    public void testFileWithColdestTemperature(){
        String fileName = fileWithColdestTemperature();
        FileResource fileResource = new FileResource(fileName);
        CSVParser csvParser = fileResource.getCSVParser();
        CSVRecord csvRecord = coldestHourInFile(csvParser);
        System.out.println("coldest temperature was " + csvRecord.get("TemperatureF"));
        // csvParser = fileResource.getCSVParser();
        // printEachRow(csvParser);
    }

    public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
        //If largestSoFar is nothing
        if (largestSoFar == null) {
            largestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature > largestSoFar’s
            if (currentTemp > largestTemp) {
                //If so update largestSoFar to currentRow
                largestSoFar = currentRow;
            }
        }
        return largestSoFar;
    }

    public CSVRecord getColdestOfTwo(CSVRecord currentRow, CSVRecord coldestSoFar){
        if(coldestSoFar == null){
            coldestSoFar = currentRow;
        }else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
            if(currentTemp > 0){
                if(currentTemp < coldestTemp){
                    coldestSoFar = currentRow;
                }
            }
        }
        return coldestSoFar;
    }

    public void testHottestInManyDays () {
        CSVRecord largest = hottestInManyDays();
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                " at " + largest.get("DateUTC") + " in " + largest.get("DateUTC"));
    }

    public double averageTemperatureInFile(CSVParser parser){
        double sum = 0.0;
        int count = 0;
        for (CSVRecord currentRow : parser) {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            sum = sum + currentTemp;
            count++;
        }
        double avg = sum/count;
        return avg;
    }

    public void testAverageTemperatureInFile(){
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser csvParser = fr.getCSVParser();
            double avg = averageTemperatureInFile(csvParser);
            System.out.println("Average temperature in file is " + avg);
        }
    }

    public double averageTemperatureWithHighHumidityInFile(CSVParser csvParser, int value){
        double sum = 0.0;
        int count = 0;
        double avg = 0.0;
        for (CSVRecord currentRow : csvParser) {
            double humidity = Double.parseDouble(currentRow.get("Humidity"));
            if(humidity >= value){
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                sum = sum + currentTemp;
                count++;
            }
        }
        if(sum == 0.0){
            System.out.println("No temperatures with that humidity");
        }
        if(count > 0){
            avg = sum/count;
        }
        return avg;
    }

    public void testAverageTemperatureWithHighHumidityInFile(){
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser csvParser = fr.getCSVParser();
            double avg = averageTemperatureWithHighHumidityInFile(csvParser, 80);
            System.out.println("Average Temp when high Humidity is " + avg);
        }
    }

    public static void main(String avgs[]){
        CSVMax csvMax = new CSVMax();
        // csvMax.testAverageTemperatureWithHighHumidityInFile();
        csvMax.coldestInManyDays();
        // csvMax.lowestHumidityInManyFiles();

        // csvMax.testAverageTemperatureInFile();
        // csvMax.testAverageTemperatureWithHighHumidityInFile();
    }
}