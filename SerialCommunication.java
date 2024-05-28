import com.fazecast.jSerialComm.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialCommunication {

    private SerialPort serialPort1;
    private OutputStream outputStream1;
    private String dataBuffer = "";
    private JTextArea jTextArea_incomingData;
    private JTextArea monitor;

    // Other instance variables for date and time
    private int d, m, a, h;

    public SerialCommunication() {
        serialPort1 = SerialPort.getCommPort("COM3"); // Replace with your port
        serialPort1.setBaudRate(9600);
        if (serialPort1.openPort()) {
            System.out.println("Port opened successfully.");
            hearBeat_();
            Serial_Event_(serialPort1);
        } else {
            System.err.println("Failed to open port.");
        }
    }

    public static void main(String[] args) {
        new SerialCommunication();
    }

    private void Serial_EventBasedReading(SerialPort activePort) {
        activePort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] newData = event.getReceivedData();
                for (int i = 0; i < newData.length; i++) {
                    dataBuffer += (char) newData[i];
                }
                jTextArea_incomingData.setText(dataBuffer);

                // Save data to MySQL database after receiving data
                saveReceivedDataToMySQL(dataBuffer);

                // Clear dataBuffer for next data reception (optional)
                // dataBuffer = "";
            }
        });
    }

    private void saveReceivedDataToMySQL(String cadena) {
        try {
            // Define delimiter (replace with your chosen character)
            String delimiter = "";

            // Split data
            String[] parts = cadena.split(delimiter);

            // Check if data has at least two parts
            if (parts.length < 2) {
                System.err.println("Error: Input data does not have two parts.");
                return;
            }

            // Extract parts
            String formato = parts[0];
            String areas = parts[1];

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/prueba", "AlbertoMN", "C4municipal*");

            // Prepare statement with two placeholders
            String sql = "INSERT INTO registro (formato, areas, fecha_registro) VALUES (?, ?, NOW())";
            PreparedStatement pstmt = con.prepareStatement(sql);
            // Set data to be inserted
            pstmt.setString(1, formato);
            pstmt.setString(2, areas);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Data saved successfully to MySQL database.");
                dataBuffer = "";
                jTextArea_incomingData.setText(dataBuffer);
            } else {
                System.err.println("Error saving data to MySQL database.");
                dataBuffer = "";
                jTextArea_incomingData.setText(dataBuffer);
            }

            con.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }

    public static String hexI(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            int decimal = (int) aByte & 0xff;
            String hex = Integer.toHexString(decimal);
            if (hex.length() % 2 == 1) {
                hex = "0" + hex;
            }
            result.append(hex);
        }
        return result.toString();
    }

    private void hearBeat_() {
        if (serialPort1.isOpen()) {
            try {
                outputStream1 = serialPort1.getOutputStream();
                String dat = "";
                String ack = hex(dat.getBytes(StandardCharsets.UTF_8));
                byte[] byteArrray = ack.getBytes();
                String ackByte = new String(byteArrray);
                serialPort1.writeBytes(byteArrray, byteArrray.length);
                outputStream1.write(dat.getBytes());
                System.out.println("Envio ... " + ackByte);
            } catch (IOException ex) {
                Logger.getLogger(SerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void Serial_Event_(SerialPort activePort) {
        activePort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] newData = event.getReceivedData();
                for (int i = 0; i < newData.length; i++) {
                    dataBuffer += (char) newData[i];
                }
                String aro = "@";
                String dat = ""; // HEXADECIMAL 14 STRING
                String ack = hex(dat.getBytes(StandardCharsets.UTF_8));
                String err = "#0000";
                String tes = "";
                boolean re1 = dataBuffer.contains(aro);
                boolean re2 = dataBuffer.contains(err);
                boolean re3 = dataBuffer.contains(tes);

                if (re3) {
                    if (re1) {
                        System.out.println("Buffer: " + dataBuffer);
                    }
                    if (re2) {
                        System.out.println("Code Error: " + dataBuffer);
                    }
                    fec_();
                    String datos = d + "-" + m + "-" + a + " " + h;
                    write_datas_(dataBuffer + " " + datos + "\r\n");
                    monitor.setText(monitor.getText() + "\r\n" + dataBuffer + " " + datos);
                    dataBuffer = "";
                    // hearBeat_();
                }
            }
        });
    }

    private void fec_() {
        // Implement the method to update date and time variables (d, m, a, h)
        // This is a placeholder implementation
        d = 24; m = 5; a = 2024; h = 12;
    }

    private void write_datas_(String data) {
        // Implement the method to write data to a file or other output
        // This is a placeholder implementation
        System.out.println("Data to be written: " + data);
    }
}
