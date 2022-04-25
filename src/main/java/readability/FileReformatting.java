package readability;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReformatting
{
    public static void formatFile(File originalFile, File formattedFile, int num, boolean skipChapter, boolean automated) throws IOException
    {
        try (FileWriter writer = new FileWriter(formattedFile))
        {
            Scanner fileReader = new Scanner(originalFile);
            ArrayList<StringBuilder> list = new ArrayList<>(10);
        
            cleanup(fileReader, list, skipChapter, automated);
            writeToFile(writer, list, num);
        
            fileReader.close();
        }
    }
    
    //TODO remove automated parameter
    private static void cleanup(Scanner fileReader, List<StringBuilder> list, boolean skipChapter, boolean automated)
    {
        int listIndex = 0;
        
        while (fileReader.hasNext())
        {
            String line = fileReader.nextLine();
        
            //skip any line that has chapter in it if specified
            if (skipChapter && line.matches("CHAPTER.*"))
            {
                fileReader.nextLine();
                line = fileReader.nextLine();
            }
        
            //basic cleanup
            //TODO add in a file with abbreviations and read in as a set
            line = line.replaceAll("https\\S*", " ");
            line = line.replaceAll("\\s* \\s*", " ");
            line = line.replaceAll("\\.+", ".");
            line = line.replaceAll("Mr\\.|Ms\\.|Dr\\.|Mrs\\.", "Tx");
            
        
            //add matching characters/sentences to proper index in the list
            String[] charArray = line.split("");
            for (String character : charArray)
            {
                //ignore this it just adds more space
                if (listIndex >= list.size())
                    list.add(new StringBuilder());
    
                //juicy stuff right here
                
//                else if (automated)
//                    list.get(listIndex).append(character);
//                if (character.matches("[\\d\\p{L} -]"))
                
                list.get(listIndex).append(character);
                if (character.matches("[!?.]"))
                    listIndex++;
            }
        }
    }
    
    private static void writeToFile(FileWriter writer, List<StringBuilder> list, int num) throws IOException
    {
        for (StringBuilder sentence : list)
        {
            //TODO this is a huge mess
            
            //cleanup white space in case any extra slipped through (probably useless)
    
            String temp = String.valueOf(sentence).trim();
            
        
            
            //sometimes the line starts with a space, this removes it
            if (temp.startsWith(" "))
                temp = temp.substring(1);
        
            //this removes any spaces at the end of the line
            if (temp.length() > 0 && temp.charAt(temp.length() - 1) == ' ')
                temp = temp.substring(0, temp.length() - 1);
        
            //skip any line that is too short
            String[] tempArr = temp.split(" ");
            if (tempArr.length <= num || temp.equals(""))
                continue;
        
            temp += "\n";
            writer.write(temp);
        }
    }
    
    private FileReformatting()
    {
    
    }
}
