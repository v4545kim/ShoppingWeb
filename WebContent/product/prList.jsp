<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp" %>
<%
	/* position for grid system */	
	int offset = 2 ;
	int mywidth = twelve - 2 * offset ;
	int formleft = 3 ;
	int formright = twelve - formleft ;
	int rightButton = 2 ;
%> 
<!DOCTYPE html><html>
<head>
	<script>	
		function search(){
			alert('검색');
		}
		function searchAll(){
			alert('전체 검색');
		}
		
		$(document).ready(function(){
			
		});
	</script>
</head>
<body>
	<div class="container col-sm-offset-<%=offset%> col-sm-<%=mywidth%>">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4>상품 목록 보기</h4>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>상품 번호</th>
							<th>상품명</th>
							<th>제조사</th>							
							<th>이미지</th>
							<th>상품 수량</th>
							<th>상품 가격</th>
							<th>카테고리</th>
							<th>상품 내용</th>							
							<th>포인트</th>
							<th>입고 일자</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td align="center" colspan="10">
								<form action="" class="form-inline" role="form" name="myform" method="get"> 
									<div class="form-group">
										<select id="mode" name="mode" class="form-control">
											<option value="all" selected="selected">-- 선택하세요.
											<option value="num">상품 번호
											<option value="name">상품명
										</select>
									</div>									
									<div class="form-group">
										<input type="text" class="form-control" name="keyword" id="keyword"> 
									</div>									
									&nbsp;&nbsp;
									<button class="btn btn-default" type="submit" onclick="search();">검색</button>
									&nbsp;&nbsp;
									<button class="btn btn-default" type="button" onclick="searchAll();">전체 검색</button>
								</form>
							</td>
						</tr>
						<c:forEach var="bean" items="${requestScope.lists}">		
							<tr>
								<td>${bean.num}</td>
								<td>${bean.name}</td>
								<td>${bean.company}</td>
								<td>${bean.image}</td>
								<td>${bean.stock}</td>
								<td>${bean.price}</td>
								<td>${bean.category}</td>
								<td>${bean.contents}</td>
								<td>${bean.point}</td>
								<td>${bean.inputdate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>