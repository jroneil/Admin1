$().ready(function() {
	$('#eventTable').hide();
	
		var dynatable = $('#eventTable').dynatable({
			features: {
			    paginate: true,
			    recordCount: true,
			    sort: false,
		            search:false
		            
			  },
			  dataset: {
			    records: []
			  },
			  
			}).data('dynatable');

	   

	$("#searchBtn").on("click", function (event) {
		alert("hi");
		var showIdFrom=$("#showIdFrom").val();
		var showIdTo=$("#showIdTo").val();
		var rclURL=""
			if(showIdFrom!=""&&showIdTo!=""){
				rclURL="/api/v1/order/search?showIdFrom="+showIdFrom+"&showIdTo="+showIdTo;
			}
		if(showIdFrom!=""&&showIdTo==""){
			rclURL="/api/v1/order/search?showIdFrom="+showIdFrom;
		}
		if(showIdFrom==""&&showIdTo!=""){
			rclURL="/api/v1/order/search?&showIdTo="+showIdTo;
		}
		totalDocs = 0;
		alert(rclURL);
		    $.ajax({
		        url: rclURL,
		        type: "GET",
		        dataType: "json",
		        success: function(result, status, xhr) {	
		        	 alert("after\\");
		        	if (result.length > 0) {
			        	$.each( result, function( key, record ) {
			        		totalDocs = totalDocs + record.docCount;
			        	});
			        	$('#eventTable').show();
			        	$('#noresults').hide();
			        	dynatable.settings.dataset.originalRecords = result;
			        	dynatable.settings.dataset.queries.search = '';	  
			        	dynatable.paginationPage.set(1);
			        	dynatable.process();
			        
		        	} else {
		        		$('#noresults').show();
		        	
		        	}
		        },
		        error: function (jqXHR, textStatus, errorThrown) { 
		        	 $( "#sysError" ).append( "<h1>System Error try again later</h1>" );
		        }
		    });
		    alert("after");
	});	

});