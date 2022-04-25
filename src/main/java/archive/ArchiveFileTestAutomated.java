package archive;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static readability.FileReformatting.formatFile;

//TODO rework format for this index cuz its really stupid
public class ArchiveFileTestAutomated
{
    public static void main(String[] args) throws IOException
    {
        File ogFile = new File("src/files/readabilityTextTESTONLY.txt");
        File file = new File("src/files/formattedReadabilityTESTONLY.txt");
        formatFile(ogFile, file, 0, true, true);
        
        System.out.println(automated(file));
    }
    
    public static double automated(File book) throws IOException
    {
        double index;
        double letterCount = 0;
        double sentenceCount = 0;
        double wordCount = 0;
        
        Scanner fileReader = new Scanner(book);
        while (fileReader.hasNext())
        {
            String sentence = fileReader.nextLine();
            sentenceCount++;
            letterCount++;
            
            String[] wordsArray = sentence.split(" ");
            for (String word : wordsArray)
            {
                wordCount++;
                word = word.replaceAll("[aeiouyAEIOUY]", "#");
                word = word.replaceAll("#{2,}", "#");
//                word = word.trim();
                letterCount += word.length();
            }
        }
        System.out.println(wordCount);
        System.out.println(letterCount);
        System.out.println(sentenceCount);
        
        index = (4.71 *(letterCount/wordCount)) + (0.5 * (wordCount / sentenceCount)) - 21.43;
        return index;
    }
}
