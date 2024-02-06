# TurnBasedGameShadowAbyss

The game has a square grid map and requires there to be a Hero (the player) and enemies of different categories. 
Each character (including the hero) has attack functions and can take damage. 
Each has properties such as life points, attack damage multiplier, and defense multiplier. 
There are also powerups that can only be collected by the hero.

The Hero can move on the grid one position at a time (but not diagonally). 
Every time the player moves, the cell he lands on must randomly generate one of the enemies or a powerup. 
If an enemy appears, a turn-based battle starts (ends when one of the two dies or if the player decides to flee). 
At each turn the player can decide whether to attack or use one of the powerups (effect only for the ongoing fight, at the end the player's properties are reset). 
The enemy's turns consist of attacking the player. 
If the player decides to flee in one of his turns then he will suffer damage that will take away a percentage of his current life.
The objective of the game is to reach the boss in a specific cell on the map diagonally opposite to the starting one.
