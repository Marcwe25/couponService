// Constructor function for customer beans

function Customer(id, custName, coupons) {
    this.id = id ;
    this.custName = custName;
    this.coupons = coupons;

// 	boolean value to make customer's field editable
    this.edit = false;
}