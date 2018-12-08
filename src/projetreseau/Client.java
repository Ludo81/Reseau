package projetreseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
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
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        
        InetAddress addr;
        Socket client = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        ResolutionDeNom rdn = new ResolutionDeNom();
        String ip = rdn.getIp();
        
        CryptageCle cc = new CryptageCle();
        String key = cc.getCle();
        
        try {
            Scanner sc = new Scanner(System.in);
            client = new Socket(ip, 4444);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String a = in.readLine();
            if (! a.equals("bye")) {
                System.out.println("serveur 'original' : " + a);
                String crypte = cc.encrypter(a, key);
                System.out.println("serveur 'crypté' : " + crypte);
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
        System.out.print("close");
        out.close();
        in.close();
        entree.close();
        client.close();
    }
}
