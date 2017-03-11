/**
 * @author Michael Lant
 * @version 1.0
 */
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class ColTransDec
{
    private String cipherText;
    private String decryptionKey;
    private String plainText;
    private String[] keyArray;
    private String[] keyTemp;
    String[][] msg;

    /**
     * Constructs an object used to decrypt Columnar Transposition Ciphers.
     *
     * @param cipherText  The message to be decrypted
     * @param decryptionKey The decryption key
     */
    protected ColTransDec(String cipherText, String decryptionKey)
    {
        this.plainText="";
        this.cipherText = cipherText;
        this.decryptionKey = decryptionKey;
        keyArray = new String[this.decryptionKey.length()];
        keyTemp = new String[this.decryptionKey.length()];

        for (int i = 0; i < this.decryptionKey.length(); i++)
        {
            keyArray[i] = String.valueOf(this.decryptionKey.charAt(i));
            keyTemp[i] = String.valueOf(this.decryptionKey.charAt(i));
        }

        msg = new String[(int) Math.ceil((double) this.cipherText.length() / (double) this.decryptionKey.length())]
                [this.decryptionKey.length()];

        int charCount = 0;

        for (int i = 0; i < this.decryptionKey.length(); i++)
        {
            for (int j = 0; j < (int) Math.ceil((double) this.cipherText.length() / (double) this.decryptionKey.length()); j++)
            {
                if (charCount < this.cipherText.length())
                {
                    msg[j][i] = String.valueOf(this.cipherText.charAt(charCount));
                }
                else
                {
                    msg[j][i] = "!";
                }
                charCount++;
            }
        }
    }

    /**
     * Checks and sorts the transposed columns using the encryption/decryption key.
     */
    private void decrypt()
    {
        keySort();

        // Print for visual representation of cipher (may be commented out)
        System.out.println("\nCiphertext Input: " + cipherText);

        for (int i = 0; i < keyArray.length; i++)
        {
            if (((keyArray[i]).compareTo(keyTemp[i]) != 0))
            {
                for (int j = i + 1; j < keyArray.length; j++)
                {
                    if (((keyArray[j]).compareTo(keyTemp[i]) == 0))
                    {
                        keySwap(i, j);
                        columnSwap(i, j);
                    }
                }
            }
        }

        for (int i = 0; i < (int) Math.ceil((double) cipherText.length() / (double) decryptionKey.length()); i++)
        {
            for (int j = 0; j < decryptionKey.length(); j++)
            {
                plainText += (msg[j][i]);
            }
        }

        // Print for visual representation of cipher (may be commented out)
        System.out.println("\nPlaintext Output: " + plainText);
    }

    /**
     * Sorts the encryption/decryption key alphabetically.
     */
    public void keySort()
    {
        for (int i = 0; i < keyArray.length; i++)
        {
            for (int j = 0; j < keyArray.length; j++)
            {
                if ((keyArray[i]).compareTo(keyArray[j]) < 0)
                {
                    keySwap(i, j);
                }
            }
        }
    }

    /**
     * Takes parameters to switch two columns in the array containing encrypted text.
     *
     * @param left  Column number that will be replacing right column
     * @param right Column number that will be replacing left column
     */
    private void columnSwap(int left, int right)
    {
        String tmp;

        for (int i = 0; i < (int) Math.ceil((double) cipherText.length() / (double) decryptionKey.length()); i++)
        {
            tmp = msg[i][left];
            msg[i][left] = msg[i][right];
            msg[i][right] = tmp;
        }
    }

    /**
     * Takes parameters to switch two columns in the array containing encryption/decryption key.
     *
     * @param left  Column number that will be replacing right column
     * @param right Column number that will be replacing left column
     */
    private void keySwap(int left, int right)
    {
        String temp = keyArray[left];
        keyArray[left] = keyArray[right];
        keyArray[right] = temp;
    }

    /**
     * Runs the decryption method and outputs the decrypted message to a text file.
     */
    protected void decryptOut() throws IOException
    {
        decrypt();
        DataOutputStream exc = new DataOutputStream(new FileOutputStream(new File("ColTransDec.txt")));

        for (int i = 0; i < plainText.length(); i++)
        {
            exc.writeByte(plainText.charAt(i));
        }
        exc.close();
    }
}
