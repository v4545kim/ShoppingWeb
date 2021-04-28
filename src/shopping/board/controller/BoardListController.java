package shopping.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.board.model.Board;
import shopping.board.model.BoardDao;
import shopping.common.controller.SuperClass;
import shopping.utility.FlowParameters;
import shopping.utility.Paging;

public class BoardListController extends SuperClass {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		FlowParameters parameters
			= new FlowParameters(
					request.getParameter("pageNumber"), 
					request.getParameter("mode"), 
					request.getParameter("keyword"));
		
		System.out.println("parameter list ");
		System.out.println(parameters.toString());
		
		String contextPath = request.getContextPath();
		String url = contextPath + "/Shopping?command=boList";
		
		BoardDao dao = new BoardDao();
		
		int totalCount = dao.SelectTotalCount(parameters.getMode(), parameters.getKeyword()); // 행(row)의 총 개수
		System.out.println("total data size : " + totalCount);
		
		Paging pageInfo = new Paging(
								parameters.getPageNumber(), 
								totalCount, 
								url, 
								parameters.getMode(), 
								parameters.getKeyword());
		
		List<Board> lists = dao.SelectDataList(
								pageInfo.getBeginRow(), 
								pageInfo.getEndRow(), 
								parameters.getMode(), 
								parameters.getKeyword());
		System.out.println("board list count : " + lists.size());
		
		// 바인딩
		request.setAttribute("lists", lists);
		request.setAttribute("pageInfo", pageInfo);
		
		// 자주 사용되는 파라미터를 속성으로 정의합니다.
		request.setAttribute("parameters", parameters.toString());
		
		
		String gotopage = "/board/boList.jsp" ;
		super.GotoPage(gotopage);
	}	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}
}