# LUẬT BẮT BUỘC DÀNH CHO AI (ANTIGRAVITY)

1. **Quyền hạn tự chủ tối đa:** AI được cấp toàn quyền để tự do code, review code, lặp lại quy trình (loop), chạy server, và test các tính năng. Tuyệt đối **không cần phải xin phép** người dùng trước khi chạy lệnh (run command) hay kiểm thử (test).
2. **Trách nhiệm kiểm thử:** AI bắt buộc phải tự chạy server và tự dùng các công cụ (như curl, gọi API, xem log, v.v.) để test xem code mình viết ra đã hoạt động đúng chưa trước khi báo cáo cho người dùng.
3. **Đối chiếu yêu cầu khắt khe:** Bắt buộc phải so sánh và đối chiếu kết quả thực tế với yêu cầu của người dùng và các **ảnh giao diện mẫu** (nếu có). Chỉ khi nào chắc chắn 100% khớp và không có lỗi mới được kết luận là hoàn thành.
4. **Dữ liệu thật:** Tất cả dữ liệu của web/app phải được lấy từ Backend. Nếu thiếu bảng trong DB theo chuẩn `DB_SCHEMA.md` thì tự động tạo bảng; nếu thiếu dữ liệu thì tự động viết Data Seeder để thêm dữ liệu mẫu.
