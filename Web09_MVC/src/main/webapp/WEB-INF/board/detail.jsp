<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js">
</script>
<title>${vo.boardTitle }</title>
</head>
<body>
	<h2>글 보기</h2>
	<div>
		<p>글 번호 : ${vo.boardId }</p>
	</div>
	<div>
		<p>제목 : </p>
		<p>${vo.boardTitle }</p>
	</div>
	<div>
		<p>작성자 : ${vo.memberId }</p>
		<p>작성일 : ${vo.boardDateCreated }</p>
	</div>
	<div>
		<textarea rows="20" cols="120" readonly="readonly">${vo.boardContent }</textarea>
	</div>
	
	<a href="index.jsp"><input type="button" value="글 목록"></a>
	<c:if test="${sessionScope.memberId == vo.memberId }">	
	<a href="update.do?boardId=${vo.boardId }">
		<input type="button" value="글 수정"></a>
		<form action="delete.do" method="POST">
			<input type="hidden" name="boardId" value="${vo.boardId }">
			<input type="submit" value="글 삭제">
   		</form>
	</c:if>
    
    <c:if test="${empty sessionScope.memberId }">
    	* 댓글은 로그인이 필요한 서비스입니다.
    	<a href="login.go">로그인하기</a>
    </c:if>
    
	<input type="hidden" id="boardId" value="${vo.boardId }">
    <c:if test="${not empty sessionScope.memberId }">    
    	<div style="text-align: center;">
			<input type="text" id="memberId" value="${sessionScope.memberId }" readonly="readonly">
			<input type="text" id="replyContent">
			<button id="btnAdd">작성</button>
    	</div>
    </c:if>
    
    <hr>
    <div style="text-align: center;">
    	<div id="replies">
    	</div>
    </div>
    
    <script type="text/javascript">
    $(document).ready(function(){
    	$('#btnAdd').click(function(){
    		let boardId = $('#boardId').val(); // 게시판 번호 데이터
    		let memberId = $('#memberId').val(); // 작성자 데이터
    		let replyContent = $('#replyContent').val(); // 댓글 내용 데이터
    		
    		let obj = {
    				'boardId' : boardId,
    				'memberId' : memberId,
    				'replyContent' : replyContent
    				
    		};
    		console.log(obj);
    		
    		// $.ajax로 송수신
    		$.ajax({
    			type : 'POST',
    			url : 'replies/add',
    			data : {'obj' : JSON.stringify(obj)}, // JSON으로 변환
    			success: function(result){
    				console.log(result);
    				if(result == 'success'){
    					alert('댓글 입력 성공')
    					$('#replyContent').val('');
                        getAllReplies();
    				}
    			}
    		})
    	}); // end btnAdd.click()
    	
    	getAllReplies();
    	// 현재 게시글의 댓글 전체 가져오기
    	function getAllReplies(){
    		// 댓글을 가져오기 위해 boardId 필요
    		let boardId = $('#boardId').val();
    		
    		// url에 boardId 전송
    		let url = 'replies/all?boardId=' + boardId;
    		
    		// ajax로 전체 데이터 GET
    		$.getJSON(url, function(data){
    			// console.log(data);
    			
    			let list = ''; // 댓글 데이터를 HTML에 표현할 문자열 변수
    			let memberId = '${sessionScope.memberId}';
    			console.log('memberId = ' + memberId);
    			
    			$(data).each(function(){
    				// 퀴즈 : 로그인한 사용자와 작성자가 같은 경우에만
    				//		 댓글 수정/삭제가 가능하도록 구현
    				// this : 컬렉션(배열)의 각 인덱스 데이터를 의미
    				console.log(this);
    			
    				// replyDateCreated는 string 타입이므로 date 타입으로 변경
    				let replyDateCreated = new Date(this.replyDateCreated);
    				//let disabled = '';
    				let readonly = '';
    				
    				if(memberId != this.memberId){
    				//	disabled = 'disabled';
    					readonly = 'readonly';
    				}
    				list += '<div class="reply_item">'
    					+ '<pre>'
    					+ '<input type="hidden" id="replyId" value="'+ this.replyId +'">'
    					+ this.memberId
    					+ '      ' // 공백
    					+ '<input type="text" id="replyContent" '+ readonly +' value="'+ this.replyContent +'">'
    					+ '      ' // 공백
    					+ replyDateCreated;
    					
    				if ('${not empty sessionScope.memberId}' && '${sessionScope.memberId}' == this.memberId){
    				list +=	'      ' // 공백
    				//	+ '<button class="btn_update" '+ disabled +'>수정</button>'
    					+ '<button class="btn_update">수정</button>'
    				//	+ '<button class="btn_delete" '+ disalbed +'>삭제</button>';
    					+ '<button class="btn_delete">삭제</button>';
    				}
    					
    				list += '</pre>'
    					 + '</div>';
    			}); // end each()
    			
    			$('#replies').html(list);
    			
    		}); // end getJSON()
    		
    	} // end getAllReplies()
    	 
    	// 선택된 댓글 수정
    	$('#replies').on('click', '.reply_item .btn_update', function(){
    		console.log(this);
    		
    		// 선택된 수정 버튼과 같은 노드에 있는 replyId, replyContent 값을 저장
    		// prevAll() : 선택된 노드 이전에 있는 모든 형제 노드에 접근
    		let replyId = $(this).prevAll('#replyId').val();
    		let replyContent = $(this).prevAll('#replyContent').val();
    		console.log('선택된 댓글 번호 : ' + replyId + ', 댓글 내용 : ' + replyContent);
    		
    		let obj = {
    	            'replyId': replyId,
    	            'replyContent': replyContent
    	        };

    	        console.log(obj);

    	        $.ajax({
    	            type: 'POST',
    	            url: 'replies/update',
    	            data: { 'obj': JSON.stringify(obj) },
    	            success: function (result) {
    	                console.log(result);
    	                if (result === 'success') {
    	                    alert('댓글 수정 성공');
    	                    getAllReplies();
    	                } else {
    	                    alert('댓글 수정 실패');
    	                }
    	            }
    	        });
    	});
    	
    	// 선택된 댓글 삭제
    	$('#replies').on('click', '.reply_item .btn_delete', function(){
    		console.log(this);
    		
    		// 선택된 삭제 버튼과 같은 노드에 있는 replyId, replyContent 값을 저장
    		// prevAll() : 선택된 노드 이전에 있는 모든 형제 노드에 접근
    		let replyId = $(this).prevAll('#replyId').val();
    		console.log('선택된 댓글의 번호 : ' + replyId);
    		
    	        let obj = {
    	            'replyId': replyId
    	        };

    	        console.log(obj);

    	        $.ajax({
    	            type: 'POST',
    	            url: 'replies/delete',
    	            data: { 'obj': JSON.stringify(obj) },
    	            success: function (result) {
    	                console.log(result);
    	                if (result === 'success') {
    	                    alert('댓글 삭제 성공');
    	                    getAllReplies();
    	                } else {
    	                    alert('댓글 삭제 실패');
    	                }
    	            }
    	        });
    	    });
    }); // end document
    </script>
</body>
</html>