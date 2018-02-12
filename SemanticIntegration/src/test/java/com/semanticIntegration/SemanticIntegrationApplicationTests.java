package com.semanticIntegration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.apache.catalina.connector.Response;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.semanticIntegration.controller.AppController;
import com.semanticIntegration.model.Queryer;
import com.semanticIntegration.model.RevenueCalculatorImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SemanticIntegrationApplicationTests {

	@Autowired
	private AppController appController;
	
	@Mock
	private Repository sparqlRepository;
	@Mock private SPARQLRepository spRepo;
	@Mock RevenueCalculatorImpl rev; 
	
	@Before
	public void setUp() {
	
	    MockitoAnnotations.initMocks(this);
	
	 }

	@Test
	public void contextLoads() {
		assertThat(appController, is(notNullValue()));
	}
	
	/**
	 * 
	 */
	@Test
	public void shouldTestRevenueCalc()
	{
		BigDecimal input1 = new BigDecimal(10);
		BigDecimal input2 = new BigDecimal(10);
		BigDecimal input3 = new BigDecimal(0);
		BigDecimal input4 = null;
		BigDecimal answer1 = new BigDecimal(11.11);
		when(rev.calculateRevenue(input1, input2)).thenReturn(answer1);
		when(rev.calculateRevenue(Matchers.any(BigDecimal.class), Matchers.isNull(BigDecimal.class))).thenReturn(input4);
		assertEquals(rev.calculateRevenue(input1,input2), answer1);
		assertEquals(rev.calculateRevenue(input3,null),input4);
	}
	
	
	/**
	 * @throws Exception 
	 * Basic test to show that result is ok
	 */
	@Test
	public void testRestClient() throws Exception
	{
		AppController app = mock(AppController.class);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(app).build();
		mockMvc.perform(get("/service/pagination/search")).andExpect(status().isOk());
	    
	}
	
	
	
}
