/**
 * @author Michael Lant
 * @version 1.0
 *
 * This encryption uses the Standard Variant of the Columnar Transposition Cipher.
 *
 * The plaintext is put into columns. If the plaintext does not fill the number
 * of columns given by the key, the remainder of the array is padded.
 *
 * The ending ciphertext is read out as one line.
 */
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Random;
import java.io.IOException;
import java.io.File;

public class ColTransEnc {
    private String plainText;
    private String encryptionKey;
    private String cipherText;
    private String[] keyArray;
    String[][] msg;


    /**
     * Constructs an object used to encrypt Columnar Transposition Ciphers.
     *
     * @param plainText     The message to be encrypted
     * @param encryptionKey The encryption key
     */
    protected ColTransEnc(String plainText, String encryptionKey) {
        this.cipherText = "";
        this.plainText = plainText;
        this.encryptionKey = encryptionKey;

        keyArray = new String[this.encryptionKey.length()];

        for (int i = 0; i < this.encryptionKey.length(); i++) {
            keyArray[i] = String.valueOf(this.encryptionKey.charAt(i));
        }

        msg = new String[(int) Math.ceil((double) this.plainText.length() / (double) this.encryptionKey.length())]
                [this.encryptionKey.length()];

        // Print for visual representation of cipher (may be commented out)
        System.out.println("\nPlaintext Input: " + plainText);

        // Print for visual representation of cipher (may be commented out)
        System.out.println("\nColumnar Transposition Matrix (Transposed): \n");

        int charCount = 0;

        for (int i = 0; i < (int) Math.ceil((double) this.plainText.length() /
                (double) this.encryptionKey.length()); i++) {
            for (int j = 0; j < this.encryptionKey.length(); j++) {
                if (charCount < this.plainText.length()) {
                    msg[i][j] = String.valueOf(this.plainText.charAt(charCount));
                } else {
                    Random rand = new Random();
                    char nullChar = (char) (rand.nextInt(26) + 'a');
                    msg[i][j] = String.valueOf(nullChar);
                }
                charCount++;

                // Print for visual representation of cipher (may be commented out)
                System.out.print(msg[i][j]);
            }

            // Print for visual representation of cipher (may be commented out)
            System.out.println();
        }
    }

    /**
     * Transposes the columns of the array containing the message using the encryption/decryption key.
     */
    private void encrypt() throws InterruptedException {
        columnSort();

        // Print for visual representation of cipher (may be commented out)
        System.out.println("\nColumnar Transposition Matrix: \n");

        for (int i = 0; i < (int) Math.ceil((double) plainText.length() / (double) encryptionKey.length()); i++) {
            for (int j = 0; j < encryptionKey.length(); j++) {
                cipherText += (msg[i][j]);

                // Print for visual representation of cipher (may be commented out)
                System.out.print(msg[i][j]);
            }
            // Print for visual representation of cipher (may be commented out)
            System.out.println();
        }

        // Print for visual representation of cipher (may be commented out)
        System.out.println("\nCiphertext Output: " + cipherText);
    }

    /**
     * Transposes the columns based the encryption/decryption key alphabetically.
     */
    private void columnSort() {
        for (int i = 0; i < keyArray.length; i++) {
            for (int j = 0; j < keyArray.length; j++) {
                if ((keyArray[i]).compareTo(keyArray[j]) < 0) {
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
    private void columnSwap(int left, int right) {
        String temp = keyArray[left];
        keyArray[left] = keyArray[right];
        keyArray[right] = temp;

        String tmp;
        for (int i = 0; i < (int) Math.ceil((double) plainText.length() / (double) encryptionKey.length()); i++) {
            tmp = msg[i][left];
            msg[i][left] = msg[i][right];
            msg[i][right] = tmp;
        }
    }

    /**
     * Runs the encryption method and outputs the encrypted message to a text file.
     */
    protected void encryptOut() throws InterruptedException, IOException {
        encrypt();
        DataOutputStream exc = new DataOutputStream(new FileOutputStream(new File("ColTransEnc.txt")));

        for (int i = 0; i < cipherText.length(); i++) {
            exc.writeByte(cipherText.charAt(i));
        }
        exc.close();
    }
}
