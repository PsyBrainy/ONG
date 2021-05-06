package com.team32.ong.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.team32.ong.dto.AddCommentBody;
import com.team32.ong.dto.CommentDto;
import com.team32.ong.exception.custom.EmptyInputException;
import com.team32.ong.service.CommentService;


@RestController
@RequestMapping("api/v1/news")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/addComment")
	public ResponseEntity<CommentDto> addComment(@RequestParam(name="newsId") Optional<Long> newsId,
												 @RequestParam("userId") Optional<Long> userId,
												 @RequestBody(required = false) AddCommentBody commentBody) {		
		
		if(commentBody == null) {
			throw new EmptyInputException("Tiene que existir un comentario");
		} else if(commentBody.getBody().isBlank() | commentBody.getBody().isEmpty()) {
			throw new EmptyInputException("El cuerpo del comentario no puede estar vacío");
		}
		
    	return commentService.createNewComment(newsId, userId, commentBody.getBody());
    	
	}
}
