This is the implementation of MS1.
I made 4 changes on MS1 diagram to make it easier and more reasonable.
1.remove rating class
2.create prediction interface and its implementation class
3.create similarity interface and its implementation class
4.create preference interface and its implementation class
5.create converter interface and its implementation class

The reason to do this is because:
for change #1:
There is no need to create a class for rating since we have ratingMatrix class, which is sufficient to handle all kinds of changes by ratingMatrix itself. And since rating only have one method, it will waste too much space to create a rating object.

for change #2:
In order to handle potential changes of prediction method, I made this prediction more abstract. So that I create a interface, and create a WeightedPrediction class to implement it, if we want to change prediction method, just use another class implements that.

for change #3:
In order to handle potential changes of similarity method, I made this similarity more abstract. So that I create a interface, and create a PearsonSimilarity class to implement it, if we want to change similarity method, just use another class implements that.

for change #4 and #5:
the reasons are similar.
