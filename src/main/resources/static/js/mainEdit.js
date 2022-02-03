// const addBtn = document.getElementById(30);
// var i = 0;
// addBtn.onclick = addIng(i);

const ricettaInput = document.getElementById('ricettaInput');

const results = document.getElementById('results');
ricettaInput.addEventListener('input', getRicetta);



function getRicetta(){
	const previous = document.getElementsByClassName('ricette')
	//console.log(previous);
	for(let p of previous){
		p.classList.remove('d-none');
	}
	
	let input = ricettaInput.value.toLowerCase();	
	console.log(input);
	let results = [];
	const all = document.getElementsByClassName('ricette');
	for(let i = 0; i < all.length; i++) {
		if(!all.item(i).id.toLowerCase().includes(input)){
			console.log(all.item(i));
			results.push(all.item(i))
		}
	}		
		for (let el of results) {
			el.classList.add('d-none');
		}
		
	};
	
	
    	
		
//

//	};
	



function addIng(i){
    let btn = document.getElementById('btn' + i);
    btn.className = 'd-none';
    if(i < 25){
        let div = document.getElementById(i);
        div.className = 'row'
        let newBtn = document.getElementById('btn' + (1 + i));
        newBtn.className = 'btn btn-warning w-auto m-2'
    }else if(i==25){
     document.getElementById('alert').className = 'alert alert-warning w-auto m-2';       
    }

}

function addImg(i){
	let imgBtn = document.getElementById('imgBtn' + i);
	imgBtn.className = 'd-none';
	if(i<5){
		let div = document.getElementById('img' + i);
		div.className = 'row mt-2'
		let newBtn = document.getElementById('imgBtn' + (1+i));
		newBtn.className = 'btn btn-warning w-auto m-2';
	}else if(i==5){
		document.getElementById('imgAlert').className = 'alert alert-warning w-auto m-2';
	}
}





  function increaseLike(url){
    axios
    .get(url)
    .then((response) =>{
      document.getElementById('clicks').innerHTML = response.data;
      document.getElementById('likeBtn').disabled = true;
    })
    .catch((error)=>{
      console.log(error);
    })
  }
 
  
  
 
  