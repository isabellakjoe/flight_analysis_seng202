**Thank you for choosing Hop2it!**
------------------------------
------------------------------

*Authors:*
--------

Erika Savell, Isabella Joe, Sophie Walton-Smith, Callum Sinclair, Angus Schuler

*How to run the program:*
------------------------
------------------------
Open terminal and navigate to the directory the executable Jar is located.
Use the command 'java -jar seng202_2016_team8_phase3.jar‘'. 

*How to import the program:*
------------------------------------
------------------------------------
To import the program open your choice of Java IDE (We recommend
intelliJ IDEA) and import the folder containing the src and pom.xml files
as maven project.

*How to use the Program:*
-----------------------
-----------------------

*Loading Data:*
-------------
To use our program, simply load in data using the "Load" menu at the top left
corner of the program and choose the type of data file you wish to add.
Once added, the data will be displayed in either one of the three tables if
Add Route, Airline or Airport was chosen, otherwise the Fight Pane with be
displayed showing added flight data. If the file contains an object that already
exists in the table, the entire file will not be added.
*Filtering, Detailed Data, Editing and Deleting:*
---------
The data can be filtered using the Filter dropdown boxes at the top right of
the tables, and will filter data based on the selected parameter.
Once loaded, the objects in the data tables can be double clicked to display a
more detailed pane on that object.
Inside this detailed pane, you can choose to edit an existing Object by
clicking the Edit button, which will allow editing.
Edits can be saved using Save, or discard using Cancel. The Back button will
return you to the data tables. Additionally, if you hold CTRL and click
two Airport objects, then right click to display a ContextMenu, you can choose to get the distance between those two Airports. Using CTRL or SHIFT click then right clicking, objects be deleted using the Delete All Selected Route/Airport/Airline option in the ContextMenu.
*Searching:*
----------
On the left hand side of the program is a search pane, with three tabs for
Route, Airline and Airport searches.
Here you can input search parameters for the different tables, and an Advanced
Pane with more search options is available by clicking the Advanced button.
Searching begins once Search is clicked, and Reset will reset the tables and
the search inputs. If nothing satisfies the search parameters, no data will be
displayed in the table. Also, if spelling is incorrect, the desired data will
not be displayed until the spelling is corrected. Searching is case sensitive.
*Adding Individual Airlines/Airports/Routes:*
--------------------------------------------
Below the Search and Reset buttons is Add New Route/Airport/Airline,
(Depending on which tab you are in) which allows for adding a single object to
the table. Filling out the fields and clicking save will add the created object
to its corresponding table. Fields required to create the object will be commented if missing.
*Map View:*
---------------
Upon navigating to the map tab at the top of the screen, a map of the world is shown, with a quick display pane at the top right hand corner.  The quick display pane can be used to display all loaded airports and routes found in the table view, search for and display routes using a particular  equipment or departing a specific airport. Additionally, a loaded flight can be displayed on the map using the checkbox. All routes and all airports can be quickly removed using the clear buttons located at the bottom of the pane.
*Graph View:*
------------------
The graph view tab contains graphical information for the data. If no graphs have been requested, the graph view will prompt the user to analyse all loaded data. Upon doing so the different graphs can be shown by selecting them from the dropdown box. To show a graph of specific data, navigate to the data view, select all the data you wish to graph using ctrl+click. Then right-click and select the graphing option you wish to use.
*Create/Load Itineraries*
---------------------------------
Clicking on the 'Itineraries' tab will bring you to a welcome screen allowing the creation or loading of an itinerary. Whichever you choose, the process will be similar. If you create a new itinerary, you will be prompted to choose your first source airport. To aid in finding your desired starting point, the airport search bars on the left can be used. Once an airport has been selected an confirmed, routes originating from this airport will be displayed. Choose a route to add it to your itinerary. 
At any stage during airport and route selection, changes can be cancelled and you will be returned to a page allowing you to review your itinerary. 
This page is also displayed after each route is added, and it the screen you will be shown after loading an itinerary. It features a table displaying all routes in your itinerary, and a map for a visual depiction of the paths travelled. From here, more route can be added, routes can be deleted, and changes can be saved permanently. 

