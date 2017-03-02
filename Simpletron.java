
/**
 * Simpletron computer simulation
 * 
 * @author Carlos Revés 
 * @version 2.0
 */

import java.io.IOException;
import java.util.InputMismatchException;

public class Simpletron
{
    private int[] memory;
    private final int MEMORY_SIZE; 
    
    public Simpletron (int memorySize)
    {
        MEMORY_SIZE = memorySize;
        memory = new int[memorySize];
    }
    
    private void clearMemory()
    {
        memory = new int[MEMORY_SIZE];
    }
    
    public void loadProgram (SMLInterativeLoader loader) throws OutOfMemoryError
    {
        clearMemory();
        try
        {
            loader.openFile();
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
        int memoryLocation = 0;
        
        while (loader.hasNext())
        {
            if (memoryLocation == MEMORY_SIZE)
            {
                throw new OutOfMemoryError("Not enough memory to load program");
            }
            
            memory[memoryLocation] = loader.nextInt(10);
            memoryLocation++;
        }
        
        loader.closeFile();
    }
    
    public void runProgram ()
    {
        SMLProcessor processor = new SMLProcessor(memory);
        processor.processSML();
    }
}
