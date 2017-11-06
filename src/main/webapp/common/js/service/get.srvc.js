(function()
		{
	var module = angular.module("couponAdmin");
	
	module.service("getterService", getterServiceCtor);
	
	function getterServiceCtor($http,typeService,loginService) {
		var self = this;
		this.dataArray=[];
		
		this.type = typeService.type;

		this.setData = function(somePromise){
			self.dataArray.length=0;
			if(self.type.targetType!="coupon"){
			for(var i=0; i<somePromise.data.length;i++)
				{
				self.dataArray[i]=somePromise.data[i];
				}}
			else{
				var newPromise = somePromise.data.map(function(x){
					x.start_date = new Date(x.start_date);
					x.end_date = new Date(x.end_date);
					return x;
				});
				
				for(var i=0; i<newPromise.length;i++)
				{
				self.dataArray[i]=newPromise[i];
				}
			}
		}
		
		this.position = function(id){
			for (var i = 0; i < this.dataArray.length; i++) {
				if(this.dataArray[i].id==id) return i;
			}
			return -1;
		}
		
		this.removeItem = function(itemID){
						this.x = this.position(itemID);
						if(this.x>-1){
							this.dataArray.splice(this.x,1);}					
					}
		
		this.getDataArray = function(){
		if(typeService.types.indexOf(typeService.type.targetType)==-1)
				{
			return;}
		$http.get("http://localhost:8080/couponService/webapi/"+this.type.clientType+"/"+this.type.targetType).then(
				function(promise){
					if(promise.status=='205'){loginService.reaplyForLogin()}
					else if(promise.status=='202'){
						self.setData(promise);}
		},
				function(promise,error, status){loginService.reaplyForLogin();
		})
		}
		
	}
		})();
