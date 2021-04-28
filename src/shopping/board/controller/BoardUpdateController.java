package shopping.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.board.model.Board;
import shopping.board.model.BoardDao;
import shopping.common.controller.SuperClass;

public class BoardUpdateController extends SuperClass {
	private Board bean = null;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		int no = Integer.parseInt(request.getParameter("no"));
		BoardDao dao = new BoardDao();
		
		Board bean = dao.SelectDataByPk(no);
		
		request.setAttribute("bean", bean);
		
		String gotopage = "/board/boUpdateForm.jsp" ;
		super.GotoPage(gotopage);
	}	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);

		bean = new Board();
		bean.setContent(request.getParameter("content"));
		bean.setPassword(request.getParameter("password"));
		bean.setSubject(request.getParameter("subject"));
		bean.setWriter(request.getParameter("writer"));
		bean.setNo(Integer.parseInt(request.getParameter("no")));
		bean.setReadhit(Integer.parseInt(request.getParameter("readhit")));
		
		System.out.println("bean information");
		System.out.println(bean.toString());
		
		if(this.validate(request) == true) {
			System.out.println("board update validation check success");
			BoardDao dao = new BoardDao();
			int cnt = -9999;
			cnt = dao.UpdateData(bean);
			
			new BoardListController().doGet(request, response);
			
		} else {
			System.out.println("board update validation check failure");
			
			request.setAttribute("bean", bean);
			String gotopage = "/board/boUpdateForm.jsp" ;
			super.GotoPage(gotopage);
		}
		
	}
	
	@Override
	public boolean validate(HttpServletRequest request) {
	
		boolean isCheck = true;
		
		if (bean.getSubject().length() < 3 || bean.getSubject().length() > 10) {
			request.setAttribute(super.PREFIX + "subject", "제목은 3자리 이상 10자리 이하이어야 합니다.");
			isCheck = false;
		}
		if (bean.getPassword().length() < 4 || bean.getPassword().length() > 10) {
			request.setAttribute(super.PREFIX + "password", "비밀번호는 4자리 이상 10자리 이하이어야 합니다.");
			isCheck = false;
		}
		if (bean.getContent().length() < 5 || bean.getContent().length() > 10) {
			request.setAttribute(super.PREFIX + "content", "글 내용은 5글자 이상 30글자 이하이어야 합니다.");
			isCheck = false;
		}
		
		return isCheck;
	}
}