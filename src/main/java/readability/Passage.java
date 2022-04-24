package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Passage
{
    
    
    public double getCharacterCount()
    {
        return characterCount;
    }
    
    public double getWordCount()
    {
        return wordCount;
    }
    
    public double getSentenceCount()
    {
        return sentenceCount;
    }
    
    public double getSyllableCount()
    {
        return syllableCount;
    }
    
    private double characterCount = 0;
    private double wordCount = 0;
    private double sentenceCount = 0;
    private double syllableCount = 0;
    
    Passage(File file) throws FileNotFoundException
    {
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNext())
        {
            String sentence = fileReader.nextLine();
            sentenceCount++;
        
            String[] wordsArray = sentence.split(" ");
            for (String word : wordsArray)
            {
                word = word.replaceAll("-", "");
                wordCount++;
                characterCount += word.length();
                
                word = word.replaceAll("[aeiouyAEIOUY]", "#");
                word = word.replaceAll("#{2,}", "#");
                
                String[] charArray = word.split("");
                for (String character : charArray)
                    if (character.equals("#"))
                        syllableCount++;
            }
        }
    }
}
