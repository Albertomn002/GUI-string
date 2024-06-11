InputStream inputStream = port.getInputStream();
                    FileWriter fileWriter = new FileWriter("datos_seriales.txt", true);

                    try {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            fileWriter.write(new String(buffer, 0, bytesRead));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        fileWriter.close();
                        inputStream.close();
                        port.close();
                    }