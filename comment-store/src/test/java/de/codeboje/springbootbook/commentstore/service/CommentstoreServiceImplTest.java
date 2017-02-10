package de.codeboje.springbootbook.commentstore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.codeboje.springbootbook.model.CommentModel;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class CommentstoreServiceImplTest {

	@Autowired
	private CommentModelRepository repository;

	@Autowired
	private CommentService service;

	private CommentModel model;

	@Before
	public void setup() {
		model = new CommentModel();
		model.setUsername("testuser");
		model.setId("dqe345e456rf34rw");
		model.setPageId("product0815");
		model.setEmailAddress("example@example.com");
		model.setComment("I am the comment");
		repository.deleteAll();
	}

	@Test
	public void testPutAndGet() throws IOException {
		service.put(model);

		CommentModel dbModel = service.get(model.getId());
		assertNotNull(dbModel);
		assertEquals(model.getComment(), dbModel.getComment());
		assertEquals(model.getId(), dbModel.getId());
		assertEquals(model.getPageId(), dbModel.getPageId());
		assertEquals(model.getUsername(), dbModel.getUsername());
		assertEquals(model.getEmailAddress(), dbModel.getEmailAddress());

		assertNotNull(dbModel.getLastModificationDate());
		assertNotNull(dbModel.getCreationDate());
		assertFalse(model.isSpam());
	}

	@Test
	public void testListNotFound() throws IOException {
		service.put(model);
		List<CommentModel> r = service.list("sdfgsdwerwert");
		assertTrue(r.isEmpty());
	}

	@Test
	public void testList() throws IOException {
		service.put(model);
		List<CommentModel> r = service.list(model.getPageId());
		assertNotNull(r);
		assertEquals(1, r.size());
		assertEquals(model.getId(), r.get(0).getId());
	}
	
	@Test
	public void testListspam() throws IOException {
		model.setComment("I Love Viagra");
		service.put(model);
		List<CommentModel> r = service.listSpamComments(model.getPageId());
		assertNotNull(r);
		assertEquals(1, r.size());
		assertEquals(model.getId(), r.get(0).getId());
	}

}
