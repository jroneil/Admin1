	$("#Submit").on("click", function(event) {
                $(".server-error").hide();
		if ($('#loginform').valid()) {	
			var emailId = $("#email").val();
                        var recaptchaValue= $(".hiddenRecaptcha").val();
			$.ajax({
				type : "GET",
				url : "checkUser?emailId=" + emailId+"&hiddenRecaptcha="+recaptchaValue,
				dataType : "json",
				cache : false,
				success : function(data) {
					var emailVal = data.emailAddress;
					var userExist = data.userExist;
					var confirmCounter = data.confirmCounter;
					$("#checkEmail").hide();
					if (!userExist) {
						$("#hemail").val(emailId);
						$("#semail").append(emailId);
						$("#newUser").show();	
					} else {
						if (confirmCounter == null || confirmCounter == 0) {
							$("#unconfirmedUser").show();                                                       
						} else {
							$("#existingUser").show();
						}	
					}
					event.preventDefault();
					return false;
				},
				error: function (jqXHR, textStatus, errorThrown) { 
                                  $(".server-error").show();
				},
                                timeout: 10000 // sets timeout to 10 seconds
			});
		} else {
			event.preventDefault();
			return false;
		}
	})
	