package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws InterruptedException, IOException
    {
        Scanner input = new Scanner(System.in);
        Scanner loadMSG = null;

        System.out.println("Columnar Transposition Ciphers V1");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Action: ");

        int choice = input.nextInt();

        while ((choice<1) || (choice>2))
        {
            System.out.println("\nPlease choose a valid option.");
            System.out.print("Action: ");
            choice = input.nextInt();
        }

        System.out.println();
        System.out.print("Please enter the location of the file: ");
        String location = input.next();
        File f = new File(location);

        while (!f.exists())
        {
            System.out.println("That file does not exist.");
            System.out.print("Please enter the location of the file: ");
            location = input.next();
            f = new File(location);
        }

        System.out.println("File Located.");
        loadMSG = new Scanner(new File(location));
        loadMSG.useDelimiter("\\s");
        String message = "";

        System.out.println();
        while (loadMSG.hasNext())
        {
            message += loadMSG.next();
        }

        System.out.print("Please enter encryption key: ");
        String key = input.next();

        if (choice==2)
        {
            ColTransDec dec = new ColTransDec(message, key);
            dec.decryptOut();
            System.out.println("Message successfully decrypted.");
        }
        else
        {
            ColTransEnc enc = new ColTransEnc(message, key);
            enc.encryptOut();
        }
    }
}
