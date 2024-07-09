package despacho;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.util.Locale;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.*;
import com.fazecast.jSerialComm.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat; 
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewJFrame extends javax.swing.JFrame {

    //variables
    SerialPort serialPort1;
    OutputStream outputStream1;
    String dataBuffer = "";
    String heartBeat = "";
    //String jdbcUrl = "jdbc:mysql://172.17.1.206:3306/newrec";
    //String jdbcUser = "AlbertoMN";
    //String jdbcPassword = "C4municipal*";

    public NewJFrame() {
        initComponents();
        initializeDefaults();
    }

    //valores de la interfaz
        private void initializeDefaults() {
        jComboBox_baudRate.setSelectedItem("9600");
        jComboBox_dataBits.setSelectedItem("8");
        jComboBox_stopBits.setSelectedItem("1");
        jComboBox_parityBits.setSelectedItem("NO_PARITY");
        jComboBox_endLine.setSelectedItem("None");

        jComboBox_comPort.setEnabled(true);
        jProgressBar_comStatus.setValue(0);
        jButton_open.setEnabled(true);
        jButton_close.setEnabled(false);
        jButton_send.setEnabled(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox_comPort = new javax.swing.JComboBox<>();
        jComboBox_baudRate = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox_dataBits = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox_stopBits = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox_parityBits = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jProgressBar_comStatus = new javax.swing.JProgressBar();
        jButton_open = new javax.swing.JButton();
        jButton_close = new javax.swing.JButton();
        jButton_cleanScreen = new javax.swing.JButton();
        jComboBox_endLine = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField_dataToSend = new javax.swing.JTextField();
        jButton_send = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_incomingData = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_registro = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "COM PORT SETTINGS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("COM PORT");

        jComboBox_comPort.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBox_comPortPopupMenuWillBecomeVisible(evt);
            }
        });
        jComboBox_comPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_comPortActionPerformed(evt);
            }
        });

        jComboBox_baudRate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "4800", "9600", "38400", "57600", "115200" }));
        jComboBox_baudRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_baudRateActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("BAUD RATE");

        jComboBox_dataBits.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "6", "7", "8" }));
        jComboBox_dataBits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_dataBitsActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("DATA BITS");

        jComboBox_stopBits.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "1.5", "2", " " }));
        jComboBox_stopBits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_stopBitsActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("STOP BITS");

        jComboBox_parityBits.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO_PARITY", "EVEN_PARITY", "ODD_PARITY", "MARK_PARITY", "SPACE_PARITY" }));
        jComboBox_parityBits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_parityBitsActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("PARITY BITS");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("COM STATUS");

        jButton_open.setText("OPEN");
        jButton_open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_openActionPerformed(evt);
            }
        });

        jButton_close.setText("CLOSE");
        jButton_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_closeActionPerformed(evt);
            }
        });

        jButton_cleanScreen.setText("CLEAN");
        jButton_cleanScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cleanScreenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_parityBits, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_stopBits, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_dataBits, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_baudRate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_comPort, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar_comStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_open, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_cleanScreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_close)
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox_comPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox_baudRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox_dataBits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox_stopBits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox_parityBits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar_comStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(16, 16, 16)
                .addComponent(jButton_cleanScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_close, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jButton_open, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        jComboBox_endLine.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "New Line(ln)", "Carriage Return (y)", "Both (\\r\\n)" }));
        jComboBox_endLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_endLineActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("END LINE");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jTextField_dataToSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_dataToSendActionPerformed(evt);
            }
        });

        jButton_send.setText("SEND");
        jButton_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sendActionPerformed(evt);
            }
        });

        jTextArea_incomingData.setEditable(false);
        jTextArea_incomingData.setColumns(20);
        jTextArea_incomingData.setRows(5);
        jScrollPane1.setViewportView(jTextArea_incomingData);

        jTextArea_registro.setColumns(20);
        jTextArea_registro.setRows(5);
        jScrollPane2.setViewportView(jTextArea_registro);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField_dataToSend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_send))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField_dataToSend)
                    .addComponent(jButton_send, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_endLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox_endLine, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 260, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void jComboBox_comPortPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {                                                             
        // obtenemos las conexiones probenientes de los puertos COM
        jComboBox_comPort.removeAllItems();
        SerialPort []portLists = SerialPort.getCommPorts();
        for(SerialPort port : portLists){
            jComboBox_comPort.addItem(port.getSystemPortName());
        }
    }                                                            

    private void jComboBox_comPortActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void jButton_openActionPerformed(java.awt.event.ActionEvent evt) {                                             

        //Conectamos con el puerto COM seleccionado presionando OPEN y si hay algun error arroja un mensaje de "ERROR"
        try {
            SerialPort[] portLists = SerialPort.getCommPorts();
            serialPort1 = portLists[jComboBox_comPort.getSelectedIndex()];
            configureSerialPort(serialPort1);

            if (serialPort1.isOpen()) {
                updateUIOnPortOpen(true);
                Serial_EventBasedReading(serialPort1);
                System.out.println("Se conecto al puerto COM");
            } else {
                JOptionPane.showMessageDialog(this, serialPort1.getDescriptivePortName() + " -- No se pudo conectar");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Seleccione un puerto COM", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }                                            

     private void configureSerialPort(SerialPort port) {
         //configuración del puerto serial para la conexión con la receptora
        port.setBaudRate(Integer.parseInt(jComboBox_baudRate.getSelectedItem().toString()));
        port.setNumDataBits(Integer.parseInt(jComboBox_dataBits.getSelectedItem().toString()));
        port.setNumStopBits(Integer.parseInt(jComboBox_stopBits.getSelectedItem().toString()));
        port.setParity(jComboBox_parityBits.getSelectedIndex());
        port.openPort();
    }
    private void updateUIOnPortOpen(boolean isOpen) {
        
        //si se conecto con el puerto arroja un mensaje de conoexión "EXITO"
        if (isOpen) {
            JOptionPane.showMessageDialog(this, serialPort1.getDescriptivePortName() + " -- Se conecto con exito");
            jComboBox_comPort.setEnabled(false);
            jProgressBar_comStatus.setValue(100);
            jButton_open.setEnabled(false);
            jButton_close.setEnabled(true);
            jButton_send.setEnabled(true);
        }
    }
    private void jButton_closeActionPerformed(java.awt.event.ActionEvent evt) {                                              
        
        //Boton para cerar la conexión con la receptora
        if (serialPort1 != null && serialPort1.isOpen()) {
            serialPort1.closePort();
            resetUIOnPortClose();
        }
    }                                             

        private void resetUIOnPortClose() {
            //funcion para el boton de CLOSE, desconectar de la receptora
        jComboBox_comPort.setEnabled(true);
        jProgressBar_comStatus.setValue(0);
        jButton_open.setEnabled(true);
        jButton_close.setEnabled(false);
        jButton_send.setEnabled(false);
        try {
            if (outputStream1 != null) {
                outputStream1.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            System.out.println("no fununcio");
        }
    }
    private void jButton_cleanScreenActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // limpiar la pantalla 
        //jTextArea_registro.setText(dataBuffer);
        dataBuffer = "";
        jTextArea_incomingData.setText(dataBuffer);
       
    }                                                   

    private void jTextField_dataToSendActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        // TODO add your handling code here:
    }                                                     

    private void jButton_sendActionPerformed(java.awt.event.ActionEvent evt) {                                             
        //Mandar Mensajes manualmente a la conexión COM a la cual esta conectado
        
        if (serialPort1.isOpen()) {
            try {
                outputStream1 = serialPort1.getOutputStream();
                String dataToSend = formatDataToSend();
                outputStream1.write(dataToSend.getBytes());
            } catch (IOException e) {
                JOptionPane.showConfirmDialog(this, e.getMessage());
            }
        }
    }                                            

    private String formatDataToSend() {
        //Formas para mandar datos
        String dataToSend = jTextField_dataToSend.getText();
        switch (jComboBox_endLine.getSelectedIndex()) {
            case 1: return dataToSend + "\n"; // new line
            case 2: return dataToSend + "\r"; // return
            case 3: return dataToSend + "\r\n"; // both
            default: return dataToSend; // nothing
        }
    }
    private void jComboBox_baudRateActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void jComboBox_dataBitsActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void jComboBox_stopBitsActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void jComboBox_parityBitsActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // TODO add your handling code here:
    }                                                    

    private void jComboBox_endLineActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
    }                                                 

    private void Serial_EventBasedReading(SerialPort activePort) {
    // datos recibidos en el puerto serial
    activePort.addDataListener(new SerialPortDataListener() {

        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        // convertimos los datos a byte y guardamos en un string
        @Override
        public void serialEvent(SerialPortEvent event) {
            try {
                byte[] newData = event.getReceivedData();
                String data = new String(newData);

                // Actualizar el JTextArea
                StringBuilder existingText = new StringBuilder(jTextArea_incomingData.getText());
                existingText.append(data).append(System.lineSeparator());  // Append with newline
                jTextArea_incomingData.setText(existingText.toString());
                System.out.println("se obtuvo un código que guardar ");

                // Detectar formato y guardar en archivo
                detectFormatAndSave(data);
                System.out.println("se obtuvo un código guardado");
                
                // Append data to registro with newline
                StringBuilder registroText = new StringBuilder(jTextArea_registro.getText());
                registroText.append(data).append(System.lineSeparator());
                jTextArea_registro.setText(registroText.toString());

                System.out.println("se obtuvo un codigo");
                // Limpiar buffer de entrada
                //dataBuffer = "";

            } catch (SQLException ex) {
                Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
}   
    
    //ejemplos   
    //heartbite: 101000           @    
    // contact id: 501001 189999R30500000
    // 4/2: 101001      9999 E  22
    private void detectFormatAndSave(String data) throws SQLException {
    // Get current date and time
    LocalDateTime now = LocalDateTime.now();
    String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    try (FileWriter writer = new FileWriter("c:/AlarmaDigital/received_data.txt", true)) {
        // Detect format and save data with timestamp
        if (data.length() == 23 && data.startsWith("1") && data.charAt(17) == '@') {
            System.out.println("Es un heartBeat");
            sendHeartBeat();
            System.out.println("se envio heartbeat");
            writer.write(timestamp + " - " + data + System.lineSeparator());
            System.out.println("se registro en txt");
        } else if (data.length() == 23 && data.startsWith("5") && data.charAt(6) == ' ') {
            System.out.println("El formato es CONTACT ID");
            extractDescriptionContactID(data);
            writer.write(timestamp + " - " + data + System.lineSeparator());
        } else if (data.length() == 23 && data.startsWith("1") && data.charAt(6) == ' ') {
            System.out.println("Es el formato 4/2");
            extractDescription42(data);
            writer.write(timestamp + " - " + data + System.lineSeparator());
            System.out.println("se registro en txt");
        } else {
            System.out.println("Es un formato no reconocido");
            //proceso para un formato no reconocdo.
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void sendHeartBeat() {
        //heartbite sirve para responder al llamado que hace la receptora al sistema
        try (OutputStream outputStream = serialPort1.getOutputStream()) {
            outputStream.write(heartBeat.getBytes());
            System.out.println("Mensaje enviado: " + heartBeat);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(this, e.getMessage());
        }
    }

   public String extractDescriptionContactID(String code) throws SQLException {
        String description = null;

        try {
          // Extract code from fields 8, 9, and 10
          if (code.length() < 11) {
            throw new IllegalArgumentException("Invalid code format. Code must be at least 11 characters long.");
          }
          String extractedCode = code.substring(14, 17);
                  System.out.println(extractedCode);


          // Establish connection to database (replace with your connection logic)
          Connection conn = getConnection();  // Replace with your connection logic

          System.out.println("conectado a la base de datos");
          if (conn != null) {
            try (PreparedStatement compareStmt = conn.prepareStatement("SELECT des FROM _codes WHERE cod = ?")) {
              compareStmt.setString(1, extractedCode);

              try (ResultSet rs = compareStmt.executeQuery()) {
                if (rs.next()) {
                  description = rs.getString("des");
                  // Save description to received_data table
                  saveToReceivedDataTable(conn, description);
                  System.out.println("código encontrado");
                } else {
                  System.out.println("El codigo no fue encontrado, datos no guardados.");
                }
              } catch (SQLException e) {
                System.err.println("Error: Error checking code in 'codes' table." + e.getMessage());
              } finally {
                compareStmt.close(); // Close compareStmt even if an exception occurs
              }
            } finally {
              // No need for closeConnection since we're not using try-with-resources here
            }
          } else {
            System.err.println("Fallo la conexión a la base de datos.");
          }
        } catch (IllegalArgumentException e) {
          System.err.println("Formato de codigo no valido." + e.getMessage());
        }

        return description;
      }

// Function to save data to received_data table remains the same
    public String extractDescription42(String code) throws SQLException {
        String description = null;

        try {
          
          if (code.length() < 11) {
            throw new IllegalArgumentException("Invalid code format. Code must be at least 11 characters long.");
          }
             String extractedCode = code.substring(20, 22);
             System.out.println(extractedCode);
          // Establish connection to database (replace with your connection logic)
          Connection conn = getConnection();  // Replace with your connection logic

          System.out.println("se conecto a la base de datos");
          if (conn != null) {
            try (PreparedStatement compareStmt = conn.prepareStatement("SELECT des FROM _codes WHERE cod = ?")) {
              compareStmt.setString(1, extractedCode);

              try (ResultSet rs = compareStmt.executeQuery()) {
                if (rs.next()) {
                  description = rs.getString("des");
                  // Save description to received_data table
                  saveToReceivedDataTable(conn, description);
                  System.out.println("códig encontrado");
                } else {
                  System.out.println("El codigo no fue encontrado, datos no guardados.");
                }
              } catch (SQLException e) {
                System.err.println("Error: Error checking code in 'codes' table." + e.getMessage());
              } finally {
                compareStmt.close(); // Close compareStmt even if an exception occurs
              }
            } finally {
              //closeConnection(conn);  
            }
          } else {
            System.err.println("Error: Connection to database failed.");
          }
        } catch (IllegalArgumentException e) {
          System.err.println("Error: Invalid code format." + e.getMessage());
        }

        return description;
    }

// Implement these methods to handle connection logic specific to your application
private Connection getConnection() {
  Connection conn = null;
  try {
    String dbFilePath = "jdbc:sqlite:C:\\AlarmaDigital\\_sam.db";
    Class.forName("org.sqlite.JDBC");
    conn = DriverManager.getConnection(dbFilePath);
    System.out.println("conectado a la base de datos");
  } catch (SQLException | ClassNotFoundException e) {
    System.err.println("Error connecting to database: " + e.getMessage());
  }
  return conn;
}



//hacer el insert en la base de datos, el codigo que se encontro
private void saveToReceivedDataTable(Connection conn, String data) throws SQLException {
    String insertSQL = "INSERT INTO _received_data (data, date) VALUES (?, ?)";
    try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
        insertStmt.setString(1, data);
        insertStmt.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        int rowsAffected = insertStmt.executeUpdate();
        if (rowsAffected == 1) {
            System.out.println("Datos guardados en received_data.");
        } else {
            System.err.println("Error saving data to received_data table.");
        }
    }
}

 
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton_cleanScreen;
    private javax.swing.JButton jButton_close;
    private javax.swing.JButton jButton_open;
    private javax.swing.JButton jButton_send;
    private javax.swing.JComboBox<String> jComboBox_baudRate;
    private javax.swing.JComboBox<String> jComboBox_comPort;
    private javax.swing.JComboBox<String> jComboBox_dataBits;
    private javax.swing.JComboBox<String> jComboBox_endLine;
    private javax.swing.JComboBox<String> jComboBox_parityBits;
    private javax.swing.JComboBox<String> jComboBox_stopBits;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar_comStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea_incomingData;
    private javax.swing.JTextArea jTextArea_registro;
    private javax.swing.JTextField jTextField_dataToSend;
    // End of variables declaration                   
}
