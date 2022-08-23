window.addEventListener("load", function(){
        //time function
        //system time is 6hrs ahead of the actual time. 3,600,000 ms = 1 hour
        //I deduct the 6 hours from the current system time to get the correct time
    let t = new Date(); 
    t.setTime(t.getTime() - 21600000);   
    let y = t.getFullYear();
    let m = t.getUTCMonth()+1;
    let d = t.getUTCDate();
  
    if(m < 10){
        m =`0${m}`;
    }
    if(d < 10){
         d = `0${d}`;
        }

    dateStr = `${y}-${m}-${d}`;





    let topTxt = document.getElementById("mainTopText");
    document.getElementById("dateSent").value =  dateStr;

});