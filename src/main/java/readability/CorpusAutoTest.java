package readability;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static readability.ReadingIndexCalculation.*;
import static readability.FormattedFile.formatFile;

public class CorpusAutoTest
{
    public static void main(String[] args) throws IOException
    {
        //reads all the corpus into a list
        List<String[]> list;
        try (CSVReader reader = new CSVReader(new FileReader("src/files/CLEAR Corpus 6.01 - CLEAR Corpus 6.01.csv")))
        {
            list = reader.readAll();
        }
        
        //create a writer for the new csv file
        try (CSVWriter writer = new CSVWriter(new FileWriter("src/main/java/readability/FormattedCSV.csv")))
        {
            List<String[]> completeList = new ArrayList<>();
            
            /*
            this is the first line of our spreadsheet, every row must have each of these. every time we add a
            new index this is the first thing to be changed.
             */
            String[] firstLine = {"ID", "Title", "Excerpt", "Coleman-Liau Index", "Coleman-Liau Index (-1)",
                    "Coleman-Liau Index (-2)", "Flesch-Reading-Ease", "Flesch-Reading-Ease (-1)",
                    "Flesch-Reading-Ease (-2)", "Flesch-Reading-Ease CORPUS", "Flesch-Kincaid-Grade-Level",
                    "Flesch-Kincaid-Grade-Level (-1)", "Flesch-Kincaid-Grade-Level (-2)",
                    "Flesch-Kincaid-Grade-Level CORPUS", "Automated Readability Index",
                    "Automated Readability Index (-1)", "Automated Readability Index (-2)",
                    "Automated Readability Index CORPUS", "SMOG Readability",
                    "New Dale-Chall Readability Formula"
            };
            
            completeList.add(firstLine);
            writeResults(list, completeList);
            writer.writeAll(completeList);
        }
        
        System.out.println("Complete! ♥");
    }
    
    private static void writeResults(List<String[]> list, List<String[]> completeList) throws IOException
    {
        for (int j = 1; j < list.size(); j++)
        {
            //open the files and grab the row contents
            String[] row = list.get(j);
            File file = new File("src/files/unformattedFile.txt");
            File formattedFile = new File("src/files/formattedFile.txt");
            
            //calculate indexes excluding 0,1,2-word sentences
            String[] colemanIndexes = new String[3];
            String[] fleschReadingEaseIndexes = new String[3];
            String[] fleschGradeLevelIndexes = new String[3];
            String[] automatedIndexes = new String[3];
            for (int i = 0; i < colemanIndexes.length; i++)
            {
                try (FileWriter fileWriter = new FileWriter(file))
                {
                    fileWriter.write(row[14]);
                }
                formatFile(file, formattedFile, i, false, false);
                Passage passage = new Passage(formattedFile);
                double colemanIndex = calculateColemanLiau(passage);
                double fleshReadingEaseIndex = calculateFleschReadingEase(passage);
                double fleshReadingGradeIndex = calculateFleschGradeLevel(passage);
    
                formatFile(file, formattedFile, i, false, true);
                passage = new Passage(formattedFile);
                double automatedIndex = calculateAutomated(passage);
                
                colemanIndexes[i] = "" + colemanIndex;
                fleschReadingEaseIndexes[i] = "" + fleshReadingEaseIndex;
                fleschGradeLevelIndexes[i] = "" + fleshReadingGradeIndex;
                automatedIndexes[i] = "" + automatedIndex;
            }
            
            //get reading levels in string form
            String fleschEaseIndex = row[24];
            String fleschGradeIndex = row[25];
            String automatedIndex = row[26];
            String smogIndex = row[27];
            String daleIndex = row[28];
            
            //format the line and add it to the list
            String completeLine = "%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s".formatted(row[0], row[3], row[14], colemanIndexes[0],
                    colemanIndexes[1], colemanIndexes[2], fleschReadingEaseIndexes[0],fleschReadingEaseIndexes[1],
                    fleschReadingEaseIndexes[2], fleschEaseIndex, fleschGradeLevelIndexes[0],
                    fleschGradeLevelIndexes[1], fleschGradeLevelIndexes[2], fleschGradeIndex, automatedIndexes[0],
                    automatedIndexes[1], automatedIndexes[2], automatedIndex, smogIndex, daleIndex);
            
            row = completeLine.split("\\^");
            completeList.add(row);
        }
    }
    
    //these methods just convert the index to a grade level, simple is used if the grade level is the index without decimals
    //DO NOT NEED THESE FOR THE TIME BEING
    private static String fleschReadingEase(String num)
    {
        double temp = Double.parseDouble(num);
        String readingLevel;
        
        if (90 <= temp)
            readingLevel = "5";
        else if (80 <= temp)
            readingLevel = "6";
        else if (70 <= temp)
            readingLevel = "7";
        else if (60 <= temp)
            readingLevel = "8-9";
        else if (50 <= temp)
            readingLevel = "10-12";
        else
            readingLevel = "12+";
        
        return readingLevel;
    }
    
    private static String fleschKincaidGradeLevel(String num)
    {
        return String.valueOf(simple(num));
    }
    
    private static String automated(String num)
    {
        return String.valueOf(simple(num));
    }
    
    private static String smog(String num)
    {
        double index = Double.parseDouble(num);
        String readingLevel;
        
        if (index < 2)
            readingLevel = "4";
        else if (index < 6)
            readingLevel = "5";
        else if (index < 12)
            readingLevel = "6";
        else if (index < 20)
            readingLevel = "7";
        else if (index < 42)
            readingLevel = "8-9";
        else if (index < 56)
            readingLevel = "10";
        else if (index < 72)
            readingLevel = "11";
        else
            readingLevel = "12+";
        
        return readingLevel;
    }
    
    private static String newDaleChall(String num)
    {
        double index = Double.parseDouble(num);
        String readingLevel;
        
        if (index < 5)
            readingLevel = "4";
        else if (index < 6)
            readingLevel = "5-6";
        else if (index < 7)
            readingLevel = "7-8";
        else if (index < 8)
            readingLevel = "9-10";
        else if (index < 9)
            readingLevel = "11-12";
        else
            readingLevel = "12+";
        
        return readingLevel;
    }
    
    private static int simple(String num)
    {
        int readingLevel = (int) Double.parseDouble(num);
        return Math.min(readingLevel, 12);
    }
}
