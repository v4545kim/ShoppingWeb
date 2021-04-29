package shopping.product.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import shopping.common.controller.SuperClass;
import shopping.product.model.Product;
import shopping.product.model.ProductDao;

public class ProductInsertController extends SuperClass {
	private Product bean = null;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		
		String gotopage = "/product/prInsertForm.jsp" ;
		super.GotoPage(gotopage);
	}	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		
		MultipartRequest multi = (MultipartRequest)request.getAttribute("multi");
		bean = new Product();
		
		bean.setCategory(multi.getParameter("category"));
		bean.setCompany(multi.getParameter("company"));
		bean.setContents(multi.getParameter("contents"));
		bean.setInputdate(multi.getParameter("inputdate"));
		bean.setName(multi.getParameter("name"));
		bean.setRemark(multi.getParameter("remark"));
		
		bean.setImage(multi.getFilesystemName("image"));

		//bean.setNum(0); 시퀀스가 알아서 하므로 필요 X
		if (multi.getParameter("point") != null && multi.getParameter("point").equals("") == false) {
			bean.setPoint(Integer.parseInt(multi.getParameter("point")));
		}
		if (multi.getParameter("price") != null && multi.getParameter("price").equals("") == false) {
			bean.setPrice(Integer.parseInt(multi.getParameter("price")));
		}
		if (multi.getParameter("stock") != null && multi.getParameter("stock").equals("") == false) {
			bean.setStock(Integer.parseInt(multi.getParameter("stock")));
		}
		
		
		if(this.validate(request) == true) {
			System.out.println("product insert validation check success");
			ProductDao dao = new ProductDao();
			
			int cnt = -9999;
			cnt = dao.InsertData(bean);
			
			new ProductListController().doGet(request, response);
		} else {
			System.out.println("product insert validation check failure");
			request.setAttribute("bean", bean);
			
			super.doPost(request, response);
			
			String gotopage = "/product/prInsertForm.jsp" ;
			super.GotoPage(gotopage);
		}
		
	}
	
	@Override
	public boolean validate(HttpServletRequest request) {
		boolean isCheck = true;
		if (bean.getName().length() < 3 || bean.getName().length() > 15) {
			request.setAttribute(super.PREFIX + "name", "상품 이름은 3자리 이상 15자리 이하이어야 합니다.");
			isCheck = false;
		}
		if (bean.getCompany().length() < 3 || bean.getCompany().length() > 30) {
			request.setAttribute(super.PREFIX + "company", "제조 회사 이름은 3자리 이상 30자리 이하이어야 합니다.");
			isCheck = false;
		}
		if (bean.getContents().length() < 5 || bean.getContents().length() > 255) {
			request.setAttribute(super.PREFIX + "content", "상품에 대한 설명은 5자리 이상 255자리 이하이어야 합니다.");
			isCheck = false;
		}
		if (bean.getCategory().equals("-")) {
			request.setAttribute(super.PREFIX + "category", "상품 카테고리를 선택하셔야 합니다.");
			isCheck = false;
		}
		// 날짜 형식은 yyyy/mm/dd 또는 yyyy-mm-dd
				String regex = "\\d{4}[-/]\\d{2}[-/]\\d{2}";
				if(bean.getInputdate() == null) {
					bean.setInputdate("");
				}
				
				boolean result = Pattern.matches(regex, bean.getInputdate());
				if(result == false) {
					request.setAttribute(super.PREFIX + "inputdate", "입고일자는 yyyy/mm/dd 또는 yyyy-mm-dd으로 입력해주셔야 합니다.");
					isCheck = false;
				}
		if (bean.getImage() == null || bean.getImage().equals("")) {
			request.setAttribute(super.PREFIX + "image", "이미지는 필수 입력 사항입니다.");
			isCheck = false;
		}
				
		int stock = 10;
		if(bean.getStock() < stock) {
			request.setAttribute(super.PREFIX + "stock", "재고 수량은 " + stock + "개 이상이어야 합니다.");
			isCheck = false;
		}
		
		if(bean.getPoint() < 5 || bean.getPoint() > 10) {
			request.setAttribute(super.PREFIX + "point", "포인트는 최소 5 이상 10 이하로 입력하셔야 합니다.");
			isCheck = false;
		}
		return isCheck;
		
	}
}

//Product bean = null ;
//ProductDao dao = new ProductDao();
//String data = dao.toString() ;
//
//String id = request.getParameter("id") ;
//int no = Integer.parseInt(request.getParameter("no")) ;
//
//List<Product> lists = new ArrayList<Product>() ;
//
//request.setAttribute("bean", bean);