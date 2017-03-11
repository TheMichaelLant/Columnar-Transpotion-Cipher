/**
 * @author Michael Lant
 * @version 1.0
 */
package com.company;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Coldecv1{
    private String decryptIn;
    private String decryptKey;
    private String[] keyArray;
    private String[] keyTemp;
    String[][] msg;

    /**
     * Constructs an object used to decrypt Columnar Transposition Ciphers.
     *
     * @param decryptIn  The message to be decrypted
     * @param decryptKey The decryption key
     */
    protected ColTransDec(String decryptIn, String decryptKey) {
        this.decryptIn = decryptIn;
        this.decryptKey = decryptKey;
        keyArray = new String[decryptKey.length()];
        keyTemp = new String[decryptKey.length()];

        for (int i = 0; i < decryptKey.length(); i++) {
            keyArray[i] = String.valueOf(decryptKey.charAt(i));
            keyTemp[i] = String.valueOf(decryptKey.charAt(i));
        }

        msg = new String[(int) Math.ceil((double) decryptIn.length() / (double) decryptKey.length())]
                [decryptKey.length()];

        int charCount = 0;

        for (int i = 0; i < (int) Math.ceil((double) decryptIn.length() / (double) decryptKey.length()); i++) {
            for (int j = 0; j < decryptKey.length(); j++) {
                if (charCount < decryptIn.length()) {
                    msg[i][j] = String.valueOf(decryptIn.charAt(charCount));
                } else {
                    msg[i][j] = "!";
                }
                charCount++;
            }
        }
    }

    /**
     * Checks and sorts the transposed columns using the encryption/decryption key.
     */
    private void decrypt() {
        keySort();

        for (int i = 0; i < keyArray.length; i++) {
            if (((keyArray[i]).compareTo(keyTemp[i]) != 0)) {
                for (int j = i + 1; j < keyArray.length; j++) {
                    if (((keyArray[j]).compareTo(keyTemp[i]) == 0)) {
                        keySwap(i, j);
                        columnSwap(i, j);
                    }
                }
            }
        }
    }

    /**
     * Sorts the encryption/decryption key alphabetically.
     */
    public void keySort() {
        for (int i = 0; i < keyArray.length; i++) {
            for (int j = 0; j < keyArray.length; j++) {
                if ((keyArray[i]).compareTo(keyArray[j]) < 0) {
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
    private void columnSwap(int left, int right) {
        String tmp = "";

        for (int i = 0; i < (int) Math.ceil((double) decryptIn.length() / (double) decryptKey.length()); i++) {
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
    private void keySwap(int left, int right) {
        String temp = keyArray[left];
        keyArray[left] = keyArray[right];
        keyArray[right] = temp;
    }

    /**
     * Runs the decryption method and outputs the decrypted message to a text file.
     */
    protected void decryptOut() throws IOException {
        decrypt();
        DataOutputStream exc = new DataOutputStream(new FileOutputStream("src/ColTransDec.txt"));

        for (int i = 0; i < (int) Math.ceil((double) decryptIn.length() / (double) decryptKey.length()); i++) {
            for (int j = 0; j < decryptKey.length(); j++) {
                exc.writeBytes(msg[i][j]);
            }
        }
        exc.close();
    }
}
