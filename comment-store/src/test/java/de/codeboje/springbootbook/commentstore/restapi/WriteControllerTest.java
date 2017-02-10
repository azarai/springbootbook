package de.codeboje.springbootbook.commentstore.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.codeboje.springbootbook.commentstore.service.CommentService;
import de.codeboje.springbootbook.model.CommentModel;


@RunWith(SpringRunner.class)
@SpringBootTest()
@WebAppConfiguration
public class WriteControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CommentService service;
    
    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }
    
    @Test
    public void testPost() throws Exception {

        CommentModel model = setupDummyModel();
        
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post("/create")
                        .param("comment", model.getComment())
                        .param("pageId", model.getPageId())
                        .param("emailAddress", model.getEmailAddress())
                        .param("username", model.getUsername()))
                    .andExpect(status().is(200)).andReturn();
        
        String id = result.getResponse().getContentAsString();
        CommentModel dbModel = service.get(id);
		assertNotNull(dbModel);
		assertEquals(model.getComment(), dbModel.getComment());
		assertEquals(model.getPageId(), dbModel.getPageId());
		assertEquals(model.getUsername(), dbModel.getUsername());
		assertEquals(model.getEmailAddress(), dbModel.getEmailAddress());

		assertNotNull(dbModel.getLastModificationDate());
		assertNotNull(dbModel.getCreationDate());
		assertFalse(model.isSpam());

    }

    private CommentModel setupDummyModel() {
    	CommentModel model = new CommentModel();
		model.setUsername("testuser");
		model.setId("dqe345e456rf34rw");
		model.setPageId("product0815");
		model.setEmailAddress("example@example.com");
		model.setComment("I am the comment");
		return model;
    }

    @Test
    public void testDelete() throws Exception {
    	CommentModel model = new CommentModel();
		model.setUsername("testuser");
		model.setId("dqe345e456rf34rw");
		model.setPageId("product0815");
		model.setEmailAddress("example@example.com");
		model.setComment("I am the comment");
        String id = service.put(model);
        
        this.mvc.perform(delete("/" + id)).andExpect(status().isOk());
        
        assertNull(service.get(model.getId()));        
    }
    
}
