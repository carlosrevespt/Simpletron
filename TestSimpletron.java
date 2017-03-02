
/**
 * Write a description of class TestSimpletron here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestSimpletron
{
    public static void main(String[] args)
    {
        Simpletron computer = new Simpletron(100);
        SMLInterativeLoader loader = new SMLInterativeLoader();
        computer.loadProgram(loader);
        computer.runProgram();
    }
}
