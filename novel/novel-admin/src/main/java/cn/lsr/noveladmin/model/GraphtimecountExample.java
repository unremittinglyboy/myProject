package cn.lsr.noveladmin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GraphtimecountExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public GraphtimecountExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterion(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterion(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterion(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Integer value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Integer value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Integer value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Integer value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Date value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Date> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Date> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Date value1, Date value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Date value1, Date value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andAuthorsIsNull() {
            addCriterion("Authors is null");
            return (Criteria) this;
        }

        public Criteria andAuthorsIsNotNull() {
            addCriterion("Authors is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorsEqualTo(Long value) {
            addCriterion("Authors =", value, "authors");
            return (Criteria) this;
        }

        public Criteria andAuthorsNotEqualTo(Long value) {
            addCriterion("Authors <>", value, "authors");
            return (Criteria) this;
        }

        public Criteria andAuthorsGreaterThan(Long value) {
            addCriterion("Authors >", value, "authors");
            return (Criteria) this;
        }

        public Criteria andAuthorsGreaterThanOrEqualTo(Long value) {
            addCriterion("Authors >=", value, "authors");
            return (Criteria) this;
        }

        public Criteria andAuthorsLessThan(Long value) {
            addCriterion("Authors <", value, "authors");
            return (Criteria) this;
        }

        public Criteria andAuthorsLessThanOrEqualTo(Long value) {
            addCriterion("Authors <=", value, "authors");
            return (Criteria) this;
        }

        public Criteria andAuthorsIn(List<Long> values) {
            addCriterion("Authors in", values, "authors");
            return (Criteria) this;
        }

        public Criteria andAuthorsNotIn(List<Long> values) {
            addCriterion("Authors not in", values, "authors");
            return (Criteria) this;
        }

        public Criteria andAuthorsBetween(Long value1, Long value2) {
            addCriterion("Authors between", value1, value2, "authors");
            return (Criteria) this;
        }

        public Criteria andAuthorsNotBetween(Long value1, Long value2) {
            addCriterion("Authors not between", value1, value2, "authors");
            return (Criteria) this;
        }

        public Criteria andVipsIsNull() {
            addCriterion("Vips is null");
            return (Criteria) this;
        }

        public Criteria andVipsIsNotNull() {
            addCriterion("Vips is not null");
            return (Criteria) this;
        }

        public Criteria andVipsEqualTo(Long value) {
            addCriterion("Vips =", value, "vips");
            return (Criteria) this;
        }

        public Criteria andVipsNotEqualTo(Long value) {
            addCriterion("Vips <>", value, "vips");
            return (Criteria) this;
        }

        public Criteria andVipsGreaterThan(Long value) {
            addCriterion("Vips >", value, "vips");
            return (Criteria) this;
        }

        public Criteria andVipsGreaterThanOrEqualTo(Long value) {
            addCriterion("Vips >=", value, "vips");
            return (Criteria) this;
        }

        public Criteria andVipsLessThan(Long value) {
            addCriterion("Vips <", value, "vips");
            return (Criteria) this;
        }

        public Criteria andVipsLessThanOrEqualTo(Long value) {
            addCriterion("Vips <=", value, "vips");
            return (Criteria) this;
        }

        public Criteria andVipsIn(List<Long> values) {
            addCriterion("Vips in", values, "vips");
            return (Criteria) this;
        }

        public Criteria andVipsNotIn(List<Long> values) {
            addCriterion("Vips not in", values, "vips");
            return (Criteria) this;
        }

        public Criteria andVipsBetween(Long value1, Long value2) {
            addCriterion("Vips between", value1, value2, "vips");
            return (Criteria) this;
        }

        public Criteria andVipsNotBetween(Long value1, Long value2) {
            addCriterion("Vips not between", value1, value2, "vips");
            return (Criteria) this;
        }

        public Criteria andBooksIsNull() {
            addCriterion("Books is null");
            return (Criteria) this;
        }

        public Criteria andBooksIsNotNull() {
            addCriterion("Books is not null");
            return (Criteria) this;
        }

        public Criteria andBooksEqualTo(Long value) {
            addCriterion("Books =", value, "books");
            return (Criteria) this;
        }

        public Criteria andBooksNotEqualTo(Long value) {
            addCriterion("Books <>", value, "books");
            return (Criteria) this;
        }

        public Criteria andBooksGreaterThan(Long value) {
            addCriterion("Books >", value, "books");
            return (Criteria) this;
        }

        public Criteria andBooksGreaterThanOrEqualTo(Long value) {
            addCriterion("Books >=", value, "books");
            return (Criteria) this;
        }

        public Criteria andBooksLessThan(Long value) {
            addCriterion("Books <", value, "books");
            return (Criteria) this;
        }

        public Criteria andBooksLessThanOrEqualTo(Long value) {
            addCriterion("Books <=", value, "books");
            return (Criteria) this;
        }

        public Criteria andBooksIn(List<Long> values) {
            addCriterion("Books in", values, "books");
            return (Criteria) this;
        }

        public Criteria andBooksNotIn(List<Long> values) {
            addCriterion("Books not in", values, "books");
            return (Criteria) this;
        }

        public Criteria andBooksBetween(Long value1, Long value2) {
            addCriterion("Books between", value1, value2, "books");
            return (Criteria) this;
        }

        public Criteria andBooksNotBetween(Long value1, Long value2) {
            addCriterion("Books not between", value1, value2, "books");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table graphtimecount
     *
     * @mbg.generated do_not_delete_during_merge Mon Oct 25 22:35:48 CST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table graphtimecount
     *
     * @mbg.generated Mon Oct 25 22:35:48 CST 2021
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
