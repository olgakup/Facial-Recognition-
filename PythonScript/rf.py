#Libraries
import scipy
from sklearn.tree import DecisionTreeClassifier
import numpy.core.multiarray
import cv2
from sklearn.datasets  import fetch_olivetti_faces
import numpy

#Start Program:
print "Starting Random Forest"
#Load Dataset:
print "Loading Dataset"
faces = fetch_olivetti_faces()
D = faces.data
y = faces.target

print "starting Training Data"
from sklearn.model_selection import train_test_split
D_train, D_test, y_train, y_test = train_test_split(
    D, y, random_state=21
)

#Start Random Forest:

rtree = cv2.ml.RTrees_create()
num_trees = 150
print "initializing number of trees %s." % num_trees
eps = 0.01
criteria = (cv2.TERM_CRITERIA_MAX_ITER + cv2.TERM_CRITERIA_EPS,
            num_trees, eps)
rtree.setTermCriteria(criteria)
rtree.setMaxCategories(len(numpy.unique(y)))
#Set minimum data points:
min_sample = 5
rtree.setMinSampleCount(min_sample)
print "Setting minimum number of sample =  %s." % min_sample
#Set maximum depth in the tree:
max_depth = 25
rtree.setMaxDepth(max_depth)
print "Settting maximum tree depth number %s." % max_depth
print "Building Forest."
rtree.train(D_train, cv2.ml.ROW_SAMPLE, y_train);
rtree.getMaxDepth()
_, y_hat = rtree.predict(D_test)
from sklearn.metrics import accuracy_score
print "Facial Recognition Algorithm Accuracy %s. percent" % (accuracy_score(y_test, y_hat)*100)

