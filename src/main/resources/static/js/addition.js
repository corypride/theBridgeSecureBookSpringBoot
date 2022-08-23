/**Addition 1 is a game for children ages 3 to 10
 * there should be levels and there will be a choice of start game mode - where the levels will start
 * at 0 and progress gradually, and Level mode - where the level will stay the came until it the 
 * command is given to manually change the level.
 * Level 0 addition with numbers 0 - 9
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



 let gmObj ={
     category: "Addition",
     gameMode: "levelSelect",
     gameType: "addition_1",
     levelEnd: 750,
     playerName: null,
     score: 0,
     scoreFunc:"",
     startingLevel: "0"
 }

 function addition_1 (gmObj) {
     let greeting = `Hello, ${gmObj.playerName} welcome to ${gmObj.category} ${gmObj.gameType}... Let's Play!`
     console.log(greeting);
     console.log(`Current level: ${gmObj.startingLevel} `);
     
     let questionsArr = [];
     for (let i = 25; i >0; i--) {
        let a = Math.floor(Math.random() * 10);
        let b = Math.floor(Math.random() * 10);

        questionsArr.push(`${a} + ${b} = ?`);
        window.prompt(`${a} + ${b} = ?`);

     }
 }

 


 addition_1(gmObj);