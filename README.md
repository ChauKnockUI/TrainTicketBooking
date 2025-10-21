# 🚆 Distributed Train Ticket Booking System (RMI)

A **distributed multi-server train ticket booking system** developed with **Java RMI**, implementing a **Client/Server architecture** and **MySQL Master-Master Replication** to ensure data consistency and prevent duplicate ticket sales across multiple servers.

---

## ⚙️ Tech Stack

| Category | Technology |
|-----------|-------------|
| **Language** | Java 17 |
| **GUI Framework** | JavaFX |
| **Networking** | Java RMI |
| **Database** | MySQL (Master-Master Replication) |
| **Build Tool** | Maven |
| **IDE** | IntelliJ IDEA |
| **Operating Systems** | Windows / Ubuntu |

---

## 🧩 Features

### 👩‍💼 Ticket Seller
- Login / Logout  
- Search train routes  
- Book and cancel tickets  
- Manage and view invoices  

### 🧑‍💻 Administrator
- Manage stations, routes, trains, and schedules  
- Create and update train information  
- View all invoices and revenue reports  

### 🔄 System
- Real-time data synchronization across multiple servers via MySQL replication  
- Prevents seat duplication and data inconsistency  

---

## 🗄️ Database Overview

**Main Tables:**  
`hoadon`, `chitiethoadon`, `ga`, `tau`, `toa`, `ghe`, `loaighe`, `banggia`, `giotau`, `tuyen`, `nhanvien`

> MySQL Master-Master Replication ensures all servers share consistent ticket data in real time.

---

## 🖼️ Demo Screenshots

> *(Replace with your actual image paths if stored locally, e.g. `/images/` folder)*

### 🔍 Train Search UI  
![Train Search UI](https://github.com/user-attachments/assets/af9073be-5a46-4926-b014-66a0cbdb0271)

### 💺 Seat Selection UI  
![Seat Selection UI](https://github.com/user-attachments/assets/4d2bdbdf-866e-4224-9f6b-3824106c3319)

### 🧾 Admin Dashboard  
![Admin Dashboard](https://github.com/user-attachments/assets/68654e8d-6b2b-442d-bef6-6ee6c1037b17)

### 🚆 Create Train (Admin)  
![Admin Create Train](https://github.com/user-attachments/assets/41b7e79c-adfb-45e9-a42d-a2948111da1c)

### 📊 Revenue Statistics  
![Admin Statistic 1](https://github.com/user-attachments/assets/4b49d07f-b056-49db-a69e-29553f4bcd5b)  
![Admin Statistic 2](https://github.com/user-attachments/assets/acd82fd4-422a-4325-bab2-e3d7a7c123a5)

---
