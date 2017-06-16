package com.dy;

/**
 * Created by dy on 15/06/17.
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;

public class Names {

    public void totalBirths(){
        int totalNames = 0;
        int totalGirlNames = 0;
        int totalGirlNameNumber = 0;
        int totalBoyNames = 0;
        int totalBoyNameNumber = 0;
        DirectoryResource directoryResource = new DirectoryResource();
        for(File file : directoryResource.selectedFiles()){
            FileResource fileResource = new FileResource(file);
            CSVParser csvParser = fileResource.getCSVParser(false);
            for(CSVRecord csvRecord : csvParser){
                if (csvRecord.get(1).equals("F")){
                    System.out.println(csvRecord.get(0));
                    totalGirlNames += Integer.parseInt(csvRecord.get(2));
                    totalGirlNameNumber++;
                }else if (csvRecord.get(1).equals("M")){
                    totalBoyNames += Integer.parseInt(csvRecord.get(2));
                    totalBoyNameNumber++;
                }else{
                    System.out.println("Wrong with gender!");
                }
                totalNames += Integer.parseInt(csvRecord.get(2));
            }
        }
        System.out.println("Total names " + totalNames);
        System.out.println("Total girl names " + totalGirlNames);
        System.out.println("Total girl names number " + totalGirlNameNumber);
        System.out.println("Total boy names " + totalBoyNames);
        System.out.println("Total boy names number " + totalBoyNameNumber);
    }

    public int getRank(int year, String name, String gender){
        int rank = 0;
        int have = 0;
        // String s = "data_name/testing/yob" + Integer.toString(year) + "short.csv";
        String s = "data_name/us_babynames/us_babynames_by_year/yob" + Integer.toString(year) + ".csv";
        // System.out.println(s);
        FileResource fileResource = new FileResource(s);
        CSVParser csvParser = fileResource.getCSVParser(false);
        CSVRecord lastRecord = null;
        for(CSVRecord record : csvParser){
            if(record.get(1).equals(gender)){
                if(record.get(0).equals(name)){
                    // System.out.println("add");
                    rank++;
                    have = 0;
                    break;
                }else{
                    // System.out.println(year +" NOT have name "+name);
                    rank++;
                    have = -1;
                }
            }
            lastRecord = record;
        }
//        if(lastRecord != null && lastRecord.get(1).equals("M")){
//            rank--;
//        }
        if(have == -1){
            rank = -1;
        }
        System.out.println(year + " rank is " + rank);
        return rank;
    }

    public String getName(int year, int rank, String gender){
        int count = 0;
        String name = null;
        // String s = "data_name/testing/yob" + Integer.toString(year) + "short.csv";
        String s = "data_name/us_babynames/us_babynames_by_year/yob" + Integer.toString(year) + ".csv";
        // System.out.println(s);
        FileResource fileResource = new FileResource(s);
        CSVParser csvParser = fileResource.getCSVParser(false);
        for(CSVRecord record : csvParser){
            if(record.get(1).equals(gender)){
                count++;
                if(count == rank){
                    name = record.get(0);
                    System.out.println("name is " + name);
                }
            }else {

            }
        }
        return name;
    }

    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        System.out.println(name+ " born in " + year +  " would be " +newName+ " if she was born in " +newYear+".");
    }

    public void yearOfHighestRank(String name, String gender){
        DirectoryResource directoryResource = new DirectoryResource();
        int highestRank = -2;
        int highestYear = -2;
        for(File file : directoryResource.selectedFiles()) {
            String fileName = file.getName();
            int year = Integer.parseInt(fileName.substring(3, 7));
            // System.out.println(year);
            int currentRank = getRank(year, name, gender);
            if (highestRank == -2 && currentRank != -1){
                highestRank = currentRank;
            }
            if(highestYear == -2){
                highestYear = year;
            }
            // System.out.println("current rank " + currentRank);
            if(currentRank != -1 && highestRank > currentRank){
                highestRank = currentRank;
                System.out.println("current rank " + currentRank);
                System.out.println("highest rank " + highestRank);
                highestYear = year;
            }
        }
        System.out.println(name+ " highest rank " +highestRank+ " is in " + highestYear);
    }

    public void getAverageRank(String name, String gender){
        double totalRank = 0;
        int count = 0;
        DirectoryResource directoryResource = new DirectoryResource();
        for(File file : directoryResource.selectedFiles()) {
            String fileName = file.getName();
            int year = Integer.parseInt(fileName.substring(3, 7));
            int currentRank = getRank(year, name, gender);
            if(currentRank != -1){
                totalRank += currentRank;
                count++;
            }

        }
        double avgRank = totalRank/count;
        System.out.println(name+ " avg rank " +avgRank);
    }

    public void getTotalBirthsRankedHigher(int year, String name, String gender){
        int rank = 0;
        int totalBirthNumber = 0;
        // String s = "data_name/testing/yob" + Integer.toString(year) + "short.csv";
        String s = "data_name/us_babynames/us_babynames_by_year/yob" + Integer.toString(year) + ".csv";
        // System.out.println(s);
        FileResource fileResource = new FileResource(s);
        CSVParser csvParser = fileResource.getCSVParser(false);
        for(CSVRecord record : csvParser) {
            if (record.get(1).equals(gender)) {
                if (record.get(0).contains(name)) {
                    break;
                } else {
                    totalBirthNumber += Integer.parseInt(record.get(2));
                    rank++;
                }
            }
        }
        System.out.println(name + " has " + totalBirthNumber + " and " + rank + " rank in front.");
    }

    public static void main(String avgs[]){
        Names names = new Names();
        // names.totalBirths();
        System.out.println(names.getRank(1960, "Emily", "F"));
//        System.out.println(names.getRank(1971, "Frank", "M"));
//////        System.out.println(names.getRank(2014, "Mason", "M"));
//        System.out.println(names.getName(1980, 350, "F"));
//        System.out.println(names.getName(1982, 450, "M"));
//
//        names.whatIsNameInYear("Susan", 1972, 2014, "F");
//        names.whatIsNameInYear("Owen", 1974, 2014, "M");
//
//        // System.out.println(names.getRank(1994, "Mich", "M"));
//        // System.out.println(names.getRank(1994, "Mich", "M"));
////        names.yearOfHighestRank("Genevieve", "F");
////        names.yearOfHighestRank("Mich", "M");
//
//        // names.getAverageRank("Susan", "F");
//        // names.getAverageRank("Robert", "M");
//        names.getTotalBirthsRankedHigher(1990,"Emily", "F");
//        names.getTotalBirthsRankedHigher(1990,"Drew", "M");
    }
}
