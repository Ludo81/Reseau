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

/**
 *
 * @author ludovic
 */
public class CryptageCle {
    
    String cle = "";

    public CryptageCle(){

        try {                                    //Si le fichier exisite
            File f = new File("result.txt");
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

                File fic = new File("result.txt"); // définir l'arborescence
                fic.createNewFile();

                FileWriter ficEcrire = new FileWriter(fic);

                ficEcrire.write(cle);  // écrire une ligne dans le fichier resultat.txt

                ficEcrire.close(); // fermer le fichier à la fin des traitements

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
    }
    
    public String getCle(){
        return cle;
    }
    
    public String encrypter(String message, String cle) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        
        byte[] data;
        byte[] result;
            
        Cipher cipher = Cipher.getInstance("AES");
        
        byte[] decodedKey = Base64.getDecoder().decode(cle);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        
        cipher.init(Cipher.ENCRYPT_MODE, key); //Encriptage avec la clé
        data = message.getBytes();
        result = cipher.doFinal(data);
        
        String crypte = new String(result);
        
        return crypte;
    }
    
    public String decrypter(String message, String cle) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        
        byte[] data;
        byte[] result;
            
        Cipher cipher = Cipher.getInstance("AES");
        
        byte[] decodedKey = Base64.getDecoder().decode(cle);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        
        cipher.init(Cipher.DECRYPT_MODE, key); //Encriptage avec la clé
        data = message.getBytes();
        result = cipher.doFinal(data);
        
        String decrypte = new String(result);
        
        return decrypte;
    }
    
}
