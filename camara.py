import cv2

# Replace URL with address provided by DroidCam app (usually around 4747)
url = "http://localhost:" + str(URL)

# Open video capture using the IP camera URL
cap = cv2.VideoCapture(url)

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
