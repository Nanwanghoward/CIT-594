I decide to participate in the class tournament to get EC.(YES)

This game implements three interfaces including Game, Player and Location.
For using this game, just start this game. It will print the instruction of game initialization. Just follow the instruction. As a human player, you should give the five location of ship you want to put on board. After that, computer automatically put the ship on board. And game starts. During each turn, you first give the two coordinates of location you want to fire. Then you will see the results on the screen where two boards printed out. Your board will print the location of your ships and the location where computer hit or sunk your ships. For computer board, you only see the location you hit or sunk its ships. And if someone’s ship is sunk, it will print out the whose ship and which ship is sunk. And if it is a hit, it will print out this is a hit. After some player’s ships all sunk, the game is over. It prints out the winner.

For the design of this game. I use two boards to track the game status in controller class which implements game interface. So that it will determine the player’s target is a hit or sunk or miss. For each player implementations, I use User class as a Humanplayer, Computer class as a computer player. Both of them use their own two boards independent from each other and the ones in Controller class. So that, theoretically, based on the setResult method, it is able to track this results on their own board.
For computer player strategy, I use fixed-length space to scan the whole board, with randomly start point. For space size I choose, I list the following option to choose.

space size	fire times	the number of possibilities left for placing ships
space: 5	number: 19	18*(1+2+3+3+4)+18*(1+2+2+3)=378
space: 4	number: 20	14+14+32*2*2+27*2*2=264
space: 3	number: 24	40*2+23*2*2=172
space: 2	number: 33	60

In this tables, we see 3-spaced fire pattern is most efficient.
The fire pattern shows below.
~ ~ ~ F ~ ~ ~ F ~ ~ 
~ ~ F ~ ~ ~ F ~ ~ ~ 
~ F ~ ~ ~ F ~ ~ ~ F 
F ~ ~ ~ F ~ ~ ~ F ~ 
~ ~ ~ F ~ ~ ~ F ~ ~ 
~ ~ F ~ ~ ~ F ~ ~ ~ 
~ F ~ ~ ~ F ~ ~ ~ F 
F ~ ~ ~ F ~ ~ ~ F ~ 
~ ~ ~ F ~ ~ ~ F ~ ~ 
~ ~ F ~ ~ ~ F ~ ~ ~ 
And if get a hit, I will searching the surrounding spaces. 
