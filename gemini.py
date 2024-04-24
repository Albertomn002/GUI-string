import os
from dotenv import load_dotenv # type: ignore

import google.generativeai as genai # type: ignore

load_dotenv()

model = genai.GenerativeModel("gemini-pro")

GOOGLE_API_KEY ="AIzaSyB2IynbRx6-1JSiZaldlob2ljm6Ez69OME"

genai.configure(api_key=GOOGLE_API_KEY)


class GeminiAI:
    def __init__(self, model_name):
        self.model_name = model_name
        self.api_key = os.getenv("AIzaSyB2IynbRx6-1JSiZaldlob2ljm6Ez69OME")

        genai.configure(api_key=GOOGLE_API_KEY)

        self.mode = genai.GenerativeModel(self.model_name)
    
    def generate_response(self,input_text):
        """Generates a response using Gemini model"""
        response = self.mode.generate_content(input_text)
        return response