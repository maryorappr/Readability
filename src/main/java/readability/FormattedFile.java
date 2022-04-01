package readability;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FormattedFile
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("src/files/readabilityText.txt");
        File formattedFile = new File("src/files/formattedReadability.txt");
    
        Scanner input = new Scanner(System.in);
        System.out.print("Enter number: ");
        int num = input.nextInt();
        
        formatFile(file, formattedFile, num, true);
        System.out.println("Formatted");
    }
    
    public static void formatFile(File originalFile, File formattedFile, int num, boolean skipChapter) throws IOException
    {
        try (FileWriter writer = new FileWriter(formattedFile))
        {
            Scanner fileReader = new Scanner(originalFile);
            ArrayList<StringBuilder> list = new ArrayList<>(10);
        
            cleanup(fileReader, list, skipChapter);
            writeToFile(writer, list, num);
        
            fileReader.close();
        }
    }
    
    private static void cleanup(Scanner fileReader, List<StringBuilder> list, boolean skipChapter)
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
            line = line.replaceAll("https\\S*", " ");
            line = line.replaceAll("\\s* \\s*", " ");
            line = line.replaceAll("\\.+", ".");
            line = line.replaceAll("Mr\\.|Ms\\.|Dr\\.|Mrs\\.", "Tx");
        
            //add matching characters/sentences to proper index in the list
            String[] charArray = line.split("");
            for (String character : charArray)
            {
                if (listIndex >= list.size())
                    list.add(new StringBuilder());
            
                if (character.matches("[\\p{L} -]"))
                    list.get(listIndex).append(character);
                else if (character.matches("[!?.]"))
                    listIndex++;
            }
        }
    }
    
    private static void writeToFile(FileWriter writer, List<StringBuilder> list, int num) throws IOException
    {
        for (StringBuilder sentence : list)
        {
            //cleanup white space in case any extra slipped through (probably useless)
            String temp = String.valueOf(sentence).replaceAll("\\s* \\s*", " ");
        
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
    
}
