<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./../common/common.jsp" %>
<%
	/* position for grid system */	
	int offset = 2 ;
	int mywidth = twelve - 2 * offset ;
	int formleft = 3 ;
	int formright = twelve - formleft ;
	//int rightButton = 2 ;
%> 
<!DOCTYPE html><html>
<head>
	<script>
		$(document).ready(function(){
		});
	</script>
</head>
<body>
	<div class="container col-sm-offset-<%=offset%> col-sm-<%=mywidth%>">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4>상품 등록</h4>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="<%=YesForm%>" 
					method="post" enctype="multipart/form-data">
					<input type="hidden" name="command" value="prInsert">
					
					<div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="name">상품명</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="text" class="form-control" 
				        		id="name" name="name" value="${bean.name}">
				        	<span class="err form-control-static">${errname}</span>	
				      	</div>
				    </div>
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="company">제조 회사</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="text" class="form-control" 
				        		id="company" name="company" value="${bean.company}">
				        	<span class="err form-control-static">${errcompany}</span>	
				      	</div>
				    </div>
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="image">이미지</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="file" class="form-control" 
				        		id="image" name="image" value="${bean.image}">
				        	<span class="err form-control-static">${errimage}</span>	
				      	</div>
				    </div>
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="stock">재고 수량</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="number" class="form-control" 
				        		id="stock" name="stock" value="${bean.stock}">
				        	<span class="err form-control-static">${errstock}</span>	
				      	</div>
				    </div>
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="price">단가</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="number" class="form-control" 
				        		id="price" name="price" value="${bean.price}">
				        	<span class="err form-control-static">${errprice}</span>	
				      	</div>
				    </div>
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="category">카테고리</label>
				      	<div class="col-sm-<%=formright%>">
				        	<select class="form-control" id="category" name="category">
				        		<option value="-">-- 카테고리를 선택하세요
				        		<option value="hit">인기 상품
				        		<option value="new">신상품
				        		<option value="best">베스트 상품
				        	</select>
				        	<span class="err form-control-static">${errcategory}</span>	
				      	</div>
				    </div>
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="contents">상품 설명</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="text" class="form-control" 
				        		id="contents" name="contents" value="${bean.contents}">
				        	<span class="err form-control-static">${errcontents}</span>	
				      	</div>
				    </div>
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="point">적립 포인트</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="number" class="form-control" 
				        		id="point" name="point" value="${bean.point}">
				        	<span class="err form-control-static">${errpoint}</span>	
				      	</div>
				    </div>
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="inputdate">입고 일자</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="date" class="form-control" 
				        		id="inputdate" name="inputdate" value="${bean.inputdate}">
				        	<span class="err form-control-static">${errinputdate}</span>	
				      	</div>
				    </div>
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="remark">비고</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="text" class="form-control" 
				        		id="remark" name="remark" value="${bean.remark}">
				        	<span class="err form-control-static">${errremark}</span>	
				      	</div>
				    </div>
				    <div class="form-group">        
				      	<div class="col-sm-offset-<%=offset%> col-sm-<%=mywidth%>">
				        	<button type="submit" class="btn btn-default">상품 등록</button>
				        	&nbsp;&nbsp;&nbsp;
				        	<button type="reset" class="btn btn-default">초기화</button>
				      	</div>
				    </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>