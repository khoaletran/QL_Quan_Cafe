package Model;

public class MaGiamGia {
	private String maGiam;
	private int giamGia;
	
	// Constructor đầy đủ
	public MaGiamGia(String maGiam, int giamGia) {
		setMaGiam(maGiam);
		setGiamGia(giamGia);
	}

	// Getter & Setter với kiểm tra hợp lệ
	public String getMaGiam() {
		return maGiam;
	}

	public void setMaGiam(String maGiam) {
		if (maGiam == null || !maGiam.matches("^[a-zA-Z]{10}$")) {
			throw new IllegalArgumentException("Mã giảm giá sai định dạng. Phải gồm 10 chữ cái.");
		}
		this.maGiam = maGiam;
	}

	public int getGiamGia() {
		return giamGia;
	}

	public void setGiamGia(int giamGia) {
		if (giamGia <= 0 || giamGia > 100) {
			throw new IllegalArgumentException("Giảm giá phải nằm trong khoảng 1 - 100%.");
		}
		this.giamGia = giamGia;
	}

	@Override
	public String toString() {
		return "MaGiamGia [maGiam=" + maGiam + ", giamGia=" + giamGia + "%]";
	}
}
