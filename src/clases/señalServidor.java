/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.home;

/**
 *
 * @author Huayllani
 */
public class señalServidor extends Thread {
    
    private home parent;
    
    public señalServidor(home p){
        this.parent = p;
    }

    @Override
    public void run() {
        String des,ipLocal,ipDestino,nick,msg;
        paquete recibido, enviar;
        InetAddress address ;
        ArrayList<String> listIpTem = new ArrayList();
        HashMap<String, String> listIp = new HashMap<>();
        try {
            ServerSocket puente = new ServerSocket(9999);
            while (true) {
                Socket conexion = puente.accept();
                ObjectInputStream capturarObjeto = new ObjectInputStream(conexion.getInputStream());
                recibido = (paquete) capturarObjeto.readObject();
                ipDestino = recibido.getIpDestino();
                des = recibido.getDescripcion();
                nick = recibido.getNick();
                msg = recibido.getMsg();
                switch (des){                    
                    case "ChatGrupal" : 
                        for (String ip : listIpTem) {
                            Socket conChatGrupal = new Socket(ip, 9090);
                            ObjectOutputStream enviarObjetoCG = new ObjectOutputStream(conChatGrupal.getOutputStream());
                            enviarObjetoCG.writeObject(recibido);
                            enviarObjetoCG.close();
                            conChatGrupal.close();
                        }
                        break;             
                    case "Conectado" :  
                        address = conexion.getInetAddress();
                        ipLocal = address.getHostAddress();
                        listIp.put(nick, ipLocal);
                        listIpTem.add(ipLocal);
                        recibido.setListIps(listIp);
                        recibido.setDescripcion("listaIps");
                        home.jTextArea1.setText("");
                        //parent.getList1().removeAll();
                        for (String ip : listIpTem) {
                            Socket con = new Socket(ip, 9090);
                            ObjectOutputStream enviarObjeto = new ObjectOutputStream(con.getOutputStream());
                            enviarObjeto.writeObject(recibido);                            
                            parent.getList1().addItem(nick);
                            enviarObjeto.close();
                            con.close();
                            home.jTextArea1.append(ip + "\n");
                        }
                        break;
                    case "Desconectado":
                        parent.getList1().remove(nick);
                        address = conexion.getInetAddress();
                        ipLocal = address.getHostAddress();
                        listIp.remove(nick);
                        listIpTem.remove(ipLocal);
                        recibido.setListIps(listIp);
                        recibido.setDescripcion("listaIps");
                        home.jTextArea1.append(ipLocal + ": Desconectado\n");
                        for (String ip : listIpTem) {
                            Socket con = new Socket(ip, 9090);
                            ObjectOutputStream enviarObjeto = new ObjectOutputStream(con.getOutputStream());
                            enviarObjeto.writeObject(recibido);
                            enviarObjeto.close();
                            con.close();
                        }
                        break;
                    case "chatIndividual":
                        //System.out.println(recibido.toString());   
                        Socket conChatIndividual = new Socket(ipDestino, 9090);
                        ObjectOutputStream enviarObjetoCI = new ObjectOutputStream(conChatIndividual.getOutputStream());
                        enviarObjetoCI.writeObject(recibido);
                        
                        enviarObjetoCI.close();
                        conChatIndividual.close();
                        break;
                }
                capturarObjeto.close();
                conexion.close();
            }  
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(señalServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
