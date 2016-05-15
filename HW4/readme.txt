This is a UML design of a recommender Sys. In class diagram, I have 7 classes. RecommendSys as a main class and user interface. This class will create a recommender instance. First, system will create FileReader instance to read the source and set appropriate neighbor number. Then user will come in, let system set as current user. 

Before the two main requirements, I calculate the current user’s neighbor list. To do this, main class will create Recommender instance which is filled with a rating matrix parsed by FileReader instance. Then use this recommender to traverse all other users, using similarity function to get every similarity index of between other user and current one. Then we have a list of user with similarity, and It will sort them  to get k nearest neighbors.

After this, user can use this neighbor list to get prediction of certain unrated item. To do this, recommender calls getPrediction(Item), using prediction formula to get prediction. 

For recommender list of n unrated item, recommender will call getPreference() method, which loops over all unrated item, to get predictions, sort them, return the N highest prediction items, return them to main class.

For other tasks, sequence diagram shows how this system insert new entries such as new user, new item, new ratings.