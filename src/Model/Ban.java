package Model;

/**
 * Lớp Ban đại diện cho một bàn trong hệ thống.
 * Lớp này chứa các thông tin cơ bản của bàn như mã bàn, tên bàn và thuộc tính.
 */
public class Ban {
    // Thuộc tính
    private String maBan;
    private String tenBan;
    private String thuocTinh;

    /**
     * Khởi tạo một đối tượng Ban với các thông tin được cung cấp.
     *
     * @param maBan     Mã bàn
     * @param tenBan    Tên bàn
     * @param thuocTinh Thuộc tính của bàn (ví dụ: "Trống", "Đã đặt")
     */
    public Ban(String maBan, String tenBan, String thuocTinh) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.thuocTinh = thuocTinh;
    }

    // Getter và Setter cho maBan
    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    // Getter và Setter cho tenBan
    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    // Getter và Setter cho thuocTinh
    public String getThuocTinh() {
        return thuocTinh;
    }

    public void setThuocTinh(String thuocTinh) {
        this.thuocTinh = thuocTinh;
    }

    /**
     * Trả về chuỗi biểu diễn thông tin của đối tượng Ban.
     *
     * @return Chuỗi chứa thông tin của bàn
     */
    @Override
    public String toString() {
        return "Ban [maBan=" + maBan + ", tenBan=" + tenBan + ", thuocTinh=" + thuocTinh + "]";
    }
}