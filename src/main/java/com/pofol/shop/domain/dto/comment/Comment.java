package com.pofol.shop.domain.dto.comment;

import com.pofol.shop.domain.Member;
import com.pofol.shop.domain.dto.item.Item;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "COMMENT_PK_GENERATOR",
        sequenceName = "COMMENT_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
@EntityListeners(AuditingEntityListener.class)
public class Comment{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_PK_GENERATOR")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String contents;

    @OneToMany(mappedBy = "comment")
    private List<ReplyComment> replyComments;

    @CreatedDate
    private LocalDateTime createdDate;

    private Boolean deleted;

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
