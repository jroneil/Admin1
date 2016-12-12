$().ready(function() {
	
	
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
		$('#eventTable').hide();
		$('#dynatable-record-count-eventTable').hide();
		$('.dynatable-per-page').hide();
		$('#dynatable-pagination-links-eventTable').hide();
	   

	$("#searchBtn").on("click", function (event) {
		
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
		
		    $.ajax({
		        url: rclURL,
		        type: "GET",
		        dataType: "json",
		        success: function(result, status, xhr) {	
		        
		        	if (result.length > 0) {
			        	$.each( result, function( key, record ) {
			        		totalDocs = totalDocs + record.docCount;
			        	});
			        	$('#eventTable').show();
			        	$('#noresults').hide();
			        	$('#dynatable-record-count-eventTable').show();
			        	dynatable.settings.dataset.originalRecords = result;
			        	dynatable.settings.dataset.queries.search = '';	  
			        	dynatable.paginationPage.set(1);
			        	dynatable.process();
			        	$('.dynatable-per-page').show();
			        	$('#dynatable-pagination-links-eventTable').show();
			        
		        	} else {
		        		$('#noresults').show();
		        		$('#eventTable').hide();
		        		$('#dynatable-record-count-eventTable').hide();
		        		$('.dynatable-per-page').hide();
		        		$('#dynatable-pagination-links-eventTable').hide();
		        			
		        	
		        	}
		        },
		        error: function (jqXHR, textStatus, errorThrown) { 
		        	 $( "#sysError" ).append( "<h1>System Error try again later</h1>" );
		        }
		    });
		   
	});	

});