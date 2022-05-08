package cn.lsr.noveladmin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookIndex {
    private Long id;

    private Long bookId;

    private String indexName;

    private Long chapterCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName == null ? null : indexName.trim();
    }

    public Long getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(Long chapterCount) {
        this.chapterCount = chapterCount;
    }
}
