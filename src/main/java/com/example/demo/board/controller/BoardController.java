package com.example.demo.board.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.board.dto.BoardDTO;
import com.example.demo.board.dto.PageDTO;
import com.example.demo.board.service.BoardService;

// http://localhost:8090/board/list.do
@Controller
public class BoardController {
	
	@Autowired
	private PageDTO pdto;
	private int currentPage;
	
	@Autowired
	private BoardService service;
	public BoardController() {

	}
	
//	public void setService(BoardService service) {
//		this.service = service;
//	}
	
	@RequestMapping("/board/list.do")
	public ModelAndView listMethod(PageDTO pv, ModelAndView mav,HttpServletRequest request ) {
		String viewName= (String)request.getAttribute("viewName");
		
		int totalRecord = service.countProcess();
		if(totalRecord >= 1) {//테이블에 데이터가 있어서 이 작업이 필요한것
			if(pv.getCurrentPage()==0)
				currentPage=1;
			else 
				currentPage=pv.getCurrentPage();
			pdto=new PageDTO(currentPage, totalRecord);
			List<BoardDTO> aList = service.listProcess(pdto);
			mav.addObject("aList", aList);
			mav.addObject("pv",pdto);
		}	

		
		//mav.setViewName("/board/list");
		mav.setViewName(viewName); //위에꺼랑 둘 다 상관없이 실행
		return mav;
		
	}///end listMethod///////////////
	
	@RequestMapping("/board/view.do")
	public ModelAndView viewMethod(HttpServletRequest request,int currentPage,int num,ModelAndView mav) {
		//BoardDTO dto= service.contentProcess(num);
		String viewName= (String)request.getAttribute("viewName");

		
		mav.addObject("dto", service.contentProcess(num));
		mav.addObject("currentPage",currentPage);
		//mav.setViewName("/board/view");
		mav.setViewName(viewName);
		return mav;
		
	}//end viewMethod////////////////////////////////////
	
	
	@RequestMapping(value="/board/write.do", method = RequestMethod.GET)
	public ModelAndView writeMethod(HttpServletRequest request,BoardDTO dto,PageDTO pv, ModelAndView mav) {
		String viewName= (String)request.getAttribute("viewName");
	
		
		if(dto.getRef()!=0) {//답변글이면..
			mav.addObject("currentPage",pv.getCurrentPage());
			mav.addObject("dto",dto);
		}
		//mav.setViewName("/board/write");
		mav.setViewName(viewName);
		return mav;
	}//end writeMethod()////////////////////

	@RequestMapping(value="/board/write.do", method = RequestMethod.POST)
	public String writeProMethod(BoardDTO dto,PageDTO pv ,HttpServletRequest request) {
		MultipartFile file= dto.getFilename();
		if(!file.isEmpty()) {
			UUID random= saveCopyFile(file,request);
			dto.setUpload(random+"_"+file.getOriginalFilename());
		}
		
		dto.setIp(request.getRemoteAddr());		
		
		service.insertProcess(dto);

		//답변글이면
		if(dto.getRef()!=0) {
			return "redirect:/board/list.do?currentPage=" + pv.getCurrentPage();
		}else {//제목글이면
			return "redirect:/board/list.do";
			
		}

		
	}
	

	@RequestMapping("/board/contentdownload.do")
	public ModelAndView downMethod(int num, ModelAndView mav) {
		mav.addObject("num",num);
		mav.setViewName("download");
		return mav;
		
	}
	

	
	//업로드 파일첨부
	public UUID saveCopyFile(MultipartFile file, HttpServletRequest request) {
		String fileName= file.getOriginalFilename();
		
		//중복파일명을 처리하기 위한 난수 발생
		UUID random= UUID.randomUUID();
		
		File fe= new File(urlPath(request));
		if(!fe.exists()) {
			fe.mkdir();
		}
		
		//원래 파일명 앞에 난수값을 붙임: 중복될 일이 거의 없다
		File ff= new File(urlPath(request),random+"_"+fileName);
		
		//넘어오는 파일을 ff에 써줘라
		try {
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(ff));
			
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
 			e.printStackTrace();
		}
		return random;
	}//end saveCopyFile()//////////////////
	
	@RequestMapping(value="/board/update.do",method = RequestMethod.GET)
	public ModelAndView updateMethod(int num , int currentPage, ModelAndView mav) {
		mav.addObject("dto", service.updateSelectProcess(num));
		mav.addObject("currentPage",currentPage);
		mav.setViewName("/board/update");
		return mav;
	}
	@RequestMapping(value="/board/update.do",method = RequestMethod.POST)
	public String updateProMethod(BoardDTO dto,int currentPage,HttpServletRequest request) {
		MultipartFile file= dto.getFilename();
		if(!file.isEmpty()) {
			UUID random= saveCopyFile(file,request);
			dto.setUpload(random+"_"+file.getOriginalFilename());
		}
		service.updateProcess(dto, urlPath(request));
		return "redirect:/board/list.do?currentPage="+currentPage;
	}
	
	@RequestMapping(value="/board/delete.do",method = RequestMethod.GET)
	public String deleteMethod(int num, int currentPage, HttpServletRequest request) {
		service.deleteProcess(num, urlPath(request));
		return "redirect:/board/list.do?currentPage="+currentPage;
		
	}
	private String urlPath(HttpServletRequest request) {
		String root=request.getSession().getServletContext().getRealPath("/");
		//root:C:\smart_study\spring_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\spring08_board\
		//System.out.println("root:" +root);
		String saveDirectory= root+"temp"+File.separator;
		return saveDirectory;
	}
	
	
}

