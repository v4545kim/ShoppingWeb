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
				<h4>게시물 등록</h4>
			</div>
			<div class="panel-body">
				<form class="form-horizontal" action="" method="post">
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="id">글 번호</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="number" class="form-control" id="id" name="no" >
				      	</div>
				    </div>
					<div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="writer">작성자</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="text" class="form-control" disabled="disabled" 
				        		id="fakewriter" name="fakewriter">
				        	<input type="text" name="writer">
				      	</div>
				    </div>
				    <div class="form-group">
						<label class="control-label col-sm-<%=formleft%>" for="password">비밀 번호</label>
				      	<div class="col-sm-<%=formright%>">          
				        	<input type="password" class="form-control" id="password" name="password">
				      	</div>
				    </div>	
					<div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="subject">글 제목</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="text" class="form-control" id="subject" name="subject">
				      	</div>
				    </div>
					<div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="content">글 내용</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="text" class="form-control" id="content" name="content">
				      	</div>
				    </div>				    
				    <div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="readhit">조회수</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="number" class="form-control" disabled="disabled" 
				        		id="fakereadhit" name="fakereadhit">
				        	<input type="number" name="readhit" value="0">
				      	</div>
				    </div>
					<div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="regdate">작성 일자</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="text" class="form-control" id="regdate" name="regdate">
				      	</div>
				    </div> 
					<div class="form-group">
				      	<label class="control-label col-sm-<%=formleft%>" for="remark">비고(remark)</label>
				      	<div class="col-sm-<%=formright%>">
				        	<input type="text" class="form-control" id="remark" name="remark">
				      	</div>
				    </div>
				    <div class="form-group">        
				      	<div class="col-sm-offset-<%=offset%> col-sm-<%=mywidth%>">
				        	<button type="submit" class="btn btn-default">게시물 작성</button>
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