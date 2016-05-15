for problem 1/a 1/b:
I just find the common pattern of all this files, split lines with ‘,’ or ‘::’ or ‘;’
and leave the FileRead class as abstract class and instantiated by its sub classes, which is mention in class as factory method.
The file I use is BX-Book-Ratings.csv, ratings.csv files.
 
for problem 1/c(i):
I made FileRead class as abstract class, let it instantiated by its sub classes.


For problem 2/a:
I made Similarity as interface, so that I created PearsonSimilarity and CosineSimilarity implementation classes to implement it. In which I implement the algorithm of these two.
And in recommender class, I just instantiate one of these two classes, let user to pick which one.

for problem 2/b:
In this problem, I created BaselinePrediction to implement Prediction interface, which is also implemented by WeightedPrediction.
And in preference class, it will instantiate to give prediction or recommendation.

for problem 2/c
I created an Experiment class which is used to generate BaseLine, pearson, cosine results together in certain cases, to compare with each other



