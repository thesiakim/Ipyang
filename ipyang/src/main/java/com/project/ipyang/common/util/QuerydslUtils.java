package com.project.ipyang.common.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

public class QuerydslUtils {
    //Querydsl 문자열 동일한지 체크
    public static BooleanExpression stringEqCheck(StringPath select, String condition){
        return hasText(condition)?select.equalsIgnoreCase(condition):null;
    }

    public static BooleanExpression numberEqCheck(NumberPath select, Number condition){
        return condition != null ? select.eq(condition) : null;
    }

    public static BooleanExpression enumEqCheck(EnumPath select, Enum condition){
        return condition != null ? select.eq(condition) : null;
    }

    public static BooleanExpression stringContainsCheck(StringPath select, String condition){
        return hasText(condition)?select.equalsIgnoreCase(condition):select.isNull();
    }

    public static BooleanExpression isNumberCheck(NumberPath select, List<Integer> condition){
        return condition != null && condition.size() >0 ? select.in(condition) : null;
    }

    public static BooleanExpression isStringCheck(StringPath select, List<String> condition){
        return condition!=null && condition.size() >0 ?select.in(condition):select.isNull();
    }

    public static BooleanExpression isEnumCheck(EnumPath select, List<?> condition){
        return condition != null && condition.size()>0 ? select.in(condition) : null;
    }
}
