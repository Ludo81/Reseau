/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseau;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author ltourn01
 */
public class ResolutionDeNom {
    
    String ip = "";

    public ResolutionDeNom() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    
    public String getIp(){
        return ip;
    }
    
}
