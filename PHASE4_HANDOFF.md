# Phase 4 handoff — Bé Gạo & Thầy Ny

## Những thay đổi đã thực hiện

- Thay mascot Canvas đang hiển thị bằng bộ 6 ảnh PNG Thầy Ny đã duyệt: idle, speaking, pointing, encouraging, celebrating và storytelling.
- Thầy Ny mới xuất hiện tại trang chủ/đố vui, thanh hướng dẫn thẻ học, kho truyện và thẻ lời kể.
- Đố vui chỉ có 2 ô ảnh lớn cạnh nhau; bỏ dòng “Chạm để chọn” và biểu tượng bàn tay gây rối.
- Mỗi lượt thẻ học hiển thị tối đa 6 thẻ; nút **Đổi bộ hình** chuyển sang nhóm tiếp theo.
- Thêm dấu `›` ở mép phải thanh chủ đề để báo còn nội dung có thể vuốt ngang.
- Bìa kho truyện dùng cảnh đầu thật thay vì emoji.
- Gấu Trúc dùng ảnh chụp cục bộ, không phụ thuộc mạng; đồng thời sửa URL sai một ký tự trong dữ liệu cũ.
- Thêm kiểm thử ngăn Gà Trống và Chú Vịt trỏ chung ảnh, kiểm tra ảnh Gấu Trúc và đủ 6 trạng thái Thầy Ny.
- Giữ nguyên GitHub Actions workflow từ bản đang chạy xanh.

## Chủ ý an toàn

- Mã Canvas Thầy Ny cũ chưa bị xóa trong Phase 4; UI đã ngừng gọi nó. Sau khi kiểm thử thực tế ổn định mới nên dọn mã cũ.
- Không thêm trò chơi mới trong lượt này. Ưu tiên ổn định truyện, thẻ học, đố vui và hình ảnh trước.

## Nghiệm thu sau khi push

1. GitHub Actions phải xanh ở bước unit tests và build debug APK.
2. Mở app trên điện thoại/emulator và kiểm tra cả 4 màn: Trang chủ, Thẻ học, Kho truyện, Đố vui.
3. Kiểm tra Gấu Trúc có ảnh khi tắt mạng.
4. Kiểm tra Thầy Ny đổi trạng thái khi đang nói, trả lời đúng và trả lời sai.
5. Kiểm tra nút **Đổi bộ hình** không lặp vô hạn cùng một nhóm 6 thẻ.
