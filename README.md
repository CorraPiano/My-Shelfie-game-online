# My Shelfie - Online Version

<div align="center" style="line-height: 1;">
   <img alt="Language" src="https://img.shields.io/badge/Language-Java-536af5?color=green" style="display: inline-block; vertical-align: middle;"/>
   <img alt="Platform" src="https://img.shields.io/badge/Platform-IntelliJ IDEA-536af5?color=blue" style="display: inline-block; vertical-align: middle;"/>
   <img alt="Main interface" src="https://img.shields.io/badge/Main interface-GUI-brightgreen" style="display: inline-block; vertical-align: middle;"/>
</div>

<div align="center" style="line-height: 1;">
   <img alt="Additional interface" src="https://img.shields.io/badge/Additional interface-TUI-536af5?color=red" style="display: inline-block; vertical-align: middle;"/>
   <img alt="Functionality" src="https://img.shields.io/badge/Functionality-Multigame-536af5?color=536af5" style="display: inline-block; vertical-align: middle;"/>
   <img alt="License" src="https://img.shields.io/badge/License-MIT-ffc107?color=ffc107" style="display: inline-block; vertical-align: middle;"/>
</div>

<p align="center">
    <br>
    <img src="src/main/resources/Images/assets/Boxnoshadow280x280.png"/>
<p>

## Project Overview
This project is a Java-based online adaptation of the board game My Shelfie, developed by [Cranio Creations].
It allows users to play the game in both a graphical user interface (GUI) and a text-based user interface (TUI), supporting multiplayer gameplay over networks using both socket and RMI connections.

This project was developed as part of the Software Engineering course at Politecnico di Milano

## Features
| Functionality                | Status                                                     |
|:-----------------------------|:----------------------------------------------------------:|
| Basic rules                  |   ![alt text](src/main/resources/Images/assets/tick.png)   |
| Complete rules               |   ![alt text](src/main/resources/Images/assets/tick.png)   |
| TUI                          |   ![alt text](src/main/resources/Images/assets/tick.png)   |
| GUI                          |   ![alt text](src/main/resources/Images/assets/tick.png)   |
| Socket Networking            |   ![alt text](src/main/resources/Images/assets/tick.png)   |
| RMI Networking               |   ![alt text](src/main/resources/Images/assets/tick.png)   |
| Multiple concurrent games    |   ![alt text](src/main/resources/Images/assets/tick.png)   |
| Persistency (Save/Load)      |   ![alt text](src/main/resources/Images/assets/cross.png)  |
| Disconnection Handling       |   ![alt text](src/main/resources/Images/assets/tick.png)   |
| In-game Chat                 |   ![alt text](src/main/resources/Images/assets/tick.png)   |

## Game frames 
Below are some images showcasing the graphical user interface of the game:

## Test cases
The project has been thoroughly tested, achieving high test coverage:

| Package     | Tested Class | Tested Methods | Coverage       |
|:------------|:-------------|:--------------:|:--------------:|
| Model       | 100% (31/31) | 100% (216/216)  | 97% (1154/1182) |

## Team members (AM26)
This project was developed as part of a software engineering course at Politecnico di Milano by the following contributors:
* [Marco Conti](https://github.com/C0NN)
* [Davide Corradina](https://github.com/CorraPiano)
* [Flavio De Lellis](https://github.com/flaviodelellis)
* [Nicola de March](https://github.com/nicola-de-march)

## Tools & Technologies Used
- [Java](https://www.java.com/): Core programming language.
- [IntelliJ IDEA](https://www.jetbrains.com/idea/): Main IDE for development.
- [Maven](https://maven.apache.org/): Dependency and build management.
- [JUnit](https://junit.org/): Testing framework.

## Running the Game
To launch the game, navigate to the directory where the JAR file is located (inside the `deliveries` package) and run:
```sh
java -jar AM26.jar
```

## License
This project is developed as an academic exercise and is not intended for commercial use. The original board game *My Shelfie* is a product of [_Cranio Creations_].

[_Cranio Creations_]: https://www.craniocreations.it/
