package guru.springframework.msscbrewery.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

public class Customer {

    private UUID id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

}
