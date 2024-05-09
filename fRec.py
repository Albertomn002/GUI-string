import cv2
import face_recognition

def cargar_imagenes(ruta_imagenes):
  """
  Carga las imágenes del directorio especificado.

  Args:
    ruta_imagenes: C:\Users\SPT\Documents\GUI\cropped_faces

  Returns:
    Una lista de imágenes cargadas y sus nombres.
  """
  imagenes = []
  nombres = []

  for imagen in os.listdir(ruta_imagenes):
    if imagen.endswith(".jpg") or imagen.endswith(".png"):
      ruta_imagen = os.path.join(ruta_imagenes, imagen)
      imagen_cargada = cv2.imread(ruta_imagen)
      imagenes.append(imagen_cargada)
      nombres.append(imagen.split(".")[0])

  return imagenes, nombres

def detectar_rostros(imagen):
  """
  Detecta rostros en una imagen.

  Args:
    imagen: La imagen a procesar.

  Returns:
    Una lista de cuadros delimitadores de los rostros encontrados.
  """
  # Convertir la imagen a formato RGB
  imagen_rgb = cv2.cvtColor(imagen, cv2.COLOR_BGR2RGB)

  # Detectar rostros con el detector de rostros pre-entrenado
  rostros = face_recognition.face_locations(imagen_rgb)

  return rostros

def reconocer_rostros(imagen, rostros, nombres_conocidos, codificaciones_conocidas):
  """
  Reconoce rostros en una imagen.

  Args:
    imagen: La imagen a procesar.
    rostros: Una lista de cuadros delimitadores de los rostros encontrados.
    nombres_conocidos: Una lista de nombres de las personas conocidas.
    codificaciones_conocidas: Una lista de codificaciones de rostros de las personas conocidas.

  Returns:
    Una lista de nombres predichos para cada rostro.
  """
  rostros_en_imagen = []
  nombres_predichos = []

  # Convertir la imagen a formato RGB
  imagen_rgb = cv2.cvtColor(imagen, cv2.COLOR_BGR2RGB)

  # Codificar los rostros encontrados
  codificaciones_encontradas = face_recognition.face_encodings(imagen_rgb, rostros)

  # Recorrer cada rostro encontrado
  for codificacion_encontrada, rostro in zip(codificaciones_encontradas, rostros):
    # Comparar la codificación encontrada con las codificaciones conocidas
    distancias = face_recognition.face_distance(codificaciones_conocidas, codificacion_encontrada)
    minimo_distancia = min(distancias)
    indice_minimo = distancias.index(minimo_distancia)

    # Si la distancia mínima es menor que un umbral, se considera una coincidencia
    if minimo_distancia < 0.5:
      nombre_predicho = nombres_conocidos[indice_minimo]
      rostros_en_imagen.append(rostro)
      nombres_predichos.append(nombre_predicho)
    else:
      rostros_en_imagen.append(rostro)
      nombres_predichos.append("Desconocido")

  return rostros_en_imagen, nombres_predichos


