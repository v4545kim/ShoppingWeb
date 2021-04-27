package shopping.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.board.model.Board;
import shopping.board.model.BoardDao;
import shopping.common.controller.SuperClass;
import shopping.utility.Paging;

public class BoardListController extends SuperClass {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		String pageNumber = request.getParameter("pageNumber");
		String mode = request.getParameter("mode");
		String keyword = request.getParameter("keyword");
		
		if(mode == null || mode.equals("null") || mode.equals("")) {
			mode = "all";
		}
		if(keyword == null || keyword.equals("null") || keyword.equals("")) {
			keyword = "";
		}
		
		String url = super.CommandName + "boList";
		
		BoardDao dao = new BoardDao();
		
		int totalCount = dao.SelectTotalCount(mode, keyword); // 행(row)의 총 개수
		System.out.println("total data size : " + totalCount);
		
		Paging pageInfo = new Paging(pageNumber, totalCount, url, mode, keyword);
		
		List<Board> lists = dao.SelectDataList(
								pageInfo.getBeginRow(), 
								pageInfo.getEndRow(), 
								mode, 
								keyword);
		System.out.println("board list count : " + lists.size());
		
		// 바인딩
		request.setAttribute("lists", lists);
		request.setAttribute("pageInfo", pageInfo);
		
		String gotopage = "/board/boList.jsp" ;
		super.GotoPage(gotopage);
	}	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}
}