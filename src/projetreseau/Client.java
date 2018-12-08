package projetreseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Client {

    public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, Exception {

        ResolutionDeNom rdn = new ResolutionDeNom();
        String ip = rdn.getIp();

        CryptageCle cc = new CryptageCle();
        String key = cc.getCle();

        Socket client;
        BufferedReader in;
        PrintWriter out;

        try {

            client = new Socket(ip, 4444);

            Scanner s = new Scanner(System.in);

            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            boolean etat = false;

            while (true) {
                if (etat) { // Envoi du message

                    String message = s.nextLine();
                    String crypte = cc.encrypter(message, key);
                    out.println(crypte);
                    out.flush();

                    
                    etat = false;
                } else { //Ecoute du message
                    String message_distant = in.readLine();
                    try {
                        String decrypte = cc.decrypter(message_distant, key);
                        System.out.println("serveur : " + decrypte);
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
            //socketserver.close();
        } catch (IOException e) {
            System.out.println("On ne peut pas écouter le port 4444");
            System.exit(-1);

        }
    }
}
