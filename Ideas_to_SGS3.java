private void Serial_EventBasedReading(SerialPort activePort) {
    activePort.addDataListener(new SerialPortDataListener() {
      @Override
      public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
      }
  
       @Override
          public void serialEvent(SerialPortEvent event) {
              byte[] newData = event.getReceivedData();
              String dataString = new String(newData); // Convert byte array to String
  
              // Split the data string based on the format
              String[] dataParts = dataString.split("s"); // Use space as the separator
  
              if (dataParts.length == 8 && dataParts[0].length() == 11 && dataParts[7].equals("[DC4]")) {
                  // Validate the data format and length
                  String contactID = dataParts[0].substring(0, 1); // Extract contactID
                  String receiverNumber = dataParts[0].substring(2, 4); // Extract receiverNumber
                  String lineNumber = dataParts[0].substring(5, 6); // Extract lineNumber
                  String formatInUse = dataParts[0].substring(7, 9); // Extract formatInUse
                  String accessCode = dataParts[1]; // Extract accessCode
                  String classifier = dataParts[2]; // Extract classifier
                  String eventCode = dataParts[3]; // Extract eventCode
                  String eventCodes = dataParts[4] + dataParts[5]; // Combine eventCodes
                  String zoneCodeOrUserID = dataParts[6]; // Extract zoneCodeOrUserID
  
                  // Process and save the parsed data
                  System.out.println("Parsed Data:");
                  System.out.println("Contact ID: " + contactID);
                  System.out.println("Receiver Number: " + receiverNumber);
                  System.out.println("Line Number: " + lineNumber);
                  System.out.println("Format In Use: " + formatInUse);
                  System.out.println("Access Code: " + accessCode);
                  System.out.println("Classifier: " + classifier);
                  System.out.println("Event Code: " + eventCode);
                  System.out.println("Event Codes: " + eventCodes);
                  System.out.println("Zone Code/User ID: " + zoneCodeOrUserID);
  
                  // You can add code here to save the parsed data to a database or perform other actions.
              } else {
                  System.err.println("Invalid data format: " + dataString);
              }
          
  
        // Save data to MySQL database after receiving data
        saveReceivedDataToMySQL(dataBuffer);
  
        
      }
    });
  }
  
  private void saveReceivedDataToMySQL(String[] dataParts) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/serial_data", "AlbertoMN", "C4municipal*"); // Replace with your credentials
  
      // Prepare the INSERT statement
      String sql = "INSERT INTO parsed_data (contact_id, receiver_number, line_number, format_in_use, access_code, classifier, event_code, event_codes, zone_code_user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement pstmt = con.prepareStatement(sql);
  
      // Set the values to be inserted based on dataParts (assuming correct parsing)
      pstmt.setString(1, dataParts[0].substring(0, 11)); // Contact ID
      pstmt.setString(2, dataParts[0].substring(2, 4)); // Receiver Number
      pstmt.setString(3, dataParts[0].substring(5, 6)); // Line Number
      pstmt.setString(4, dataParts[0].substring(7, 9)); // Format In Use
      pstmt.setString(5, dataParts[1]); // Access Code
      pstmt.setString(6, dataParts[2]); // Classifier
      pstmt.setString(7, dataParts[3]); // Event Code
      pstmt.setString(8, dataParts[4] + dataParts[5]); // Event Codes (combine)
      pstmt.setString(9, dataParts[6]); // Zone Code/User ID
  
      int rowsAffected = pstmt.executeUpdate();
  
      if (rowsAffected == 1) {
        System.out.println("Data saved successfully to MySQL database.");
      } else {
        System.err.println("Error saving data to MySQL database.");
      }
  
      con.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }


// investigar esto
