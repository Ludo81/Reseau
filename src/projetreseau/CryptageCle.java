/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseau;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author ludovic
 */
public class CryptageCle {

    String cle = "";

    /**
     * Clé une clé si le fichier result n'est pas existant
     */
    public CryptageCle() {

        try {                                    //Si le fichier exisite
            File f = new File("clef.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            try {
                String line = br.readLine();

                cle = line;

                byte[] decodedKey = Base64.getDecoder().decode(line);
                SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

                br.close();
                fr.close();
            } catch (IOException exception) {
                System.out.println("Erreur lors de la lecture : " + exception.getMessage());
            }

        } catch (FileNotFoundException exception) { //Si le fichier n'existe pas, on créé la clé

            Key key = null;

            try {              //Ecriture de la clé dans fichier
                KeyGenerator kg = KeyGenerator.getInstance("AES");
                key = kg.generateKey();

                cle = Base64.getEncoder().encodeToString(key.getEncoded());

                File fic = new File("clef.txt"); // définir l'arborescence
                fic.createNewFile();

                FileWriter ficEcrire = new FileWriter(fic);

                ficEcrire.write(cle);  // écrire une ligne dans le fichier resultat.txt

                ficEcrire.close(); // fermer le fichier à la fin des traitements

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String getCle() {
        return cle;
    }

    /**
     *
     * @param message
     * @param key
     * @return Retourne le message décrypté en message crypté
     * @throws Exception
     */
    public String encrypter(String message, String key) throws Exception {
            Key clef = new SecretKeySpec(key.getBytes("ISO-8859-2"), "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, clef);
            byte[] encVal = c.doFinal(message.getBytes());
            String encryptedValue = DatatypeConverter.printBase64Binary(encVal);
            return encryptedValue;
    }

    /**
     *
     * @param message
     * @param key
     * @return  Retourne le message crypté en message décrypté
     * @throws Exception
     */
    public String decrypter(String message, String key) throws Exception {
        Key clef = new SecretKeySpec(key.getBytes("ISO-8859-2"), "AES");
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, clef);       
        byte[] decordedValue = DatatypeConverter.parseBase64Binary(message);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

}
