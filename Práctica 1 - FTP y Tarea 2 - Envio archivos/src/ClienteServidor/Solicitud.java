package ClienteServidor;

import java.io.*;

/**
 *
 * @author Carlos Tule
 */
public class Solicitud implements Serializable{
    String req;
    int num;
    public Solicitud(String req,int num){
        this.req = req;
        this.num = num;
    }
    public String getReq(){
        return req;
    }
}
