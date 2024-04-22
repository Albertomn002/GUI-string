import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import fetch_lfw_people
from sklearn.decomposition import PCA

faces_data = fetch_lfw_people(min_faces_per_person=70, resize=0.4)


X = faces_data.data
n_samples, n_features = X.shape
n_components = 150
pca = PCA(n_components=n_components, svd_solver='randomized', whiten=True).fit(X)
eigenfaces = pca.components_.reshape(
(n_components, faces_data.images.shape[1], faces_data.images.shape[2]))
plt.figure(figsize=(10, 3))
for i in range(10):
    plt.subplot(2, 5, i + 1)
    plt.imshow(eigenfaces[i], cmap='gray')
    plt.title(f"Eigenface {i + 1}")
plt.show()


n_faces = 5
random_faces_indices = np.random.randint(0, n_samples, n_faces)
random_faces = X[random_faces_indices]
faces_pca = pca.transform(random_faces)


faces_reconstructed = pca.inverse_transform(faces_pca)
plt.figure(figsize=(10, 3))
for i in range(n_faces):
    plt.subplot(2, n_faces, i + 1)
    plt.imshow(random_faces[i].reshape(
        faces_data.images.shape[1], faces_data.images.shape[2]), cmap='gray')
    plt.title("Original")
 
    plt.subplot(2, n_faces, i + 1 + n_faces)
    plt.imshow(faces_reconstructed[i].reshape(
        faces_data.images.shape[1], faces_data.images.shape[2]), cmap='gray')
    plt.title("Reconstructed")
plt.show()
from sklearn.decomposition import NMF
nmf = NMF(n_components=n_components, tol=5e-3)
nmf.fit(X)  # original non- negative dataset
 
# Visualize
nmf_faces = nmf.components_.reshape(
    (n_components, faces_data.images.shape[1], faces_data.images.shape[2]))
 
# Plot the first 10 faces
plt.figure(figsize=(10, 3))
for i in range(10):
    plt.subplot(2, 5, i + 1)
    plt.imshow(nmf_faces[i], cmap='gray')
    plt.title(f"NMF face {i + 1}")
 
plt.show()
 
# Reconstruct faces
n_faces = 5
random_faces_indices = np.random.randint(0, n_samples, n_faces)
random_faces = X[random_faces_indices]
 
# Transform faces
faces_nmf = nmf.transform(random_faces)
 
# Reconstruct faces
faces_reconstructed = nmf.inverse_transform(faces_nmf)
 
# Visualize original and reconstructed faces
plt.figure(figsize=(10, 3))
for i in range(n_faces):
    plt.subplot(2, n_faces, i + 1)
    plt.imshow(random_faces[i].reshape(
        faces_data.images.shape[1], faces_data.images.shape[2]), cmap='gray')
    plt.title("Original")
 
    plt.subplot(2, n_faces, i + 1 + n_faces)
    plt.imshow(faces_reconstructed[i].reshape(
        faces_data.images.shape[1], faces_data.images.shape[2]), cmap='gray')
    plt.title("Reconstructed")
 
plt.show()