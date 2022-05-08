package cn.lsr.noveladmin.model;

public class ChapterCommentReply {
    private Long id;

    private Long chapterCommentId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChapterCommentId() {
        return chapterCommentId;
    }

    public void setChapterCommentId(Long chapterCommentId) {
        this.chapterCommentId = chapterCommentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}