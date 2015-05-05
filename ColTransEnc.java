/**
 * @author Michael Lant
 * @version 1.0
 */
package com.company;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ColTransEnc
{
    private String encryptIn;
    private String encryptKey;
    private String[] keyArray;
    String[][] msg;

    /**
     * Constructs an object used to encrypt Columnar Transposition Ciphers.
     *
     * @param encryptIn  The message to be encrypted
     * @param encryptKey The encryption key
     */
    protected ColTransEnc(String encryptIn, String encryptKey)
    {
        this.encryptIn = encryptIn;
        this.encryptKey = encryptKey;

        keyArray = new String[encryptKey.length()];

        for (int i = 0; i < encryptKey.length(); i++)
        {
            keyArray[i] = String.valueOf(encryptKey.charAt(i));
        }

        msg = new String[(int) Math.ceil((double) encryptIn.length() / (double) encryptKey.length())]
                [encryptKey.length()];

        int charCount = 0;

        for (int i = 0; i < (int) Math.ceil((double) encryptIn.length() / (double) encryptKey.length()); i++)
        {
            for (int j = 0; j < encryptKey.length(); j++)
            {
                if (charCount < encryptIn.length())
                {
                    msg[i][j] = String.valueOf(encryptIn.charAt(charCount));
                }
                else
                {
                    msg[i][j] = "!";
                }
                charCount++;
            }
        }
    }

    /**
     * Transposes the columns of the array containing the message using the encryption/decryption key.
     */
    private void encrypt() throws InterruptedException
    {
        columnSort();
        System.out.println("Message successfully encrypted.");
    }

    /**
     * Transposes the columns based the encryption/decryption key alphabetically.
     */
    private void columnSort()
    {
        for (int i = 0; i < keyArray.length; i++)
        {
            for (int j = 0; j < keyArray.length; j++)
            {
                if ((keyArray[i]).compareTo(keyArray[j]) < 0)
                {
                    columnSwap(i, j);
                }
            }
        }
    }

    /**
     * Takes parameters to switch two columns in the array containing the message.
     *
     * @param left  Column number that will be replacing right column
     * @param right Column number that will be replacing left column
     */
    private void columnSwap(int left, int right)
    {
        String temp = keyArray[left];
        keyArray[left] = keyArray[right];
        keyArray[right] = temp;

        String tmp = "";
        for (int i = 0; i < (int) Math.ceil((double) encryptIn.length() / (double) encryptKey.length()); i++)
        {
            tmp = msg[i][left];
            msg[i][left] = msg[i][right];
            msg[i][right] = tmp;
        }
    }

    /**
     * Runs the encryption method and outputs the encrypted message to a text file.
     */
    protected void encryptOut() throws InterruptedException, IOException
    {
        encrypt();
        DataOutputStream exc = new DataOutputStream(new FileOutputStream("src/ColTransEnc.txt"));

        for (int i = 0; i < (int) Math.ceil((double) encryptIn.length() / (double) encryptKey.length()); i++)
        {
            for (int j = 0; j < encryptKey.length(); j++)
            {
                exc.writeBytes(msg[i][j]);
            }
            exc.writeBytes(" ");
        }
        exc.close();
    }
}
