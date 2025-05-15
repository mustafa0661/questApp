package com.project.guestApp.services;

import com.project.guestApp.entities.Like;
import com.project.guestApp.entities.Post;
import com.project.guestApp.entities.User;
import com.project.guestApp.repos.LikeRepository;
import com.project.guestApp.requests.LikeCreateRequest;
import com.project.guestApp.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        }else
            list = likeRepository.findAll();
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest newLikeRequest) {
        User user = userService.getOneUserById(newLikeRequest.getUserId());
        Post post = postService.getOnePostById(newLikeRequest.getPostId());
        if (user != null && post != null) {
            Like likeToSave = new Like();
            likeToSave.setId(newLikeRequest.getId());
            likeToSave.setUser(user);
            likeToSave.setPost(post);
            likeRepository.save(likeToSave);
            return likeToSave;
        } else
            return null;
    }


    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
