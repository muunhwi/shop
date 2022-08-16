package com.pofol.shop.controller;


import com.pofol.shop.domain.Member;
import com.pofol.shop.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/shop/item/comments/{itemId}")
    public String PostComments(@PathVariable("itemId") Long id, String contents, @AuthenticationPrincipal Member member) {
        commentService.saveComments(id, contents, member);
        return "redirect:/shop/item/"+id;
    }

    @PostMapping("/shop/item/reply/{commentId}/{itemId}")
    public String PostReply(@PathVariable("commentId") Long id, @PathVariable("itemId") Long itemId,
                            String contents, @AuthenticationPrincipal Member member) {
        commentService.saveReplies(id, contents, member);
        return "redirect:/shop/item/"+ itemId;
    }

    @PostMapping("/shop/item/update/{commentId}/{itemId}")
    public String updateComment(@PathVariable("commentId") Long id, @PathVariable("itemId") Long itemId,
                                String contents) {
        commentService.updateComments(id, contents);
        return "redirect:/shop/item/"+ itemId;
    }

    @PostMapping("/shop/item/reply/update/{replyId}/{itemId}")
    public String updateReply(@PathVariable("replyId") Long id, @PathVariable("itemId") Long itemId,
                              String contents) {
        commentService.updateReply(id, contents);
        return "redirect:/shop/item/"+ itemId;
    }

    @GetMapping("/shop/item/delete/comment")
    public String deleteComment(Long id, Long itemId) {
        commentService.deleteComment(id);
        return "redirect:/shop/item/"+ itemId;
    }

    @GetMapping("/shop/item/delete/reply")
    public String deleteReply(Long id, Long itemId) {
        commentService.deleteReply(id);
        return "redirect:/shop/item/"+ itemId;
    }
}
