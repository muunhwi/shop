package com.pofol.shop.service;

import com.pofol.shop.domain.Comment;
import com.pofol.shop.domain.Item;
import com.pofol.shop.domain.Member;
import com.pofol.shop.domain.ReplyComment;
import com.pofol.shop.domain.dto.comment.CommentDTO;
import com.pofol.shop.domain.dto.comment.ReplyDTO;
import com.pofol.shop.repository.CommentRepository;
import com.pofol.shop.repository.ItemRepository;
import com.pofol.shop.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final ItemRepository itemRepository;

    public void saveComments(Long id, String contents, Member member) {
        Optional<Item> findItem = itemRepository.findById(id);
        Item item = findItem.orElseThrow(EntityNotFoundException::new);

        Comment comment = Comment.builder()
                .contents(contents)
                .member(member)
                .deleted(false)
                .item(item)
                .build();

        commentRepository.save(comment);
    }
    public void saveReplies(Long id, String contents, Member member) {

        Optional<Comment> findComment = commentRepository.findById(id);
        Comment comment = findComment.orElseThrow(EntityNotFoundException::new);

        ReplyComment reply = ReplyComment.builder()
                .contents(contents)
                .member(member)
                .comment(comment)
                .deleted(false)
                .build();

        replyRepository.save(reply);
    }
    public List<CommentDTO> getComments(Long id) {

        List<Comment> comments = commentRepository.findCommentsByItemId(id);
        return comments.stream()
                .map(c -> {
                    CommentDTO build = CommentDTO
                            .builder()
                            .id(c.getId())
                            .contents(c.getContents())
                            .hoursAgo(
                                    getHoursAgo(c.getCreatedDate())
                            )
                            .nickName(c.getMember().getNickname())
                            .deleted(c.getDeleted())
                            .replies(c.getReplyComments()
                                    .stream()
                                    .map(r -> {
                                                ReplyDTO reply = ReplyDTO
                                                        .builder()
                                                        .contents(r.getContents())
                                                        .hoursAgo(getHoursAgo(r.getCreatedDate()))
                                                        .nickName(r.getMember().getNickname())
                                                        .deleted(r.getDeleted())
                                                        .id(r.getId())
                                                        .build();
                                                return reply;
                                            }
                                    ).collect(Collectors.toList())
                            ).build();
                    return build;
                }).collect(Collectors.toList());
    }
    @Transactional
    public void updateComments(Long id, String contents) {
        Optional<Comment> findComment = commentRepository.findById(id);
        Comment comment = findComment.orElseThrow(EntityNotFoundException::new);
        comment.setContents(contents);
    }
    @Transactional
    public void updateReply(Long id, String contents) {
        Optional<ReplyComment> findReply = replyRepository.findById(id);
        ReplyComment replyComment = findReply.orElseThrow(EntityNotFoundException::new);
        replyComment.setContents(contents);
    }

    @Transactional
    public void deleteComment(Long id) {
        Optional<Comment> findComment = commentRepository.findById(id);
        Comment comment = findComment.orElseThrow(EntityNotFoundException::new);
        comment.setDeleted(true);
    }

    @Transactional
    public void deleteReply(Long id) {
        Optional<ReplyComment> findReply = replyRepository.findById(id);
        ReplyComment replyComment = findReply.orElseThrow(EntityNotFoundException::new);
        replyComment.setDeleted(true);
    }

    private String getHoursAgo(LocalDateTime createdDate) {

        Duration between = Duration.between(createdDate, LocalDateTime.now());
        if(( between.getSeconds()/ 60 )<60) {
            return "최근에 작성됨";
        } else if( ( between.getSeconds()/ 60 / 60) < 24) {
            return ( between.getSeconds()/ 60 / 60) + "전에 작성됨";
        } else {
            return ( between.getSeconds()/ 60 / 60 / 24) + "전에 작성됨";
        }
    }


}
