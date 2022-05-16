## Monster VS Hero GUI (JavaFX)
Project Description: GUI-based editor for Monster vs Hero's simulation game

Authors: Abhay Chopra, Jonathan Hudson

Project Internals:
GUI application, using JavaFX, to get a view of World.java objects created
through program. Additionally, adds saving and reading from file functionality 
using FileChooser. Handles various events and incorporates event-driven design.

# Running the Project-from command line (Windows): 

1) Go to directory location with provided .jar file
2) Launch Command Prompt from directory
3) Use the following command: java --module-path "Add Path where JavaFx openSDK is located" --add-modules 
javafx.controls,javafx.fxml -jar CPSC233W22A3.jar

Example run (when running command prompt from location with.jar file):
java --module-path "C:\Program Files\Java\javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar CPSC233W22A3.jar


For usage (when program is launched): 
1) Create a world of desired size or load a world using world file (csv file)
2) Add entities to world (select hero or monster type) by providing required information
3) Capable of viewing details about world by providing row/column and pressing get details button
4) Able to delete entities in world by providing valid location within existing world
5) Can save world data to external file
6) Can acquire additional information about project using "About" in menu items
