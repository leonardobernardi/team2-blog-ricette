// const addBtn = document.getElementById(30);
// var i = 0;
// addBtn.onclick = addIng(i);


function addIng(i){
    let btn = document.getElementById('btn' + i);
    btn.className = 'd-none';
    if(i < 25){
        let div = document.getElementById(i);
        div.className = 'row'
        let newBtn = document.getElementById('btn' + (1 + i));
        newBtn.className = 'btn btn-success w-auto m-2'
    }else if(i==25){
     document.getElementById('alert').className = 'alert alert-warning w-auto m-2';       
    }

}

function addImg(i){
	let imgBtn = document.getElementById('imgBtn' + i);
	imgBtn.className = 'd-none';
	if(i<5){
		let div = document.getElementById('img' + i);
		div.className = 'row'
		let newBtn = document.getElementById('imgBtn' + (1+i));
		newBtn.className = 'btn btn-success w-auto m-2';
	}else if(i==5){
		document.getElementById('imgAlert').className = 'alert alert-warning w-auto m-2';
	}
}





  function increaseLike(url){
    axios
    .get(url)
    .then((response) =>{
      document.getElementById('clicks').innerHTML = response.data;
    })
    .catch((error)=>{
      console.log(error);
    })
  }
 
  
  
 
  