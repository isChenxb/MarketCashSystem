package model.vo;

public class ProductVo {
	private String product_no;
	private String product_name;
	private String product_from;
	private float product_price;
	
	
	public ProductVo(String product_no,String product_name,float product_price,String product_from){
		this.setProduct_no(product_no);
		this.setProduct_name(product_name);
		this.setProduct_price(product_price);
		this.setProduct_from(product_from);
	}


	public String getProduct_no() {
		return product_no;
	}


	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}


	public String getProduct_name() {
		return product_name;
	}


	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}


	public String getProduct_from() {
		return product_from;
	}


	public void setProduct_from(String product_from) {
		this.product_from = product_from;
	}


	public float getProduct_price() {
		return product_price;
	}


	public void setProduct_price(float product_price) {
		this.product_price = product_price;
	}


	@Override
	public String toString() {
		return "ProductVo [product_no=" + product_no + ", product_name="
				+ product_name + ", product_from=" + product_from
				+ ", product_price=" + product_price + "]";
	}
	
	
	
}
	