package com.hoaxify.hoaxifybackend;

import com.hoaxify.hoaxifybackend.hoax.Hoax;
import com.hoaxify.hoaxifybackend.hoax.HoaxService;
import com.hoaxify.hoaxifybackend.hoax.vm.HoaxSubmitVm;
import com.hoaxify.hoaxifybackend.user.User;
import com.hoaxify.hoaxifybackend.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

//exclude = SecurityAutoConfiguration.class
@SpringBootApplication()
@EnableJpaRepositories
public class HoaxifyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoaxifyBackendApplication.class, args);
    }

    @Bean
        //@Profile("dev")
    CommandLineRunner createInitialUsers(UserService userService, HoaxService hoaxService) {
        return (args) -> {
            try {
                userService.getUser("user1");
            } catch (Exception e) {
                for (int i = 0; i < 10; i++) {
                    User user = new User();
                    String username = "user" + i;

                    user.setUserName(username);
                    user.setDisplayName("display" + i);
                    user.setPassword("devS2002");
                    userService.save(user);
                    for (int j = 0; j < 15; j++) {
                        HoaxSubmitVm hoax = new HoaxSubmitVm();
                        hoax.setContent("Hoax" + j + "from " + i);
                        hoaxService.saveHoax(hoax, user);
                    }
                }
            }
        };
    }

}
