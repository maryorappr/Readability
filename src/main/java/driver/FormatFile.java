package driver;

import java.io.File;
import java.io.IOException;

import static readability.FileReformatting.formatFile;

public class FormatFile
{
    public static void main(String[] args) throws IOException
    {
        //TODO redo this area and move files around
        File file = new File("src/files/readabilityTextTESTONLY.txt");
        File formattedFile = new File("src/files/formattedReadabilityTESTONLY.txt");
        
        formatFile(file, formattedFile, 0, true);
        System.out.println("Formatted");
    }
}
