import cv2

# Open video capture using default webcam index (DroidCam)
cap = cv2.VideoCapture(0)

while True:
  # Capture frame-by-frame
  ret, frame = cap.read()

  # Display the resulting frame
  cv2.imshow('frame', frame)

  # Exit if 'q' key is pressed
  if cv2.waitKey(1) == ord('q'):
    break

# Release the capture and close all windows
cap.release()
cv2.destroyAllWindows()