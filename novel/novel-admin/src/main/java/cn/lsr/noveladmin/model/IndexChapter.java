package cn.lsr.noveladmin.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class IndexChapter {
    private Long id;

    private Long indexId;

    @NotEmpty(message = "章节名不能为空")
    @Length(max = 30, message = "章节名限长30个字符")
    private String chapterName;

    private String chapterContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIndexId() {
        return indexId;
    }

    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName == null ? null : chapterName.trim();
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent == null ? null : chapterContent.trim();
    }
}
