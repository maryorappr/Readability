package corpus;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import readability.Passage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static readability.ReadingIndexCalculation.*;
import static readability.IndexConversion.*;
import static readability.FileReformatting.formatFile;

public class CorpusSpreadsheet
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
            
            String[] firstLine =
            {
                    "ID", //ID of entry, corresponds top CLEAR Corpus entry IDs
                    "Title", //Title of passage
                    "Excerpt", //passage that is being tested
                    
                    //(-2) means excluding 2 word sentences, (-1) is excluding 1 word sentences
                    "Coleman-Liau Index (-2)",
                    "Coleman-Liau Index (-1)",
                    "Coleman-Liau Index",
                    
                    
                    "Flesch-Reading-Ease (-2)",
                    "Flesch-Reading-Ease (-1)",
                    "Flesch-Reading-Ease",
                    "*Flesch-Reading-Ease", //star is target number
                    "Flesch Ease deviation from target", //target number minus my calculation
                    
                    "Flesch-Kincaid-Grade-Level (-2)",
                    "Flesch-Kincaid-Grade-Level (-1)",
                    "Flesch-Kincaid-Grade-Level",
                    "*Flesch-Kincaid-Grade-Level",
                    "Flesch-Kincaid deviation from target",
                    
                    "Automated Readability Index (-2)",
                    "Automated Readability Index (-1)",
                    "Automated Readability Index",
                    "*Automated Readability Index",
                    "Automated deviation from target",
                    
                    //these two I don't calculate for
                    "*SMOG Readability",
                    "*New Dale-Chall Readability Formula",
                    /*
                    below are the actual grade levels, so just an integer
                    pls figure out what the range is going to be ([0 - 17] for now but can be changed)
                     */
                    
                    "Coleman-Liau Grade Level",
                    "Flesch-Reading-Ease Grade Level",
                    "*Flesch-Reading-Ease Grade Level",
                    "Flesch-Kincaid Grade Level",
                    "*Flesch-Kincaid Grade Level",
                    "Automated Grade Level",
                    "*Automated Grade Level",
                    "*SMOG Grade Level",
                    "*New Dale-Chall Grade Level",
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
            double[] colemanIndexes = new double[3];
            double[] fleschEaseIndexes = new double[3];
            double[] fleschGradeIndexes = new double[3];
            double[] automatedIndexes = new double[3];
            for (int i = 0; i < colemanIndexes.length; i++)
            {
                try (FileWriter fileWriter = new FileWriter(file))
                {
                    fileWriter.write(row[14]);
                }
                formatFile(file, formattedFile, i, false, false);
                Passage passage = new Passage(formattedFile);
                //this is just to avoid negative numbers
                double colemanIndex = Math.max(calculateColemanLiau(passage), 0);
                double fleschReadingEaseIndex = Math.max(calculateFleschReadingEase(passage), 0);
                double fleschReadingGradeIndex = Math.max(calculateFleschGradeLevel(passage), 0);
    
                //automated needs the file formatted slightly differently
                formatFile(file, formattedFile, i, false, true);
                passage = new Passage(formattedFile);
                double automatedIndex = Math.max(calculateAutomated(passage), 0);
                
                colemanIndexes[i] = colemanIndex;
                fleschEaseIndexes[i] = fleschReadingEaseIndex;
                fleschGradeIndexes[i] = fleschReadingGradeIndex;
                automatedIndexes[i] = automatedIndex;
            }
            
            //get reading levels in string form
            //TODO pls make this an array this is insanity
            //actually maybe a map ?
            double fleschEaseCorpus = Double.parseDouble(row[24]);
            double fleschGradeCorpus = Double.parseDouble(row[25]);
            double automatedCorpus = Double.parseDouble(row[26]);
            double smogCorpus = Double.parseDouble(row[27]);
            double daleCorpus = Double.parseDouble(row[28]);
    
            //TODO this isn't as bad but pls
            double fleschEasePercentError = Math.abs(fleschEaseCorpus - fleschEaseIndexes[0]);
            double fleschGradePercentError = Math.abs(fleschGradeCorpus - fleschGradeIndexes[0]);
            double automatedPercentError = Math.abs(automatedCorpus - automatedIndexes[0]);
            
            //TODO also this
            int colemanGrade = colemanGrade(colemanIndexes[0]);
            int fleschEaseGrade = fleschEaseGrade(fleschEaseIndexes[0]);
            int fleschEaseCorpusGrade = fleschEaseGrade(fleschEaseCorpus);
            int fleschKincaidGrade = fleschKincaidGrade(fleschGradeIndexes[0]);
            int fleschKincaidCorpusGrade = fleschKincaidGrade(fleschGradeCorpus);
            int automatedGrade = automatedGrade(automatedIndexes[0]);
            int automatedCorpusGrade = automatedGrade(automatedCorpus);
            int smogGrade = smogGrade(smogCorpus);
            int daleChallGrade = daleChallGrade(daleCorpus);
            
            //format the line and add it to the list
            //TODO DONT REMOVE THIS pls dont forget to actually add in the stuff that u added in above pls thank u
            String completeLine = "%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s^%s".
                    formatted(row[0], row[3], row[14], colemanIndexes[2], colemanIndexes[1], colemanIndexes[0],
                    fleschEaseIndexes[2],fleschEaseIndexes[1], fleschEaseIndexes[0], fleschEaseCorpus,
                    fleschEasePercentError, fleschGradeIndexes[2], fleschGradeIndexes[1], fleschGradeIndexes[0],
                    fleschGradeCorpus, fleschGradePercentError, automatedIndexes[2], automatedIndexes[1],
                    automatedIndexes[0], automatedCorpus, automatedPercentError, smogCorpus, daleCorpus, colemanGrade,
                    fleschEaseGrade, fleschEaseCorpusGrade, fleschKincaidGrade, fleschKincaidCorpusGrade, automatedGrade,
                    automatedCorpusGrade, smogGrade, daleChallGrade);
            
            row = completeLine.split("\\^");
            completeList.add(row);
        }
    }
}
