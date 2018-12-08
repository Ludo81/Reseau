/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author ltourn01
 */
public class Serveur {

    public static void main(String[] zero) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, Exception {

        ServerSocket socketserver;
        Socket client;
        BufferedReader in;
        PrintWriter out;
        CryptageCle cc = new CryptageCle();
        String key = cc.getCle();

        try {
            socketserver = new ServerSocket(4444);
            Socket clientSocket = null;

            Scanner s = new Scanner(System.in);

            client = socketserver.accept();
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            boolean etat = true;

            while (true) {
                if (etat) { //Envoie du message

                    String message = s.nextLine();
                    String crypte = cc.encrypter(message, key);
                    out.println(crypte);
                    out.flush();

                    etat = false;
                } else { //Ecoute du message
                    String message_distant = in.readLine();
                    try {
                        System.out.println("client 'crypté': " + message_distant);
                        String decrypte = cc.decrypter(message_distant, key);
                        System.out.println("client 'décrypté' : " + decrypte);
                        if (decrypte.equals("bye")) {
                            break;
                        }
                    } catch (NullPointerException e) {
                        break;
                    }

                    etat = true;
                }
            }
            out.close();
            in.close();
            client.close();
            socketserver.close();
        } catch (IOException e) {
            System.out.println("On ne peut pas écouter le port 4444");
            System.exit(-1);

        }
    }
}
