package com.pofol.shop.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {

    private Long id;
    private String nickName;
    private String hoursAgo;
    private String contents;
    private Boolean deleted;
    private Long memberId;

}
