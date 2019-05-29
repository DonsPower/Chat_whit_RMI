/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_rmi;
import Interfaces.InterfazCliente;
import Interfaces.InterfazServidor;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dons
 */
public class Servidor extends UnicastRemoteObject implements InterfazServidor{
    //Objeto remoto
    private Vector lista_clientes;
    
    
    public Servidor()throws RemoteException{
        super();
        lista_clientes= new Vector();
    }

    @Override
    public void registrar(InterfazCliente cliente) throws RemoteException {
        if(cliente!=null) lista_clientes.add(cliente);
    }

    @Override
    public void publicar(String msg) throws RemoteException {
        for (int i=0;i<lista_clientes.size();i++) {
            InterfazCliente cln=(InterfazCliente)lista_clientes.get(i);
            cln.enviarMsg(msg);
        }
    }

    @Override
    public Vector obtenerClientesActivos() throws RemoteException {
        return lista_clientes;
    }
    
    public static void main(String args[]){
        try {
            //Creamos el objeto remoto en esta caso sera el servidor
            Servidor serv=new Servidor();
            Registry reg= LocateRegistry.createRegistry(1099);
            //Enlazar el objeto remoto para que sea publico 
            reg.rebind("Servidor", serv);
            System.out.println("Servidor activo...");
            System.out.println("");
        } catch (RemoteException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
