package test;

import java.util.ArrayList;

import Dao.NhanVien_DAO;
import Model.NhanVien;

public class TestNhanVienDAO {
    public static void main(String[] args) {
        // Khởi tạo đối tượng NhanVien_DAO
        NhanVien_DAO nhanVienDAO = new NhanVien_DAO();

        // Lấy danh sách nhân viên từ cơ sở dữ liệu
        ArrayList<NhanVien> dsNhanVien = nhanVienDAO.getAllNhanVien();

        // In thông tin của từng nhân viên
        for (NhanVien nv : dsNhanVien) {
            System.out.println(nv);  // Gọi phương thức toString() của NhanVien để hiển thị thông tin
        }
    }
}
