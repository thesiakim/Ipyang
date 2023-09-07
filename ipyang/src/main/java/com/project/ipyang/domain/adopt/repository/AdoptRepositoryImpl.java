package com.project.ipyang.domain.adopt.repository;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.adopt.dto.AdoptDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.ipyang.domain.adopt.entity.QAdopt.adopt;
import static com.project.ipyang.domain.catType.entity.QCatType.catType;
import static com.project.ipyang.domain.vaccine.entity.QVaccine.vaccine;


public class AdoptRepositoryImpl implements AdoptRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public AdoptRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //입양 상태, 고양이 품종, 백신 종류에 따라 필터링
    @Override
    public Page<AdoptDto> findFilteredAdopt(String adopted, List<Long> catIds, List<Long> vacIds,
                                            int page, String searchKeyword, String searchType,
                                            Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int offset = page * pageSize;

         List<AdoptDto> content =  queryFactory
                                              .select(Projections.constructor(AdoptDto.class,
                                                       adopt.id, adopt.title, adopt.status, adopt.member.id,
                                                       adopt.member.nickname, adopt.viewCnt, adopt.createdAt)
                                                        )
                                              .from(adopt)
                                              .leftJoin(adopt.catType, catType)
                                              .leftJoin(adopt.vaccine, vaccine)
                                              .where(eqAdopted(adopted),
                                                     inCatType(catIds),
                                                     inVaccine(vacIds),
                                                     containKeyword(searchKeyword, searchType))
                                              .orderBy(adopt.id.desc())
                                              .offset(offset)
                                              .limit(pageSize)
                                              .fetch();

        JPAQuery<Long> count = queryFactory
                                        .select(adopt.count())
                                        .from(adopt)
                                        .leftJoin(adopt.catType, catType)
                                        .leftJoin(adopt.vaccine, vaccine)
                                        .where(eqAdopted(adopted),
                                                inCatType(catIds),
                                                inVaccine(vacIds),
                                                containKeyword(searchKeyword, searchType));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);

    }

    //입양 상태 (완료, 미완료) 조회
    private BooleanExpression eqAdopted(String adopted) {
        if(adopted != null) {
            IpyangEnum.Status status = IpyangEnum.Status.valueOf(adopted);
            return adopt.status.eq(status);
        } else return null;
    }

    //고양이 품종 조회
    private BooleanExpression inCatType(List<Long> catIds) {
        if(catIds != null && !catIds.isEmpty()) {
            return adopt.catType.id.in(catIds);
        }
        return null;
    }

    //백신 종류 조회
    private BooleanExpression inVaccine(List<Long> vacIds) {
        if(vacIds != null && !vacIds.isEmpty()) {
            return adopt.vaccine.id.in(vacIds);
        }
        return null;
    }

    //카테고리별 검색
    private BooleanExpression containKeyword(String searchKeyword, String searchType) {
        if(searchType.equals("total")) {
            return adopt.title.contains(searchKeyword)
                    .or(adopt.content.contains(searchKeyword));
        }
        if(searchType.equals("title")) {
            return adopt.title.contains(searchKeyword);
        }
        if(searchType.equals("content")) {
            return adopt.content.contains(searchKeyword);
        }
        return null;
    }
}
