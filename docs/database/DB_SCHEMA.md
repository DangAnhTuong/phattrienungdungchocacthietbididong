# Database Architecture (ERD Summary)

Dưới đây là lược đồ cơ sở dữ liệu hoàn chỉnh của hệ thống được trích xuất từ tài liệu của thầy giáo.
Ghi chú: Bảng `roles` đã được tối ưu hóa và gộp thẳng vào `staff_accounts` trong source code thực tế để giảm độ trễ truy vấn (đáp ứng đúng yêu cầu cập nhật mới nhất).

## 1. Hệ thống Quản trị & Nhân sự (Admin & Staff)
- **staff_accounts**: Bảng lưu trữ thông tin nhân viên (Gồm cả First/Last name, Email, Password Hash, Role Name và Privileges).

## 2. Hệ thống Sản phẩm & Danh mục (Products & Categories)
- **categories**: Lưu trữ cây danh mục sản phẩm (Parent-Child category).
- **products**: Lưu trữ thông tin cốt lõi của sản phẩm (SKU, sale_price, compare_price, quantity...).
- **product_categories**: Bảng trung gian n-n giữa Sản phẩm và Danh mục.
- **tags**: Lưu trữ các nhãn phân loại nhanh (New, Sale, Winter...).
- **product_tags**: Bảng trung gian n-n kết nối Sản phẩm với Tags (Được dùng để lọc sản phẩm động trên Home Screen).
- **gallery**: Lưu trữ hình ảnh slide/thumbnail cho từng sản phẩm.

## 3. Hệ thống Biến thể & Thuộc tính (Variants & Attributes)
- **attributes**: Tên thuộc tính (VD: Size, Color).
- **attribute_values**: Các giá trị của thuộc tính (VD: S, M, L, Đỏ, Xanh).
- **product_attributes**: Liên kết Sản phẩm - Thuộc tính.
- **product_attribute_values**: Liên kết Sản phẩm - Giá trị Thuộc tính.
- **variant_options**: Các tùy chọn biến thể tổng hợp.
- **variants** / **variant_values**: Bảng trung gian định nghĩa chính xác biến thể cụ thể của sản phẩm.

## 4. Hệ thống Khách hàng (Customers)
- **customers**: Lưu trữ thông tin người mua hàng.
- **customer_addresses**: Sổ địa chỉ giao hàng của khách.

## 5. Hệ thống Đơn hàng & Giao vận (Orders & Shipping)
- **orders**: Thông tin đơn đặt hàng tổng quát.
- **order_items**: Chi tiết các sản phẩm trong một đơn hàng.
- **order_statuses**: Trạng thái đơn hàng (Đang xử lý, Đang giao...).
- **shipping_zones** / **shipping_rates**: Cấu hình phí ship theo khu vực.
- **product_shipping_info**: Trọng lượng, kích thước đóng gói của sản phẩm.

## 6. Hệ thống Khuyến mãi (Coupons)
- **coupons**: Mã giảm giá tổng.
- **product_coupons**: Mã giảm giá áp dụng riêng cho một số sản phẩm.

## 7. Khác (Misc)
- **slideshows**: Cấu hình các banner quảng cáo chạy trên App.
- **notifications**: Thông báo đẩy.
- **cards** / **card_items**: Giỏ hàng (Cart) của người dùng chưa thanh toán.
- **suppliers** / **product_suppliers**: Quản lý nhà cung cấp.

---
*Bản thiết kế này đóng vai trò là "Single Source of Truth" cho toàn bộ cấu trúc dữ liệu của dự án App Mobile.*
