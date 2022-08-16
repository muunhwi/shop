package com.pofol.shop.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long id;
    private String contents;
    private String nickName;
    private String hoursAgo;
    private Boolean deleted;
    private List<ReplyDTO> replies;
}
