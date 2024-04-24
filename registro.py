import serial
import threading
import tkinter as tk

# Function to read data from the serial port in a separate thread
def read_serial_data():
    global data_buffer

    while True:
        try:
            data = ser.readline().decode('utf-8').strip()
            if data:
                data_buffer.append(data)
                window.event_generate("<<SerialData>>")
        except UnicodeDecodeError:
            print("Error decoding data (might be non-UTF-8)")
        except serial.SerialException as e:
            print("Error reading serial port:", e)
            break  # Exit the loop on error

# Function to update the text widget with received data
def update_text_widget(event):
    global data_buffer, text_widget

    # Access the data buffer safely within the GUI thread
    new_data = data_buffer.copy()
    data_buffer.clear()  # Clear the buffer after displaying

    # Update the text widget
    text_widget.insert(tk.END, '\n'.join(new_data))
    text_widget.see(tk.END)  # Scroll to the bottom to show new data

# Function to change the serial port
def change_port():
    global ser, port_name, baudrate_var

    selected_port = port_dropdown.get()
    selected_baudrate = baudrate_var.get()

    # Close existing connection if necessary
    if ser.isOpen():
        ser.close()

    try:
        ser = serial.Serial(selected_port, baudrate=selected_baudrate)
        print("Serial connection established on", selected_port)
    except serial.SerialException as e:
        print("Error connecting to serial port:", e)

    # Restart the reading thread (optional)
    # serial_thread = threading.Thread(target=read_serial_data)
    # serial_thread.start()

# Main GUI code
window = tk.Tk()
window.title("Serial Data Reader")

# Text widget to display received data
text_widget = tk.Text(window)
text_widget.pack(expand=True, fill=tk.BOTH)

# Global variable to store received data (accessed by both threads)
data_buffer = []

# Connect to the serial port (replace with your initial settings)
port_name = 'COM3'
baudrate = 9600

try:
    ser = serial.Serial(port_name, baudrate=baudrate)
    print("Serial connection established on", port_name)
except serial.SerialException as e:
    print("Error connecting to serial port:", e)
    exit()

# Serial port selection dropdown
port_options = [  # Replace with available ports on your system
    'COM1', 'COM2', 'COM3', 'COM4', '/dev/ttyUSB0', '/dev/ttyACM0'
]
port_label = tk.Label(window, text="Serial Port:")
port_label.pack()
port_dropdown = tk.StringVar(window)
port_dropdown.set(port_name)  # Set initial selection
port_menu = tk.OptionMenu(window, port_dropdown, *port_options)
port_menu.pack()

# Baud rate selection
baudrate_options = [9600, 115200, 57600]  # Common baud rates
baudrate_label = tk.Label(window, text="Baud Rate:")
baudrate_label.pack()
baudrate_var = tk.IntVar(window)
baudrate_var.set(baudrate)  # Set initial selection
baudrate_menu = tk.OptionMenu(window, baudrate_var, *baudrate_options)
baudrate_menu.pack()

# Change port button
change_port_button = tk.Button(window, text="Change Port", command=change_port)
change_port_button.pack()

# Start the serial data reading thread
serial_thread = threading.Thread(target=read_serial_data)
serial_thread.start()

# Bind the update function to the custom event
window.bind("<<SerialData>>", update_text_widget)

# Run the main GUI loop
window.mainloop()

# Close the serial port when finished (happens in the main thread)
ser.close()
print("Serial connection closed")
