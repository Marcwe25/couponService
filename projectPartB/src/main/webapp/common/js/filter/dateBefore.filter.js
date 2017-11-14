(function(){

var module = angular.module("couponAdmin");

var myComparator = function(i,items,a,someController){
	if (someController.cfSwitch[a]){
		return new Date(items[i][a]) >= someController.couponFilter[a];
	}
	else {return (new Date(items[i][a]) < someController.couponFilter[a]);}
}



module.filter("dateBefore", function() {
	  return function(items, from , theController) {
		  if(theController.couponFilter[from]=="" || !angular.isDate(theController.couponFilter[from]) )return items;

	        var result = [];        
	        for (var i=0; i<items.length; i++){
	            if (myComparator(i,items,from,theController))  {
	                result.push(items[i]);
	            }
	        }            
	        return result;
	  };
	});

})()