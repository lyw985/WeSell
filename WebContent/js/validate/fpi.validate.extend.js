//继承
$.extend($.validator.defaults, {
	 errorPlacement : function(promptText, caller) {
		 	promptText.attr("rel",$(caller).attr("name"));
        	if(promptText.html() !=''){
        		if($("."+$(caller).attr("name")).size() == 0){
	        		var divFormError = document.createElement('div');
		    		var formErrorContent = document.createElement('div');
		    		var arrow = document.createElement('div');
		    		
		    		$(divFormError).addClass("formError");
		    		$(divFormError).addClass($(caller).attr("name"));
		    		$(formErrorContent).addClass("formErrorContent");
		    		$(arrow).addClass("formErrorArrow");

		    		$("body").append(divFormError);
		    		$(divFormError).append(arrow);
		    		$(divFormError).append(formErrorContent);
		    		$(arrow).html('<div class="line10"></div><div class="line9"></div><div class="line8"></div><div class="line7"></div><div class="line6"></div><div class="line5"></div><div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div>');
		    		$(formErrorContent).html(promptText);
		    	
		    		callerTopPosition = $(caller).offset().top;
		    		callerleftPosition = $(caller).offset().left;
		    		callerWidth =  $(caller).width();
		    		callerHeight =  $(caller).height();
		    		inputHeight = $(divFormError).height();

		    		callerleftPosition = callerleftPosition + callerWidth -30;
		    		callerTopPosition = callerTopPosition  -inputHeight -10;
		    	
		    		$(divFormError).css({
		    			top:callerTopPosition,
		    			left:callerleftPosition,
		    			opacity:0
		    		});
		    		$(divFormError).fadeTo("fast",0.8);
	        	} else {
	        		updateThisPrompt =  $(caller).attr("name");
	        		$("."+updateThisPrompt).find(".formErrorContent").html(promptText);
	        		
	        		callerTopPosition  = $(caller).offset().top;
	        		inputHeight = $("."+updateThisPrompt).height();
	        		
	        		callerTopPosition = callerTopPosition  -inputHeight -10;
	        		$("."+updateThisPrompt).animate({
	        			top:callerTopPosition
	        		});
	        	}
        	}
        	
        },
        success : function(lbl){
        	closingPrompt = lbl.attr("rel");
    		$("."+closingPrompt).fadeTo("fast",0,function(){
    			$("."+closingPrompt).remove();
    		});
        }
});