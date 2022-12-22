package uzum.trelloapp.dto.auth;

import lombok.*;
import uzum.trelloapp.entity.User;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {

    private Long expireIn;

    private String accessToken;

    private String refreshToken;

    private User user;

}
