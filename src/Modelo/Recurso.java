package Modelo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

public class Recurso
{
    private int estado, numSolicitudes;
    private final String nombre;
    private final Image img;
    private final Point origen;
    private final Font font;
    private final int separacion = 8;
    
    public Recurso(String nombre, int x, int y, Font font)
    {
        estado = 0; // 0 = Libre, 1 = Ocupado
        this.nombre = nombre;
        numSolicitudes = 0;
        img = new ImageIcon("src/ImgRecursos/Recurso_"+nombre+".png").getImage();
        origen = new Point(x, y);
        this.font = font;
    }

    public synchronized int getEstado() {
        return estado;
    }

    public synchronized void setEstado(int estado)
    {
        if(estado == 1)
            numSolicitudes++;
        
        this.estado = estado;
    }

    public synchronized String getNombre() {
        return nombre;
    }
    
    public String[] getInfo()
    {
        String info[] = new String[2];
        
        if(estado == 0)
            info[0] = "Libre";
        
        else
            info[0] = "Ocupado";
        
        info[1] = String.valueOf(numSolicitudes);
        
        return info;
    }
    
    public void dibujar(Graphics2D g2)
    {
        g2.setPaint(Color.BLACK);
        g2.drawImage(img, (int)origen.getX(), (int)origen.getY(), null);
        
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D r2D = font.getStringBounds("Recurso "+nombre, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (img.getWidth(null) / 2) - (rWidth / 2) - rX;
        int b = img.getHeight(null) - (rHeight / 2) - rY;        
        
        g2.setFont(font);
        g2.drawString("Recurso "+nombre, ((float)origen.getX() + a), ((float)origen.getY() + b + separacion));
    }
    
    public Point getOrigen() { return origen; }
    public int getAnchoImagen() { return img.getWidth(null); }
    public int getAltoImagen() { return img.getHeight(null); }
    public int getNumSolicitudes() { return numSolicitudes; }
    public int getSeparacion() { return separacion; }
}