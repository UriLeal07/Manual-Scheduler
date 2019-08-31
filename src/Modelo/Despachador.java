package Modelo;

import Controlador.Sistema;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Color;

public class Despachador extends Thread
{
    private final Sistema s;
    private final Proceso procesos[];
    private final Recurso recursos[];
    private final ArrayList<Proceso> Alta, Baja;
    private final ArrayList<Instruccion> gIns;
    private final int numProcesos, numRecursos;
    private final int delay;
    private Graphics g;

    public Despachador(int delay, Sistema s, Graphics g)
    {
        super("Despachador");
        numProcesos = 4;
        numRecursos = 5;
        procesos = new Proceso[numProcesos];
        recursos = new Recurso[numRecursos];
        Alta = new ArrayList<>();
        Baja = new ArrayList<>();
        gIns = new ArrayList<>();
        this.delay = delay;
        this.s = s;
        this.g = g;
    }
    
    @Override
    public void run()
    {
        inicializar();
        actualizaInfo();
        
        while(!Alta.isEmpty() || !Baja.isEmpty())
        {
            despachaProcesos();
            despiertaProcesos();
            actualizaInfo();
        }

        actualizaInfo();
        s.restaurar();
        dibujar(g);
    }
    
    public void despachaProcesos()
    {
        // Una lista para obtener las instrucciones de cada proceso
        // Una para separar instrucciones 'solicita'
        // Una para separar instrucciones 'libera'
        ArrayList <Instruccion> instrucciones = new ArrayList <>();
        ArrayList <Instruccion> instListasLibera = new ArrayList <>();
        ArrayList <Instruccion> instListasSolicita = new ArrayList <>();
        String[] instr = new String[numProcesos];
        String[] instrListas = new String[numProcesos];
        int i = 0;
        
        //Despacha procesos de lista de alta prioridad
        if(!Alta.isEmpty())
        {
            //Para cada proceso en lista Alta
            //se obtiene su instruccion.
            for(Proceso actual : Alta)
            {
                //Si el estado del proceso es 'Listo'
                if(actual.getEstado() == 0)
                {
                    String instruccion = actual.getInstActual();
                    String nombreRecurso = String.valueOf(instruccion.charAt(instruccion.length()-1));
                    instrucciones.add(new Instruccion(instruccion, actual, getRecurso((nombreRecurso))));
                    instr[i++] = instruccion;
                }
            }
            
            s.escribirInstrucciones(instr, "instrucciones");
            
            // Separar Instrucciones
            for(i = 0; i < instrucciones.size(); i++)
            {
                Instruccion actual = instrucciones.get(i);
                //Si la instruccion es 'libera'
                if(actual.getInstruccion().contains("libera"))
                    instListasLibera.add(actual);
                    
                //Si la instruccion es 'solicita'
                if(actual.getInstruccion().contains("solicita"))
                {
                    Proceso x = actual.getProceso();
                    Recurso y = actual.getRecurso();
                        
                    //Si el recurso esta disponible
                    if(y.getEstado() == 0)
                        instListasSolicita.add(actual);
                        
                    //Si no el proceso se marca en espera del recurso
                    else
                    {
                        x.setEstado(2);
                        x.setEsperandoRecurso(actual.getRecurso().getNombre());
                    }
                }
            }

            // Matriz para guardar (de cada proceso):
            // 1.-Pasos hasta que se libera el recurso, 2.-Instrucciones restantes                
            Object info[][] = new Object[3][instListasSolicita.size()];
            ArrayList <Proceso> auxProc = new ArrayList<>();
            ArrayList pasos = new ArrayList();
            int n = 0;
            
            // Verificar si dos o mas procesos solicitan el mismo recurso
            // de ser asi llenamos la matriz 'info' con la informacion de
            // cada proceso.
            for(i = 0; i < instListasSolicita.size(); i++)
            {
                Instruccion s1 = instListasSolicita.get(i);

                for(int j = i+1; j < instListasSolicita.size(); j++)
                {
                    Instruccion s2 = instListasSolicita.get(j);

                    // Si dos procesos solicitan el mismo recurso
                    if(s1.getRecurso().equals(s2.getRecurso()))
                    {
                        Proceso p;
                            
                        if(!auxProc.contains(s1.getProceso()))
                        {
                            p = s1.getProceso();
                            auxProc.add(p);
                            pasos.add(p.pasosLibera(s1.getRecurso().getNombre()));
                            info[0][n] = p;
                            info[1][n] = p.pasosLibera(s1.getRecurso().getNombre());
                            info[2][n++] = p.getInstRestantes();
                        }

                        if(!auxProc.contains(s2.getProceso()))
                        {
                            p = s2.getProceso();
                            auxProc.add(p);
                            pasos.add(p.pasosLibera(s2.getRecurso().getNombre()));
                            info[0][n] = p;
                            info[1][n] = p.pasosLibera(s2.getRecurso().getNombre());
                            info[2][n++] = p.getInstRestantes();                                    
                        }
                    }
                }
            }

            // Si hay procesos que solicitan el mismo recurso
            if(!auxProc.isEmpty())
            {
                int nProcesos = auxProc.size(), menor = (int)info[1][0];
                pasos.remove(0);
                Proceso pAux = (Proceso)info[0][0];
                auxProc.clear();
                
                // Si existe al menos un proceso que libera el recurso en igual numero de pasos
                if(pasos.contains((int)menor))
                {
                    // Verificamos que procesos liberan el recurso en igual numero de pasos
                    for(i = 0; i < nProcesos; i++)
                    {
                        for(int j = i+1; j < nProcesos; j++)
                            if((int)info[1][i] == (int)info[1][j])
                            {
                                if(!auxProc.contains((Proceso)info[0][i]))
                                    auxProc.add((Proceso)info[0][i]);

                                if(!auxProc.contains((Proceso)info[0][j]))
                                   auxProc.add((Proceso)info[0][j]);
                            }
                    }
                }
                
                // Si hay procesos que devuelven el mismo recurso en igual numero de pasos
                // Verificamos cual de ellos termina de ejecutarse primero
                if(!auxProc.isEmpty())
                {
                    menor = 50;
                    
                    for (i = 0; i < auxProc.size(); i++)
                    {
                        if((int)info[2][i] < menor)
                        {
                            menor = (int)info[2][i];
                            pAux = (Proceso)info[0][i];
                        }
                    }
                }
                    
                // Si Existe un proceso que libera antes el recurso
                // O que termina su ejecucion antes que los demas.
                if(pAux != null)
                {
                    // Conservamos la instruccion del proceso que libere mas rapido al recurso
                    // O bien del proceso que termine su ejecucion antes
                    // O bien del proceso que este primero en la lista.
                    // ** Los demas procesos se ponen en espera del recurso.
                    ArrayList <Instruccion> aux = (ArrayList <Instruccion>) instListasSolicita.clone();
                    instListasSolicita.clear();
                    
                    for(Instruccion inst : aux)
                        if(inst.getInstruccion().equals(pAux.getInstActual()))
                        {
                            instListasSolicita.add(inst);
                            break;
                        }
                }
            }
            
            int k = 0;
            
            for(Instruccion actual : instListasSolicita)
            {
                instrListas[k++] = actual.getInstruccion();
                gIns.add(actual);
                actual.start();
            }
            
            int j = 0;
            
            for(Instruccion actual : instListasLibera)
            {
                // Cambiamos los estados de los procesos que estaban en espera
                // por algun recurso que a continuacion sera liberado.
                for(i = 0; i < procesos.length; i++)
                {
                    if(procesos[i].getEsperandoRecurso().equals(actual.getRecurso().getNombre()))
                    {
                        procesos[i].setEstado(0);
                        procesos[i].setEsperandoRecurso("");
                    }
                    
                    // Removemos una o varias de las instrucciones en pantalla (flechas)
                    if(j < gIns.size() && actual.getProceso().equals(gIns.get(j).getProceso())
                                       && actual.getRecurso().equals(gIns.get(j).getRecurso()))
                        gIns.remove(j--);
                    
                    j++;
                }
                
                instrListas[k++] = actual.getInstruccion();
                actual.start();
            }
            
            s.escribirInstrucciones(instrListas, "ejecutadas");
            s.agregarAlHistorial(instrListas);
        }
        
        //Despacha procesos de lista de baja prioridad
        else if(!Baja.isEmpty())
        {
            // Despachamos procesos secuencialmente
            Proceso actual = Baja.get(0);
            // Si el proceso esta listo para ser atendido
            if(actual.getEstado() == 0)
            {
                String instruccion = actual.getInstActual();
                String nombreRecurso = String.valueOf(instruccion.charAt(instruccion.length()-1));
                instr[0] = instruccion;
                instrListas[0] = instruccion;
                Instruccion x = new Instruccion(instruccion, actual, getRecurso((nombreRecurso)));
                
                if(x.getInstruccion().contains("solicita"))
                    gIns.add(x);
                
                else
                {
                    for(i = 0; i < gIns.size(); i++)
                    {
                        Instruccion y = gIns.get(i);
                        
                        if(x.getProceso().equals(y.getProceso()) && x.getRecurso().equals(y.getRecurso()))
                            gIns.remove(i--);
                    }
                }
                
                x.start();
            }
            
            s.escribirInstrucciones(instr, "total");
            s.escribirInstrucciones(instrListas, "ejecutadas");
            s.agregarAlHistorial(instrListas);
        }
        
        dibujar(g);

        for(i = 0; i < Alta.size(); i++)
        {
            Proceso actual = Alta.get(i);
            
            if(actual.getEstado() == 1)
            {
                 Alta.remove(Alta.indexOf(actual));
                 i--;
            }
        }
        
        for(i = 0; i < Baja.size(); i++)
        {
            Proceso actual = Baja.get(i);
            
            if(actual.getEstado() == 1)
            {
                Baja.remove(Baja.indexOf(actual));
                i--;
            }
        }
        
        for(i = 0; i < numProcesos; i++)
        {
            instr[i] = null;
            instrListas[i] = null;
        }
        
        espera(delay);
    }
    
    private void dibujar(Graphics g)
    {
        int i;
        
        for(i = 0; i < numRecursos; i++)
            recursos[i].dibujar((Graphics2D)g);
        
        for(i = 0; i < numProcesos; i++)
            procesos[i].dibujar((Graphics2D)g);
        
        for(Instruccion inst : gIns)
            if(inst.getProceso().getEstado() != 1)
                inst.paint((Graphics2D)g);
        
        s.actualizarPanel();
    }
    
    public void setGraphics(Graphics g) { this.g = g; }
    
    public void despiertaProcesos()
    { 
        for(int i = 0 ; i < procesos.length; i++)
            procesos[i].despertar();
    }
    
    public synchronized void espera(int delay)
    {
        try {
            wait(delay);
        } catch (InterruptedException ex) {}
    }
    
    public synchronized void continua() { notify(); }
       
    public int getNumProcesos() { return numProcesos; }
    public int getNumRecursos() { return numRecursos; }
    
    private Recurso getRecurso(String nombre)
    {
        for(int i = 0 ; i < recursos.length; i++)
            if(recursos[i].getNombre().equals(nombre))
                return recursos[i];
        
        return null;
    }
    
    private void actualizaInfo()
    {
        ArrayList <String> infoProcesos = new ArrayList<>();
        ArrayList <String[]> infoRecursos = new ArrayList<>();
        int i;
        
        for(i = 0 ; i < procesos.length; i++)
            infoProcesos.add(procesos[i].getInfo());
        
        for(i = 0 ; i < recursos.length; i++)
            infoRecursos.add(recursos[i].getInfo());
        
        s.actualizarEstadoProcesos(infoProcesos);
        s.actualizarEstadoRecursos(infoRecursos);
    }
    
    
    private void inicializar()
    {
        creaRecursosYProcesos();
        int numSolicProc[] = new int[numProcesos];
        int i;
        
        //Obtiene el numero de recursos necesarios
        //de cada proceso hasta su instruccion
        //'libera' mas proxima.
        for(i = 0; i < numProcesos; i++)
            numSolicProc[i] = procesos[i].getNumRecursosNecesarios();
        
        int max = numSolicProc[0], indiceProceso = 0;
        
        //Obtenemos el proceso que pide mas recursos
        for(i = 1; i < numProcesos; i++)
            if(numSolicProc[i] > max)
            {
                max = numSolicProc[i];
                indiceProceso = i;
            }
        
        // Agregamos el proceso a la lista de baja prioridad
        Baja.add(procesos[indiceProceso]);
        
        // Llenamos la lista de alta prioridad con los procesos restantes
        for(i = 0; i < numProcesos; i++)
        {
            if(i != indiceProceso)
                Alta.add(procesos[i]);
        }
        
        for(i = 0; i < numProcesos; i++)
            procesos[i].start();
    }
    
    private void creaRecursosYProcesos()
    {
        ArrayList <ArrayList <String> > instrucciones = new ArrayList <>();
        ArrayList <String> inst = new ArrayList<>();
        
        //Instrucciones de A
         inst.add("A solicita R");
         inst.add("A solicita T");
         inst.add("A solicita W");
         inst.add("A libera T");
         inst.add("A libera W");
         inst.add("A solicita U");
         inst.add("A libera R");
         inst.add("A libera U");
         
         instrucciones.add(inst);
         inst = new ArrayList<>();
         
        //Instrucciones de B               
         inst.add("B solicita W");
         inst.add("B solicita T");
         inst.add("B solicita R");
         inst.add("B solicita S");
         inst.add("B libera T");
         inst.add("B solicita U");
         inst.add("B libera S");
         inst.add("B libera U");  
         inst.add("B libera R"); 
         inst.add("B libera W"); 
         
        instrucciones.add(inst);
        inst = new ArrayList<>();
        
        //Instrucciones de C              
         inst.add("C solicita U");
         inst.add("C solicita T");
         inst.add("C solicita W");
         inst.add("C libera U");
         inst.add("C libera W");
         inst.add("C libera T");
         
        instrucciones.add(inst);
        inst = new ArrayList<>();
        
        //Instrucciones de D
         inst.add("D solicita S");
         inst.add("D solicita T");
         inst.add("D solicita R");
         inst.add("D libera R");
         inst.add("D solicita W");
         inst.add("D libera T");
         inst.add("D libera W");
         inst.add("D libera S");
        
        instrucciones.add(inst);
        
        int anchoImg = 64, posX = 30, separacion = 15, posY = 20, i;
        String[] nombresR = {"R", "S", "T", "U", "W"};
        String[] nombresP = {"A", "B", "C", "D"};
        Color[] colores = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE};
        Rectangle medidas = s.getDimensionesLienzo();
        
        for(i = 0; i < numProcesos; i++)
            procesos[i] = new Proceso(instrucciones.get(i), nombresP[i], (posX += anchoImg + separacion), medidas.height-posY, colores[i]);
        
        posX = 10;
        recursos[0] = new Recurso(nombresR[0], posX, posY, new Font("Arial", Font.BOLD, 12));
        
        for(i = 1; i < numRecursos; i++)
            recursos[i] = new Recurso(nombresR[i], (posX += anchoImg + separacion), posY, new Font("Arial", Font.BOLD, 12));
    }
}