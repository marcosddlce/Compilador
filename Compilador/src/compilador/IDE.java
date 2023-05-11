/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package compilador;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;





/**
 *
 * @author mdavi
 */
public class IDE extends javax.swing.JFrame {
    NumeroLinea numerolinea;
    Directorio dir;
    /**
     * Creates new form IDE
     */
    public IDE() {
        initComponents();
        inicializar();
        colors();
    }

    //METODO PARA ENCONTRAR LAS ULTIIMAS CADENAS
    private int findLastNonWordChar(String text, int index){
        while(--index >= 0){
            // \\w = [A-Za-z0-9]
            if(String.valueOf(text.charAt(index)).matches("\\W")){
                break;
            }
        }
        return index;
    }
    
    //METODO PARA ENCONTRAR LAS PRIMERAS CADENAS
    private int findFirstNonWordChar(String text, int index){
        while(index < text.length()){
            if(String.valueOf(text.charAt(index)).matches("\\W")){
                break;
            }
            index++;
        }
        return index;
    }
    
    //METODO PARA PINTAR LAS PALABRAS RESERVADAS
    private void colors(){
        
        final StyleContext cont = StyleContext.getDefaultStyleContext();
        
        //COLORES
        final AttributeSet attred = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(231, 152, 132));
        final AttributeSet attblue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(62,109,156));
        final AttributeSet attyellow = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,217,102));
        final AttributeSet attblack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0,0,0));
        
        //STYLE
        DefaultStyledDocument doc = new DefaultStyledDocument(){
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException{
                super.insertString(offset,str,a);
                
                String text = getText(0,getLength());
                int before = findLastNonWordChar(text,offset);
                if(before < 0){
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;
                
                while(wordR <= after){
                    if(wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")){
                        if(text.substring(wordL, wordR).matches
                            ("(\\W)*(main|if|then|else|end|do|while|repeat|until|cin|cout|real|int|boolean)"))
                            setCharacterAttributes(wordL,wordR - wordL,attblue, false);
                        else if (text.substring(wordL, wordR).matches("(\\W)*(:|;|\\.|,|\\(|\\)|\\{|\\}|\\[|\\]|-|\\+|\\*|/|\\^|<|<=|>|>=|==|!=|=|\\+\\+|--).*"))
                            setCharacterAttributes(wordL,wordR - wordL,attyellow, false);
                        else if(text.substring(wordL, wordR).matches("(\\W)*(a|b|c|d)"))
                            setCharacterAttributes(wordL,wordR - wordL,attred, false);
                        else if (text.substring(wordL, wordR).matches("(\\W)*(\\d+).*"))
                            setCharacterAttributes(wordL,wordR - wordL,attred, false);
                        else
                            setCharacterAttributes(wordL,wordR - wordL,attblack, false);
                        wordL = wordR;
                    }
                    wordR ++;
                }
            }
            
            public void remove(int offs, int len) throws BadLocationException{
                super.remove(offs, len);
                
                String text = getText(0,getLength());
                int before = findLastNonWordChar(text,offs);
                if(before < 0){
                    before = 0;
                }
            }
        };
        
        JTextPane txt = new JTextPane(doc);
        String temp = jtpCode.getText();
        jtpCode.setStyledDocument(txt.getStyledDocument());
        jtpCode.setText(temp);
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGuardar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnAbrir = new javax.swing.JButton();
        btnCompilar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtpCode = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaCompile = new javax.swing.JTextArea();
        btnTokens = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/Iconos/Icon/icono-guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Guardad documento");
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/Iconos/pressed/icono-guardar-p.png"))); // NOI18N
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 90, 80));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/Iconos/Icon/icono-nuevo.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setToolTipText("Boton Nuevo");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/Iconos/pressed/icono-nuevo-p.png"))); // NOI18N
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 90, 80));

        btnAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/Iconos/Icon/icono-abrir.png"))); // NOI18N
        btnAbrir.setText("Abrir");
        btnAbrir.setToolTipText("Boton Abrir");
        btnAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbrir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/Iconos/pressed/icono-abrir-p.png"))); // NOI18N
        btnAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });
        getContentPane().add(btnAbrir, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 90, 80));

        btnCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/Iconos/Icon/icono-compilar.png"))); // NOI18N
        btnCompilar.setText("Compilar");
        btnCompilar.setToolTipText("Boton compilar");
        btnCompilar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompilar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/Iconos/pressed/icono-compilar-p.png"))); // NOI18N
        btnCompilar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCompilar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 90, 80));

        jtpCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtpCodeKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jtpCode);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 1060, 420));

        jtaCompile.setColumns(20);
        jtaCompile.setRows(5);
        jScrollPane2.setViewportView(jtaCompile);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 1060, 150));

        btnTokens.setIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/Iconos/Icon/icono-tokens.png"))); // NOI18N
        btnTokens.setText("Tokens");
        btnTokens.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTokens.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTokens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTokensActionPerformed(evt);
            }
        });
        getContentPane().add(btnTokens, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 90, 80));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
        dir.Guardar(this);
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jtpCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtpCodeKeyReleased
        
        int keyCode = evt.getKeyCode();
        if((keyCode >= 65 && keyCode <= 90) || (keyCode >= 48 && keyCode <= 57)
                || (keyCode >= 97 && keyCode <= 122) || (keyCode != 27 && !(keyCode >=37
                && keyCode <= 40) && !(keyCode >= 16
                && keyCode <= 18) && keyCode != 524
                && keyCode != 20)){
            if(!getTitle().contains("*")){
                setTitle(getTitle() + "*");
            }
        }

// TODO add your handling code here:
    }//GEN-LAST:event_jtpCodeKeyReleased

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
    
        jtaCompile.setText("");
        dir.Nuevo(this);
        clearAllComp();
        
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        
        dir.Abrir(this);
        clearAllComp();

    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void btnTokensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTokensActionPerformed
        // TODO add your handling code here:
        // Ruta del script de Python
        String pythonScriptPath = "C:\\Users\\mdavi\\OneDrive\\Documentos\\Nuevacarpeta\\Compilador\\main.py";

        // Texto del IDE que deseas analizar
        String ideText = jtpCode.getText();

        try {
            // Construir el comando para ejecutar el script de Python con el texto del IDE como argumento
            String command = "python " + pythonScriptPath;

            // Crear el proceso y ejecutar el comando
            Process process = Runtime.getRuntime().exec(command);

            // Obtener el flujo de entrada del proceso (para enviar el texto del IDE al script Python)
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

            // Enviar el texto del IDE al script Python
            writer.write(ideText);
            writer.newLine();
            writer.flush();
            writer.close();

            // Obtener el flujo de salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Leer la salida del proceso línea por línea
            String line;
            //StringBuilder sbbb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // Procesar la línea de salida (tokens, errores, etc.)
                System.out.println(line);
                //sbbb.append(line);
                //sbbb.append(System.lineSeparator());
            }
            //String fileContent = sbbb.toString();
            //jtaCompile.setText(fileContent);            
            
            // Obtener el flujo de error del proceso
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Leer los mensajes de error línea por línea
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                // Mostrar los mensajes de error en la consola de Java
                System.err.println(errorLine);
            }

            // Esperar a que el proceso termine y obtener el código de salida
            int exitCode = process.waitFor();
            System.out.println("Codigo de salida: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
    }
    }//GEN-LAST:event_btnTokensActionPerformed

    /**
     * @param args the command line arguments
     */
    
    private void inicializar(){
        
        dir = new Directorio();
        
        //* para guardar
        setTitle("#Compilador");
        String[] options = new String[]{"Guardar y Continuar","Descartar"};
        
        numerolinea = new NumeroLinea(jtpCode);
        
        jScrollPane1.setRowHeaderView(numerolinea);
        
    }
    
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IDE().setVisible(true);
            }
        });
    }
    
    public void clearAllComp(){
        jtaCompile.setText("");
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnTokens;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jtaCompile;
    public javax.swing.JTextPane jtpCode;
    // End of variables declaration//GEN-END:variables
}
