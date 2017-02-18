/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareas;

import agentes.AgenteOperacion;
import agentes.AgenteFormulario;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 *
 * @author jcsp0003
 */
public class buscarAgente extends TickerBehaviour {

    private AID[] agentes;
    private String tipo;
    private String tAgente;
    private AgenteOperacion operacion;
    private AgenteFormulario formulario;

    public buscarAgente(Agent _agente, long p, String _tipo, AgenteFormulario _formulario, String _tAgente) {
        super(_agente, p);
        this.tipo = _tipo;
        this.formulario = _formulario;
        this.tAgente = _tAgente;
    }
    
    public buscarAgente(Agent _agente, long p, String _tipo, AgenteOperacion _operacion, String _tAgente) {
        super(_agente, p);
        this.tipo = _tipo;
        this.operacion = _operacion;
        this.tAgente = _tAgente;
    }

    

    @Override
    protected void onTick() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setName(this.tipo);
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            if (result.length > 0) {
                agentes = new AID[result.length];
                for (int i = 0; i < result.length; ++i) {
                    agentes[i] = result[i].getName();
                }
                if ("Agente Operacion".equals(this.tAgente)) {
                    operacion.lista(agentes, result.length);
                } else {
                    if ("Agente Formulario".equals(this.tAgente)) {
                        if ("Operacion".equals(tipo)) {
                            formulario.operaciones(agentes, result.length);
                        } else {
                            if ("Consola".equals(tipo)) {
                                formulario.lista(agentes, result.length);
                            }
                        }
                    }
                }
            } else {
                //No se han encontrado agentes
                agentes = null;
                if ("Agente Operacion".equals(this.tAgente)) {
                    operacion.noLista();
                } else {
                    if ("Agente Formulario".equals(this.tAgente)) {
                        if ("Operacion".equals(tipo)) {
                            this.formulario.noOperacion();
                        } else {
                            if ("Consola".equals(tipo)) {
                                this.formulario.noLista();
                            }
                        }
                    }
                }
            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
}