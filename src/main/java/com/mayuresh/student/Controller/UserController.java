package com.mayuresh.student.Controller;

import com.mayuresh.student.Models.User;
import com.mayuresh.student.RequestDTO.AdminUserResponse;
import com.mayuresh.student.RequestDTO.UserDto;
import com.mayuresh.student.Response.CommonResponse;
import com.mayuresh.student.Response.GenericResponse;
import com.mayuresh.student.Service.UserService;

import com.mayuresh.student.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;





    @PostMapping("/create")
    public GenericResponse<User> createUser(@RequestBody UserDto userDto){
        GenericResponse<User> response = new GenericResponse<>();
        try {
            response.setData(userService.createUser(userDto));
            response.setStatus("Success");
        }catch (Exception e){
            response.setStatus("Failed");
            response.setError(e.getMessage());
            logger.info("exception while saving the new user");
//            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/hellouser")
    public String sayHello(){
        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
        return "hello User";
    }


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody User authRequest) {
        System.out.println("authenticateAndGetToken,authenticateAndGetToken");
        logger.info("username:"+authRequest.getUsername()+" "+"password:"+authRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/example")
    public String getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return roles.toString();
    }

    @PostMapping("/tryauthenticate")
    public GenericResponse<AdminUserResponse> createTokenNew(@RequestBody User request) {
        System.out.println("lets try to authenticate this api");
        GenericResponse<AdminUserResponse> response = new GenericResponse<AdminUserResponse>();
        try {
            AdminUserResponse adminResponse = new AdminUserResponse();
            System.out.println("username : "+request.getUsername()+","+ "password : "+request.getPassword());
            this.authenticate(request.getUsername(), request.getPassword());

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
            System.out.println("userdetails : " + userDetails);

            String token = jwtService.generateToken(request.getUsername());

            //adminResponse.setRole(userDetails.getAuthorities().toString()); //i got empty authorities that why comment out
            //adminResponse.setRole("ROLE_ADMIN");
            adminResponse.setUsername(request.getUsername());
            adminResponse.setToken(token);
            response.setData(adminResponse);
            response.setStatus("Success");
        } catch (Exception e) {
            System.out.println("ERROR:: "+e.getMessage());
            response.setStatus("Failed");
            response.setError("Invalid Credentials : "+ e.getMessage());
        }
        return response;
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password !!");
        }
    }

    @GetMapping("/logout")
    public CommonResponse logout(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse commonresponse = new CommonResponse();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("AUTH"+" "+auth);
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            commonresponse.setError(null);
            commonresponse.setStatus("Success");
        }
        return commonresponse;
    }

}
