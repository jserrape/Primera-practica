/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareas;

import agentes.AgenteOperacion;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class enviarAConsola extends TickerBehaviour {

    private AID[] agentesConsola;
    private ArrayList<String> mensajesPendientes;

    public enviarAConsola(AgenteOperacion agente, long p) {
        super(agente, p);
    }

    @Override
    protected void onTick() {
        ACLMessage mensaje;
        if (agentesConsola != null) {
            if (!mensajesPendientes.isEmpty()) {
                mensaje = new ACLMessage(ACLMessage.INFORM);
                mensaje.setSender(myAgent.getAID());
                mensaje.addReceiver(agentesConsola[0]);
                mensaje.setContent(mensajesPendientes.remove(0));

                myAgent.send(mensaje);
            } else {
                //Si queremos hacer algo si no tenemos mensajes
                //pendientes para enviar a la consola
            }
        }
    }
}
