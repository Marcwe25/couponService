(function(){
var module = angular.module("couponAdmin");
module.filter("allBut",function(){
	
	var myComparator = function(i,items,a,someController){
		if (someController.cfSwitch[a]){
			return items[i][a]  === someController.couponFilter[a];
		}
		else return items[i][a] !== someController.couponFilter[a];
	}

	  return function(items, prop, theController) {
		  if(theController.couponFilter[prop]==="" || theController.couponFilter[prop] == null){return items;};
	        var result = [];
	        for (var i=0; i<items.length; i++){
	            if (myComparator(i,items,prop,theController))  {
	                result.push(items[i]);
	            }
	        }  
	        return result;
	  };
	
	  });

})()
