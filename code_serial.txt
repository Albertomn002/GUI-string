SerialPort serialPort1;
OutputStream outputStream1;


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
private void hearBeat_(){
        if(serialPort1.isOpen()){
            try {
                outputStream1 = serialPort1.getOutputStream();
                String dat = "";
                String ack = hex(dat.getBytes(StandardCharsets.UTF_8));
                byte[] byteArrray = ack.getBytes();
                String ackByte = new String(byteArrray);
                serialPort1.writeBytes(byteArrray, SOMEBITS);
                outputStream1.write(dat.getBytes());
                System.out.println("Envio ... "+ackByte);
            } catch (IOException ex) {
                Logger.getLogger(_main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
private void Serial_Event_(SerialPort activePort){
        activePort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            @SuppressWarnings("empty-statement")
            public void serialEvent(SerialPortEvent event) {
                byte[] newData = event.getReceivedData();
                String datos = "";
                
                for(int i=0; i<newData.length; i++){
                    dataBuffer += (char)newData[i];
                    //System.out.println(dataBuffer);
                }
                String aro = "@";
                String dat = ""; // HEXADECIMAL 14 STRING
                String ack = hex(dat.getBytes(StandardCharsets.UTF_8));
                String err = "#0000";
                
                String tes = "";
                boolean re1 = dataBuffer.contains(aro);
                boolean re2 = dataBuffer.contains(err);
                boolean re3 = dataBuffer.contains(tes);
                
                String co1 = dataBuffer.substring(dataBuffer.length()-1);
                
                char ultimo = dataBuffer.charAt(dataBuffer.length() - 2);
                
                //System.out.println("Ultimo: "+ultimo+" - "+dataBuffer.length());
                if(re3){
                    if(re1){
                        System.out.println("Buffer: "+dataBuffer);
                    }
                    if(re2){
                        System.out.println("Code Error: "+dataBuffer);
                    }
                    fec_();
                    datos = d+"-"+m+"-"+a+" "+h;
                    write_datas_(dataBuffer+" "+datos+"\r\n");
                    monitor.setText(monitor.getText()+"\r\n"+dataBuffer+" "+datos);
                    dataBuffer = "";
                    //hearBeat_();
                }                
            }
        });
    }