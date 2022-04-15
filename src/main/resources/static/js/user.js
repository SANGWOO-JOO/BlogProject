/**
 * 
 */
 
 let index = {
	init: function(){
		$("#btn-save").on("click",()=>{
			this.save();
		});
	},
	
	save: function(){
		//alert('user의 save함수 호출됨'); //8:41 분까지 .. 
		let data = {
			username:$("#username").val(),
			username:$("#password").val(),
			username:$("#email").val()
		}
		//console.log(data);
		//.ajax 호출시 default가 비동기 호출
		$.ajax({ 
			//회원가입 수행 요청
			type:"POST",
			url:"/blog/api/user",
			data:JSON.stringify(data), //http body 데이터
			contentType:"application/json; charset=utf-8", 
			dataType:"json"
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
		//	console.log(resp);
			
			location.href = "/blog";
			
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		});
		}
}

index.init();
