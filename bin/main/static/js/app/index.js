var main = {
	init : function() {
		var _this = this;
		$('#btnSave').on('click', function() {
			_this.save();
		});
		$('#btnUpdate').on('click', function() {
			_this.update();
		});
		$('#btnDelete').on('click', function() {
			_this.delete();
		});
	},
	save : function() {
		var data = {
			title : $('#title').val(),
			author : $('#author').val(),
			content : $('#content').val()	
		};
		
		$.ajax({
			type : 'POST',
			url : '/api/v1/posts',
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			async: false,
			data : JSON.stringify(data),
			success: function(data) {
				console.log('pId : ' +data);
				alert('새 글이 등록되었습니다.');
				window.location.href='/posts/select/' +data;
	        },
	        error : function(error) {
	        	alert(JSON.stringify(error));
			}
		});
	},
	update : function() {
		var data = {
			title : $('#title').val(),
			content : $('#content').val()	
		};
		
		var id = $('#id').val();
		
		$.ajax({
			type : 'PUT',
			url : '/api/v1/posts/' +id,
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			data : JSON.stringify(data),
			success: function(data) {
				alert(id +'번 글이 수정되었습니다.');
				window.location.href='/posts/select/' +id;
	        },
	        error : function(error) {
	        	alert(JSON.stringify(error));
			}
		});
	},
	delete : function() {
		var id = $('#id').val();
		
		$.ajax({
			type : 'DELETE',
			url : '/api/v1/posts/' +id,
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			success: function(data) {
				alert(id +'번 글이 삭제되었습니다.');
				window.location.href='/';
	        },
	        error : function(error) {
	        	alert(JSON.stringify(error));
			}
		});
	}
}

main.init();