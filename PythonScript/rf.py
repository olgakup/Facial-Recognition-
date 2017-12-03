#Libraries
import scipy
from sklearn.tree import DecisionTreeClassifier
import numpy.core.multiarray
import cv2
from sklearn.datasets  import fetch_olivetti_faces
import numpy

#Start Program:

#Load Dataset:
faces = fetch_olivetti_faces()
D = faces.data

y = faces.target



from sklearn.model_selection import train_test_split
D_train, D_test, y_train, y_test = train_test_split(
    D, y, random_state=21
)

#Start Random Forest:

rtree = cv2.ml.RTrees_create()
num_trees = 150
eps = 0.01
criteria = (cv2.TERM_CRITERIA_MAX_ITER + cv2.TERM_CRITERIA_EPS,
            num_trees, eps)
rtree.setTermCriteria(criteria)
rtree.setMaxCategories(len(numpy.unique(y)))
#Set minimum data points:
rtree.setMinSampleCount(5)
#Set maximum depth in the tree:
rtree.setMaxDepth(25)
rtree.train(D_train, cv2.ml.ROW_SAMPLE, y_train);
rtree.getMaxDepth()
_, y_hat = rtree.predict(D_test)
from sklearn.metrics import accuracy_score
print accuracy_score(y_test, y_hat)

