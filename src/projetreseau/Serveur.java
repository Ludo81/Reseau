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
import java.util.Scanner;

/**
 *
 * @author ltourn01
 */
public class Serveur {

    public static void main(String[] zero) {
        ServerSocket socketserver;
        Socket client;
        BufferedReader in;
        PrintWriter out;

        try {
            socketserver = new ServerSocket(4444);
            Socket clientSocket = null;

            Scanner s = new Scanner(System.in);

            client = socketserver.accept();
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            while (true) {

                String message = s.nextLine();
                out.println(message);
                out.flush();

                String message_distant = in.readLine();
                System.out.println("client : " + message_distant);

                try {
                    clientSocket = socketserver.accept();
                } catch (IOException e) {
                    System.out.println("Acceptation échoué sur le port 4444");
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            System.out.println("On ne peut pas écouter le port 4444");
            System.exit(-1);

        }
    }
}
