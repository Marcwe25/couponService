(function()
		{
	var module = angular.module("couponAdmin");
	
	module.service("checkerService", checkerServiceCtor);
	
	function checkerServiceCtor($http,typeService) {
		
		this.error = {message: "all field must be filled" ,isBad: false};
		
		this.check = function(data){
			switch (typeService.type.targetType) {
		    case "company":
		    	this.error.isBad = data.name=="";
		        break;
		    case "customer":
		    	this.error.isBad = data.custName=="";
		    	break;
		    case "coupon":
		    	this.error.isBad = data.title==""||
	    	 	data.start_date==""||
	    	 	data.end_date==""||
	    	 	data.amount==""||
	    	 	data.type==""||
	    	 	data.message==""||
	    	 	data.price==""||
	    	 	data.image=="";
		    	if(this.error.isBad){
		    		this.error.message = "all field must be filled, amount and price must be number";
		    		return;
		    	}
		    	if(data.start_date>data.end_date){
		    		this.error.message = "start date must be before end date";
		    		this.error.isBad = true;
		    		return;
		    	}
		    	this.date = new Date();
		    	if(data.end_date<this.date){
		    		this.error.message = "end date already passed";
		    		this.error.isBad = true;
		    		return;
		    	}
	    		this.error.message = "all field must be filled";
		    	return;	

		    default:
		        break;
		}
		}
		
		this.setDuplicateError = function(){
			if(typeService.type.targetType==='coupon'){
				this.error.message = "this title is already used, please choose another."
				this.error.isBad = true;
				
				}
			else{
				this.error.message = "the name is already used, please choose another one."
				this.error.isBad = true;
				}
			}
		this.setIncompleteError = function(){
				this.error.message = "please fill all fields"
				this.error.isBad = true;
		}
	}
		})();