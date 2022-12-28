package uzum.trelloapp.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.auth.ReqLoginDTO;
import uzum.trelloapp.dto.auth.ReqRefreshTokenDTO;
import uzum.trelloapp.dto.auth.ReqRegDTO;
import uzum.trelloapp.dto.auth.SessionDTO;
import uzum.trelloapp.dto.user.*;
import uzum.trelloapp.entity.Role;
import uzum.trelloapp.entity.User;
import uzum.trelloapp.enums.RoleType;
import uzum.trelloapp.exception.AlreadyExistsException;
import uzum.trelloapp.exception.UserNotFoundException;
import uzum.trelloapp.helper.Utils;
import uzum.trelloapp.mapper.UserMap;
import uzum.trelloapp.repo.RoleRepo;
import uzum.trelloapp.repo.UserRepo;
import uzum.trelloapp.service.UserServ;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServImpl implements UserServ {

    private final UserRepo repo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMap mapper;

    @Override
    public ResponseEntity<ResponseData<List<UserDTO>>> getAll() {
        List<User> list = repo.findAllByDeletedIsFalseAndActiveIsTrue();
        if (list.isEmpty()) {
            log.warn("Foydalanuvchilar ro'yxati topilmadi!");
            return ResponseData.notFoundData("Users are not found !!!");
        }
        List<UserDTO> dtoList = new ArrayList<>();
        list.forEach(user -> dtoList.add(mapper.toDto(user)));
        log.info("Barcha foydalanuvchilar ro'yxati olindi");
        return ResponseData.success200(dtoList);
    }

    @Override
    public ResponseEntity<ResponseData<UserDTO>> get(UUID uuid) {
        Optional<User> user = repo.findByUuid(uuid);
        if (Utils.isEmpty(user)) {
            log.error("User uuid, {} bo'yicha ma'lumot topilmadi", uuid);
            ResponseData.notFoundData("User not found !!!");
        }
        log.info("User uuid, {} bo'yicha ma'lumot olindi", uuid);
        return ResponseData.success200(mapper.toDto(user.get()));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<UserDTO>> add(UserCrDTO dto) {
        String roleName = RoleType.ROLE_USER.getDescription();

        Optional<User> username = repo.findByUsername(dto.getUsername());
        if (Utils.isPresent(username)) {
            return ResponseData.alreadyExists("This username is already registered! \n" +
                    "Please choose another username!");
        }

        Role role = roleRepo.findByName(roleName);
        User user = mapper.toEntity(dto);
        user.getRoles().add(role);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        repo.save(user);
        log.info("Yangi user - {} saqlandi", user.getFirstName());
        return ResponseData.success201(mapper.toDto(user));
    }

    /*@Override
    public ResponseEntity<ResponseData<UserDTO>> getLogin(String phone, String password) {
        Optional<User> user = repo.findByPhoneAndPassword(phone, password);
        if (user.isEmpty()) {
            return ResponseData.notFoundData("User not found !!!");
        } else if (!user.get().isActive()) {
            return ResponseData.inActive("This user is not active !!!");
        }
        return ResponseData.success200(mapper.toDto(user.get()));
    }*/

    @Transactional
    @Override
    public ResponseEntity<ResponseData<UserDTO>> edit(UserUpDTO dto) {
        Optional<User> optional = repo.findByUuid(dto.getUuid());
        if (optional.isEmpty()) {
            log.error("User uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            return ResponseData.notFoundData("User not found !!!");
        }
        User user = optional.get();
        if (!user.isActive()) {
            log.error("User uuid, {} bo'yicha faol emas!", dto.getUuid());
            return ResponseData.inActive("This user is not active !!!");
        }
        user = mapper.toEntity(user, dto);
        repo.save(user);
        log.info("User {} - ma'lumotlari yangilandi!", user.getFirstName());
        return ResponseData.success202(mapper.toDto(user));
    }

    @Override
    public ResponseEntity<ResponseData<UserDTO>> me(UserMeDTO dto) {
        Optional<User> user = repo.findByUuid(dto.getUuid());
        if (user.isEmpty()) {
            log.error("User uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            return ResponseData.notFoundData("User not found !!!");
        } else if (!user.get().isActive()) {
            log.error("User uuid, {} bo'yicha faol emas!", dto.getUuid());
            return ResponseData.inActive("This user is not active !!!");
        } else if (!user.get().getPassword().equals(dto.getPassword())) {
            log.error("User uuid, {} bo'yicha parol mos emas!", dto.getUuid());
            return ResponseData.notFoundData("Password is incorrect !!!");
        }
        return ResponseData.success200(mapper.toDto(user.get()));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<UserDTO>> delete(UserDelDTO dto) {

        Optional<User> optional = repo.findByUuid(dto.getUuid());
        if (optional.isEmpty()) {
            log.error("User uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            return ResponseData.notFoundData("User not found !!!");
        }
        User user = optional.get();
        if (user.isDeleted()) {
            log.error("User uuid, {} bo'yicha oldin o'chirilgan", dto.getUuid());
            return ResponseData.isDeleted("This user was previously disabled !!!");
        } else if (!user.getPassword().equals(dto.getPassword())) {
            log.error("User uuid, {} bo'yicha parol mos emas!", dto.getUuid());
            return ResponseData.errorStatus("Password is incorrect", HttpStatus.NOT_FOUND);
        }
        user.setDeleted(true);
        user.setActive(false);
        user.setPhone(user.getPhone() + "_isDel_" + System.currentTimeMillis());
        repo.save(user);
        log.info("User uuid, {} bo'yicha o'chirildi!", dto.getUuid());
        return ResponseData.success200(mapper.toDto(user));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<UserDTO>> changePassword(UserChPDTO dto) {
        Optional<User> optional = repo.findByUuid(dto.getUuid());
        if (optional.isEmpty()) {
            log.error("User uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            return ResponseData.notFoundData("User not found !!!");
        } else if (!optional.get().isActive()) {
            log.error("User uuid, {} bo'yicha faol emas!", dto.getUuid());
            return ResponseData.inActive("This user is not active !!!");
        }
        User user = optional.get();
        if (!user.getPassword().equals(dto.getOldPassword())) {
            log.error("User uuid, {} bo'yicha eski parol mos emas!", dto.getUuid());
            return ResponseData.errorStatus("Old password is incorrect", HttpStatus.NOT_FOUND);
        }
        user = mapper.toEntity(user, dto);
        repo.save(user);
        return ResponseData.success202(mapper.toDto(user));
    }

    public User findByUuid(UUID uuid) throws UserNotFoundException {
        Optional<User> userOptional = repo.findByUuid(uuid);
        if (userOptional.isEmpty()) {
            log.error("User uuid, {} bo'yicha ma'lumot topilmadi", uuid);
            throw new UserNotFoundException("User is not found !!!");
        }
        return userOptional.get();
    }

    @Override
    public User findByPhone(String phone) throws UsernameNotFoundException {
        Optional<User> userOptional = repo.findByPhone(phone);
        if (userOptional.isEmpty()) {
            log.error("User phone, {} bo'yicha ma'lumot topilmadi", phone);
            throw new UsernameNotFoundException("This user is not found!!!");
        }
        return userOptional.get();
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repo.findByUsername(username);
        if (userOptional.isEmpty()) {
            log.error("User username, {} bo'yicha ma'lumot topilmadi", username);
            throw new UsernameNotFoundException("This user is not found!!!");
        }
        return userOptional.get();
    }

    @Transactional
    @Override
    public Role saveRole(Role role) {
        log.info("Yangi role - {} saqlandi", role.getName());
        return roleRepo.save(role);
    }

    @Transactional
    @Override
    public void attachRoleToUser(String phone, String roleName) {
        log.info("User {} ga {} - role biriktirildi!", phone, roleName);
        Optional<User> optional = repo.findByPhone(phone);
        User user = optional.get();
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
        repo.save(user);
    }

    @Override
    public ResponseEntity<ResponseData<UserDTO>> registration(ReqRegDTO req, HttpServletRequest httpReq) throws AlreadyExistsException {
        String roleName = RoleType.ROLE_USER.getDescription();

        Optional<User> optional = repo.findByPhone(req.getPhone());
        if (optional.isPresent()) {
            log.error("This user is already registered!");
            throw new AlreadyExistsException("This user is already registered!");
        }

        Role role = roleRepo.findByName(roleName);
        User user = mapper.toEntity(req);
        user.getRoles().add(role);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        repo.save(user);
        log.info("Yangi user - {} saqlandi", user.getFirstName());
        return ResponseData.success201(mapper.toDto(user));
    }

    @Override
    public ResponseEntity<SessionDTO> login(ReqLoginDTO req, HttpServletRequest httpReq) {
        if (Utils.isEmpty(req.getUsername())) {
            log.error("Username is required field!");
            throw new RuntimeException("Username is required field!");
        }
        if (Utils.isEmpty(req.getPassword())) {
            log.error("Password is required field!");
            throw new RuntimeException("Password is required field!");
        }

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("username", req.getUsername()));
            nameValuePairs.add(new BasicNameValuePair("password", req.getPassword()));

            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/login").toUriString());

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(httpPost);
            return getSessionDto(req, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("User username, {} bo'yicha ma'lumot topilmadi", req.getUsername());
            throw new RuntimeException("User not found !!!");
        }
    }

    @Override
    public ResponseEntity<SessionDTO> refreshToken(ReqRefreshTokenDTO req, HttpServletRequest httpReq) {
        if (Utils.isEmpty(req.getRefreshToken())) {
            throw new RuntimeException("Refresh token is missing");
        }
        try {
            String refresh_token = req.getRefreshToken();
            Algorithm algorithm = Algorithm.HMAC256("trello".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refresh_token);
            String username = decodedJWT.getSubject();
            Optional<User> user = repo.findByUsername(username);
            long expireIn = 60 * 60 * 1000;  // todo o'zgartirish mumkin 1 minutes
            String access_token = JWT.create()
                    .withSubject(user.get().getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireIn))   // 1 minutes
                    .withIssuer(httpReq.getRequestURL().toString())
                    .withClaim("roles", new ArrayList<>())  // todo set user roles
                    .sign(algorithm);
            SessionDTO sessionDto = SessionDTO.builder()
                    .expireIn(expireIn)
                    .accessToken(access_token)
                    .refreshToken(refresh_token)
                    .user(user.get())
                    .build();
            return ResponseEntity.ok(sessionDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<SessionDTO> getSessionDto(ReqLoginDTO req, HttpResponse response) throws IOException {
        JsonNode json_auth = new ObjectMapper().readTree(EntityUtils.toString(response.getEntity()));
        if (!json_auth.has("error") && !json_auth.has("detail_message")) {
            SessionDTO sessionDto = SessionDTO.builder()
                    .expireIn(json_auth.get("expires_in").asLong())
                    .accessToken(json_auth.get("access_token").asText())
                    .refreshToken(json_auth.get("refresh_token").asText())
                    .user(repo.findByUsername(req.getUsername()).get())
                    .build();
            return ResponseEntity.ok(sessionDto);
        } else {
            String error_message = "";
            if (json_auth.has("error")) {
                error_message = json_auth.get("error").asText();
            } else if (json_auth.has("detail_message")) {
                error_message = json_auth.get("detail_message").asText();
            }
            throw new RuntimeException(error_message);
        }
    }

    @Override
    public User checkUser(UUID uuid) {
        Optional<User> optional = repo.findByUuid(uuid);
        if (optional.isEmpty()) {
            throw new RuntimeException("User not found !!!");
        }
        User user = optional.get();
        if (!user.isActive()) {
            throw new RuntimeException("This user is not active !!!");
        }
        return user;
    }

}
