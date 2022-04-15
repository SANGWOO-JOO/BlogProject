package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
@Getter
@Setter
@Data가 getter setter 모두 포함!!*/
@Data
@NoArgsConstructor
public class Member {
	//어떤 변수를 만들때는 private러! 
	//private를 수정할 수 있게 getter . setter
	private  int id;
	private  String username;
	private  String password;
	private  String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

}
