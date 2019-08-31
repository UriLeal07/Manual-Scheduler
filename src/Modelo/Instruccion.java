package Modelo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Instruccion extends Thread
{
    private final String instruccion;
    private final Proceso proceso;
    private final Recurso r;
    private Color color;
    
    public Instruccion(String instruccion, Proceso proceso, Recurso r)
    {
        this.instruccion = instruccion;
        this.proceso = proceso;
        this.r = r;
        color = Color.BLACK;
    }
    
    @Override
    public void run()
    {
        // Si la instruccion es un libera cambiamos el estado del recurso
        if(instruccion.contains("libera"))
            r.setEstado(0);
        
        else
            r.setEstado(1);
        
        proceso.ejecutarInst(r.getOrigen());
    }
    
    public void paint(Graphics2D g2)
    {
        Point  origen = proceso.getOrigen();
        Point  destino = r.getOrigen();
        int anchoImgRec = r.getAnchoImagen();
        int altoImgRec = r.getAltoImagen();
        Rectangle dimCirculo = proceso.getDimensionesCirculo();
        color = proceso.getColor();
        
        int xOrigen = origen.x - (dimCirculo.width/2);
        int yOrigen = origen.y - (dimCirculo.height/2);
        int xDestino = destino.x + (anchoImgRec/2);
        int yDestino = destino.y + altoImgRec+r.getSeparacion();

        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f));
        g2.setColor(color);
        g2.drawLine(xOrigen, yOrigen, xDestino, yDestino);
    }
    
    public String getInstruccion() {
        return instruccion;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public Recurso getRecurso() {
        return r;
    }
}