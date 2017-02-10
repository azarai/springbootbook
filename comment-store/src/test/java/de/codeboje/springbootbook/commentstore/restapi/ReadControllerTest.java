package de.codeboje.springbootbook.commentstore.restapi;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.codeboje.springbootbook.commentstore.service.CommentModelRepository;
import de.codeboje.springbootbook.commentstore.service.CommentService;
import de.codeboje.springbootbook.model.CommentModel;

@RunWith(SpringRunner.class)
@SpringBootTest()
@WebAppConfiguration
@ActiveProfiles({ "test", "FS" })
public class ReadControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private CommentModelRepository repository;

	@Autowired
	private CommentService service;

	private MockMvc mvc;

	private CommentModel model;

	private DateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");

	@Before
	public void setUp() throws Exception {
		repository.deleteAll();
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		CommentModel dummyModel = setupDummyModel();
		service.put(dummyModel);
		model = service.get(dummyModel.getId());
	}

	@Test
	public void testGetComments() throws Exception {
		this.mvc.perform(get("/list/" + model.getPageId()))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$[0].id", is(model.getId())))
				.andExpect(
						jsonPath("$[0].created", is(SDF.format(model
								.getCreationDate().getTime()))))
				.andExpect(jsonPath("$[0].username", is(model.getUsername())))
				.andExpect(jsonPath("$[0].comment", is(model.getComment())))
				.andExpect(
						jsonPath("$[0].email_address",
								is(model.getEmailAddress())));
	}

	@Test
	public void testGetSpamComments() throws Exception {
		this.mvc.perform(get("/listspam/" + model.getPageId()))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$", hasSize(0)));
		;
	}

	@Test
	public void testGetCommentsNotFound() throws Exception {
		this.mvc.perform(get("/list/2")).andExpect(status().is(404));
	}

	private CommentModel setupDummyModel() {
		model = new CommentModel();
		model.setUsername("testuser");
		model.setId("dqe345e456rf34rw");
		model.setPageId("product0815");
		model.setEmailAddress("example@example.com");
		model.setComment("I am the comment");
		return model;
	}
}
