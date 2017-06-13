package de.codeboje.springbootbook.commentstore.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.codeboje.springbootbook.model.CommentModel;
import de.codeboje.springbootbook.spamdetection.SpamDetector;

@Service
public class CommentServiceImpl implements CommentService {
            
    @Autowired
    private SpamDetector spamDetector;

    @Autowired
    private CommentModelRepository repository;

    @Override
    @Transactional
    public String put(CommentModel model) throws IOException {

    	if (StringUtils.isEmpty(model.getId())) {
    		model.setId(UUID.randomUUID().toString());
    	}
    	if(spamDetector.containsSpam(model.getUsername()) || 
    			spamDetector.containsSpam(model.getEmailAddress())
    			|| spamDetector.containsSpam(model.getComment())) {
    		model.setSpam(true);
    	}
    	
        final CommentModel dbModel = get(model.getId());
        if (dbModel != null) {
            dbModel.setUsername(model.getUsername());
            dbModel.setComment(model.getComment());            
            dbModel.setLastModificationDate(Instant.now());
            repository.save(dbModel);
        }
        else {
            model.setCreationDate(Instant.now());
            model.setLastModificationDate(Instant.now());
            repository.save(model);
        }
        return model.getId();
    }

    @Override
    public CommentModel get(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<CommentModel> list(String pageId) throws IOException {
        return repository.findByPageId(pageId);
    }

	@Override
	public List<CommentModel> listSpamComments(String pageId)
			throws IOException {
		return repository.findByPageIdAndSpamIsTrue(pageId);
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

}
