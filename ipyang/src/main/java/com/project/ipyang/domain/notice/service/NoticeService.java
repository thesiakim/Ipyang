package com.project.ipyang.domain.notice.service;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.common.dto.PageDto;
import com.project.ipyang.common.util.S3Utils;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import com.project.ipyang.domain.notice.dto.NoticeListDto;
import com.project.ipyang.domain.notice.dto.SelectNoticeDto;
import com.project.ipyang.domain.notice.dto.UpdateNoticeDto;
import com.project.ipyang.domain.notice.dto.WriteNoticeDto;
import com.project.ipyang.domain.notice.entity.Notice;
import com.project.ipyang.domain.notice.entity.NoticeImg;
import com.project.ipyang.domain.notice.repository.NoticeImgRepository;
import com.project.ipyang.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeImgRepository noticeImgRepository;
    private final MemberRepository memberRepository;
    private final HttpSession session;
    private final S3Utils s3Utils;

    //안내글 작성
    @Transactional
    public ResponseDto createNotice(IpyangEnum.NoticeCategory selectedCategory, WriteNoticeDto request) throws IOException {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        Optional<Member> member = memberRepository.findById(memberId);
        Notice writeNotice = null;

        //파일 미첨부
        if(request.getNoticeFile() == null || request.getNoticeFile().isEmpty()) {
            Notice notice = Notice.builder()
                                            .title(request.getTitle())
                                            .content(request.getContent())
                                            .category(selectedCategory)
                                            .member(member.get())
                                            .build();
            writeNotice = noticeRepository.save(notice);

        //파일 첨부
        } else {
            Notice notice = Notice.builder()
                                            .title(request.getTitle())
                                            .content(request.getContent())
                                            .category(selectedCategory)
                                            .member(member.get())
                                            .build();
            writeNotice = noticeRepository.save(notice);

            Long savedId = writeNotice.getId();
            Notice noticeId = noticeRepository.findById(savedId).get();

            for(MultipartFile noticeFile : request.getNoticeFile()) {
                String imgOriginFile = noticeFile.getOriginalFilename();
                String imgUrl = s3Utils.uploadFileToS3(noticeFile, "notice");

                NoticeImg noticeImg  = new NoticeImg(imgOriginFile, imgUrl, noticeId);
                noticeImgRepository.save(noticeImg);
            }
        }
        if(writeNotice != null) return new ResponseDto("글을 작성했습니다", HttpStatus.OK.value());
        else return new ResponseDto("에러가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //카테고리별 안내글 목록 조회 (페이징)
    @Transactional
    public ResponseDto selectAllNotice(IpyangEnum.NoticeCategory selectedCategory, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;   //요청 받은 페이지
        int pageLimit = 10;                        //페이지당 글 개수
        int blockLimit = 5;                        //한번에 5개의 페이지만 노출

        Page<Notice> notices = noticeRepository.findByCategory(selectedCategory, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        if(!notices.isEmpty()) {
            Page<NoticeListDto> noticeListDtos = notices.map(notice -> new NoticeListDto(notice.getId(), notice.getTitle(), notice.getCreatedAt()));
            int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
            int endPage = ((startPage + blockLimit - 1) < noticeListDtos.getTotalPages()) ? startPage + blockLimit - 1 : noticeListDtos.getTotalPages();

            PageDto noticePage = new PageDto(noticeListDtos, startPage, endPage);

            return new ResponseDto<>(noticePage, HttpStatus.OK.value());
        }
        else return new ResponseDto("글이 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //특정 안내글 조회
    @Transactional
    public ResponseDto selectNotice(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if(!notice.isPresent()) {
            return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        Notice findNotice = notice.get();
        SelectNoticeDto detailNotice = findNotice.convertSelectDto();

        List<String> noticeImgs = new ArrayList<>();

        for(NoticeImg noticeImg : findNotice.getNoticeImgs()) {
            noticeImgs.add(noticeImg.getImgStoredFile());
        }
        if(!noticeImgs.isEmpty()) detailNotice.setNoticeImgs(noticeImgs);

        return new ResponseDto(detailNotice, HttpStatus.OK.value());
    }


    //특정 안내글 수정
    /*
    * 현재 이미지 수정은 제외하였음
    * */
    @Transactional
    public ResponseDto updateNotice(Long id,
                                    UpdateNoticeDto request) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if(!notice.isPresent()) {
            return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        notice.get().update(request.getTitle(), request.getContent(), request.getCategory());
        return new ResponseDto("수정되었습니다", HttpStatus.OK.value());

    }


    //특정 안내글 삭제
    @Transactional
    public ResponseDto deleteNotice(Long id) {
        Optional<Notice> searchNotice = noticeRepository.findById(id);
        if(!searchNotice.isPresent()) {
            return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else noticeRepository.deleteById(id);

        Optional<Notice> notice = noticeRepository.findById(id);
        if(!notice.isPresent()) {
            return new ResponseDto("삭제되었습니다", HttpStatus.OK.value());
        }
        else return new ResponseDto("에러가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

    }
}
