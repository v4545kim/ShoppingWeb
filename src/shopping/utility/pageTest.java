package shopping.utility;

public class pageTest {
	public static void main(String[] args) {
		String _pageNumber = "13";
		int totalCount = 250;
		String url = "boList";
		String mode = "writer"; // 검색 모드
		String keyword = "홍길동"; // 검색 키워드
		
		Paging pageInfo = new Paging(_pageNumber, totalCount, url, mode, keyword);
	}
	
}