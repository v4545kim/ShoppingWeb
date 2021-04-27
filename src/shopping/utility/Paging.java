package shopping.utility;

public class Paging {
	private int totalCount = 0; // 총 행(레코드) 수
	private int totalPage = 0; // 전체 페이지 수
	
	private int pageNumber = 0; // 현재 페이지 번호
	private int pageSize = 10; // 한 페이지에 보여줄 건수
	private int beginRow = 0; // 현재 페이지의 시작 행(랭킹)
	private int endRow = 0; // 현재 페이지의 끝 행(랭킹)
	
	private int pageCount = 10; // 하단에 보여줄 페이지 링크 수
	private int beginPage = 0; // 페이징 처리 시작 페이지 번호
	private int endPage = 0; // 페이징 처리 끝 페이지 번호
	
	private String url = ""; // url
	private String pagingHtml = ""; // 하단의 숫자 페이지 링크(이전, 다음 링크 포함)
	private String pagingStatus = ""; // 상단 우측의 현재 페이지 위치 표시
	
	private String mode = ""; // 검색 모드(전체 검색은 all)
	private String keyword = ""; // 검색 키워드
	
	public Paging(String _pageNumber, int totalCount, String url, 
			String mode, String keyword) {
		if (_pageNumber == null || _pageNumber.equals("null") || _pageNumber.equals("")) {
			_pageNumber = "1";
		}
		this.pageNumber = Integer.parseInt(_pageNumber);
		this.totalCount = totalCount;
		this.url = url;
		this.mode = mode;
		this.keyword = keyword;
		
		// 계산이 필요한 나머지 변수들
		this.totalPage = (int)Math.ceil((double)totalCount / pageSize) ;
		this.beginRow = (pageNumber - 1) * pageSize + 1;
		this.endRow = pageNumber * pageSize;
		this.beginPage = (pageNumber - 1) / pageCount * pageCount + 1;
		this.endPage = this.beginPage + pageCount - 1;
		
		if(totalPage < endPage) {
			endPage = totalPage;
		}
		
		// pagingHtml 변수는 코딩량이 길어서 별도의 메소드로 처리합니다.
		this.pagingHtml = this.getPagingHtml(url);;
		this.pagingStatus = "총 " + totalCount + "건[" 
				+ this.pageNumber + "/" + this.totalPage + "]" ;
		
		
		//this.Display();
	}

	private String getPagingHtml(String url) {
		String result = "";
		
		// 필드 검색을 위한 변수
		String field_search = "&mode=" + this.mode + "&keyword" + this.keyword;
		
		// href_attr은 <a> 태그의 href 속성에 들어갈 값
		String href_attr= url + "&pageNumber=" + 100 + field_search;
		
		result += "<ul class=\"pagination\">";
		
		// part 맨 처음, 이전
		if (pageNumber <= pageCount) {
			System.out.println("맨 처음과 이전이 없습니다.");
		} else {
			result += "<li><a href=\"" + url + "&pageNumber=" + 1 + field_search + "\">" + "맨 처음" + "</a></li>";
			result += "<li><a href=\"" + url + "&pageNumber=" + (beginPage - 1) + field_search + "\">" + "이전" + "</a></li>";
		}
		// part 중간(beginPage부터 endPage까지)
		for (int i = beginPage; i <= endPage; i++) {
			if (i == pageNumber) { // 현재 페이지이면
				result += "<li class=\"active\"><a><font color='red'><b>"+i+"</b></font></a></li>";
			} else {
				result += "<li><a href=\"" + url + "&pageNumber=" + i + field_search + "\">" + i + "</a></li>";
			}
		}
		// part 다음, 맨 끝
		if (pageNumber >= (totalPage / pageCount * pageCount + 1)) {
			System.out.println("맨 끝과 다음이 없습니다.");
		} else {
			result += "<li><a href=\"" + url + "&pageNumber=" + totalPage + field_search + "\">" + "맨 끝" + "</a></li>";
			result += "<li><a href=\"" + url + "&pageNumber=" + (endPage+1) + field_search + "\">" + "다음" + "</a></li>";
			
			
		}
		result += "</ul>";
		return result;
	}

	private void Display() {
		System.out.println("totalCount : " + totalCount);
		System.out.println("totalpage : " + totalPage);
		System.out.println("pageNumber : " + pageNumber);
		System.out.println("pageSize : " + pageSize);
		System.out.println("beginRow : " + beginRow);
		System.out.println("endRow : " + endRow);
		System.out.println("pageCount : " + pageCount);
		System.out.println("beginPage : " + beginPage);
		System.out.println("endPage : " + endPage);
		System.out.println("url : " + url);
		System.out.println("pagingHtml : " + pagingHtml);
		System.out.println("pagingStatus : " + pagingStatus);
		System.out.println("modemode : " + mode);
		System.out.println("keyword : " + keyword);
	}


	public int getPageNumber() { return pageNumber; }
	public int getBeginRow() { return beginRow; }
	public int getEndRow() { return endRow; }
	public String getPagingHtml() { return pagingHtml; }
	public String getPagingStatus() { return pagingStatus; }
	public String getMode() { return mode; }
	public String getKeyword() { return keyword; }

	
//	public int getTotalCount() {
//		return totalCount;
//	}
//	public int getTotalPage() {
//		return totalPage;
//	}
//	public int getPageSize() {
//		return pageSize;
//	}
//	public int getPageCount() {
//		return pageCount;
//	}
//	public int getBeginPage() {
//		return beginPage;
//	}
//	public int getEndPage() {
//		return endPage;
//	}
//	public String getUrl() {
//		return url;
//	}
	
}


























