design pattern:
MVC
factory methods
observer
I use MVC because MVC will separate functionality of different classes, so that it makes debug more convenient and make it flexible to improve functionality. 
I create Model, View, Controller classes to implement MVC pattern.

I also use factory method to prevent future change of file format, so that I makes read file class as abstract class.
FileReadSub extends FileRead as a factory method pattern

And I also use observer and observable, since MVC is used. So that View as JPanel will observer Model who do the calculation and operation, can update the changes made in Model.
Model as observable, View as observer.
 

In placeOrder method in Controller class , swing worker used, to prevent any delay it may cause while doing some I/O stuff, so that GUI can still keep responsive.