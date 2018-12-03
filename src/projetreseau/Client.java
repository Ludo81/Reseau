package projetreseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        InetAddress addr;
        Socket client = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            Scanner sc = new Scanner(System.in);
            client = new Socket("10.163.6.6", 4444);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String a = in.readLine();
            while (! a.equals("bye")) {
                System.out.println("serveur : " + a);
            }
            

        } catch (UnknownHostException e) {
            System.out.println("Destination inconnu");
            System.exit(-1);
        } catch (IOException ioe) {
            System.out.println("Il faut inverstiguer ce IO marlich");
            System.exit(-1);
        }
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        while ((userInput = entree.readLine()) != null) {
            out.println(userInput);
            System.out.println("echo : " + in.readLine());
        }
        out.close();
        in.close();
        entree.close();
        client.close();
    }
}
