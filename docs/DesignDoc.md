---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information
* Team name: Back of the Bus
* Team members
  * Elijah Parrish
  * Daria Chaplin
  * Alex Hurley
  * Lillian Kuhn
  * Paula Register

## Executive Summary

WebCheckers is an online application that will allow
multiple players to log in and play a game of checkers with one another. The game interface
will support drag and drop browser capabilities for making moves. Beyond this basic
set of features we plan to implement a system so that the players can spectate a game that is 
in progress as well as replay a game they recently played, so that they can further
refine their checker playing skills.


### Purpose
> _The purpose of this project is to provide the players the ablity
to log in and play one another online wherever they are._

### Glossary and Acronyms
> _Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| VO | Value Object |


## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._

### Definition of MVP
> _Provide a simple description of the Minimum Viable Product._

### MVP Features
> _Provide a list of top-level Epics and/or Stories of the MVP._

### Roadmap of Enhancements
> _Provide a list of top-level features in the order you plan to consider them._


## Application Domain

This model shows the general domain of the project

![The WebCheckers Domain Model](domain_model.png)

> The central entity of our application is the Checkers game, which is played on a board.
The board is defined by Squares, which are in turn defined by their color and location. 
The checkers game is played with the pieces and played by the player. The player makes moves
that can be defined by the type of piece that is being moved and the type of move
that the piece is making.   


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](state_diagram.png)


_
The flow of the web pages from the user's perspective is as follows: When the user opens the home page
they first see a simple welcome message and a button to sign in, they will also be presented
with the number of players who are signed in. When they click to sign in they will be redirected to the Signin
page where they can post their username. They will then be redirected to home. If they then click 
the name of another player then both players will be redirected to the game screen 
where they can play the game of checkers. Once a winner has been decided they will be redirected to the home
screen.Inside of the game there will be the option to sign out or resign, both of which will result in that player 
forfeiting the game to the other player, returning them back to the home page._


### UI Tier
> _ Provide a summary of the Server-side UI tier of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow.
_
It all starts at GetHomeRoute this is the first thing the user will see
as it displays the homepage.Before even signing in users are able to 
see the number of current players. From the homepage the user is given
the optionto signin which will invoke GetSignInRoute. GetSignInRoute displays 
the signin.ftl page which has a user input box where they can input their username.
Once the user inputs their username then PostSignInRoute is invoked. PostSignIn
then requests the user input and checks to see that it meets the conditions. If 
it passes all of the conditions the player is added to the playerLobby and the 
page is redirected to GetHomeRoute again. Here the user can see other players, 
and can click on their username to invoke GetRequestGameRoute, which checks if the
requested player is already in a game or not, if so then it redirects to homepage,
and returns an error message, otherwise a It calls to GetGameRoute with a hashmap
of all the necessary information, inside of GetGameRoute the gameboard is displayed
and it checks whether or not the player is in the game or has resigned. Inside of 
the game If the user chooses to resign, PostResignRoute is called, inside of 
Resign the player who initiates the resignation is set as the loser, and removed 
from the game, and redirected to GetHomeRoute and the opposing player is then set
as the winner of the game, removed from the game and redirected to GetHomeRoute as 
well. Inside of a game when a player clicks signout PostResignGameRoute is also called, 
but the player is also removed from playerLobby therefore effectivley deleting the 
instance of that player. If a player signs out from outside of a homepage then 
GetSignOutRoute is also called upon in which the player is simply removed from the
playerLobby and effectivley removed from the server, then redirected to 
GetHomeRoute._

[link to sequence diagram][1]
[link to second sequence diagram][2]
[link to UML Diagram and statechart][3]
[1]:https://www.lucidchart.com/invitations/accept/2b3504e3-8f91-4bd9-a148-9be0395c4971 "Title"
[2]:https://www.lucidchart.com/invitations/accept/1530ef9f-49d6-461c-b51e-3e344254318a "Title"
[3]:https://www.lucidchart.com/invitations/accept/b08abbb6-b75e-4da0-997a-94b82652cbb8 "Title"

> _At appropriate places as part of this narrative provide one or more
> static models (UML class structure or object diagrams) with some
> details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and
> sequence diagrams, as is relevant to a particular aspect of the design
> that you are describing.  For example, in WebCheckers you might create
> a sequence diagram of the `POST /validateMove` HTTP request processing
> or you might show a statechart diagram if the Game component uses a
> state machine to manage the game._

> _If a dynamic model, such as a statechart describes a feature that is
> not mostly in this tier and cuts across multiple tiers, you can
> consider placing the narrative description of that feature in a
> separate section for describing significant features. Place this after
> you describe the design of the three tiers._


### Application Tier
> _ Our application tier is made up of three different classes:
GameCenter, GameLobby, and PlayerLobby. GameLobby is where the game objects are 
stored and we can access the games, searching by player etc. The playerLobby 
is where the Player objects are stored we can use this to access and store
players. GameCenter is a unification of both PlayerLobby and GameLobby
so that you can access all the methods under both from just one Class._


### Model Tier
> _Our Model tier is the meat of this project. It includes ten 
 classes. Boardview is what actually displays and puts together the board
 it puts both the spaces and pieces into the gameboard effectivley making 
 the board which the player sees. Game requires two players to instantiate 
 Game holds all of the information pertaining to the game, it holds the two 
 players and their colors it is also where a loser and winner are declared.
 KingPiece changes a piece to the status of a king piece, it enables that
 piece to have more functionality over other pieces in the game. Message is 
 used for return types inside of the UI tier when sending Json information. It 
 requires a Type (error or info) and a string identifying what was happening
 Move controls the movement of the various pieces in the gameboard. It has a 
 start and end index being where the piece started and where it ends up after 
 the move will be made. The piece class make the piece object that is being 
 moved by the player. It can be identified with a color and type ie. RED,KING.
 The Player class makes a player object. The player object represents the player
 making the moves and controlling all of the actions. The player type stores 
 the number of wins, name of the player, total number of games and a boolean
 value representing whether or not the player is in a game or not. Position is 
 needed for the move class. It is the index of a specific place on the gameboard
 it stores the row and cell of a certain place. Row makes the row object 
 which is what the gameboard is made out of. The rows are made up of spaces.
 Space represents the smallest mesurment unit in the gameboard. It is a single 
 square in the gameboard Spaces store a piece and a color of the specific space._

### Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements. After completion of the Code metrics exercise, you
> will also discuss the resutling metric measurements.  Indicate the
> hot spots the metrics identified in your code base, and your
> suggested design improvements to address those hot spots._

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._
