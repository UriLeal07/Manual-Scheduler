package Modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Proceso extends Thread
{
    private final ArrayList <String> instrucciones;
    private int estado; // 0=Listo  1=Despachado  2=Esperando
    private String esperandoRecurso; // "R" | "S" | "T" | "U" | "W" | "" (ningun recurso)
    private final int totalInst;
    private int contInst;
    private final String nombre;
    private final Ellipse2D circulo;
    private final Point origen;
    private final Font font;
    private final int diametro;
    private Color color;
    
    public Proceso(ArrayList <String> instrucciones, String nombre, int x, int y, Color color)
    {
        this.instrucciones = instrucciones;
        this.nombre = nombre;
        estado = 0; // Listo
        totalInst = instrucciones.size();
        contInst = 0;
        esperandoRecurso = "";
        font = new Font("Arial", Font.BOLD, 12);
        origen = new Point(x, y);
        diametro = 40;
        circulo = new Ellipse2D.Double(x - diametro, y - diametro, diametro, diametro);
        this.color = color;
    }
    
    @Override
    public void run()
    {
        while(estado != 1)
            espera();
        
        color = Color.GRAY;
    }
    
    public void dibujar(Graphics2D g2)
    {
        g2.setPaint(color);
        g2.fill(circulo);
        
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D r2D = font.getStringBounds(nombre, frc);
        Rectangle r = circulo.getBounds();
        int separacion = 10;
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());
        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height) - (rHeight / 2) - rY;

        g2.setPaint(Color.BLACK);
        g2.setFont(font);
        g2.drawString(nombre, (r.x + a), (r.y + b + separacion));
    }
    
    public synchronized void espera()
    {
        try {
            wait();
        }
        catch(InterruptedException e) {}
    }
    
    public synchronized void despertar(){
       notify();
    }
    
    public int getInstRestantes()
    {
        return totalInst-contInst;
    }
    
    public int getNumRecursosNecesarios()
    {
        int numRecursos = 0;
        
        for(int i=0; i<totalInst; i++)
        {
            if(instrucciones.get(i).contains("libera"))
                break;
            
            else
                numRecursos++;
        }
        
        return numRecursos;
    }

    public ArrayList<String> getInstrucciones() {
        return instrucciones;
    }
    
    public String getInstActual()
    {
        if(contInst < totalInst)
            return instrucciones.get(0);
        
        return null;
    }
    
    public void ejecutarInst(Point origen)
    {
        instrucciones.remove(0);
        contInst++;
       
        if(contInst == totalInst)
            estado = 1;
    }
    
    public int pasosLibera(String r)
    {
        String inst = "libera "+r;
        int i;
        
        for(i = 0; i < totalInst-contInst; i++)
            if(instrucciones.get(i).contains(inst))
            {
                return i;
            }
        
        return i;
    }
    
    public String getInfo()
    {
        String info = "Estado: ", instActual = getInstActual();
        int instRestantes = (totalInst-contInst-1);
        String sigIns;
        
        try
        {
            sigIns = instrucciones.get(instrucciones.indexOf(instActual)+1);
        }
        catch(IndexOutOfBoundsException e) { sigIns = instActual; }
        
        switch(estado)
        {
            case 0: info += "Listo\n";
            
            break;
            
            case 1: info += "Finalizado\n";
            
                return info;
            
            case 2: info += "Esperando\n";
        }
        
        info += "Esperando Recurso: "+esperandoRecurso+"\n";
        info += "# Instrucciones Restantes: "+instRestantes+"\n";
        
        if(instRestantes > 0)
        {
            info += "Instruccion Actual: "+instActual+"\n";
            info += "Siguiente Instruccion: "+sigIns+"\n";
            info += "Lista de Instrucciones:\n";
        
            for(int i = 1; i < totalInst-contInst; i++)
                info += instrucciones.get(i)+"\n";
        }
        
        return info;
    }
    
    public Rectangle getDimensionesCirculo() { return circulo.getBounds(); }
    public Point getOrigen() { return origen; }
    public Color getColor() { return color; } 

    public int getEstado() {
        return estado;
    }

    public int getContInst() {
        return contInst;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setEsperandoRecurso(String esperandoRecurso) {
        this.esperandoRecurso = esperandoRecurso;
    }

    public String getEsperandoRecurso() {
        return esperandoRecurso;
    }

    public synchronized String getNombre() {
        return nombre;
    }
}