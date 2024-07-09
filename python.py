import tkinter as tk
from tkinter import messagebox
from tkinter.scrolledtext import ScrolledText
import requests
from PIL import ImageTk, Image

def search_mtG_cards(flavor_text):
    url = f'https://api.scryfall.com/cards/search?q=flavor:"{flavor_text}"'
    response = requests.get(url)
    if response.status_code == 200:
        return response.json()
    else:
        return None

def display_image(image_url, results_text):
    try:
        response = requests.get(image_url, stream=True)
        if response.status_code == 200:
            image_data = Image.open(response.raw)  # Open image data directly
            image = ImageTk.PhotoImage(image_data)  # Convert to PhotoImage
            # You can add a label to display the image
            image_label = tk.Label(results_text.master, image=image)  # Parent of results_text
            image_label.pack()
            image_label.image = image  # Keep a reference to avoid garbage collection
        else:
            results_text.insert(tk.END, f"Error downloading image\n")
    except Exception as e:
        print(f"Error displaying image: {e}")
        results_text.insert(tk.END, f"Error displaying image\n")

def search_cards():
    flavor_text = flavor_text_entry.get()
    if not flavor_text:
        messagebox.showwarning("Input Error", "Please enter flavor text to search.")
        return
    
    results = search_mtG_cards(flavor_text)
    if results:
        display_results(results)
    else:
        messagebox.showerror("Search Error", "No results found or an error occurred.")

def display_results(results):
    results_text.delete('1.0', tk.END)
    if 'data' in results:
        for card in results['data']:
            card_name = card['name']
            card_info = f"Name: {card_name}\nFlavor Text: {card.get('flavor_text', 'N/A')}\n\n"
            results_text.insert(tk.END, card_info)

            # Check if image URL exists
            if 'image_uris' in card and 'normal' in card['image_uris']:
                image_url = card['image_uris']['normal']
                display_image(image_url, results_text)  # Pass results_text for label creation

    else:
        results_text.insert(tk.END, "No cards found.")

app = tk.Tk()
app.title("MTG Card Search by Flavor Text")

frame = tk.Frame(app)
frame.pack(pady=10)

flavor_text_label = tk.Label(frame, text="Enter Flavor Text:")
flavor_text_label.pack(padx=10, pady=5)

flavor_text_entry = tk.Entry(frame, width=50)
flavor_text_entry.pack(padx=10, pady=5)

search_button = tk.Button(frame, text="Search", command=search_cards)
search_button.pack(padx=10, pady=10)

results_text = ScrolledText(app, width=80, height=20)
results_text.pack(padx=10, pady=10)

app.mainloop()
