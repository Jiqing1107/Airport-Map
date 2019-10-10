# Airport-Map
This is one of the projects of the Coursera course "Object Oriented Programming in Java". This project allows students to design the UI and interactions by themselves. Here is my own design:
The main functions of the map are:

1. Visualize the airports (represented by circles, the colors of which indicate the altitude of the airport) and the routines (represented by lines).

2. When a user clicks an airport marker on the map, the color of the marker of the airport will change to blue and the size will become biger. The markers of other airports will disappear unless the airports have connection with the clicked airport on the route. Most of the routines will disappear as well. Only the rountines of the clicked airport are visible. 

For me, the most fascinating part of this project is to understand the data, e.g., what properties does the data have. I got to know such information by reading the introduction of the dataset on website and using the debug function of the complier. Based on what I learnt, I implemented several methods to get properties of the data such as getName(), getCity(), getCountry(), getAltitude() and getCode(). The most challenging part, of course, is to implement the actions that gonna be taken when an airport marker is clicked, i.e., checkAirportsForClick(). 

![My Profile Picture](images/profile.png)
