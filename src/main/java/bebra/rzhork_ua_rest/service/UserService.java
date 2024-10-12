package bebra.rzhork_ua_rest.service;

import bebra.rzhork_ua_rest.model.dto.UserWithCompanyDTO;
import bebra.rzhork_ua_rest.model.entity.Company;
import bebra.rzhork_ua_rest.model.entity.Role;
import bebra.rzhork_ua_rest.model.entity.User;
import bebra.rzhork_ua_rest.repository.RoleRepository;
import bebra.rzhork_ua_rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(String username, String password) {
        User userFromDB = userRepository.findByUsername(username);
        if (userFromDB != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        Role role = roleRepository.findByName("ROLE_JOBSEEKER");
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    public User saveUserCompany(UserWithCompanyDTO dto) {
        System.out.println(dto.getUsername());
        User userFromDB = userRepository.findByUsername(dto.getUsername());

        if (userFromDB != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        Role role = roleRepository.findByName("ROLE_COMPANY");
        user.setRoles(Set.of(role));

        Company company = new Company();
        company.setTitle(dto.getCompanyTitle());
        company.setDescription(dto.getCompanyDescription());
        company.setLocation(dto.getCompanyLocation());
        company.setJoinYear(dto.getCompanyJoinYear());

        user.setCompany(company);
        company.setUser(user);
        return userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Company getCompanyByUsername(String username) {
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User user = this.findByUsername(username);
        if (user == null || user.getCompany() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        }
        return user.getCompany();
    }
}
