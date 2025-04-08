package Model;

/**
 * Lớp Ban đại diện cho một bàn trong hệ thống.
 * Lớp này chứa các thông tin cơ bản của bàn như mã bàn và trạng thái bàn (trống hay không).
 */
public class Ban {
    // Thuộc tính
    private String maBan;
    private boolean thuocTinh; // true: Trống, false: Không trống

    /**
     * Khởi tạo một đối tượng Ban với các thông tin được cung cấp.
     *
     * @param maBan      Mã bàn
     * @param thuocTinh  Trạng thái bàn (true: Trống, false: Không trống)
     */
    public Ban(String maBan, boolean thuocTinh) {
        setMaBan(maBan);
        this.thuocTinh = thuocTinh;
    }

    // Getter và Setter cho maBan
    public String getMaBan() {
        return maBan;
    }

    /**
     * Thiết lập mã bàn cho đối tượng.
     * Mã bàn phải theo định dạng: Bx.xxxx (x là chữ số), ví dụ: B1.1234
     *
     * @param maBan Mã bàn cần thiết lập
     * @throws IllegalArgumentException nếu mã bàn không đúng định dạng
     */
    public void setMaBan(String maBan) {
        if (!maBan.matches("^B\\d\\.\\d{4}$")) {
            throw new IllegalArgumentException("Mã bàn không hợp lệ! Định dạng phải là Bx.xxxx (x là chữ số).");
        }
        this.maBan = maBan;
    }

    // Getter và Setter cho thuocTinh
    public boolean isThuocTinh() {
        return thuocTinh;
    }

    public void setThuocTinh(boolean thuocTinh) {
        this.thuocTinh = thuocTinh;
    }

    /**
     * Trả về chuỗi biểu diễn thông tin của đối tượng Ban.
     *
     * @return Chuỗi chứa thông tin của bàn
     */
    @Override
    public String toString() {
        return "Ban [maBan=" + maBan + ", thuocTinh=" + thuocTinh + "]";
    }
}
