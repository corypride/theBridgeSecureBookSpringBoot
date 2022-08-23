/Todo: refine at least two levels of this game/

/**Addition 1 is a game for children ages 3 to 10
 * there should be levels and there will be a choice of start game mode - where the levels will start
 * at 0 and progress gradually, and Level mode - where the level will stay the same until it the 
 * command is given to manually change the level.
 * Level 0-1 addition with numbers 0 - 9
 * Level 1 addition with numbers 1 - 10
 * Level 2 addition with numbers 10 - 19
 * Level 3 addition with numbers 20 - 29
 * Level 4 addition with numbers 30 - 39
 * Level 5 addition with numbers 0 - 40 **Special Note commending player of good work and ask if 
 * they would like to take a crack at addition 2
 * Addition 2
 * Level 0 addition with numbers 0 - 99
 * Level 1 addition with numbers 100 + 0-20
 * Level 2 addition with numbers 100 + 30-50
 * Level 3 addition with numbers 100 + 40-60
 * Level 5 addition with numbers 100 + 60-99
 * **Special Note commending player of good work and ask if 
 * they would like to take a crack at addition 3
 * Addition 3
 * Level 0 addition with numbers 0 -100 + 0-99
 * Level 1 addition with numbers 100 -199 + 100 -199
 * Level 2 addition with numbers 100 -199 + 100 -199
 * Level 3 addition with numbers 100 -199 + 100 -199 + 0-99
 * Level 4 addition with numbers 0 -199 + 0 -199 + 0-199
 * Bonus level addition with numbers 0-500 + 0 - 1000
 */ 

 let openingMsg = "Would you like to play Math Whiz?";
 let continueGameMsg0 = "Do you want to continue to the next level?"
 let continueGameMsg1 = `Level Select:\nChoose a level between 0 and 5`;
 let newGameMsg = "Do you want to start a new game?"
 let goodByeMsg = "";
 let gameSelect = ["addition1", "addition2", "addition3"];
 let endGameMsg = "You have successfully conqured math whiz!"
 let willPlay = false;

 function setPlayButtonVisible(){
    document.getElementById("playButton").style.visibility = "visible";
 }

 function gameChooser () {
     //creates and returns a gameObject 

     /**MathGames is an array of game objects, It holds the addition games now, 
      * and will eventually hold the multiplication,division and subtraction game objects */

      /TODO: write the scoring function and refactor the code to use it, add other games/
     let MathGames = [{
        category: "Addition",
        types: ["addition_1", "addition_2", "addition_3"],
        levels: [0, 1, 2, 3, 4, 5, "Bonus"],
        defaultLevel: 0,
        levelMsg: continueGameMsg1, //for normal mode levelmsg = continueMsg1 for levelSelect mode levelMsg = continueGameMsg0
        scoringCap: 0,
        scoringFunction: function (numCorrect, isBonus){

        }
        
     }];
     return MathGames;
 }

 
 function gameSettings( newGame = true, currentMode = null, currentLevel = 0, levelScore = 0, gameScore = 0 ){
    // creates and returns a game and level object
    let modeOptions = ["normal", "levelSelect"];
    let modeMsg = `
    Mode Select
    Choose One:
    0 - Normal or 1 - Level Select `;

    let getPlayerName = window.prompt(`Enter your Name.`);

    if (getPlayerName === null){
        setPlayButtonVisible();
        return;
    }
    let modeSelect = currentMode;
    let modeObj ={};


    if (getPlayerName === '' ) {
        getPlayerName = "Player"
    }

        while (!(modeSelect === 0 || modeSelect === 1 )){
            modeSelect = window.prompt(modeMsg); 

            //to stop program if user selects cancel           
            if(modeSelect === null) {
                setPlayButtonVisible();
                return;
            }
            modeSelect = Number(modeSelect);
        }

         modeObj = {
            playerName: getPlayerName, //use DOM to grab this from the login 
            category: null, 
            gameType: null,
            gameMode: modeOptions[modeSelect],
            newGame: newGame,
            startingLevel: null,
            levelEnd: null,
            levelScore: levelScore,
            gameScore: gameScore +  levelScore,
            scoreFunc: null,
            numRounds: 0
        };

        /TODO: create addition_2 and addition_3 games and subtraction, multiplication, and division games/
        //write the code that uses the gameChooser object instead of the hard coding below
        let gameInfo = gameChooser()[0]; //gameChooser returns an array of game objects 0 is addition game
        modeObj.category = gameInfo.category;
        modeObj.gameType = gameInfo.types[0]; //these will be populated by choosing the game and type eventually and will not need an index
        modeObj.levelEnd = gameInfo.scoringCap; /TODO: use the scoring cap to determine how many questions to ask before advancing levels/
        modeObj.scoreFunc = gameInfo.scoringFunction;

        let msg = gameInfo.levelMsg;
        let choice;

        //modeSelect 1 = levelSelect mode. User should be able to choose level and at the end of
        //a level asked if they would like to choose a different level, it the answer is no, then 
        //they will stay on the same level until they cancel the game
        if (modeSelect === 1) {
             choice = window.prompt(msg);
             //to stop program if user selects cancel
             if (choice === null){
                setPlayButtonVisible();  
                return;
             }

            choice = Number(choice);
            //prompt the user to select the level //put a checker here to validate input
            while( !(choice >= 0 && choice < 6)) {
                choice = window.prompt(continueGameMsg1);
                //to stop program if user selects cancel
                if (choice === null){
                    setPlayButtonVisible();
                    return;
                }
                choice = Number(choice);
            }
            
            modeObj.startingLevel = gameInfo.levels[choice];
             /**modeSelect 0 = normal mode.The levels will start at 0 and
             progress gradually */
        } else if (modeSelect === 0) {
            modeObj.startingLevel = gameInfo.defaultLevel;
        } else {
            //user inputs anything invalid or presses cancel
            alert("Invalid input!");
            setPlayButtonVisible();
            return;
        }

         
         return modeObj;
}


/**Addition 1 is a game for children ages 3 to 10
 * there should be levels and there will be a choice of start game mode - where the levels will start
 * at 0 and progress gradually, and Level mode - where the level will stay the came until it the 
 * command is given to manually change the level.
 * Level 0-1 addition with numbers 0 - 9
 * Level 1 addition with numbers 1 - 10
 * Level 2 addition with numbers 10 - 19
 * Level 3 addition with numbers 20 - 29
 * Level 4 addition with numbers 30 - 39
 * Level 5 addition with numbers 0 - 40 **Special Note commending player of good work and ask if 
 * they would like to take a crack at addition 2
    **/

function addition_1 (gmObj) {
    let gmDisplay = document.getElementById("gameTextDisplay");
    let greeting = `
    Hello, ${gmObj.playerName} welcome to ${gmObj.category} ${gmObj.gameType}... 
    Your current level is: ${gmObj.startingLevel} 
    Let's Play!`;
    let newGame = false;  
    let numCorrect = 0;
    window.alert(greeting);

    let equationP1;
    let equationP2;
    //these variables will be used to manipulate the ranges of the equations according to the current level

    if (gmObj.startingLevel === 0) {
        equationP1 = 0;
        equationP2 = 10;
    } else if (gmObj.startingLevel === 1) {
        equationP1 = 1;
        equationP2 = 11;
    } else if (gmObj.startingLevel === 2) {
        equationP1 = 10;
        equationP2 = 10;
    } else if (gmObj.startingLevel === 3) {
        equationP1 = 20;
        equationP2 = 10;
    } else if (gmObj.startingLevel === 4) {
        equationP1 = 30;
        equationP2 = 10;
     } else if (gmObj.startingLevel === 5) {
        equationP1 = 0;
        equationP2 = 41;
     }


    let questionsArr = [];
    for (let i = 5; i >0; i--) {
       
        let a = Math.floor(equationP1 + Math.random() * equationP2);
        let b = Math.floor(equationP1 + Math.random() * equationP2);
    

       let equation = a + b;

       questionsArr.push(`${a} + ${b} = ?`);
       let ans = window.prompt(`${a} + ${b} = ?`);
       if (ans === null) {
           window.alert("Canceling the game now!");
           gmObj.levelScore = numCorrect * 25;
           gmObj.gameScore += gmObj.levelScore;
           willPlay = false;
           setPlayButtonVisible();
           return gmObj;
    } 
       ans = Number(ans);

       //answer validation here, ensure that the user enters numbers
        while (isNaN(ans)) {
            ans = Number(window.prompt(`Answers must be valid numbers only! Please try again!
            ${a} + ${b} = ?`));
        }
       
       if (equation === ans) {
            numCorrect++;
            window.alert("correct!");
       }

    }
   
    gmObj.newGame = newGame; //after a level, set new game to false
    gmObj.levelScore = numCorrect * 25; //calculate score -- this should eventually be replace with the gmObj scoring function
    gmObj.gameScore += gmObj.levelScore;
    
    alert(`
    The number of correct answers 
    for level ${gmObj.startingLevel} is: ${numCorrect}. 
    Your level score is ${gmObj.levelScore} and your 
    total score for the game is ${gmObj.gameScore}! `);
    

    return gmObj;
}



function mathWhiz() {

    //play button disappears while playing the game
    document.getElementById("playButton").style.visibility = "hidden";

    //this is the text that appears in the nav pane at the top of the page, by default it displays a Welcome message
    let gmDisplay = document.getElementById("gameTextDisplay");
    
    //start game here
    willPlay = confirm(openingMsg);
    let endGmObj = null;
    
    if (!willPlay) {
        setPlayButtonVisible();
        return;
    }

    
    let settings = gameSettings(); //returns the initial gameSetting Object{}
    let curLevel = settings.startingLevel; //in gameMode = normal, the level should increase by one after each iteration of the while loop
   
    while (willPlay) {

        //get config from gameSetting to play game, return game results to the settings
        settings = addition_1(settings); //when looping the settings from the prev iteration are used for the next iteration of the game
    
        if (settings === null) {
            alert("Ending game now!");
            setPlayButtonVisible();
            return;
        }
        
        if (settings.startingLevel === 0) {
            settings.gameScore = settings.levelScore;
        }
        endGmObj = settings; //pass game results to the endgame object which is used to prompt user of end game results
        
        //end of level/gm stuff from this point to the end of the function
        //game will only be levels 0 - 5 for now - can add to this later
        //check gamemode here and if it is one, then the choice should be given to select a level
        
        if (willPlay){

            if (settings.gameMode === 'normal'){
                
                //this statement sets the max levels of the game to 5 - can be extened to use the scoringCap that is a part of the gameObj
                if ((settings.startingLevel + 1) === 6){
                    confirm(endGameMsg);
                    willPlay = false;
                } else {
                    //end of a level and check to see if player wants to continue
                    willPlay = confirm (continueGameMsg0);
                    settings.startingLevel = settings.startingLevel + 1;   
                }
            } else if (settings.gameMode === 'levelSelect') {

                //prompt the user to select a level
                let choice = window.prompt(continueGameMsg1);
                
                //if user presses cancel instead of choosing a level
                if (choice === null) {
                    setPlayButtonVisible();
                    willPlay = false;   
                }
                
                choice = Number(choice);
                
                //validate the input and if it does not meet the criteria prompt the user to select the level again
                while( !(choice >= 0 && choice < 6)) {
                    choice = window.prompt(continueGameMsg1);
                    //to stop program if user selects cancel
                    if (choice === null){
                        setPlayButtonVisible();
                        willPlay = false;
                        break;
                    }
                    choice = Number(choice);
                }
                //if it was determined that the user would like to continue accept the users level choice and return to the top of the loop
                if (willPlay){
                settings.startingLevel = choice;
                }
                
            }
        }
    }
    
    let endMsg = `Player: ${endGmObj.playerName}'s final score for game: ${endGmObj.category + " " + endGmObj.gameType} is: ${endGmObj.gameScore} points!`;
    setPlayButtonVisible();
    window.alert(endMsg);
    

}


 