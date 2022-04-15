package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired //DI 의존성주입
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return "삭제 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다.id "+id;
	}
	
	//email, password
	@	Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {

		System.out.println("id:"+id);
		System.out.println("password:"+requestUser.getPassword());														
		System.out.println("email:"+requestUser.getEmail()); 
		
		User user =userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});	
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
//	    requestUser.setId(id);
//		requestUser.setUsername("ssar");
//		userRepository.save(user);	
		return null;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	
	}
	
	//한 페이지당 2건의 데이턴을 리턴받아 봄
	//http://localhost:8000/blog/dummy/user?page=1
	@GetMapping("/dummy/user")
	public List<User>pageList(@PageableDefault(size=2, sort="id",direction =Sort.Direction.DESC)Pageable pageable){
		Page<User> pagingUsers  = userRepository.findAll(pageable);
		
		List<User> users =pagingUsers.getContent();
		return users;
		
	}

	//:{id}로 파라미터를 전달 받을 수 있음.
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4 을 찾으면 내가 데디비에서 못찾아오게 되면 user가 null이 될 것 아냐?
		//그럼 return nulll 이 되자나 그럼 프로그램에 문제가 되지 않겠니
		//optional로 너의 user객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해
		
//		//람다식
//		User user = userRepository.findById(id).orElseThrow(()-> {
//			return new IllegalArgumentException("해당 사용자는 없습니다.");
//		});
//		return user;
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당유저는 없습니다. id "+id);
			}
		});
		//요청 : 웹브라이저
		// user 객체 = 자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 잇는 데이터) => json (Gson 라이브러리)
		
		return user;
		
	}
	//http://localhost:8000/blog/dummy/join(요청)
	//http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) { //key = value(규칙)

		System.out.println("id:"+user.getId());
		System.out.println("username: "+ user.getUsername());
		System.out.println("password: "+ user.getPassword());
		System.out.println("email: "+ user.getEmail());
		System.out.println("role:"+user.getRole());
		System.out.println("createDate:"+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
