
-- Execute Theo từng bước đã đánh Giấu bên dưới 
--Bước 1 
CREATE DATABASE QL_QUANCAFE;

--Bước 2
USE QL_QUANCAFE;

--Bước 3 
CREATE TABLE NHANVIEN (
    MANV VARCHAR(50) PRIMARY KEY,
    TENNV NVARCHAR(100),
    DIACHI NVARCHAR(255),
	NGAYVAOLAM DATE,
    GIOITINH BIT,
    SDT VARCHAR(20),
    MATKHAU NVARCHAR(255)
);

CREATE TABLE LOAIKHACHHANG (
    MALKH VARCHAR(50) PRIMARY KEY,
    TENLKH NVARCHAR(100),
    GIAMGIA INT,
    MUCDIEM INT
);

CREATE TABLE KHACHHANG (
    MAKH VARCHAR(50) PRIMARY KEY,
    MALKH VARCHAR(50),
    TENKH NVARCHAR(100),
    SDT VARCHAR(20),
    DIEMTL INT,
    FOREIGN KEY (MALKH) REFERENCES LOAIKHACHHANG(MALKH)
);

CREATE TABLE LOAIHANGHOA (
    MALH VARCHAR(50) PRIMARY KEY,
    TENLH NVARCHAR(100),
    MOTA NVARCHAR(MAX)
);

CREATE TABLE HANGHOA (
    MAHH VARCHAR(50) PRIMARY KEY,
    MALH VARCHAR(50),
    TENHH NVARCHAR(100),
    HINHANH NVARCHAR(255),
    GIASP DECIMAL(18,2),
    FOREIGN KEY (MALH) REFERENCES LOAIHANGHOA(MALH)
);

CREATE TABLE HOADONBANHANG (
    MAHDBH VARCHAR(50) PRIMARY KEY,
    MANV VARCHAR(50),
    MAKH VARCHAR(50),
    NGAYHDBH DATE,
    TONGTIEN DECIMAL(18,2),
    DIEMTL INT,
    GIAMGIA INT,
    FOREIGN KEY (MANV) REFERENCES NHANVIEN(MANV),
    FOREIGN KEY (MAKH) REFERENCES KHACHHANG(MAKH)
);

CREATE TABLE CHITIETHOADON (
    MACT_HD INT IDENTITY(1,1) PRIMARY KEY,
    MAHDBH VARCHAR(50),
    MAHH VARCHAR(50),
    SOLUONG INT,
    THANHTIEN DECIMAL(18,2),
    FOREIGN KEY (MAHDBH) REFERENCES HOADONBANHANG(MAHDBH),
    FOREIGN KEY (MAHH) REFERENCES HANGHOA(MAHH)
);

CREATE TABLE MAGIAMGIA (
    MAGIAM VARCHAR(50),
    GIAMGIA INT
);

--Bước 4

--Tao user de vao DB
create login QLQuanCafe with password = '123'
create user QLQuanCafe for login QLQuanCafe
create role role_QuanCafe
grant insert, select, update, delete
to role_QuanCafe
exec sp_addrolemember role_QuanCafe, QLQuanCafe;

--Bước 5

CREATE TRIGGER TRG_TAO_MANV
ON NHANVIEN
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @MAX_MA VARCHAR(50), @NEXT_NUM INT, @NEW_MA VARCHAR(50);
    DECLARE @TENNV NVARCHAR(100), @DIACHI NVARCHAR(255), @GIOITINH BIT,
            @NGAYVAOLAM DATE, @SDT VARCHAR(20), @MATKHAU NVARCHAR(255);

    -- Lấy dòng cần insert (giả sử mỗi lần chỉ insert 1 dòng)
    SELECT TOP 1
        @TENNV = TENNV,
        @DIACHI = DIACHI,
        @GIOITINH = GIOITINH,
        @NGAYVAOLAM = NGAYVAOLAM,
        @SDT = SDT,
        @MATKHAU = MATKHAU
    FROM INSERTED;

    -- Tìm mã lớn nhất hiện có trong bảng
    SELECT @MAX_MA = MAX(MANV) FROM NHANVIEN WHERE MANV LIKE 'NV[0-9][0-9][0-9][0-9]';

    -- Nếu chưa có thì bắt đầu từ 1
    IF @MAX_MA IS NULL
        SET @NEXT_NUM = 1;
    ELSE
        SET @NEXT_NUM = CAST(RIGHT(@MAX_MA, 4) AS INT) + 1;

    -- Tạo mã mới
    SET @NEW_MA = 'NV' + RIGHT('0000' + CAST(@NEXT_NUM AS VARCHAR), 4);

    -- Thêm nhân viên với mã mới
    INSERT INTO NHANVIEN (MANV, TENNV, DIACHI, GIOITINH, NGAYVAOLAM, SDT, MATKHAU)
    VALUES (@NEW_MA, @TENNV, @DIACHI, @GIOITINH, @NGAYVAOLAM, @SDT, @MATKHAU);
END;
GO

CREATE TRIGGER TRG_TAO_MAKH
ON KHACHHANG
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @MAX_MA VARCHAR(50), @NEXT_NUM INT, @NEW_MA VARCHAR(50);
    DECLARE @MALKH VARCHAR(50), @TENKH NVARCHAR(100), @SDT VARCHAR(20), @DIEMTL INT;

    SELECT TOP 1 @MALKH = MALKH, @TENKH = TENKH, @SDT = SDT, @DIEMTL = DIEMTL FROM INSERTED;

    SELECT @MAX_MA = MAX(MAKH) FROM KHACHHANG WHERE MAKH LIKE 'KH[0-9][0-9][0-9][0-9]';

    IF @MAX_MA IS NULL SET @NEXT_NUM = 1;
    ELSE SET @NEXT_NUM = CAST(RIGHT(@MAX_MA, 4) AS INT) + 1;

    SET @NEW_MA = 'KH' + RIGHT('0000' + CAST(@NEXT_NUM AS VARCHAR), 4);

    INSERT INTO KHACHHANG (MAKH, MALKH, TENKH, SDT, DIEMTL)
    VALUES (@NEW_MA, @MALKH, @TENKH, @SDT, @DIEMTL);
END;
GO

CREATE TRIGGER TRG_TAO_MAHH
ON HANGHOA
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @MAX_MA VARCHAR(50), @NEXT_NUM INT, @NEW_MA VARCHAR(50);
    DECLARE @MALH VARCHAR(50), @TENHH NVARCHAR(100), @HINHANH NVARCHAR(255), @GIASP DECIMAL(18,2);

    SELECT TOP 1 @MALH = MALH, @TENHH = TENHH, @HINHANH = HINHANH, @GIASP = GIASP FROM INSERTED;

    SELECT @MAX_MA = MAX(MAHH) FROM HANGHOA WHERE MAHH LIKE 'HH[0-9][0-9][0-9][0-9]';

    IF @MAX_MA IS NULL SET @NEXT_NUM = 1;
    ELSE SET @NEXT_NUM = CAST(RIGHT(@MAX_MA, 4) AS INT) + 1;

    SET @NEW_MA = 'HH' + RIGHT('0000' + CAST(@NEXT_NUM AS VARCHAR), 4);

    INSERT INTO HANGHOA (MAHH, MALH, TENHH, HINHANH, GIASP)
    VALUES (@NEW_MA, @MALH, @TENHH, @HINHANH, @GIASP);
END;
GO

CREATE TRIGGER TRG_TAO_MALKH
ON LOAIKHACHHANG
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @MAX_MA VARCHAR(50), @NEXT_NUM INT, @NEW_MA VARCHAR(50);
    DECLARE @TENLKH NVARCHAR(100), @GIAMGIA INT;

    SELECT TOP 1 @TENLKH = TENLKH, @GIAMGIA = GIAMGIA FROM INSERTED;

    SELECT @MAX_MA = MAX(MALKH) FROM LOAIKHACHHANG WHERE MALKH LIKE 'LKH[0-9][0-9][0-9][0-9]';

    IF @MAX_MA IS NULL SET @NEXT_NUM = 1;
    ELSE SET @NEXT_NUM = CAST(RIGHT(@MAX_MA, 4) AS INT) + 1;

    SET @NEW_MA = 'LKH' + RIGHT('0000' + CAST(@NEXT_NUM AS VARCHAR), 4);

    INSERT INTO LOAIKHACHHANG (MALKH, TENLKH, GIAMGIA)
    VALUES (@NEW_MA, @TENLKH, @GIAMGIA);
END;
GO

CREATE TRIGGER TRG_TAO_MALH
ON LOAIHANGHOA
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @MAX_MA VARCHAR(50), @NEXT_NUM INT, @NEW_MA VARCHAR(50);
    DECLARE @TENLH NVARCHAR(100), @MOTA NVARCHAR(MAX);

    SELECT TOP 1 @TENLH = TENLH, @MOTA = MOTA FROM INSERTED;

    SELECT @MAX_MA = MAX(MALH) FROM LOAIHANGHOA WHERE MALH LIKE 'LH[0-9][0-9][0-9][0-9]';

    IF @MAX_MA IS NULL SET @NEXT_NUM = 1;
    ELSE SET @NEXT_NUM = CAST(RIGHT(@MAX_MA, 4) AS INT) + 1;

    SET @NEW_MA = 'LH' + RIGHT('0000' + CAST(@NEXT_NUM AS VARCHAR), 4);

    INSERT INTO LOAIHANGHOA (MALH, TENLH, MOTA)
    VALUES (@NEW_MA, @TENLH, @MOTA);
END;
GO

--Dữ liệu demo 

INSERT INTO NHANVIEN (TENNV, DIACHI, NGAYVAOLAM, GIOITINH, SDT, MATKHAU)
VALUES
(N'Nguyễn Văn A', N'Quận 1, TP.HCM', '2024-01-01', 1, '0909123456', N'passA');

INSERT INTO NHANVIEN (TENNV, DIACHI, NGAYVAOLAM, GIOITINH, SDT, MATKHAU)
VALUES
(N'Trần Thị B', N'Quận 2, TP.HCM', '2024-02-15', 0, '0909234567', N'passB');

INSERT INTO NHANVIEN (TENNV, DIACHI, NGAYVAOLAM, GIOITINH, SDT, MATKHAU)
VALUES
(N'Lê Văn C', N'Quận 3, TP.HCM', '2024-03-10', 1, '0909345678', N'passC');

INSERT INTO NHANVIEN (TENNV, DIACHI, NGAYVAOLAM, GIOITINH, SDT, MATKHAU)
VALUES
(N'Phạm Thị D', N'Quận 4, TP.HCM', '2024-04-20', 0, '0909456789', N'passD');

INSERT INTO NHANVIEN (TENNV, DIACHI, NGAYVAOLAM, GIOITINH, SDT, MATKHAU)
VALUES
(N'Hoàng Văn E', N'Quận 5, TP.HCM', '2024-05-25', 1, '0909567890', N'passE');

INSERT INTO LOAIKHACHHANG (TENLKH, GIAMGIA, MUCDIEM)
VALUES
(N'Thường', 0, 0);

INSERT INTO LOAIKHACHHANG (TENLKH, GIAMGIA, MUCDIEM)
VALUES
(N'Thân Thiết', 5, 100);

INSERT INTO LOAIKHACHHANG (TENLKH, GIAMGIA, MUCDIEM)
VALUES
(N'Bạc', 10, 300);

INSERT INTO LOAIKHACHHANG (TENLKH, GIAMGIA, MUCDIEM)
VALUES
(N'Vàng', 15, 500);

INSERT INTO LOAIKHACHHANG (TENLKH, GIAMGIA, MUCDIEM)
VALUES
(N'Kim Cương', 20, 1000);

INSERT INTO KHACHHANG (MALKH, TENKH, SDT, DIEMTL)
VALUES
('LKH0001', N'Nguyễn Thị X', '0911123456', 100);

INSERT INTO KHACHHANG (MALKH, TENKH, SDT, DIEMTL)
VALUES
('LKH0002', N'Trần Văn Y', '0911234567', 200);

INSERT INTO KHACHHANG (MALKH, TENKH, SDT, DIEMTL)
VALUES
('LKH0003', N'Lê Thị Z', '0911345678', 300);

INSERT INTO KHACHHANG (MALKH, TENKH, SDT, DIEMTL)
VALUES
('LKH0004', N'Phạm Văn W', '0911456789', 400);

INSERT INTO KHACHHANG (MALKH, TENKH, SDT, DIEMTL)
VALUES
('LKH0005', N'Hoàng Thị V', '0911567890', 500);

INSERT INTO LOAIHANGHOA (TENLH, MOTA)
VALUES
(N'Cà phê', N'Cà phê các loại');

INSERT INTO LOAIHANGHOA (TENLH, MOTA)
VALUES
(N'Trà', N'Trà các loại');

INSERT INTO LOAIHANGHOA (TENLH, MOTA)
VALUES
(N'Nước ép', N'Nước ép trái cây');

INSERT INTO LOAIHANGHOA (TENLH, MOTA)
VALUES
(N'Sinh tố', N'Sinh tố trái cây');

INSERT INTO LOAIHANGHOA (TENLH, MOTA)
VALUES
(N'Bánh ngọt', N'Các loại bánh ngọt');

INSERT INTO HANGHOA (MALH, TENHH, HINHANH, GIASP)
VALUES
('LH0001', N'Cà phê đen', N'Resource/HangHoa/capheden.jpg', 20000);

INSERT INTO HANGHOA (MALH, TENHH, HINHANH, GIASP)
VALUES
('LH0002', N'Trà sữa', N'Resource/HangHoa/TraSua.jpg', 30000);

INSERT INTO HANGHOA (MALH, TENHH, HINHANH, GIASP)
VALUES
('LH0003', N'Nước cam', N'Resource/HangHoa/NuocCam.jpg', 25000);

INSERT INTO HANGHOA (MALH, TENHH, HINHANH, GIASP)
VALUES
('LH0004', N'Sinh tố bơ', N'Resource/HangHoa/SinhToBo.jpg', 35000);

INSERT INTO HANGHOA (MALH, TENHH, HINHANH, GIASP)
VALUES
('LH0005', N'Bánh mì', N'Resource/HangHoa/BanhMi.jpg', 15000);

INSERT INTO MAGIAMGIA (MAGIAM, GIAMGIA)
VALUES
('MGG10', 10);

INSERT INTO MAGIAMGIA (MAGIAM, GIAMGIA)
VALUES
('MGG20', 20);

INSERT INTO MAGIAMGIA (MAGIAM, GIAMGIA)
VALUES
('MGG30', 30);

INSERT INTO MAGIAMGIA (MAGIAM, GIAMGIA)
VALUES
('MGG40', 40);

INSERT INTO MAGIAMGIA (MAGIAM, GIAMGIA)
VALUES
('MGG50', 50);
