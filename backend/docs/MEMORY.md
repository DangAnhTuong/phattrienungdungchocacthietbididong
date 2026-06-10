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
- **Date**: 2026-06-10
- **Phase**: Project Restructuring & Git Deployment
- **State**: Project is completely restructured into exactly two root directories: `frontend` and `backend`. All documentation files are now inside `backend/`. Git is initialized and successfully pushed. Local git config (`.git/info/exclude`) is handling the gitignores so the project root doesn't contain hidden `.gitignore` files. App source code in `frontend` is clean of the 1.6GB `build` artifacts.

## Changes Made Today (2026-06-10)
- **Project Structure**:
  - Restructured the root directory to only contain `frontend` and `backend`.
  - Moved Spring Boot files, Python scripts, and Markdown documentations (`CHANGELOG_TODAY.md`, `PROJECT_RULES.md`, `HELP.md`) into `backend/`.
  - Copied Flutter source files to `frontend/`, deliberately leaving behind the bulky `build/` directory to save 1.6GB of space.
- **Git & GitHub Optimization**:
  - Copied `.gitignore` rules directly into `.git/info/exclude`.
  - Deleted `.gitignore` and `.gitattributes` from the root directory so the GitHub repo looks perfectly clean with strictly two folders.
  - Successfully committed and pushed the source code.

## Next Steps (Nhiệm vụ tiếp theo)
- **Hoàn thiện dữ liệu DataSeeder cho mục Shop > Women > New**:
  - Ghi chú quan trọng: Danh mục "Tops" ở đây mang ý nghĩa là "Top sản phẩm bán chạy" (Bestsellers) chứ không chỉ đơn thuần là "áo".
  - Nhiệm vụ 1: Bổ sung thêm dữ liệu sản phẩm cho các tab đang bị trống (Knitwear, Blazers, Pants, Jeans, Shorts, Skirts...).
  - Nhiệm vụ 2: Thiết lập lại quan hệ danh mục (categories). Các sản phẩm nổi bật nằm ở "Knitwear", "Blazers", "Pants"... cũng phải được gán thêm vào danh mục "Tops" để khi bấm vào tab nào cũng có sản phẩm, và tab "Tops" sẽ là nơi tổng hợp các món đồ bán chạy nhất của các tab kia.
- Kết nối API/Logic giao diện (State Management).
