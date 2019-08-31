package Vista;

import Controlador.Sistema;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class FramePrincipal extends JFrame implements TreeExpansionListener
{
    private final Sistema s;
    private BufferedImage image;
    private Graphics g;
    private final ArrayList <File> dirImagenes;
    private final ArrayList <String> historial;
    private int index;
    private final File directorioImagenes;
    private final DefaultMutableTreeNode carpetaRaiz;
    private final DefaultTreeModel modelo;
    private int indexNodo;
    private String jTreeVar;
    private final int altoImg, anchoImg;
    private FrameAcerca frmAcerca;
    
    public FramePrincipal()
    {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Despachador de Procesos");
        
        URL iconURL = getClass().getResource("/Icons/planner.png");
        
        if(iconURL != null)
        {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        }
        
        // Configuracion de tablas
        tablaConjuntoInst.setCellSelectionEnabled(false);
        tablaConjuntoInst.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaConjuntoInst.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        tablaConjuntoInst.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tablaConjuntoInst.setRowHeight(20);
        tablaConjuntoInst.setGridColor(Color.BLACK);
        tablaConjuntoInst.setShowGrid(true);        
        tablaInstEjec.setCellSelectionEnabled(false);
        tablaInstEjec.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaInstEjec.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        tablaInstEjec.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tablaInstEjec.setRowHeight(20);
        tablaInstEjec.setGridColor(Color.BLACK);
        tablaInstEjec.setShowGrid(true);
        tablaHistorialInstr.setCellSelectionEnabled(false);
        tablaHistorialInstr.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaHistorialInstr.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        tablaHistorialInstr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tablaHistorialInstr.setRowHeight(20);
        tablaHistorialInstr.setGridColor(Color.BLACK);
        tablaHistorialInstr.setShowGrid(true);
        
        index = 1;
        directorioImagenes = new File("src/Pasos/");
        directorioImagenes.mkdirs();
        s = new Sistema(this);
        //arbolDiagramas.setEditable(false);
        carpetaRaiz = (DefaultMutableTreeNode) arbolDiagramas.getModel().getRoot();
        modelo = (DefaultTreeModel) arbolDiagramas.getModel();
        arbolDiagramas.addTreeExpansionListener(this);
        indexNodo = 0;
        jTreeVar = "";
        dirImagenes = new ArrayList<>();
        historial = new ArrayList <>();
        image = null;
        g = null;
        altoImg = panelDibujo.getHeight();
        anchoImg = panelDibujo.getWidth();
        frmAcerca = null;
    }
    
    private void crearArbol()
    {
        for(File f : dirImagenes.subList(indexNodo, dirImagenes.size()))
        {
             DefaultMutableTreeNode obj = new DefaultMutableTreeNode(f.getName());
             modelo.insertNodeInto(obj, carpetaRaiz, indexNodo++);
             actualizaNodo(obj, f); 
        }  
    }
    
    private boolean actualizaNodo(DefaultMutableTreeNode nodo, File f)
    {
        nodo.removeAllChildren();

        return actualizaNodo2(nodo,f,2);
    }
    
    private boolean actualizaNodo2(DefaultMutableTreeNode nodo, File f, int profundidad)
    {
        File[] files = f.listFiles(); // de el nodo que llega listamos todos sus archivos
        
        if(files!=null && profundidad>0) //permite detener la recursividad si ya llegó al limite
        {
            for(File file: files)  // recorre todos los archivos
            {
                DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(file);
                //vuelve a mandar en caso que sea directorio
                actualizaNodo2(nuevo, file,profundidad-1);
                nodo.add(nuevo); //crea el nodo
            }
        }
        
        return true;
    }
    
    @Override
    public void treeExpanded(TreeExpansionEvent event)
    {
        TreePath path = event.getPath(); // Se obtiene el path del nodo
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
        // verifica que sea nodo valido
        if(node == null || !(node.getUserObject() instanceof File))
            return;
        
        File f = (File) node.getUserObject();
        actualizaNodo(node, f);  //actualiza la estructura
        JTree tree = ( JTree) event.getSource();
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        model.nodeStructureChanged(node);
    }
    
    public void guardarDiagrama()
    {
        File file = new File("src/Pasos/"+(index++)+".png");
        
        try
        {
            ImageIO.write(image, "png", file); // Salvar la imagen en el fichero
            dirImagenes.add(file);
        }
        catch (IOException ex) {}
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInstrucciones = new javax.swing.JPanel();
        ConjuntoInstSP = new javax.swing.JScrollPane();
        tablaConjuntoInst = new javax.swing.JTable();
        InstruccionesEjecSP = new javax.swing.JScrollPane();
        tablaInstEjec = new javax.swing.JTable();
        panelPestanas = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        panelProcesos = new javax.swing.JPanel();
        procesoASP = new javax.swing.JScrollPane();
        procesoATxtA = new javax.swing.JTextArea();
        procesoBSP = new javax.swing.JScrollPane();
        procesoBTxtA = new javax.swing.JTextArea();
        procesoCSP = new javax.swing.JScrollPane();
        procesoCTxtA = new javax.swing.JTextArea();
        procesoDSP = new javax.swing.JScrollPane();
        procesoDTxtA = new javax.swing.JTextArea();
        panelRecursos = new javax.swing.JPanel();
        recursoRPane = new javax.swing.JPanel();
        lbImgRecR = new javax.swing.JLabel();
        recursoSPane = new javax.swing.JPanel();
        lbImgRecS = new javax.swing.JLabel();
        recursoTPane = new javax.swing.JPanel();
        lbImgRecT = new javax.swing.JLabel();
        recursoUPane = new javax.swing.JPanel();
        lbImgRecU = new javax.swing.JLabel();
        recursoWPane = new javax.swing.JPanel();
        lbImgRecW = new javax.swing.JLabel();
        infoRecursosPane = new javax.swing.JPanel();
        lbEstadoR = new javax.swing.JLabel();
        lbSolicitudesR = new javax.swing.JLabel();
        lbEstadoS = new javax.swing.JLabel();
        lbSolicitudesS = new javax.swing.JLabel();
        lbEstadoT = new javax.swing.JLabel();
        lbSolicitudesT = new javax.swing.JLabel();
        lbEstadoU = new javax.swing.JLabel();
        lbSolicitudesU = new javax.swing.JLabel();
        lbEstadoW = new javax.swing.JLabel();
        lbSolicitudesW = new javax.swing.JLabel();
        panelHistorial = new javax.swing.JPanel();
        historialInstSP = new javax.swing.JScrollPane();
        tablaHistorialInstr = new javax.swing.JTable();
        arbolDiagSP = new javax.swing.JScrollPane();
        arbolDiagramas = new javax.swing.JTree();
        panelBtCargarEstado = new javax.swing.JPanel();
        cargarEstadoBt = new javax.swing.JButton();
        panelDibujo = new javax.swing.JPanel();
        lbAreaDibujo = new javax.swing.JLabel();
        panelBotones = new javax.swing.JPanel();
        inicioManualBt = new javax.swing.JButton();
        pasoSigBt = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menuMB = new javax.swing.JMenu();
        submenuMBIniciar = new javax.swing.JMenu();
        pasoApasoMenuItem = new javax.swing.JMenuItem();
        lentoMenuItem = new javax.swing.JMenuItem();
        medioMenuItem = new javax.swing.JMenuItem();
        rapidoMenuItem = new javax.swing.JMenuItem();
        salirMenuItem = new javax.swing.JMenuItem();
        AyudaMB = new javax.swing.JMenu();
        acercaAyudaItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelInstrucciones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        ConjuntoInstSP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Conjunto de Instrucciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        ConjuntoInstSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tablaConjuntoInst.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ConjuntoInstSP.setViewportView(tablaConjuntoInst);

        InstruccionesEjecSP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instrucciones Ejecutadas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        InstruccionesEjecSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tablaInstEjec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        InstruccionesEjecSP.setViewportView(tablaInstEjec);

        javax.swing.GroupLayout panelInstruccionesLayout = new javax.swing.GroupLayout(panelInstrucciones);
        panelInstrucciones.setLayout(panelInstruccionesLayout);
        panelInstruccionesLayout.setHorizontalGroup(
            panelInstruccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInstruccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ConjuntoInstSP, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(InstruccionesEjecSP, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelInstruccionesLayout.setVerticalGroup(
            panelInstruccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInstruccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInstruccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InstruccionesEjecSP, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(ConjuntoInstSP, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelPestanas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setToolTipText("");

        panelProcesos.setToolTipText("Procesos del sistema");

        procesoASP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proceso A", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        procesoASP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        procesoATxtA.setEditable(false);
        procesoATxtA.setColumns(20);
        procesoATxtA.setRows(5);
        procesoASP.setViewportView(procesoATxtA);

        procesoBSP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proceso B", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        procesoBSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        procesoBTxtA.setEditable(false);
        procesoBTxtA.setColumns(20);
        procesoBTxtA.setRows(5);
        procesoBSP.setViewportView(procesoBTxtA);

        procesoCSP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proceso C", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        procesoCSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        procesoCTxtA.setEditable(false);
        procesoCTxtA.setColumns(20);
        procesoCTxtA.setRows(5);
        procesoCSP.setViewportView(procesoCTxtA);

        procesoDSP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proceso D", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        procesoDSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        procesoDTxtA.setEditable(false);
        procesoDTxtA.setColumns(20);
        procesoDTxtA.setRows(5);
        procesoDSP.setViewportView(procesoDTxtA);

        javax.swing.GroupLayout panelProcesosLayout = new javax.swing.GroupLayout(panelProcesos);
        panelProcesos.setLayout(panelProcesosLayout);
        panelProcesosLayout.setHorizontalGroup(
            panelProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(procesoASP, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
            .addComponent(procesoBSP)
            .addComponent(procesoCSP)
            .addComponent(procesoDSP)
        );
        panelProcesosLayout.setVerticalGroup(
            panelProcesosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProcesosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(procesoASP, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(procesoBSP, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(procesoCSP, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(procesoDSP, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Procesos", new javax.swing.ImageIcon(getClass().getResource("/Icons/process_18.png")), panelProcesos); // NOI18N

        panelRecursos.setToolTipText("Recursos activos");

        recursoRPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recurso R", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        recursoRPane.setPreferredSize(new java.awt.Dimension(90, 90));

        lbImgRecR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImgRecR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgRecursos/Recurso_R.png"))); // NOI18N

        javax.swing.GroupLayout recursoRPaneLayout = new javax.swing.GroupLayout(recursoRPane);
        recursoRPane.setLayout(recursoRPaneLayout);
        recursoRPaneLayout.setHorizontalGroup(
            recursoRPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );
        recursoRPaneLayout.setVerticalGroup(
            recursoRPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecR, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );

        recursoSPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recurso S", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        recursoSPane.setPreferredSize(new java.awt.Dimension(90, 90));

        lbImgRecS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImgRecS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgRecursos/Recurso_S.png"))); // NOI18N

        javax.swing.GroupLayout recursoSPaneLayout = new javax.swing.GroupLayout(recursoSPane);
        recursoSPane.setLayout(recursoSPaneLayout);
        recursoSPaneLayout.setHorizontalGroup(
            recursoSPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        recursoSPaneLayout.setVerticalGroup(
            recursoSPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecS, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );

        recursoTPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recurso T", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        recursoTPane.setPreferredSize(new java.awt.Dimension(90, 90));

        lbImgRecT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImgRecT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgRecursos/Recurso_T.png"))); // NOI18N

        javax.swing.GroupLayout recursoTPaneLayout = new javax.swing.GroupLayout(recursoTPane);
        recursoTPane.setLayout(recursoTPaneLayout);
        recursoTPaneLayout.setHorizontalGroup(
            recursoTPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecT, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );
        recursoTPaneLayout.setVerticalGroup(
            recursoTPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecT, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );

        recursoUPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recurso U", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        recursoUPane.setPreferredSize(new java.awt.Dimension(90, 90));

        lbImgRecU.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImgRecU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgRecursos/Recurso_U.png"))); // NOI18N

        javax.swing.GroupLayout recursoUPaneLayout = new javax.swing.GroupLayout(recursoUPane);
        recursoUPane.setLayout(recursoUPaneLayout);
        recursoUPaneLayout.setHorizontalGroup(
            recursoUPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecU, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );
        recursoUPaneLayout.setVerticalGroup(
            recursoUPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecU, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );

        recursoWPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recurso W", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        recursoWPane.setPreferredSize(new java.awt.Dimension(90, 90));

        lbImgRecW.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbImgRecW.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgRecursos/Recurso_W.png"))); // NOI18N

        javax.swing.GroupLayout recursoWPaneLayout = new javax.swing.GroupLayout(recursoWPane);
        recursoWPane.setLayout(recursoWPaneLayout);
        recursoWPaneLayout.setHorizontalGroup(
            recursoWPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecW, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );
        recursoWPaneLayout.setVerticalGroup(
            recursoWPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbImgRecW, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
        );

        lbEstadoR.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbEstadoR.setText("Estado:");

        lbSolicitudesR.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbSolicitudesR.setText("Solicitudes:");

        lbEstadoS.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbEstadoS.setText("Estado:");

        lbSolicitudesS.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbSolicitudesS.setText("Solicitudes:");

        lbEstadoT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbEstadoT.setText("Estado:");

        lbSolicitudesT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbSolicitudesT.setText("Solicitudes:");

        lbEstadoU.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbEstadoU.setText("Estado:");

        lbSolicitudesU.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbSolicitudesU.setText("Solicitudes:");

        lbEstadoW.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbEstadoW.setText("Estado:");

        lbSolicitudesW.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbSolicitudesW.setText("Solicitudes:");

        javax.swing.GroupLayout infoRecursosPaneLayout = new javax.swing.GroupLayout(infoRecursosPane);
        infoRecursosPane.setLayout(infoRecursosPaneLayout);
        infoRecursosPaneLayout.setHorizontalGroup(
            infoRecursosPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbEstadoR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbSolicitudesR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbEstadoS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbSolicitudesS, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
            .addComponent(lbEstadoT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbSolicitudesT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbEstadoU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbSolicitudesU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbEstadoW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbSolicitudesW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        infoRecursosPaneLayout.setVerticalGroup(
            infoRecursosPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoRecursosPaneLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbEstadoR, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbSolicitudesR, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(lbEstadoS, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbSolicitudesS, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(lbEstadoT, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbSolicitudesT, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(lbEstadoU, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbSolicitudesU, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(lbEstadoW, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbSolicitudesW, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelRecursosLayout = new javax.swing.GroupLayout(panelRecursos);
        panelRecursos.setLayout(panelRecursosLayout);
        panelRecursosLayout.setHorizontalGroup(
            panelRecursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecursosLayout.createSequentialGroup()
                .addGroup(panelRecursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRecursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(recursoRPane, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                        .addComponent(recursoSPane, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                    .addComponent(recursoTPane, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recursoUPane, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recursoWPane, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoRecursosPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRecursosLayout.setVerticalGroup(
            panelRecursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecursosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRecursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRecursosLayout.createSequentialGroup()
                        .addComponent(recursoRPane, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recursoSPane, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recursoTPane, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recursoUPane, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recursoWPane, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(infoRecursosPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabbedPane.addTab("Recursos", new javax.swing.ImageIcon(getClass().getResource("/Icons/recursos_18.png")), panelRecursos); // NOI18N

        panelHistorial.setToolTipText("Historial de pasos");

        historialInstSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tablaHistorialInstr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Instruccion", "#"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaHistorialInstr.getTableHeader().setResizingAllowed(false);
        tablaHistorialInstr.getTableHeader().setReorderingAllowed(false);
        historialInstSP.setViewportView(tablaHistorialInstr);
        if (tablaHistorialInstr.getColumnModel().getColumnCount() > 0) {
            tablaHistorialInstr.getColumnModel().getColumn(0).setPreferredWidth(20);
            tablaHistorialInstr.getColumnModel().getColumn(1).setPreferredWidth(5);
        }

        arbolDiagSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        arbolDiagramas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pasos Realizados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Pasos");
        arbolDiagramas.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        arbolDiagramas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                arbolDiagramasMouseClicked(evt);
            }
        });
        arbolDiagSP.setViewportView(arbolDiagramas);

        cargarEstadoBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/update_36.png"))); // NOI18N
        cargarEstadoBt.setText("Cargar Estado");
        cargarEstadoBt.setEnabled(false);
        cargarEstadoBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarEstadoBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBtCargarEstadoLayout = new javax.swing.GroupLayout(panelBtCargarEstado);
        panelBtCargarEstado.setLayout(panelBtCargarEstadoLayout);
        panelBtCargarEstadoLayout.setHorizontalGroup(
            panelBtCargarEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBtCargarEstadoLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(cargarEstadoBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(49, 49, 49))
        );
        panelBtCargarEstadoLayout.setVerticalGroup(
            panelBtCargarEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBtCargarEstadoLayout.createSequentialGroup()
                .addComponent(cargarEstadoBt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelHistorialLayout = new javax.swing.GroupLayout(panelHistorial);
        panelHistorial.setLayout(panelHistorialLayout);
        panelHistorialLayout.setHorizontalGroup(
            panelHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(historialInstSP, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(arbolDiagSP, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
            .addComponent(panelBtCargarEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelHistorialLayout.setVerticalGroup(
            panelHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHistorialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(historialInstSP, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(arbolDiagSP, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBtCargarEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbedPane.addTab("Historial", new javax.swing.ImageIcon(getClass().getResource("/Icons/history_18.png")), panelHistorial); // NOI18N

        javax.swing.GroupLayout panelPestanasLayout = new javax.swing.GroupLayout(panelPestanas);
        panelPestanas.setLayout(panelPestanasLayout);
        panelPestanasLayout.setHorizontalGroup(
            panelPestanasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelPestanasLayout.setVerticalGroup(
            panelPestanasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        panelDibujo.setBackground(new java.awt.Color(255, 255, 255));
        panelDibujo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbAreaDibujo.setBackground(new java.awt.Color(255, 255, 255));
        lbAreaDibujo.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        javax.swing.GroupLayout panelDibujoLayout = new javax.swing.GroupLayout(panelDibujo);
        panelDibujo.setLayout(panelDibujoLayout);
        panelDibujoLayout.setHorizontalGroup(
            panelDibujoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbAreaDibujo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelDibujoLayout.setVerticalGroup(
            panelDibujoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbAreaDibujo, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        inicioManualBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/list_36.png"))); // NOI18N
        inicioManualBt.setText("Iniciar Paso a Paso");
        inicioManualBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inicioManualBtActionPerformed(evt);
            }
        });

        pasoSigBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/next_36.png"))); // NOI18N
        pasoSigBt.setText("Paso Siguiente");
        pasoSigBt.setEnabled(false);
        pasoSigBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasoSigBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(inicioManualBt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(pasoSigBt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inicioManualBt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pasoSigBt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        menuMB.setText("Menu");

        submenuMBIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/play_18.png"))); // NOI18N
        submenuMBIniciar.setText("Iniciar...");

        pasoApasoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/list_18.png"))); // NOI18N
        pasoApasoMenuItem.setText("Paso a paso");
        pasoApasoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasoApasoMenuItemActionPerformed(evt);
            }
        });
        submenuMBIniciar.add(pasoApasoMenuItem);

        lentoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/slow_18.png"))); // NOI18N
        lentoMenuItem.setText("Lento");
        lentoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lentoMenuItemActionPerformed(evt);
            }
        });
        submenuMBIniciar.add(lentoMenuItem);

        medioMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/medium_18.png"))); // NOI18N
        medioMenuItem.setText("Medio");
        medioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medioMenuItemActionPerformed(evt);
            }
        });
        submenuMBIniciar.add(medioMenuItem);

        rapidoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fast_18.png"))); // NOI18N
        rapidoMenuItem.setText("Rapido");
        rapidoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rapidoMenuItemActionPerformed(evt);
            }
        });
        submenuMBIniciar.add(rapidoMenuItem);

        menuMB.add(submenuMBIniciar);

        salirMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/exit_18.png"))); // NOI18N
        salirMenuItem.setText("Salir");
        salirMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirMenuItemActionPerformed(evt);
            }
        });
        menuMB.add(salirMenuItem);

        menuBar.add(menuMB);

        AyudaMB.setText("Ayuda");

        acercaAyudaItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/help_18.png"))); // NOI18N
        acercaAyudaItem.setText("Acerca de");
        acercaAyudaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercaAyudaItemActionPerformed(evt);
            }
        });
        AyudaMB.add(acercaAyudaItem);

        menuBar.add(AyudaMB);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelInstrucciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDibujo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPestanas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelPestanas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelDibujo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelInstrucciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_salirMenuItemActionPerformed

    private void lentoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lentoMenuItemActionPerformed
        limpiar();
        iniciarSimulacion(7000);
    }//GEN-LAST:event_lentoMenuItemActionPerformed

    private void pasoApasoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasoApasoMenuItemActionPerformed
        inicioManual();
    }//GEN-LAST:event_pasoApasoMenuItemActionPerformed

    private void medioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medioMenuItemActionPerformed
        limpiar();
        iniciarSimulacion(3000);
    }//GEN-LAST:event_medioMenuItemActionPerformed

    private void rapidoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rapidoMenuItemActionPerformed
        limpiar();
        iniciarSimulacion(1000);
    }//GEN-LAST:event_rapidoMenuItemActionPerformed

    private void cargarEstadoBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarEstadoBtActionPerformed
        try
        {
            lbAreaDibujo.setIcon(null);
            File archivoSeleccionado = dirImagenes.get(Integer.parseInt(jTreeVar.replace(".png", ""))-1);
            
            if(archivoSeleccionado.exists())
            {
                ImageIcon nuevoDiagrama = new ImageIcon(archivoSeleccionado.getPath());
                lbAreaDibujo.setIcon(nuevoDiagrama);
            }
            
        }catch(Exception ex) {}
    }//GEN-LAST:event_cargarEstadoBtActionPerformed

    private void arbolDiagramasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arbolDiagramasMouseClicked
        
        if(!arbolDiagramas.isSelectionEmpty() && !arbolDiagramas.getSelectionPath().getLastPathComponent().equals("Pasos"))
        {
            jTreeVar = arbolDiagramas.getSelectionPath().getLastPathComponent().toString();
        }
    }//GEN-LAST:event_arbolDiagramasMouseClicked

    private void pasoSigBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasoSigBtActionPerformed
        s.siguientePaso();
    }//GEN-LAST:event_pasoSigBtActionPerformed

    private void inicioManualBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inicioManualBtActionPerformed
        inicioManual();
    }//GEN-LAST:event_inicioManualBtActionPerformed

    private void acercaAyudaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercaAyudaItemActionPerformed
        frmAcerca = new FrameAcerca();
        frmAcerca.setVisible(true);
        frmAcerca.setResizable(false);
    }//GEN-LAST:event_acercaAyudaItemActionPerformed
    
    public void addHistorial(String s) { historial.add(s); }
    
    public void actualizarHistorial()
    {
        Object[][] modeloTabla = new Object[historial.size()][2];
        Object[] colum = {"Instruccion", "#"};
        tablaHistorialInstr.setModel(new DefaultTableModel(modeloTabla, colum));
        
        for(int i = 0; i < historial.size(); i++)
        {
            tablaHistorialInstr.setValueAt(historial.get(i), i, 0);
            tablaHistorialInstr.setValueAt(i+1, i, 1);
        }
    }
    
    private void inicioManual()
    {
        pasoSigBt.setEnabled(true);
        limpiar();
        iniciarSimulacion(0);
    }
    
    private void iniciarSimulacion(int velocidad) { s.iniciar(velocidad, g); }
    
    private void limpiar()
    {
        // Limpiar TextArea de procesos
        procesoATxtA.setText("");
        procesoBTxtA.setText("");
        procesoCTxtA.setText("");
        procesoDTxtA.setText("");
        
        // Limpiar tablas de instrucciones y tabla de historial
        Object[][] modeloDir = new Object[1][1];
        Object[][] modeloDir2 = new Object[2][1];
        Object[] columnas = {""}, colum = {"Instruccion", "#"};
        tablaInstEjec.setModel(new DefaultTableModel(modeloDir, columnas));
        tablaConjuntoInst.setModel(new DefaultTableModel(modeloDir, columnas));
        tablaHistorialInstr.setModel(new DefaultTableModel(modeloDir2, colum));
        
        
        // Limpiar labels de los recursos
        lbEstadoR.setText("Estado:");
        lbSolicitudesR.setText("Solicitudes:");
        lbEstadoS.setText("Estado:");
        lbSolicitudesS.setText("Solicitudes:");
        lbEstadoT.setText("Estado:");
        lbSolicitudesT.setText("Solicitudes:");
        lbEstadoU.setText("Estado:");
        lbSolicitudesU.setText("Solicitudes:");
        lbEstadoW.setText("Estado:");
        lbSolicitudesW.setText("Solicitudes:");
        
        // Limpiar listas y variables
        historial.clear();
        dirImagenes.clear();
        index = 1;
        indexNodo = 0;
        jTreeVar = "";
        image = new BufferedImage(anchoImg, altoImg, BufferedImage.TYPE_INT_ARGB);
        g = image.getGraphics();
        
        // Reiniciar estados de botones
        submenuMBIniciar.setEnabled(false);
        cargarEstadoBt.setEnabled(false);
        inicioManualBt.setEnabled(false);
    }
    
    public void limpiarLienzo() { lbAreaDibujo.repaint(); }
    
    public void refreshPanel()
    {
        lbAreaDibujo.setIcon(new ImageIcon(image.getScaledInstance(anchoImg, altoImg, Image.SCALE_DEFAULT)));
        guardarDiagrama();
        crearArbol();
        image = new BufferedImage(anchoImg, altoImg, BufferedImage.TYPE_INT_ARGB);
        g = image.getGraphics();
        s.setGraphics(g);
    }
   
    public JTextArea[] getTxtAProcesos()
    {
        JTextArea[] txtAreasProc = new JTextArea[4];
        
        txtAreasProc[0] = procesoATxtA;
        txtAreasProc[1] = procesoBTxtA;
        txtAreasProc[2] = procesoCTxtA;
        txtAreasProc[3] = procesoDTxtA;
        
        return txtAreasProc;
    }
    
    public JLabel[] getLbsRecursos()
    {
        JLabel[] labels = new JLabel[10];
        
        labels[0] = lbEstadoR;
        labels[1] = lbSolicitudesR;
        labels[2] = lbEstadoS;
        labels[3] = lbSolicitudesS;
        labels[4] = lbEstadoT;
        labels[5] = lbSolicitudesT;
        labels[6] = lbEstadoU;
        labels[7] = lbSolicitudesU;
        labels[8] = lbEstadoW;
        labels[9] = lbSolicitudesW;
        
        return labels;
    }
   

    public JTable getTablaConjuntoInst() {
        return tablaConjuntoInst;
    }

    public JTable getTablaHistorialInstr() {
        return tablaHistorialInstr;
    }

    public JTable getTablaInstEjec() {
        return tablaInstEjec;
    }
    
    public JMenuItem getSubmenuMBIniciar() {
        return submenuMBIniciar;
    }
    
    public JButton getPasoSigBt() {
        return pasoSigBt;
    }
    
    public JButton getCargarEstadoBt() {
        return cargarEstadoBt;
    }
    
    public JButton getInicioManualBt() {
        return inicioManualBt;
    }
    
    public Rectangle getDimensionesLienzo(){
        return lbAreaDibujo.getBounds();
    }    
    
    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu AyudaMB;
    private javax.swing.JScrollPane ConjuntoInstSP;
    private javax.swing.JScrollPane InstruccionesEjecSP;
    private javax.swing.JMenuItem acercaAyudaItem;
    private javax.swing.JScrollPane arbolDiagSP;
    private javax.swing.JTree arbolDiagramas;
    private javax.swing.JButton cargarEstadoBt;
    private javax.swing.JScrollPane historialInstSP;
    private javax.swing.JPanel infoRecursosPane;
    private javax.swing.JButton inicioManualBt;
    private javax.swing.JLabel lbAreaDibujo;
    private javax.swing.JLabel lbEstadoR;
    private javax.swing.JLabel lbEstadoS;
    private javax.swing.JLabel lbEstadoT;
    private javax.swing.JLabel lbEstadoU;
    private javax.swing.JLabel lbEstadoW;
    private javax.swing.JLabel lbImgRecR;
    private javax.swing.JLabel lbImgRecS;
    private javax.swing.JLabel lbImgRecT;
    private javax.swing.JLabel lbImgRecU;
    private javax.swing.JLabel lbImgRecW;
    private javax.swing.JLabel lbSolicitudesR;
    private javax.swing.JLabel lbSolicitudesS;
    private javax.swing.JLabel lbSolicitudesT;
    private javax.swing.JLabel lbSolicitudesU;
    private javax.swing.JLabel lbSolicitudesW;
    private javax.swing.JMenuItem lentoMenuItem;
    private javax.swing.JMenuItem medioMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuMB;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelBtCargarEstado;
    private javax.swing.JPanel panelDibujo;
    private javax.swing.JPanel panelHistorial;
    private javax.swing.JPanel panelInstrucciones;
    private javax.swing.JPanel panelPestanas;
    private javax.swing.JPanel panelProcesos;
    private javax.swing.JPanel panelRecursos;
    private javax.swing.JMenuItem pasoApasoMenuItem;
    private javax.swing.JButton pasoSigBt;
    private javax.swing.JScrollPane procesoASP;
    private javax.swing.JTextArea procesoATxtA;
    private javax.swing.JScrollPane procesoBSP;
    private javax.swing.JTextArea procesoBTxtA;
    private javax.swing.JScrollPane procesoCSP;
    private javax.swing.JTextArea procesoCTxtA;
    private javax.swing.JScrollPane procesoDSP;
    private javax.swing.JTextArea procesoDTxtA;
    private javax.swing.JMenuItem rapidoMenuItem;
    private javax.swing.JPanel recursoRPane;
    private javax.swing.JPanel recursoSPane;
    private javax.swing.JPanel recursoTPane;
    private javax.swing.JPanel recursoUPane;
    private javax.swing.JPanel recursoWPane;
    private javax.swing.JMenuItem salirMenuItem;
    private javax.swing.JMenu submenuMBIniciar;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tablaConjuntoInst;
    private javax.swing.JTable tablaHistorialInstr;
    private javax.swing.JTable tablaInstEjec;
    // End of variables declaration//GEN-END:variables
}