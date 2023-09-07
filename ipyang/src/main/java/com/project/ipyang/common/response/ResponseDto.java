package com.project.ipyang.common.response;

import com.project.ipyang.common.dto.ErrorDto;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDto<T> implements Serializable {
    private static final long serialVersionUID = 1289378297382227732L;
    private T ResultData;
    private Long ResultCount;
    private int status;
    private ErrorDto error;

    public ResponseDto(T resultData, int status){
        this.ResultData = resultData;
        this.status = status;
    }
    public ResponseDto(T resultData){
        this.ResultData = resultData;
    }
    public ResponseDto(T resultData, Long resultCount){
        this.ResultData = resultData;
        this.ResultCount = resultCount;
    }
    public ResponseDto(ErrorDto errorDto){
        this.ResultData = null;
        this.error = errorDto;
    }



    public static <T> ResponseDto<T> from(T resultData){
        return new ResponseDto<T>(resultData);
    }

    public static ResponseDto<?> ERROR(String errorMessage, HttpStatus status){
        return new ResponseDto(ErrorDto.of(errorMessage, status));
    }
    public static ResponseDto<?> ERROR(Throwable throwable, HttpStatus status){
        return new ResponseDto(ErrorDto.of(throwable, status));
    }
}
