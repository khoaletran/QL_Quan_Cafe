package Model;

public class MaGiamGia {
	private String maGiam;
	private int giamGia;
	
	public MaGiamGia(String maGiam,int giamGia) {
		
	}

	public String getMaGiam() {
		return maGiam;
	}

	public void setMaGiam(String maGiam) {
		if(maGiam == null || !maGiam.matches("^[a-zA-Z]{10}")) {
    		throw new IllegalArgumentException("Ma khách hàng sai định dạng.");
    	}
    	this.maGiam = maGiam;
	}

	public int getGiamGia() {
		return giamGia;
	}

	public void setGiamGia(int giamGia) {
		if(giamGia > 0 && giamGia < 101) {
			throw new IllegalArgumentException("Ma khách hàng sai định dạng.");
		}
		this.giamGia = giamGia;
	}
	
	
}
