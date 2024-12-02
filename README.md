# 🚂 Train Ticket Booking System 🎟️  

> **Hệ thống đặt vé tàu trực tuyến với giao diện JavaFX và giao tiếp qua RMI.**  
> Quản lý hiệu quả thông tin chuyến tàu, toa tàu, ghế ngồi và trạng thái ghế trong thời gian thực.

---

## 🌟 Tính năng nổi bật

- 🕵️ **Tìm kiếm chuyến tàu** theo ga khởi hành và ga đến.  
- 📅 **Hiển thị thông tin chi tiết** chuyến tàu: thời gian đi, đến và giá vé.  
- 💺 **Quản lý ghế ngồi**: trạng thái (còn trống, đã đặt, đang chọn).  
- 🔧 **Kiến trúc phân tán**: sử dụng RMI để đồng bộ dữ liệu giữa client và server.  

---

## 📂 Cấu trúc thư mục

```plaintext
TrainTicketBooking/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controllers/     # Bộ điều khiển giao diện JavaFX
│   │   │   ├── models/          # Lớp mô hình (Train, Seat, v.v.)
│   │   │   ├── services/        # Interface và triển khai RMI
│   │   │   └── utils/           # Các lớp tiện ích (kết nối RMI, xử lý dữ liệu)
│   │   └── resources/
│   │       └── fxml/            # File FXML cho giao diện
├── tests/                       # Bộ kiểm thử
└── README.md                    # Hướng dẫn dự án
---
```
## 📦 Cài đặt

### Yêu cầu hệ thống

| Thành phần      | Phiên bản đề xuất |
|------------------|-------------------|
| **Java**         | 11 trở lên        |
| **JavaFX**       | 17 trở lên        |
| **MySQL Server** | 8.0 hoặc mới hơn |

---
