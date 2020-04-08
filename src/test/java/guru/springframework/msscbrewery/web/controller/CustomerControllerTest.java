package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto customerDto;

    private static final String BASE_URL = "/api/v1/customer";

    @Before
    public void setUp() {
        customerDto = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Johnnnnnn")
                .build();
    }

    @Test
    public void handleValidPost() throws Exception {
        CustomerDto dto = customerDto;
        String dtoJson = objectMapper.writeValueAsString(dto);

        CustomerDto savedCustomer = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("John")
                .build();

        given(customerService.saveNewCustomer(any())).willReturn(savedCustomer);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void handlePostNameNull() throws Exception {
        CustomerDto dto = customerDto;
        dto.setName(null);
        String dtoJson = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void handlePostSmallName() throws Exception {
        CustomerDto dto = customerDto;
        dto.setName("J");
        String dtoJson = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(dtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void handlePostLargeName() throws Exception {
        CustomerDto dto = customerDto;
        String name = "";
        for(int i=0; i<=100; i++) name = name.concat("N");
        dto.setName(name);
        String dtoJson = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(dtoJson))
                .andExpect(status().isBadRequest());
    }

}