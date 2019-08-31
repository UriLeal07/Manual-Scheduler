package Controlador;

import Vista.FramePrincipal;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main
{
    public static void main(String []args)
    {
        try
        {
            javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels = javax.swing.UIManager.getInstalledLookAndFeels();
            int idx;
            
            for(idx = 0; idx < installedLookAndFeels.length; idx++)
                if("Nimbus".equals(installedLookAndFeels[idx].getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
                    break;
                }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error al cargar Look and Feel", "Error de Tema", JOptionPane.ERROR_MESSAGE);
        }
        
        FramePrincipal app = new FramePrincipal();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}