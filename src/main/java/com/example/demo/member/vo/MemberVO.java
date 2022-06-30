package com.example.demo.member.vo;

import java.sql.Date;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Component
public class MemberVO {
	
	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date joinDate;
	
	public MemberVO() {
		System.out.println("MemberVO 생성자 호출");
	}

}
