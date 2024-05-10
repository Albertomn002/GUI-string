import argparse
import pickle
from collections import Counter
from pathlib import Path

import face_recognition
from PIL import Image, ImageDraw

DEFAULT_ENCODINGS_PATH = Path("output/encodings.pkl")
BOUNDING_BOX_COLOR = "blue"
TEXT_COLOR = "white"


def load_encodings(encodings_path):
    """Loads encoded faces and labels from a pickle file."""
    try:
        with open(encodings_path, 'rb') as f:
            encoded_faces, known_names = pickle.load(f)
        return encoded_faces, known_names
    except FileNotFoundError:
        print(f"Encodings file not found: {encodings_path}")
        return None, None


def train(known_images_dir, model="hog"):
    """Trains a face recognition model using provided images and saves encodings."""
    encoded_faces = []
    known_names = []

    # Loop through known images
    for image_path in Path(known_images_dir).glob("*.jpg"):  # Adjust file extension if needed
        name = image_path.stem  # Extract name from filename

        # Load image and detect faces
        image = face_recognition.load_image_file(image_path)
        face_locations = face_recognition.face_locations(image)

        # Extract and encode face features for each detected face
        if len(face_locations) == 1:  # Ensure only one face per image for training
            face_encoding = face_recognition.face_encodings(image, face_locations)[0]
            encoded_faces.append(face_encoding)
            known_names.append(name)
        else:
            print(f"Image '{image_path.name}' may contain multiple faces or none. Skipping.")

    # Train the model (if desired, using a library like sklearn)
    # Example using sklearn (replace with your preferred model training):
    # from sklearn.neighbors import KNeighborsClassifier
    # clf = KNeighborsClassifier(n_neighbors=5)
    # clf.fit(encoded_faces, known_names)

    # Save encoded faces and labels
    with open(DEFAULT_ENCODINGS_PATH, 'wb') as f:
        pickle.dump((encoded_faces, known_names), f)
    print("Encoding complete!")


def validate(encodings_path, validation_images_dir):
    """Evaluates the model's performance on a validation dataset."""
    encoded_faces, known_names = load_encodings(encodings_path)
    if not encoded_faces or not known_names:
        print("Encodings not found. Train a model first.")
        return

    num_correct = 0
    total_faces = 0

    # Loop through validation images
    for image_path in Path(validation_images_dir).glob("*.jpg"):  # Adjust file extension if needed
        image = face_recognition.load_image_file(image_path)
        face_locations = face_recognition.face_locations(image)
        face_encodings = face_recognition.face_encodings(image, face_locations)

        for face_encoding, face_location in zip(face_encodings, face_locations):
            # Compare features to encoded faces and find closest match
            matches = face_recognition.compare_faces(encoded_faces, face_encoding)
            name = "Unknown"

            # If a match is found, get the name
            if True in matches:
                first_match_index = matches.index(True)
                name = known_names[first_match_index]

            # Check if the predicted name matches the actual name in the image filename
            # (adjust logic if your image filenames don't follow the same pattern)
            actual_name = image_path.stem
            if name == actual_name:
                num_correct += 1
            total_faces += 1

            # Draw bounding box and label
            top, right, bottom, left = face_location
            image_pil = Image.fromarray(image)
            draw = ImageDraw.Draw(image_pil)
            draw.rectangle(((left, top), (right, bottom)), outline=BOUNDING_BOX_COLOR, width=2)
            text_width, text_height = draw.textsize(name)
            draw.rectangle(((left, bottom - text_height
                                         - 10), (left + text_width, bottom)), fill=BOUNDING_BOX_COLOR, width=-1)
            draw.text((left + 6, bottom - text_height - 5), name, font=self.font, fill=TEXT_COLOR)
            image_pil.save(image_path)  # Overwrite the original image (optional)

    accuracy = num_correct / total_faces if total_faces else 0.0
    print(f"Accuracy: {accuracy:.2f}")


def test(encodings_path, unknown_image_path, model="hog"):
    """Tests the model on an image containing an unknown face."""
    encoded_faces, known_names = load_encodings(encodings_path)
    if not encoded_faces or not known_names:
        print("Encodings not found. Train a model first.")
        return

    # Load the unknown image
    unknown_image = face_recognition.load_image_file(unknown_image_path)

    # Detect faces
    face_locations = face_recognition.face_locations(unknown_image, model=model)
    face_encodings = face_recognition.face_encodings(unknown_image, face_locations)

    # Recognize faces
    for face_encoding, face_location in zip(face_encodings, face_locations):
        matches = face_recognition.compare_faces(encoded_faces, face_encoding)
        name = "Unknown"

        # If a match is found, get the name
        if True in matches:
            first_match_index = matches.index(True)
            name = known_names[first_match_index]

        # Draw bounding box and label (similar to validation)
        top, right, bottom, left = face_location
        image_pil = Image.fromarray(unknown_image)
        draw = ImageDraw.Draw(image_pil)
        draw.rectangle(((left, top), (right, bottom)), outline=BOUNDING_BOX_COLOR, width=2)
        text_width, text_height = draw.textsize(name)
        draw.rectangle(((left, bottom - text_height - 10), (left + text_width, bottom)), fill=BOUNDING_BOX_COLOR, width=-1)
        draw.text((left + 6, bottom - text_height - 5), name, font=self.font, fill=TEXT_COLOR)
        image_pil.show()  # Display the image with results

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Recognize faces in an image")
    parser.add_argument("--train", action="store_true", help="Train on input data")
    parser.add_argument(
        "--validate", action="store_true", help="Validate trained model"
    )
    parser.add_argument(
        "--test", action="store_true", help="Test the model with an unknown image"
    )
    parser.add_argument(
        "-m",
        action="store",
        default="hog",
        choices=["hog", "cnn"],
        help="Which model to use for training: hog (CPU), cnn (GPU)",
    )
    parser.add_argument(
        "-d", action="store", help="Path to a directory containing known images"
    )
    parser.add_argument(
        "-v", action="store", help="Path to a directory containing validation images"
    )
    parser.add_argument(
        "-f", action="store", help="Path to an image with an unknown face"
    )
    args = parser.parse_args()

    if args.train:
        if not args.d:
            print("Please specify a directory containing known images using the '-d' argument.")
        else:
            train(args.d, model=args.m)
    elif args.validate:
        if not args.e:  # Assuming '-e' is used for specifying encodings path
            print("Please specify the path to the encoded faces file using the '-e' argument.")
        elif not args.v:
            print("Please specify a directory containing validation images using the '-v' argument.")
        else:
            validate(args.e, args.v)
    elif args.test:
        if not args.e:  # Assuming '-e' is used for specifying encodings path
            print("Please specify the path to the encoded faces")
        elif not args.f:
            print("Please specify the path to an image containing an unknown face using the '-f' argument.")
        else:
            test(args.e, args.f, model=args.m)
    else:
        parser.print_help()
