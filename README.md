# Connect-Four
My ninth project in JetBrains Academy

Description
Before this stage, two players can play only one game. We are going to change this. In this stage, implement the option to play multiple games.

After setting the board dimensions, ask the players if they would like to play a single or multiple games. In the latter case, keep and print the score. When a player wins a game, they get 2 points. If it's a draw, give 1 point to each player.

If the players have chosen the multiple game option, alternate the first move for each new game. However, each player retains the same disc symbol (see Example 3).

Objectives
After setting the board dimensions, players see the following:

Do you want to play single or multiple games?
For a single game, input 1 or press Enter
Input a number of games:
Only positive digits are valid as for input (except 0). If a player inputs anything else, print Invalid input and ask for another try (see Example 4).

If players input 1 or press Enter, start a single game and print the following message:

<1st player's name> VS <2nd player's name>
 X 
Single game
In this case, the gameplay remains the same (see Examples 1, 2).

If players input an integer that is bigger than 1, start the multiple game mode and print the following message:

<1st player's name> VS <2nd player's name>
<Rows> X <Columns>
Total <Number of games> games
Print the score after each finished game in the following format:

Score
<1st player's name>: 2 <2nd player's name>: 2
Before the start of a game, print the game number: Game #<Number of game> (see Example 3). Players take turns for the first move, but each player keeps the same disc symbol throughout all games.

At any point of the game, if players input end, your program should output Game over! and terminate the game (see Example 5).
