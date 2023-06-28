package org.kol.junit;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kol.junit.controller.ProductController;
import org.kol.junit.model.Product;
import org.kol.junit.repository.ProductRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ProductTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;


    private MockMvc mockMvc;

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter= objectMapper.writer();

    Product RECORD_1=new Product(1L, "apple", "iphone",5);
    Product RECORD_2=new Product(2L, "nokia", "lumia",2);
    Product RECORD_3=new Product(3L, "samsung", "galaxy",3);


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void createProduct_sucess() throws  Exception{

        Product product=Product.builder()
                .productId(4L)
                .name("iphone-12")
                .summary("Electronics")
                .rating(5)
                .build();

        Mockito.when(productRepository.save(product)).thenReturn(product);

        String content=objectWriter.writeValueAsString(product);

        MockHttpServletRequestBuilder mockRequest=  MockMvcRequestBuilders.post("/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("iphone-12")));

    }

    @Test
    public void getAllRecords_sucess() throws  Exception{
        List<Product> records=new ArrayList<>(Arrays.asList(RECORD_1,RECORD_2,RECORD_3));
        Mockito.when(productRepository.findAll()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders.get("/product/fetch-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("samsung")))
                .andExpect(jsonPath("$[1].name", is("nokia")));;

    }



}
