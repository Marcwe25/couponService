// constructor function for company beans
function Company(id, name, email, coupons) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.coupons = coupons

// 	boolean value to make company's field editable
    this.edit = false;
}