package cn.lsr.noveladmin.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class Graphtimecount {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column graphtimecount.id
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column graphtimecount.year
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    private Integer year;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column graphtimecount.Authors
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    private Long authors;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column graphtimecount.Vips
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    private Long vips;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column graphtimecount.Books
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    private Long books;

    public Graphtimecount(Long authors, Long vips, Long books) {
        this.authors = authors;
        this.vips = vips;
        this.books = books;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column graphtimecount.id
     *
     * @return the value of graphtimecount.id
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column graphtimecount.id
     *
     * @param id the value for graphtimecount.id
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column graphtimecount.year
     *
     * @return the value of graphtimecount.year
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public Integer getYear() {
        return year;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column graphtimecount.year
     *
     * @param year the value for graphtimecount.year
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column graphtimecount.Authors
     *
     * @return the value of graphtimecount.Authors
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public Long getAuthors() {
        return authors;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column graphtimecount.Authors
     *
     * @param authors the value for graphtimecount.Authors
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public void setAuthors(Long authors) {
        this.authors = authors;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column graphtimecount.Vips
     *
     * @return the value of graphtimecount.Vips
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public Long getVips() {
        return vips;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column graphtimecount.Vips
     *
     * @param vips the value for graphtimecount.Vips
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public void setVips(Long vips) {
        this.vips = vips;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column graphtimecount.Books
     *
     * @return the value of graphtimecount.Books
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public Long getBooks() {
        return books;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column graphtimecount.Books
     *
     * @param books the value for graphtimecount.Books
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public void setBooks(Long books) {
        this.books = books;
    }
}