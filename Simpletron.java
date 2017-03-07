
/**
 * Simpletron computer simulation
 * 
 * @author Carlos Revés 
 * @version 3.0
 */

import java.io.IOException;
import java.util.InputMismatchException;

public class Simpletron
{
    private int[] memory;
    private final int memorySize; 
    
    public Simpletron (int memorySize)
    {
        this.memorySize = memorySize;
        memory = new int[memorySize];
    }
    
    private void clearMemory()
    {
        memory = new int[memorySize];
    }
    
    public int getMemoryLocation(int memoryLocation)
    {
        return memory[memoryLocation];
    }
    
    public void setMemoryLocation(int memoryLocation, int instruction)
    {
        memory[memoryLocation] = instruction;
    }
    
    public int getMemoryCapacity()
    {
        return memory.length;
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
            if (memoryLocation == memorySize)
            {
                throw new OutOfMemoryError("Not enough memory to load program");
            }
            
            memory[memoryLocation] = loader.nextInt(16);
            memoryLocation++;
        }
        
        loader.closeFile();
    }
    
    public void runProgram ()
    {
        SMLProcessor processor = new SMLProcessor(this);
        processor.processSML();
    }
}
