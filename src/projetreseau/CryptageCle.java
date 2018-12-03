/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseau;

import java.io.File;
import java.io.FileWriter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 *
 * @author ludovic
 */
public class CryptageCle {

    public static void main(String[] args) {
        byte[] data;
        byte[] result;
        byte[] original;
        
        Key key = null;

        try {                   //Cryptage d'un message
            
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            key = kg.generateKey();
            
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);
            data = "test".getBytes();
            result = cipher.doFinal(data);
            
            cipher.init(Cipher.DECRYPT_MODE, key);
            original = cipher.doFinal(result);
            
            String crypte = new String (result);
            
            System.out.println(crypte);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {              //Ecriture du message crypté dans un fichier
            
            String cle = Base64.getEncoder().encodeToString(key.getEncoded());
            File fic = new File("result.txt"); // définir l'arborescence
            fic.createNewFile();
            
            FileWriter ficEcrire = new FileWriter(fic);
            
            ficEcrire.write(cle);  // écrire une ligne dans le fichier resultat.txt
            
            ficEcrire.close(); // fermer le fichier à la fin des traitements
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Reconstruire clé a partie String
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        */
    }
}

