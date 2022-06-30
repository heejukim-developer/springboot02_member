package com.example.demo.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.demo.member.vo.MemberVO;

@Mapper
@Repository
public interface MemberDAO {
	
	//xml에 있는 id와 이름이 같으면 자동으로 mapping이 됨
	public List<MemberVO> selectAllMemberList() throws DataAccessException;
	
	public int insertMember (MemberVO memberVO) throws DataAccessException;
	
	public int deleteMember(String id) throws DataAccessException;
	
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException;
}
