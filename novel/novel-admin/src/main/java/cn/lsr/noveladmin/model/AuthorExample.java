package cn.lsr.noveladmin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthorExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    public AuthorExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
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
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
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

        public Criteria andPenNameIsNull() {
            addCriterion("pen_name is null");
            return (Criteria) this;
        }

        public Criteria andPenNameIsNotNull() {
            addCriterion("pen_name is not null");
            return (Criteria) this;
        }

        public Criteria andPenNameEqualTo(String value) {
            addCriterion("pen_name =", value, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameNotEqualTo(String value) {
            addCriterion("pen_name <>", value, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameGreaterThan(String value) {
            addCriterion("pen_name >", value, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameGreaterThanOrEqualTo(String value) {
            addCriterion("pen_name >=", value, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameLessThan(String value) {
            addCriterion("pen_name <", value, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameLessThanOrEqualTo(String value) {
            addCriterion("pen_name <=", value, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameLike(String value) {
            addCriterion("pen_name like", value, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameNotLike(String value) {
            addCriterion("pen_name not like", value, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameIn(List<String> values) {
            addCriterion("pen_name in", values, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameNotIn(List<String> values) {
            addCriterion("pen_name not in", values, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameBetween(String value1, String value2) {
            addCriterion("pen_name between", value1, value2, "penName");
            return (Criteria) this;
        }

        public Criteria andPenNameNotBetween(String value1, String value2) {
            addCriterion("pen_name not between", value1, value2, "penName");
            return (Criteria) this;
        }

        public Criteria andTelPhoneIsNull() {
            addCriterion("tel_phone is null");
            return (Criteria) this;
        }

        public Criteria andTelPhoneIsNotNull() {
            addCriterion("tel_phone is not null");
            return (Criteria) this;
        }

        public Criteria andTelPhoneEqualTo(String value) {
            addCriterion("tel_phone =", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneNotEqualTo(String value) {
            addCriterion("tel_phone <>", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneGreaterThan(String value) {
            addCriterion("tel_phone >", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("tel_phone >=", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneLessThan(String value) {
            addCriterion("tel_phone <", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneLessThanOrEqualTo(String value) {
            addCriterion("tel_phone <=", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneLike(String value) {
            addCriterion("tel_phone like", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneNotLike(String value) {
            addCriterion("tel_phone not like", value, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneIn(List<String> values) {
            addCriterion("tel_phone in", values, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneNotIn(List<String> values) {
            addCriterion("tel_phone not in", values, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneBetween(String value1, String value2) {
            addCriterion("tel_phone between", value1, value2, "telPhone");
            return (Criteria) this;
        }

        public Criteria andTelPhoneNotBetween(String value1, String value2) {
            addCriterion("tel_phone not between", value1, value2, "telPhone");
            return (Criteria) this;
        }

        public Criteria andChatAccountIsNull() {
            addCriterion("chat_account is null");
            return (Criteria) this;
        }

        public Criteria andChatAccountIsNotNull() {
            addCriterion("chat_account is not null");
            return (Criteria) this;
        }

        public Criteria andChatAccountEqualTo(String value) {
            addCriterion("chat_account =", value, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountNotEqualTo(String value) {
            addCriterion("chat_account <>", value, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountGreaterThan(String value) {
            addCriterion("chat_account >", value, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountGreaterThanOrEqualTo(String value) {
            addCriterion("chat_account >=", value, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountLessThan(String value) {
            addCriterion("chat_account <", value, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountLessThanOrEqualTo(String value) {
            addCriterion("chat_account <=", value, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountLike(String value) {
            addCriterion("chat_account like", value, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountNotLike(String value) {
            addCriterion("chat_account not like", value, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountIn(List<String> values) {
            addCriterion("chat_account in", values, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountNotIn(List<String> values) {
            addCriterion("chat_account not in", values, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountBetween(String value1, String value2) {
            addCriterion("chat_account between", value1, value2, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andChatAccountNotBetween(String value1, String value2) {
            addCriterion("chat_account not between", value1, value2, "chatAccount");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionIsNull() {
            addCriterion("work_direction is null");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionIsNotNull() {
            addCriterion("work_direction is not null");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionEqualTo(Byte value) {
            addCriterion("work_direction =", value, "workDirection");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionNotEqualTo(Byte value) {
            addCriterion("work_direction <>", value, "workDirection");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionGreaterThan(Byte value) {
            addCriterion("work_direction >", value, "workDirection");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionGreaterThanOrEqualTo(Byte value) {
            addCriterion("work_direction >=", value, "workDirection");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionLessThan(Byte value) {
            addCriterion("work_direction <", value, "workDirection");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionLessThanOrEqualTo(Byte value) {
            addCriterion("work_direction <=", value, "workDirection");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionIn(List<Byte> values) {
            addCriterion("work_direction in", values, "workDirection");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionNotIn(List<Byte> values) {
            addCriterion("work_direction not in", values, "workDirection");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionBetween(Byte value1, Byte value2) {
            addCriterion("work_direction between", value1, value2, "workDirection");
            return (Criteria) this;
        }

        public Criteria andWorkDirectionNotBetween(Byte value1, Byte value2) {
            addCriterion("work_direction not between", value1, value2, "workDirection");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table author
     *
     * @mbg.generated do_not_delete_during_merge Sat Oct 23 22:04:51 CST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table author
     *
     * @mbg.generated Sat Oct 23 22:04:51 CST 2021
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