/**
 * Simpletron computer simulation
 * 
 * @author Carlos Revés 
 * @version 1.0
 */

import java.io.IOException;
import java.util.InputMismatchException;

public class Simpletron
{
    public static void main(String[] args)
    {
        try
        {
            SMLLoader loader = new SMLLoader();
            loader.openFile();
            int[] memory = loader.loadInstructions();
            loader.closeFile();
            SMLProcessor processor = new SMLProcessor(memory);
            processor.processSML();
        }
        catch (IOException ioException)
        {
            System.err.println(ioException.getMessage());
            System.exit(1);
        }
        catch (InputMismatchException imException)
        {
            System.err.println(imException.getMessage());
            System.exit(1);
        }
    }
}