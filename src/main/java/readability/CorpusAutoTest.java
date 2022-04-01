package readability;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static readability.ColemanLiauIndex.calculateColemanLiauIndex;
import static readability.FormattedFile.formatFile;

public class CorpusAutoTest
{
    public static void main(String[] args) throws IOException
    {
        List<String[]> list;
        
        try (CSVReader reader = new CSVReader(new FileReader("src/files/CLEAR Corpus 6.01 - CLEAR Corpus 6.01.csv")))
        {
            list = reader.readAll();
        }
        
        try (CSVWriter writer = new CSVWriter(new FileWriter("src/files/FormattedCSV.csv")))
        {
            List<String[]> completeList = new ArrayList<>();
            
            String[] firstLine = {"ID", "Title", "Excerpt", "Coleman-Liau Index", "Coleman-Liau Index (-1)",
                    "Coleman-Liau Index (-2)", "Flesch-Reading-Ease", "Flesch-Kincaid-Grade-Level",
                    "Automated Readability Index", "SMOG Readability", "New Dale-Chall Readability Formula"
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
            String[] row = list.get(j);
            File file = new File("src/files/unformattedFile.txt");
            File formattedFile = new File("src/files/formattedFile.txt");
            
            //calculate index excluding 0,1,2-word sentences
            String[] colemanIndexes = new String[3];
            for (int i = 0; i < colemanIndexes.length; i++)
            {
                try (FileWriter fileWriter = new FileWriter(file))
                {
                    fileWriter.write(row[14]);
                }
                formatFile(file, formattedFile, i, false);
                
                double index = calculateColemanLiauIndex(formattedFile);
                String gradeLevel = String.valueOf((int) index);
                if (index > 12)
                    gradeLevel = "12+";
                
                colemanIndexes[i] = "Index: " + index + " Grade Level: " + gradeLevel;
            }
            
            //get reading levels in string form
            String fleschEaseIndex = fleschReadingEase(row[24]);
            String fleschGradeIndex = fleschKincaidGradeLevel(row[25]);
            String automatedIndex = automated(row[26]);
            String smogIndex = smog(row[27]);
            String daleIndex = newDaleChall(row[28]);
            
            //format the line and add it to the list
            String completeLine = "%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s".formatted(row[0], row[3], row[14], colemanIndexes[0],
                    colemanIndexes[1], colemanIndexes[2], fleschEaseIndex, fleschGradeIndex, automatedIndex, smogIndex,
                    daleIndex);
            
            row = completeLine.split("\\^");
            completeList.add(row);
        }
    }
    
    //these methods just convert the index to a grade level, simple is used if the grade level is the index without decimals
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
