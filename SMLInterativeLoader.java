
/**
 * Loader for SML Instructions
 * 
 * @author Carlos Revés
 * @version 1.0
 */

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class SMLInterativeLoader
{
    private File SMLInstructions;
    private Scanner scan;
    private JFileChooser chooseFile;
    
    public SMLInterativeLoader()
    {
        try
        {
            String currentDirectory = new java.io.File( "." ).getCanonicalPath();
            chooseFile = new JFileChooser(currentDirectory);
        }
        catch (IOException ioException)
        {
            System.err.println("Can't get current directory");
            System.exit(1);
        }
    }
    
    public void openFile() throws IOException
    {
        int returnValue = chooseFile.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            SMLInstructions = chooseFile.getSelectedFile();
            scan = new Scanner(SMLInstructions);
        }
        else
        {
            throw new IOException("File opening canceled");
        }
    }
        
    public void closeFile()
    {
        if (scan != null)
        {
            scan.close();
        }
    }
    
    public boolean hasNext()
    {
        return scan.hasNext();
    }
    
    public int nextInt(int radix)
    {
        return scan.nextInt(radix);
    }
}
