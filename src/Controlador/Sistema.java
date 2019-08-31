package Controlador;

import Modelo.Despachador;
import Vista.FramePrincipal;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Sistema
{
    private final FramePrincipal app;
    private Despachador despachador;
    
    public Sistema(FramePrincipal app)
    {
        this.app = app;
        despachador = null;
    }
    
    public void iniciar(int delay, Graphics g)
    {
        despachador = new Despachador(delay, this, g);
        despachador.start();
    }
    
    public void actualizarEstadoProcesos(ArrayList<String> estados)
    {
        JTextArea[] txtAProcesos = app.getTxtAProcesos();
        int i = 0;
        
        for(String info : estados)
            txtAProcesos[i++].setText(info);
    }
    
    public void actualizarEstadoRecursos(ArrayList <String[]> estados)
    {
        JLabel[] lbsRecursos = app.getLbsRecursos();
        int i = 0;
        
        for(String[] info : estados)
        {
            lbsRecursos[i++].setText("Estado: "+info[0]);
            lbsRecursos[i++].setText("Solicitudes: "+info[1]);
        }
    }
    
    public void escribirInstrucciones(String[] instrucciones, String tipoInstrucciones)
    {
        JTable tabla;
        
        if(tipoInstrucciones.equals("ejecutadas"))
            tabla = app.getTablaInstEjec();
        
        else
            tabla = app.getTablaConjuntoInst();
        
        Object[][] modeloDir = new Object[instrucciones.length][1];
        Object[] columnas = {""};
        tabla.setModel(new DefaultTableModel(modeloDir, columnas));
        
        int i = 0;
        
        for(String inst : instrucciones)
            if(inst != null)
                tabla.setValueAt(inst, i++, 0);
    }
    
    public void agregarAlHistorial(String[] instrucciones)
    {
        for(int i = 0; i < instrucciones.length; i++)
            if(instrucciones[i] != null)
                app.addHistorial(instrucciones[i]);
        
        app.actualizarHistorial();
    }
    
    public void siguientePaso() { despachador.continua(); }
    
    public void restaurar()
    {
        app.getPasoSigBt().setEnabled(false);
        app.getSubmenuMBIniciar().setEnabled(true);
        app.getCargarEstadoBt().setEnabled(true);
        app.getInicioManualBt().setEnabled(true);
        app.getCargarEstadoBt().setEnabled(true);
    }
    
    public void limpiarLienzo() { app.limpiarLienzo(); }
    
    public void actualizarPanel() { app.refreshPanel(); }
    
    public void setGraphics(Graphics g) { despachador.setGraphics(g); }
    
    public Rectangle getDimensionesLienzo() { return app.getDimensionesLienzo(); }
}