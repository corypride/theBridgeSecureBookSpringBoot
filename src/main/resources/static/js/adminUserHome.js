    let dateStr=``; //get date and time from window load function

    function ranColorPicker () {
        //randomly selects three colors between the range of 0 and 255 and returns the array
        let arr = [0, 0, 0];
        for (let i = 0; i < arr.length; i++){
            arr[i] = Math.floor((Math.random() * 255));
        }
        return arr;
    }

    //funtionality for collapsing gradesbar on aStudentReport
    function collapseGradesBar(){

       let gradesBar = document.getElementById("gradesBar");
        if(gradesBar.style.visibility==="hidden"){
                gradesBar.style.visibility = "visible";
        } else {
                gradesBar.style.visibility = "hidden";
        }
    }


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
       
      let slider = document.getElementById("maxPoints");
      let aValue = document.getElementById("rangeDisplay");
      aValue.innerHTML =slider.value;
      slider.onchange= function() {
      aValue.innerHTML = this.value;
      console.log(this.value);
      }

    function colorSwitch (){

        setTimeout(function(){
            let arr = ranColorPicker();
            //topTxt.style = `color: rgb(${arr[0]},${arr[1]},${arr[2]})`;
        },1000);
    }

    setTimeout(function(){
        let arr = ranColorPicker();
        //topTxt.style = `color: rgb(${arr[0]},${arr[1]},${arr[2]})`;
    },2000);

    colorSwitch();
    setInterval(colorSwitch, 3000);




});