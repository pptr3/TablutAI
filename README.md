# TablutAI
AI that plays Tablut better than you.

## Installation on Ubuntu/Debian 

From console, run these commands to install JDK 11 and ANT:

```
sudo apt update
sudo apt install openjdk-11-jdk -y
sudo apt install ant -y
```

Now, clone the project repository:

```
git clone https://github.com/Pptr95/TablutAI.git
```

## Run the Server

The easiest way is to utilize the ANT configuration script from console.
Go into the project folder (the folder with the `build.xml` file):
```
cd TablutAI/Tablut
```

Compile the project:

```
ant clean
ant compile
```

The compiled project is in  the `build` folder.
Run the `server` with:

```
ant server
```

To start the game, run the `white` player with this command:

```
ant failureState -Darg0=WHITE -Darg1=1 -Darg2=localhost
```
And then run the `black` player:
```
ant failureState -Darg0=BLACK -Darg1=1 -Darg2=localhost
```

At this point, a window with the game state should appear.

To be able to run other classes, change the `build.xml` file and re-compile everything.

## Strategy adopted
Both white and black players have been implemented using MinMax algorithm with AlphaBeta cuts.
The maximum depth set for the competition is `4`.
Both white and black players have a 100% rate of winning against a random player.
