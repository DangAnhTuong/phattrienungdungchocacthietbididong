# Vibe Coding Memory

## LUẬT BẮT BUỘC CHO AI (AI CODING RULES)
1. **Tuyệt đối bám sát ảnh mẫu**: Khi code UI, phải LUÔN LUÔN xem xét kỹ lưỡng ảnh thiết kế (cung cấp trong chat hoặc trong thư mục `docs/designs/`).
2. **Quy trình Code & Tự Review liên tục (Luật Bắt Buộc)**: Mỗi lần thực hiện code tính năng trang mới hay sửa lỗi bất kỳ, BẮT BUỘC phải tuân theo quy trình: Code -> Tự Review -> Code lại -> Tự Review liên tục cho đến khi ĐÚNG NHẤT VỚI ẢNH MẪU hoặc YÊU CẦU ĐẦU VÀO của người dùng. Tuyệt đối không được bỏ sót bất kỳ chi tiết nào (màu sắc, kích thước, icon, vị trí, khoảng cách). Tự so sánh từng pixel trước khi báo cáo hoàn thành.
3. **Quy tắc về hình ảnh**:
   - Nếu không có ảnh chuẩn trong dự án, được quyền tự dùng AI (`generate_image`) để tạo ảnh thay thế.
   - ẢNH TẠO RA PHẢI CHUẨN XÁC VỀ THÔNG TIN: đúng với TÊN SẢN PHẨM, đúng MÀU SẮC, và đúng LOẠI SẢN PHẨM mô tả. Không được dùng 1 ảnh cho nhiều sản phẩm khác nhau.
   - **LƯU Ý VỀ DUNG LƯỢNG (MỚI)**: Khi dùng AI sinh ảnh, KHÔNG CẦN SINH ẢNH CHẤT LƯỢNG CAO (High Quality). Chỉ cần ảnh đủ rõ để người dùng nhận diện được sản phẩm là gì. Mục đích là để tiết kiệm dung lượng, tránh làm nặng thư mục project.
   - **Ảnh Product Detail (MỚI)**: Tại trang chi tiết sản phẩm, MỖI SẢN PHẨM cần có tối thiểu 3 ảnh khác nhau (các góc độ khác nhau hoặc có ma nơ canh). Khi quota AI phục hồi, cần generate đủ 3 ảnh cho mỗi sản phẩm để PageView hiển thị.
## Current Status
- **Date**: 2026-06-09
- **Phase**: Favorites UI Re-implementation & Backend Integration Fixes
- **State**: The Favorites Screen UI (`favorites_screen.dart`) is now perfectly synced with `Lists.png` and `Favorites - Modules` design. Bug fixes applied to Favorites API logic. `flutter analyze` shows NO ISSUES.

## Changes Made Today (2026-06-09)
- **Backend & Logic Fixes**:
  - Fixed `Add to Favorites` failure (HTTP 403) by correcting token lookup key in `favorite_service.dart` from `auth_token` to `jwt_token`.
  - Fixed HTTP 404 Image load errors by updating `FavoriteService.java` to not append `baseUrl` to `assets/...` image paths, and added logic in `favorites_screen.dart` to conditionally use `Image.network` or `Image.asset`.
- **UI Perfecting (Favorites Screen)**:
  - Reconstructed `_buildListView` to precisely match the target design `Lists.png`: 
    - Added large left-aligned "Favorites" title below standard AppBar.
    - Rendered `discountTag` (-30% red pill) and `isNewBadge` (NEW black pill) overlapping the product image.
    - Fine-tuned Star rating alignment to match design.
    - Added the red shopping bag icon to the bottom right of the list card, ensuring `Stack` is wrapped in `Padding` so it overlaps the card border without clipping into the next list item.
  - Ensured that `isGridView` properly switches the AppBar header layout (centered small title vs large left-aligned title).
  - Maintained strict self-review loop to ensure 100% pixel-perfect adherence to the user's provided `Lists.png`.

## Next Steps (Nhiệm vụ cho ngày mai)
- **Hoàn thiện dữ liệu DataSeeder cho mục Shop > Women > New**:
  - Ghi chú quan trọng: Danh mục "Tops" ở đây mang ý nghĩa là "Top sản phẩm bán chạy" (Bestsellers) chứ không chỉ đơn thuần là "áo".
  - Nhiệm vụ 1: Bổ sung thêm dữ liệu sản phẩm cho các tab đang bị trống (Knitwear, Blazers, Pants, Jeans, Shorts, Skirts...).
  - Nhiệm vụ 2: Thiết lập lại quan hệ danh mục (categories). Các sản phẩm nổi bật nằm ở "Knitwear", "Blazers", "Pants"... cũng phải được gán thêm vào danh mục "Tops" để khi bấm vào tab nào cũng có sản phẩm, và tab "Tops" sẽ là nơi tổng hợp các món đồ bán chạy nhất của các tab kia.
- Kết nối API/Logic giao diện (State Management).
