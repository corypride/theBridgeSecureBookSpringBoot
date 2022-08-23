    let dateStr=``; //get date and time from window load function

    function ranColorPicker () {
        //randomly selects three colors between the range of 0 and 255 and returns the array
        let arr = [0, 0, 0];
        for (let i = 0; i < arr.length; i++){
            arr[i] = Math.floor((Math.random() * 255));
        }
        return arr;
    }
    
    /*sideNav functionality--------*/
    function openNav() {
        document.getElementById("mySidenav").style.width = "300px";
    }

    /* Set the width of the side navigation to 0 */
    function closeNav() {
        document.getElementById("mySidenav").style.width = "0";
    }

    //___End of Stuff for side navigation functionality_____________________

    //funtionality for collapsing gradesbar on aStudentReport
    function collapseGradesBar(){

        let gradesBar = document.getElementById("gradesBar");
        if(gradesBar.style.visibility==="hidden"){
            gradesBar.style.visibility = "visible";
        } else {
            gradesBar.style.visibility = "hidden";
        }
    }

    // functionality for the transitions in the bridge page ________________
    //messenger button opens to its own page
    function pageTrans(callingElInfo){
        const nmStr = {grades_button:"Grades", games_button:"Games",
        library_button:"Library", messenger_button:"Messenger"};
        let info = callingElInfo.id;
        
        //change title of page
        document.getElementById("pgNm").innerHTML =`${nmStr[info]} Page`;
        //make the button option disappear from sideNav options and make sure that all other options are visible
        
        for (id in nmStr) {
            if(id === info) {
                document.getElementById(info).style.visibility = "hidden"; 
            } else {
                document.getElementById(id).style.visibility = "visible";
            }
        }
        //change the greeting on the page
        document.getElementById("greeting").innerHTML = `Welcome to our ${nmStr[info]} page!`;
        
       

        if (info === "library_button"){

            //body_main will contain items specific to the page so you will need an if statement
            document.getElementById("mainTopText").innerHTML = "<marquee>We currently have a host of educational and informational books and videos." +
            " Please take your time to browse some of our selections. And please share what you've learned "+
            "with your student or parent!</marquee>";
            
            //document.getElementById("vidzFrame").style.visibility = "visible";
            let pic = document.getElementById("leftAlignMainPic");
            pic.src="/images/Einstien.png";


        } else if (info === "games_button") {

            document.getElementById("mainTopText").innerHTML = "<marquee>Try your luck and test your math genius " +
            "with our Math Whiz game.</marquee>";
            
            document.getElementById("games_Frame").style.visibility = "visible";
            //change the picture and adjust height and width
            let pic = document.getElementById("leftAlignMainPic");
            pic.src="/images/dice.png";
            pic.style.width="525px";
            pic.style.height="495px";
            pic.style.border="1pt solid skyblue";

            //make all other iframes invisible
            document.getElementById("vidzFrame").style.visibility = "hidden";



        } else if(info === "grades_button"){

            document.getElementById("mainTopText").innerHTML =
            "<marquee>Take the time to explore "+
            "your child's grades and assignments.</marquee>";



        }

        else if (info === "messenger_button") {

            document.getElementById("mainTopText").innerHTML = "<marquee>"+ dateStr + "</marquee>";
            document.getElementById("vidzFrame").style.visibility = "hidden";
            document.getElementById("games_Frame").style.visibility = "hidden";


        }
        closeNav();
    }
    // end of The bridge page transforms

window.addEventListener("load", function(){

        //time function
        //system time is 6hrs ahead of the actual time. 3,600,000 ms = 1 hour
        //I deduct the 6 hours from the current system time to get the correct time
        let t = new Date();
        t.setTime(t.getTime() - 21600000);
        let y = t.getFullYear();
        let m = t.getUTCMonth()+1;
        let d = t.getUTCDate();
        dateStr = `Today is ${m}/${d}/${y}`;
       

    let topTxt = document.getElementById("mainTopText");

    function colorSwitch (){

        setTimeout(function(){
            let arr = ranColorPicker();
            topTxt.style = `color: rgb(${arr[0]},${arr[1]},${arr[2]})`;
        },1000);
    }

    setTimeout(function(){
        let arr = ranColorPicker();
        topTxt.style = `color: rgb(${arr[0]},${arr[1]},${arr[2]})`;
    },2000);

    colorSwitch();
    setInterval(colorSwitch, 3000);
    

    document.getElementById("mainTopText").innerHTML = "<marquee>"+ dateStr + "</marquee>";

    const gradesbutton = document.getElementById("grades_button");
    const messengerbutton = document.getElementById("messenger_button");
    const gamesbutton = document.getElementById("games_button");
    const librarybutton = document.getElementById("library_button");


//    const viewButton = document.getElementById("viewGradesBar");
//
//    viewButton.addEventListener("click", function(){
//    let gradesBar = document.getElementById("gradesBar");
//    if(gradesBar.style.visibility==="hidden"){
//        gradesBar.style.visibility = "visible";
//        } else {
//            gradesBar.style.visibility = "hidden";
//        }
//
//    });

    gradesbutton.addEventListener("click",function(event){
        pageTrans(event.target)});
    messengerbutton.addEventListener("click",function(event){
        pageTrans(event.target)});
    gamesbutton.addEventListener("click",function(event){
        pageTrans(event.target)});
    librarybutton.addEventListener("click",function(event){
        pageTrans(event.target)
        
    });
    


});