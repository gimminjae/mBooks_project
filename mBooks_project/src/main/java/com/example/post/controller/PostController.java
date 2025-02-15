package com.example.post.controller;

import com.example.post.dto.PostDto;
import com.example.post.service.PostService;
import com.example.post.post_hashTag.HashTag;
import com.example.post.post_hashTag.HashTagDto;
import com.example.post.post_hashTag.HashTagService;
import com.example.member.dto.MemberDto;
import com.example.member.service.MemberService;
import com.example.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final MemberService memberService;
    private final PostService postService;
    private final HashTagService hashTagService;
    // 글 목록
//    @GetMapping("/list")
//    public String list(Model model, @RequestParam(value = "tag", defaultValue = "") String tag) {
//        List<PostDto> postDtoList;
//
//        if(tag.length() > 0) {
//            postDtoList = postService.getPostByTag(tag);
//        } else {
//            postDtoList = postService.getAllPost();
//        }
//
//        model.addAttribute("postList", postDtoList);
//        return "post/list";
//    }

    // 글 상세페이지
    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        PostDto postDto = postService.getPostById(id);
        List<HashTagDto> tagList = hashTagService.getTagsByPost(postDto);

        model.addAttribute("tagList", tagList);
        model.addAttribute("post", postDto);

        return "post/detail";
    }

    // 글 작성
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String write() {
        return "post/write";
    }
    @PostMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String write(@RequestParam("subject") String subject, @RequestParam("hashtag") String hashTag, @RequestParam("content") String content, Principal principal) {
        MemberDto member = memberService.getMemberByUsername(principal.getName());
        postService.write(member, subject, hashTag, content);
        return "redirect:/member?listType=post&msg=%s".formatted(Ut.url.encode("글을 작성했습니다!"));
    }

    // 글 수정
    @GetMapping("/{id}/modify")
    @PreAuthorize("isAuthenticated()")
    public String modify(Model model, @PathVariable("id") Long id) {
        PostDto postDto = postService.getPostById(id);

        List<HashTag> tagList = postDto.getHashTagList();
        StringBuilder tags = new StringBuilder();
        for(HashTag hashTag : tagList) {
            tags.append("#%s ".formatted(hashTag.getKeyword().getContent()));
        }
        model.addAttribute("tags", tags.toString());
        model.addAttribute("post", postDto);
        return "post/modify";
    }

    //글 수정 처리
    @PostMapping("/{id}/modify")
    @PreAuthorize("isAuthenticated()")
    public String modify(Principal principal, @PathVariable("id") long id,
                         @RequestParam("subject") String subject,
                         @RequestParam("content") String content,
                         @RequestParam("hashtag") String hashTag) {
        PostDto postDto = postService.getPostById(id);
        //수정 권한 확인
        if(!postDto.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        postService.modify(postDto, subject, hashTag, content);
        return "redirect:/post/%d?msg=%s".formatted(postDto.getId(), Ut.url.encode("글이 수정되었습니다!"));
    }

    //글 삭제
    @GetMapping("/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        PostDto postDto = postService.getPostById(id);
        //삭제 권한 확인
        if(!postDto.getMember().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        try {
            postService.delete(postDto);
        } catch(Exception e) {
            return "redirect:/post/%d?errorMsg=%s".formatted(id, Ut.url.encode("도서에 등록된 글은 삭제할 수 없습니다."));
        }
        return "redirect:/member?listType=post&msg=%s".formatted(Ut.url.encode("글이 삭제되었습니다!"));
    }

    //글 정보 가져오기
//    @PostMapping("/get")
//    @ResponseBody
//    public PostDto getPost(@RequestParam(value = "input_postId") Long id) {
//        PostDto postDto = postService.getPostById(id);
//
//        return postDto;
//    }
}
