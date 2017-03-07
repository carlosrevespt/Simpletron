
/**
 * Simple test for Simpletron.
 * 
 * @author Carlos Reves 
 * @version 3.0
 */
public class TestSimpletron
{
    public static void main(String[] args)
    {
        Simpletron computer = new Simpletron(200);
        SMLInterativeLoader loader = new SMLInterativeLoader();
        
        for (int i = 0; i < 1; i++)
        {
            System.out.printf("Program number %d.%n", i + 1);
            computer.loadProgram(loader);
            computer.runProgram();            
        }
    }
}
