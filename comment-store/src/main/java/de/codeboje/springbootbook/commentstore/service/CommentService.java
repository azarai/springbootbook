package de.codeboje.springbootbook.commentstore.service;

import java.io.IOException;
import java.util.List;

import de.codeboje.springbootbook.model.CommentModel;

public interface CommentService {

    /**
     * Saves the comment
     * @param model
     * @return returns the id of the model
     * @throws IOException when there are problems with the DB
     */
    String put(CommentModel model) throws IOException;

    List<CommentModel> list(String pageId) throws IOException;

    CommentModel get(String id);
    
    List<CommentModel> listSpamComments(String pageId) throws IOException;
    
    void delete(String id);

}