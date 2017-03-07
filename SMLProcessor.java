
/**
 * Simpletron Machine Language Simulation
 * 
 * @Carlos Revés 
 * @version 2.0
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class SMLProcessor
{
    private final Simpletron computer;
    private int accumulator;
    private int instructionCounter;
    private int operationCode;
    private int operand = 0;
    private int instructionRegister;
    private final int READ = 10;
    private final int WRITE = 11;
    private final int NEW_LINE = 12;
    private final int LOAD = 20;
    private final int STORE = 21;
    private final int ADD = 30;
    private final int SUBTRACT = 31;
    private final int DIVIDE = 32;
    private final int MULTIPLY = 33;
    private final int REMAINDER = 34;
    private final int POW = 35;
    private final int BRANCH = 40;
    private final int BRANCHNEG = 41;
    private final int BRANCHZERO = 42;
    private final int HALT = 43;
    
    public SMLProcessor(Simpletron computer)
    {        
        this.computer = computer;
        accumulator = 0;
        instructionCounter = 0;
        operationCode = 0;
        operand = 0;
        instructionRegister = 0;
    }
    
    public void processSML() throws InputMismatchException
    {
        System.out.printf ("%n*** Program execution begins  ***%n");
        
        boolean running = true;
        
        while (running)
        {
            instructionRegister = computer.getMemoryLocation(instructionCounter);
            operationCode = instructionRegister / 100;
            operand = instructionRegister % 100;
            switch(operationCode)
            {
                case READ:
                    Scanner scan = new Scanner(System.in);
                    System.out.print("Enter an integer: ");
                    computer.setMemoryLocation(operand, scan.nextInt());
                    instructionCounter++;
                    scan.close();
                    break;
                case WRITE:
                    System.out.printf("%n%d", computer.getMemoryLocation(operand));
                    instructionCounter++;
                    break;
                case NEW_LINE:
                    System.out.println();
                    instructionCounter++;
                    break;
                case LOAD:
                    accumulator = computer.getMemoryLocation(operand);
                    instructionCounter++;
                    break;
                case STORE:
                    computer.setMemoryLocation(operand, accumulator);
                    instructionCounter++;
                    break;
                case ADD:
                    accumulator += computer.getMemoryLocation(operand);
                    instructionCounter++;
                    break;
                case SUBTRACT:
                    accumulator -= computer.getMemoryLocation(operand);
                    instructionCounter++;
                    break;
                case DIVIDE:
                    accumulator /= computer.getMemoryLocation(operand);
                    instructionCounter++;
                    break;
                case MULTIPLY:
                    accumulator *= computer.getMemoryLocation(operand);
                    instructionCounter++;
                    break;
                case REMAINDER:
                    accumulator %= computer.getMemoryLocation(operand);
                    instructionCounter++;
                    break;
                case POW:
                    accumulator = (int)Math.pow(accumulator, computer.getMemoryLocation(operand));
                    instructionCounter++;
                    break;
                case BRANCH:
                    instructionCounter = operand;
                    break;
                case BRANCHNEG:
                    if (accumulator < 0)
                    {
                        instructionCounter = operand;
                    }
                    else
                    {
                        instructionCounter++;
                    }
                    break;
                case BRANCHZERO:
                    if (accumulator == 0)
                    {
                        instructionCounter = operand;
                    }
                    else
                    {
                        instructionCounter++;
                    }
                    break;
                case HALT:
                    System.out.printf("%n*** Simpletron execution terminated ***\n");
                    running = false;
                    break;
                default:
                    throw new InputMismatchException("Invalid code");
            }
            
            if (accumulator < -9999 || accumulator > 9999)
            {
                throw new RuntimeException("Accumulator overflow");
            }
        }
    }
}
