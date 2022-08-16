package com.pofol.shop.domain.dto.comment;

import com.pofol.shop.domain.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "REPLY_PK_GENERATOR",
        sequenceName = "REPLY_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
@EntityListeners(AuditingEntityListener.class)
public class ReplyComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLY_PK_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String contents;

    private Boolean deleted;

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @CreatedDate
    private LocalDateTime createdDate;
}
