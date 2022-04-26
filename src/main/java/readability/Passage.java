package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Passage
{
    private double totalCharacterCount = 0; //DOES NOT INCLUDE SPACES
    private double characterCount = 0;
    private double wordCount = 0;
    private double sentenceCount = 0;
    private double syllableCount = 0;
    
    public Passage(File file) throws FileNotFoundException
    {
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNext())
        {
            String sentence = fileReader.nextLine();
            sentenceCount++;
            
            String[] wordsArray = sentence.split(" ");
            for (String word : wordsArray)
            {
                totalCharacterCount += word.length();
                boolean hasLetter = false;
    
                String[] charArray = word.split("");
                for (String character : charArray)
                {
                    if (character.matches("\\p{L}"))
                    {
                        hasLetter = true;
                        characterCount++;
                    }
                }
                if (hasLetter)
                    wordCount++;
                
                //count syllables
                word = word.replaceAll("[aeiouyAEIOUY]", "#");
                word = word.replaceAll("#{2,}", "#");
                
                charArray = word.split("");
                for (String character : charArray)
                    if (character.equals("#"))
                        syllableCount++;
            }
        }
        fileReader.close();
    
        System.out.println(totalCharacterCount);
        System.out.println(characterCount);
        System.out.println(wordCount);
        System.out.println(sentenceCount);
        System.out.println(syllableCount);
    }
    
    public double getTotalCharacterCount()
    {
        return totalCharacterCount;
    }
    
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
}
