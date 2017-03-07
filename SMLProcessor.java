
/**
 * Simpletron Machine Language Simulation
 * 
 * @Carlos Revés 
 * @version 3.0
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class SMLProcessor
{
    private final Simpletron computer;
    private int accumulator;
    private int instructionCounter;
    private int operationCode;
    private int operand;
    private int instructionRegister;
    private final int READ = 0x10;
    private final int WRITE = 0x11;
    private final int NEW_LINE = 0x12;
    private final int READ_STRING = 0x13;
    private final int WRITE_STRING = 0x14;
    private final int LOAD = 0x20;
    private final int STORE = 0x21;
    private final int ADD = 0x30;
    private final int SUBTRACT = 0x31;
    private final int DIVIDE = 0x32;
    private final int MULTIPLY = 0x33;
    private final int REMAINDER = 0x34;
    private final int POW = 0x35;
    private final int BRANCH = 0x40;
    private final int BRANCHNEG = 0x41;
    private final int BRANCHZERO = 0x42;
    private final int HALT = 0x43;
    
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
            operationCode = instructionRegister >> 8;
            operand = instructionRegister & 0xff;
            Scanner scan = new Scanner(System.in);
            switch(operationCode)
            {
                case READ:                    
                    System.out.print("Enter an integer: ");
                    computer.setMemoryLocation(operand, scan.nextInt());
                    //scan.next();
                    instructionCounter++;
                    break;
                case WRITE:
                    System.out.printf("%n%d", computer.getMemoryLocation(operand));
                    instructionCounter++;
                    break;
                case NEW_LINE:
                    System.out.printf("%n");
                    instructionCounter++;
                    break;
                case READ_STRING:
                    System.out.print("Enter the String: ");
                    String inputString = scan.nextLine();
                    storeString(inputString);
                    instructionCounter++;
                    break;
                case WRITE_STRING:
                    System.out.printf("%s", getString());
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
            
            if (accumulator < -0xffff || accumulator > 0xffff)
            {
                throw new RuntimeException("Accumulator overflow");
            }
        }   
        
        printDump();
    }
    
    private void storeString(String inputString)
    {
        int stringLength = inputString.length();
        accumulator = stringLength << 8;
        accumulator += inputString.charAt(0);
        computer.setMemoryLocation(operand, accumulator);
        operand++;
        
        for (int index = 1; index < stringLength; index += 2)
        {
            accumulator = inputString.charAt(index) << 8;
            if (index + 1 < stringLength)
            {
                accumulator += inputString.charAt(index + 1);
            }
            computer.setMemoryLocation(operand, accumulator);
            operand++;
        }
    }
    
    private String getString()
    {
        int stringLength = computer.getMemoryLocation(operand) >> 8;
        StringBuilder buffer = new StringBuilder(41);
        
        for (int index = 0; index < stringLength; index++)
        {
            if (index % 2 == 0)
            {
                buffer.append((char)(computer.getMemoryLocation(operand) & 0xff));
                operand++;
            }
            else
            {
                buffer.append((char)(computer.getMemoryLocation(operand) >> 8));
            }
        }
        
        return buffer.toString();
    }
    
    private void printDump()
    {
        System.out.printf ("%nREGISTERS:%n");
        System.out.printf ("%-21s 0x%-5s%n", "accumulator", Integer.toHexString(accumulator));
        System.out.printf ("%-21s 0x%-2s%n", "instructionCounter", Integer.toHexString(instructionCounter));
        System.out.printf ("%-21s 0x%-5s%n", "instructionRegister", Integer.toHexString(instructionRegister));
        System.out.printf ("%-21s 0x%-2s%n", "operationCode", Integer.toHexString(operationCode));
        System.out.printf ("%-21s 0x%-2s%n", "operand", Integer.toHexString(operand));
        System.out.printf("%nMEMORY:%n     ");
        
        for (int i = 0; i < 16; i++)
        {
            System.out.printf ("%6s", Integer.toHexString(i));
        }
        
        for (int i = 0; i < computer.getMemoryCapacity(); i++)
        {
            if (i % 16 == 0)
            {
                System.out.printf("%n0x%-3s ", Integer.toHexString(i >> 4));
            }
            
            int memoryValue = computer.getMemoryLocation(i);
            System.out.printf("%5s ", Integer.toHexString(memoryValue));
        }
        
        System.out.printf("%n");
    }
}
