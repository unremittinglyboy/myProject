package cn.lsr.noveladmin.model;

import java.util.ArrayList;
import java.util.List;

public class FollowRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FollowRecordExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andUserId1IsNull() {
            addCriterion("user_id1 is null");
            return (Criteria) this;
        }

        public Criteria andUserId1IsNotNull() {
            addCriterion("user_id1 is not null");
            return (Criteria) this;
        }

        public Criteria andUserId1EqualTo(Long value) {
            addCriterion("user_id1 =", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1NotEqualTo(Long value) {
            addCriterion("user_id1 <>", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1GreaterThan(Long value) {
            addCriterion("user_id1 >", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1GreaterThanOrEqualTo(Long value) {
            addCriterion("user_id1 >=", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1LessThan(Long value) {
            addCriterion("user_id1 <", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1LessThanOrEqualTo(Long value) {
            addCriterion("user_id1 <=", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1In(List<Long> values) {
            addCriterion("user_id1 in", values, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1NotIn(List<Long> values) {
            addCriterion("user_id1 not in", values, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1Between(Long value1, Long value2) {
            addCriterion("user_id1 between", value1, value2, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1NotBetween(Long value1, Long value2) {
            addCriterion("user_id1 not between", value1, value2, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId2IsNull() {
            addCriterion("user_id2 is null");
            return (Criteria) this;
        }

        public Criteria andUserId2IsNotNull() {
            addCriterion("user_id2 is not null");
            return (Criteria) this;
        }

        public Criteria andUserId2EqualTo(Long value) {
            addCriterion("user_id2 =", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2NotEqualTo(Long value) {
            addCriterion("user_id2 <>", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2GreaterThan(Long value) {
            addCriterion("user_id2 >", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2GreaterThanOrEqualTo(Long value) {
            addCriterion("user_id2 >=", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2LessThan(Long value) {
            addCriterion("user_id2 <", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2LessThanOrEqualTo(Long value) {
            addCriterion("user_id2 <=", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2In(List<Long> values) {
            addCriterion("user_id2 in", values, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2NotIn(List<Long> values) {
            addCriterion("user_id2 not in", values, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2Between(Long value1, Long value2) {
            addCriterion("user_id2 between", value1, value2, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2NotBetween(Long value1, Long value2) {
            addCriterion("user_id2 not between", value1, value2, "userId2");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

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