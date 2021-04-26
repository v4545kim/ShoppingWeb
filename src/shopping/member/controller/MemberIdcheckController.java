package shopping.member.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.common.controller.SuperClass;
import shopping.member.model.Member;
import shopping.member.model.MemberDao;

public class MemberIdcheckController extends SuperClass {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		String id = request.getParameter("id") ;
		MemberDao dao = new MemberDao();
		
		Member bean = dao.SelectDataByPk(id) ;
		
		if(bean == null) { // 존재 하지 않는 회원
			request.setAttribute("message", id + "은(는) <font color='blue'><b>사용 가능</b></font>한 아이디입니다."); 
			request.setAttribute("isCheck", true);
		} else {
			if (bean.getId().equals("admin")) { // for 관리자
				request.setAttribute("message", "admin은 <font color='red'><b>사용 불가능</b></font>한 아이디입니다.<br>"
						+ "<font color='blue'><b>관리자</b></font>를 위한 아이디입니다.");
				request.setAttribute("isCheck", false);
			} else { // 일반 사용자
				request.setAttribute("message", id + "은(는) 이미 <font color='red'><b>사용중</b></font>인 아이디입니다.");
				request.setAttribute("isCheck", false);
			}
		}
		
		String gotopage = "/member/idCheck.jsp" ;
		super.GotoPage(gotopage);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}
}