package com.project.ipyang.domain.inquire.repository;

import com.project.ipyang.domain.inquire.dto.InquireListDto;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.ipyang.domain.inquire.entity.QInquire.inquire;

public class InquireRepositoryImpl implements InquireRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public InquireRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<InquireListDto> searchInquire(String searchKeyword, String searchType, Pageable pageable, int page) {
        int pageSize = pageable.getPageSize();
        int offset = page * pageSize;

        List<InquireListDto> content = queryFactory
                .select(Projections.constructor(InquireListDto.class,
                        inquire.id, inquire.member.email, inquire.member.nickname, inquire.title, inquire.status, inquire.createdAt))
                .from(inquire)
                .where(containsKeyword(searchKeyword, searchType))
                .orderBy(inquire.id.desc())
                .offset(offset)
                .limit(pageSize)
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(inquire.count())
                .from(inquire)
                .where(containsKeyword(searchKeyword, searchType));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);

    }

    //카테고리별 검색
    private BooleanExpression containsKeyword(String searchKeyword, String searchType) {
        if(searchType.equals("total")) {
            return inquire.title.contains(searchKeyword)
                    .or(inquire.content.contains(searchKeyword));
        }
        if(searchType.equals("title")) {
            return inquire.title.contains(searchKeyword);
        }
        if(searchType.equals("content")) {
            return inquire.content.contains(searchKeyword);
        }
        if(searchType.equals("nickName")) {
            return inquire.member.nickname.equalsIgnoreCase(searchKeyword);  //대소문자 구분 없이 일치
        }
        return null;
    }
}
