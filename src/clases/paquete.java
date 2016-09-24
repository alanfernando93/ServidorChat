/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Alan
 */
public class paquete implements Serializable{
    
    private String id,Nick,ipDestino,Msg,iplocal,Descripcion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private HashMap listIps;

    @Override
    public String toString() {
        return "paquete{" + "Nick=" + Nick + ", ipDestino=" + ipDestino + ", Msg=" + Msg + ", iplocal=" + iplocal + ", Descripcion=" + Descripcion + ", listIps=" + listIps + '}';
    }
    
    public paquete(){
        
    }
    
    public paquete(HashMap lista){
        this.listIps = lista;
    }
    
    public paquete(String nick,String descripcion){
        this.Nick = nick;
        this.Descripcion = descripcion;
    }

    public paquete(String nick,String destinoIp,String msg){
        this.Nick = nick;
        this.ipDestino = destinoIp;
        this.Msg = msg;
    }
        

    public HashMap getListIps() {
        return listIps;
    }

    public void setListIps(HashMap listIps) {
        this.listIps = listIps;
    }
    
    public String getNick() {
        return Nick;
    }

    public void setNick(String Nick) {
        this.Nick = Nick;
    }

    public String getIpDestino() {
        return ipDestino;
    }

    public void setIpDestino(String ipDestino) {
        this.ipDestino = ipDestino;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getIplocal() {
        return iplocal;
    }

    public void setIplocal(String iplocal) {
        this.iplocal = iplocal;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
}
