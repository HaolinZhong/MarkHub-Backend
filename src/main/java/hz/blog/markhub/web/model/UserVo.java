package hz.blog.markhub.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserVo implements Serializable {
    @Null(message = "User id should be null")
    private Long id;

    @NotBlank(message = "Username cannot be null or blank")
    private String name;

    private String avatar;

    @NotBlank(message = "Email cannot be null or blank")
    @Email(message = "Invalid email format")
    private String email;
}
