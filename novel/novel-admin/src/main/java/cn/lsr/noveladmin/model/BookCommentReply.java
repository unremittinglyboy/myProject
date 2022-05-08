package cn.lsr.noveladmin.model;

public class BookCommentReply {
    private Long id;

    private Long bookCommentId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookCommentId() {
        return bookCommentId;
    }

    public void setBookCommentId(Long bookCommentId) {
        this.bookCommentId = bookCommentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}