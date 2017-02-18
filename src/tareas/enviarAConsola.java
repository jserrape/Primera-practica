/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareas;

import agentes.AgenteFormulario;
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

    private AID agentesConsola;
    private AgenteFormulario formulario;
    private AgenteOperacion operacion;
    private String tipo;
    private ArrayList<String> mensajesPendientes;

    public enviarAConsola(Agent _agente, long p, String _tipo, AgenteFormulario _formulario) {
        super(_agente, p);
        this.tipo = _tipo;
        this.formulario = _formulario;
        this.agentesConsola = myAgent.getAID();
    }

    public enviarAConsola(Agent _agente, long p, String _tipo, AgenteOperacion _operacion) {
        super(_agente, p);
        this.tipo = _tipo;
        this.operacion = _operacion;
        this.agentesConsola = myAgent.getAID();
    }

    @Override
    protected void onTick() {
        ACLMessage mensaje;
        AID receptor = null;
        boolean notNull = false;
        boolean mensajesEnEspera = false;
        String mensajeP = null;

        if ("Agente Operacion".equals(tipo)) {
            if (operacion.getAgentesConsola() != null) {
                notNull = true;
                if (!operacion.getMensajesPendientes().isEmpty()) {
                    mensajesEnEspera = true;
                    receptor = operacion.getAgentesConsola()[0];
                    mensajeP = operacion.getMensajesPendientes().remove(0);
                }
            }
        } else if ("Agente Formulario".equals(tipo)) {
            if (formulario.getAgentesConsola() != null) {
                notNull = true;
                if (!formulario.getMensajesPendientes().isEmpty()) {
                    mensajesEnEspera = true;
                    receptor = formulario.getAgentesConsola()[0];
                    mensajeP = formulario.getMensajesPendientes().remove(0);
                }
            }
        }
        if (notNull && mensajesEnEspera) {
            mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.setSender(this.agentesConsola);
            mensaje.addReceiver(receptor);
            mensaje.setContent(mensajeP);
            myAgent.send(mensaje);
        } else {
            //Si queremos hacer algo si no tenemos mensajes
            //pendientes para enviar a la consola
        }
    }
}
