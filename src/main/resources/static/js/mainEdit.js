// const addBtn = document.getElementById(30);
// var i = 0;
// addBtn.onclick = addIng(i);


function addIng(i){
    console.log('funzione');
    let btn = document.getElementById('btn' + i);
    btn.className = 'd-none';
    if(i < 25){
        let div = document.getElementById(i);
        div.className = 'row'
        let newBtn = document.getElementById('btn' + (1 + i));
        newBtn.className = 'btn btn-primary w-auto m-2'
    }else if(i==25){
     document.getElementById('alert').className = 'alert alert-warning w-auto m-2';       
    }

}