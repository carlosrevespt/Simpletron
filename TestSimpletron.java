
/**
 * Simple test for Simpletron.
 * 
 * @author Carlos Reves 
 * @version 1.0
 */
public class TestSimpletron
{
    public static void main(String[] args)
    {
        Simpletron computer = new Simpletron(100);
        SMLInterativeLoader loader = new SMLInterativeLoader();
        
        for (int i = 0; i < 2; i++)
        {
            System.out.printf("Program number %d.%n", i + 1);
            computer.loadProgram(loader);
            computer.runProgram();            
        }
    }
}
