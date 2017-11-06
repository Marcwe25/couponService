function Coupon(id, title, start_date,end_date,amount,type,message,price,image) {
    this.id = id ;				
    this.title = title;						//1
    this.start_date = start_date;	//2
    this.end_date = end_date;		//3
    this.amount = amount;					//4
    this.type = type;						//5
    this.message = message;					//6
    this.price = price;						//7
    this.image = image;						//8

    this.edit = false;
}