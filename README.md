# Onitama
A digital version of the table game *Onitama* created as a homework for a programming course at BME. 
It follows an MVC design pattern with an observable model and Swing views.

## Requirements
- Developed with OpenJDK 11
- Gson 2.8.6
- JUnit 5.4.2  and Mockito 2.20.0 for tests

## Running
Import the project, add any missing libs from the lib folder, and start the **Main.java** file.

### Usage
In the main menu íou can choose the types of the player (Human/AI).
When you are in game the turn consists of three steps:
- Choose one of the two cards that are in your hand
- Choose a figure you want to move
- Choose a destination field xou want the figure to step
In the first two steps, you can deselect the chosen item by clicking on it again.
The rules of the game can be found here: [wiki](https://en.wikipedia.org/wiki/Onitama)
### Ai
Currently, there are two types of AI in the game. The Random ai randomly chooses a legal move and executes it.
The *HAL 3000* and *HAL 4000* are using a MinMax algorithm with maximum foreseen moves
of 3 and 4.

## Testing
Test cases are not 100% coverage, but it checks the most important parts of the Model. T
he tests are using the JUnit 5 and Mockito library. 
All the necessary jars are in the lib folder.