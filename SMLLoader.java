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

public class SMLLoader
{
    private static File SMLInstructions;
    private static Scanner scan;
    private static JFileChooser chooseFile;
    
    public SMLLoader()
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
    
    public static void openFile() throws IOException
    {
        int returnValue = chooseFile.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            SMLInstructions = chooseFile.getSelectedFile();
            scan = new Scanner(SMLInstructions);
        } else {
            throw new IOException("File opening canceled");
        }
    }
    
    public int[] loadInstructions()
    {
        int[] memory = new int[100];
        int index = 0;
        
        try
        {
            while (scan.hasNext())
            {
                memory[index] = scan.nextInt();
                index++;
            }
        }
        catch (NoSuchElementException elementException)
        {
            System.err.println("File improperly formed.");
            System.exit(1);
        }
        catch (IllegalStateException stateException)
        {
            System.err.println("Error reading from file.");
            System.exit(1);
        }
        
        return memory;
    }
    
    public static void closeFile()
    {
        if (scan != null)
        {
            scan.close();
        }
    }
}