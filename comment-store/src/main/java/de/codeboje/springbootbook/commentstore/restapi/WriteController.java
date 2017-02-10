package de.codeboje.springbootbook.commentstore.restapi;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.codeboje.springbootbook.commentstore.service.CommentService;
import de.codeboje.springbootbook.model.CommentModel;


@Controller
public class WriteController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WriteController.class);

	@Autowired
	private CommentService service;

	@Autowired
	private CounterService counterService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody String create(@RequestParam("comment") final String comment,
			@RequestParam("pageId") final String pageId,
			@RequestParam("emailAddress") final String emailAddress,
			@RequestParam("username") final String username) throws IOException {

		counterService.increment("commentstore.post");

		LOGGER.info("form post started");
		CommentModel model = new CommentModel();
		model.setPageId(pageId);
		model.setEmailAddress(emailAddress);
		model.setComment(comment);
		model.setUsername(username);

		String id = service.put(model);
		
		LOGGER.info("form post done");
		
		return id;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable(value = "id") String id,
			HttpServletResponse response) throws IOException {
		service.delete(id);
	}
	
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handle404(Exception ex, Locale locale) {
        LOGGER.debug("Resource not found {}", ex.getMessage());
    }
}
