# Nhật ký công việc - Đăng nhập & Đăng ký (Flutter + Spring Boot)
*Ngày: 25/05/2026*

## 1. Backend (Spring Boot)
- **Cấu hình Database**: Thiết lập kết nối cơ sở dữ liệu và cấu trúc các bảng (`User`, `Role`, `Provider`).
- **Bảo mật (Security)**: Mở khóa các endpoint `/api/auth/**` trong `SecurityConfig` và tắt CSRF.
- **Xử lý Logic (AuthService & AuthController)**:
  - Viết API Đăng ký (`/register`) và Đăng nhập (`/login`) bằng Email/Mật khẩu truyền thống.
  - Viết API Đăng nhập/Đăng ký bằng Google (`/google`). Xác thực Token qua thư viện Google API.
  - Viết API Đăng nhập/Đăng ký bằng Facebook (`/facebook`). Xác thực Token qua Graph API của Facebook.
  - Xử lý các ngoại lệ trả về thông báo lỗi chuẩn Tiếng Việt (UTF-8) như: *"Tài khoản chưa được đăng ký! Vui lòng đăng ký trước."* hoặc *"Tài khoản đã tồn tại"*.

## 2. Frontend (Flutter)
- **Sửa lỗi cấu hình thư viện Google Sign-In**: 
  - Cập nhật đúng chuẩn cú pháp của `google_sign_in: ^7.2.0` (`GoogleSignIn.instance.authenticate()` và `.initialize()`).
  - Cung cấp Client ID và Server Client ID vào `main.dart` để sửa dứt điểm lỗi khởi tạo trên Android và giúp đăng nhập bằng Google nhanh, mượt mà hơn.
- **Trang Đăng ký (`register_screen.dart`)**:
  - Giao diện chuẩn xác theo thiết kế. 
  - **Luồng hoạt động**: Đăng ký thành công (dù bằng Email hay Social) đều bắt buộc chuyển hướng về trang Đăng nhập.
- **Trang Đăng nhập (`login_screen.dart`)**:
  - Tái cấu trúc layout siêu chuẩn xác (Thêm "Forgot password", dời cụm nút Social xuống cuối trang bằng `Spacer`, đặt "Don't have an account?" nằm ngay dưới nút Login).
  - Bắt lỗi khi đăng nhập bằng Google/Facebook: Nếu tài khoản chưa từng đăng ký, App sẽ đọc chuẩn xác lỗi từ server, báo cho người dùng và chuyển hướng tự động sang Trang Đăng ký.
- **Trang Chủ (`home_screen.dart`)**:
  - Dựng Giao diện **Fashion Sale** đẳng cấp (Banner Hero cực lớn, danh sách hàng mới vuốt ngang, thiết kế bo góc kèm nhãn "NEW").
  - Tích hợp **Bottom Navigation Bar** gồm 5 Tabs: Home, Shop, Bag, Favorites, Profile.
  - **Tab Profile** chứa nút Log out. Bấm vào sẽ gọi lệnh `disconnect()` dọn dẹp phiên đăng nhập Google/Facebook và đá văng về màn hình Đăng nhập.

## 3. Lời nhắn cho AI / Lần làm việc tiếp theo
- **Dành cho AI**: Trước khi code tính năng mới, vui lòng lấy ngữ cảnh từ file này. Hệ thống Xác thực (Authentication) cơ bản đã làm xong và hoạt động trơn tru. Giao diện cũng đã được dựng chuẩn theo thiết kế.
- **Định hướng tiếp theo**: Có thể bắt đầu phát triển các module khác như Gọi API để lấy Danh sách sản phẩm động (thay vì dữ liệu giả ở Trang Chủ), Giỏ hàng, hoặc trang Profile chi tiết.

---
*Ngày: 01/06/2026*

## 4. Cập nhật hệ thống & Cải thiện UI (Frontend & Backend)
- **Sửa lỗi Đăng nhập Facebook**:
  - Khắc phục sự cố đăng nhập Facebook bị lỗi sau khi reload app. Cập nhật lại thư viện `flutter_facebook_auth` và cấu hình token hợp lệ để kết nối backend hoàn hảo.
- **Phát triển Giao diện Danh mục (Catalog)**:
  - Hoàn thiện giao diện **Catalog 1 (List View)** và **Catalog 2 (Grid View)** chuẩn xác đến từng pixel theo thiết kế Figma.
  - Tích hợp nút **Toggle (chuyển đổi)** cho phép người dùng lật qua lại giữa dạng danh sách và dạng lưới cực kỳ mượt mà.
  - Tinh chỉnh tự động thay đổi Header (Tiêu đề bự ở List View và Tiêu đề nhỏ ở Grid View).
- **Hoàn thiện UI Categories**:
  - Chỉnh sửa khoảng cách (padding/margin) ở mục Accessories sao cho đều đặn với các danh mục khác.
  - Đổi màu nút bấm `VIEW ALL ITEMS` sang màu trắng để nổi bật trên nền đỏ.
- **Cập nhật Dữ liệu Mẫu (DataSeeder)**:
  - Phục hồi lại các danh mục `Men` và `Kids` trên thanh Tabs theo đúng thiết kế.
  - Mở rộng thêm 12 sản phẩm mới cho các nhánh **Tops** và **Shirts & Blouses** trong danh mục Women.
  - Dùng AI để tạo và sử dụng các hình ảnh Unsplash miễn phí chất lượng cao nhằm đảm bảo **tất cả 15+ sản phẩm đều có hình ảnh thời trang riêng biệt, không trùng lặp**.

---
*Ngày: 03/06/2026*

## 5. Cập nhật Hình ảnh AI & Tái cấu trúc Trang Chủ
- **Tạo ảnh sản phẩm bằng AI**:
  - Khắc phục lỗi ảnh bị lặp lại hoặc sai ảnh trong trang Chi tiết Sản phẩm.
  - Sử dụng AI để tạo 3 góc ảnh chuẩn xác (Mặt trước, Mặt bên, Cận cảnh) cho 5 sản phẩm bán chạy nhất thuộc danh mục Tops (áo nữ) và 2 ảnh cho Modern New Shirt. Đã sao chép đè trực tiếp các file ảnh này vào `assets/images` của ứng dụng Flutter.
  - *Lưu ý*: Việc tạo ảnh liên tục đã làm cạn kiệt hạn mức (Quota) của hệ thống tạo ảnh, cần chờ vài tiếng để reset trước khi tạo ảnh cho các sản phẩm còn lại.
- **Tái cấu trúc giao diện Trang Chủ (`home_screen.dart`)**:
  - Chuyển đổi từ màn hình cuộn từng trang (`PageView` cuộn dọc) sang một màn hình cuộn tự do (`SingleChildScrollView`).
  - Gộp 3 cụm banner ("Fashion sale", "Street clothes", "New collection") thành một Slideshow vuốt ngang ở trên cùng, tự động tính toán kích thước để chiếm đúng 2/3 chiều cao màn hình.
  - Di chuyển các danh sách sản phẩm ("Sale" và "New") xuống phía dưới Slideshow, rút ngắn khoảng cách hiển thị để UI trông gọn gàng và tối ưu trải nghiệm hơn.

---
*Ngày: 08/06/2026*

## 6. Cập nhật Giao diện & Kiểm tra Server (Frontend & Backend)
- **Kiểm tra và khởi chạy Backend**:
  - Đọc và phân tích cấu trúc dự án Spring Boot, cấu hình kết nối PostgreSQL.
  - Khởi chạy thành công Backend tại cổng 8080. Đảm bảo kết nối cơ sở dữ liệu ổn định và seed dữ liệu đúng theo yêu cầu dự án.
- **Sửa UI Trang Rating & Reviews (`rating_reviews_screen.dart`)**:
  - Sửa lỗi hiển thị thanh công cụ cuộn (SliverAppBar).
  - Tích hợp `LayoutBuilder` bao quanh `FlexibleSpaceBar` để tự động tính toán chiều cao cuộn trang.
  - Giữ nguyên thiết kế tiêu đề "Rating&Reviews" ở góc trái khi bung rộng, và tự động chuyển sang căn giữa trang (center) cực mượt khi thu nhỏ thanh cuộn lại.
