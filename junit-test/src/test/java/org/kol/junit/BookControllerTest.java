package org.kol.junit;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter= objectMapper.writer();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book RECORD_1=new Book(1L, "a", "b",1);
    Book RECORD_2=new Book(2L, "b", "b",1);
    Book RECORD_3=new Book(3L, "c", "b",1);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllRecords_sucess() throws  Exception{
        List<Book> records=new ArrayList<>(Arrays.asList(RECORD_1,RECORD_2,RECORD_3));
        Mockito.when(bookRepository.findAll()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("c")))
                .andExpect(jsonPath("$[1].name", is("b")));;

    }

    @Test
    public void getBookById_sucess() throws  Exception{
        List<Book> records=new ArrayList<>(Arrays.asList(RECORD_1,RECORD_2,RECORD_3));
        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(java.util.Optional.of(RECORD_1));
        mockMvc.perform(MockMvcRequestBuilders.get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("a")));;

    }

    @Test
    public void createRecord_sucess() throws  Exception{

        Book record=Book.builder()
                .bookId(4L)
                .name("intro c")
                .summary("summary c")
                .rating(5)
                .build();

        Mockito.when(bookRepository.save(record)).thenReturn(record);

        String content=objectWriter.writeValueAsString(record);

         MockHttpServletRequestBuilder mockRequest=  MockMvcRequestBuilders.post("/book")
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                 .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
               .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("intro c")));

    }

    @Test
    public void updateRecord_sucess() throws  Exception{

        Book updateRecord=Book.builder()
                .bookId(4L)
                .name("update to java")
                .summary("summary update")
                .rating(5)
                .build();

        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(java.util.Optional.ofNullable(RECORD_1));
        Mockito.when(bookRepository.save(updateRecord)).thenReturn(updateRecord);

        String UpdateContent=objectWriter.writeValueAsString(updateRecord);

        MockHttpServletRequestBuilder mockRequest=  MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(UpdateContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("update to java")));

    }

    @Test
    public void deleteRecordById_sucess() throws  Exception{


        Mockito.when(bookRepository.findById(RECORD_2.getBookId())).thenReturn(Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/book/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    //getBookByIdnot found
    //public void not found()
}
