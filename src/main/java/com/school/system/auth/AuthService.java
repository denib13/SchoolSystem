package com.school.system.auth;

import com.school.system.config.JwtService;
import com.school.system.exception.NotFoundException;
import com.school.system.users.headmaster.*;
import com.school.system.users.parents.*;
import com.school.system.users.student.*;
import com.school.system.users.teacher.*;
import com.school.system.users.user.User;
import com.school.system.users.user.UserMapper;
import com.school.system.users.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthService {
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final ParentService parentService;
    private final ParentRepository parentRepository;
    private final HeadmasterService headmasterService;
    private final HeadmasterRepository headmasterRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(StudentService studentService,
                       StudentRepository studentRepository,
                       TeacherService teacherService,
                       TeacherRepository teacherRepository,
                       ParentService parentService,
                       ParentRepository parentRepository,
                       HeadmasterService headmasterService,
                       HeadmasterRepository headmasterRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
        this.parentService = parentService;
        this.parentRepository = parentRepository;
        this.headmasterService = headmasterService;
        this.headmasterRepository = headmasterRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Optional<Student> student = studentRepository.findByUsername(request.getUsername());
        if(student.isPresent()) {
            String token = jwtService.generateToken(student.get());
            return new AuthenticationResponse(token, StudentMapper.studentToStudentResponseDTO(student.get()));
        }

        Optional<Teacher> teacher = teacherRepository.findByUsername(request.getUsername());
        if(teacher.isPresent()) {
            String token = jwtService.generateToken(teacher.get());
            return new AuthenticationResponse(token, TeacherMapper.teacherToTeacherResponseDTO(teacher.get()));
        }

        Optional<Parent> parent = parentRepository.findByUsername(request.getUsername());
        if(parent.isPresent()) {
            String token = jwtService.generateToken(parent.get());
            return new AuthenticationResponse(token, ParentMapper.parentToParentResponseDTO(parent.get()));
        }

        Optional<Headmaster> headmaster = headmasterRepository.findByUsername(request.getUsername());
        if(headmaster.isPresent()) {
            String token = jwtService.generateToken(headmaster.get());
            return new AuthenticationResponse(token, HeadmasterMapper.headmasterToHeadmasterResponseDTO(headmaster.get()));
        }

        Optional<User> admin = userRepository.findByUsername(request.getUsername());
        if(admin.isPresent()) {
            String token = jwtService.generateToken(admin.get());
            return new AuthenticationResponse(token, UserMapper.userToUserResponseDTO(admin.get()));
        }

        throw new NotFoundException("User not found");
    }

    public AuthenticationResponse register(RegisterRequest request) {
        String jwtToken = null;
        User user = null;
        if(request.getType().equals("student")) {
            StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
            studentRequestDTO.setName(request.getName());
            studentRequestDTO.setMiddleName(request.getMiddleName());
            studentRequestDTO.setSurname(request.getSurname());
            studentRequestDTO.setNationalIdNumber(request.getNationalIdNumber());
            studentRequestDTO.setUsername(request.getUsername());
            studentRequestDTO.setPassword(passwordEncoder.encode(request.getPassword()));
            studentRequestDTO.setEmail(request.getEmail());
            studentRequestDTO.setSchool(request.getSchool());
            studentRequestDTO.setSchoolClass(request.getSchoolClass());
            user = studentService.createStudent(studentRequestDTO);
            jwtToken = jwtService.generateToken(user);
        }
        else if(request.getType().equals("teacher")) {
            TeacherRequestDTO teacherRequestDTO = new TeacherRequestDTO();
            teacherRequestDTO.setName(request.getName());
            teacherRequestDTO.setMiddleName(request.getMiddleName());
            teacherRequestDTO.setSurname(request.getSurname());
            teacherRequestDTO.setNationalIdNumber(request.getNationalIdNumber());
            teacherRequestDTO.setUsername(request.getUsername());
            teacherRequestDTO.setPassword(passwordEncoder.encode(request.getPassword()));
            teacherRequestDTO.setEmail(request.getEmail());

            user = teacherService.createTeacher(teacherRequestDTO);
            jwtToken = jwtService.generateToken(user);
        }
        else if(request.getType().equals("parent")) {
            ParentRequestDTO parentRequestDTO = new ParentRequestDTO();
            parentRequestDTO.setName(request.getName());
            parentRequestDTO.setMiddleName(request.getMiddleName());
            parentRequestDTO.setSurname(request.getSurname());
            parentRequestDTO.setNationalIdNumber(request.getNationalIdNumber());
            parentRequestDTO.setUsername(request.getUsername());
            parentRequestDTO.setPassword(passwordEncoder.encode(request.getPassword()));
            parentRequestDTO.setEmail(request.getEmail());

            user = parentService.createParent(parentRequestDTO);
            jwtToken = jwtService.generateToken(user);
        }
        else if(request.getType().equals("headmaster")) {
            HeadmasterRequestDTO headmasterRequestDTO = new HeadmasterRequestDTO();
            headmasterRequestDTO.setName(request.getName());
            headmasterRequestDTO.setMiddleName(request.getMiddleName());
            headmasterRequestDTO.setSurname(request.getSurname());
            headmasterRequestDTO.setNationalIdNumber(request.getNationalIdNumber());
            headmasterRequestDTO.setUsername(request.getUsername());
            headmasterRequestDTO.setPassword(passwordEncoder.encode(request.getPassword()));
            headmasterRequestDTO.setEmail(request.getEmail());
            headmasterRequestDTO.setSchool(request.getSchool());

            user = headmasterService.createHeadmaster(headmasterRequestDTO);
            jwtToken = jwtService.generateToken(user);
        }
        else if(request.getType().equals("admin")) {
            user = new User(
                    request.getName(),
                    request.getMiddleName(),
                    request.getSurname(),
                    request.getNationalIdNumber(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getEmail()
            );
            userRepository.save(user);
            jwtToken = jwtService.generateToken(user);
        }

        return new AuthenticationResponse(jwtToken, UserMapper.userToUserResponseDTO(user));
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        User admin = new User(
                request.getName(),
                request.getMiddleName(),
                request.getSurname(),
                request.getNationalIdNumber(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail()
        );
        userRepository.save(admin);
        String jwtToken = jwtService.generateToken(admin);
        return new AuthenticationResponse(jwtToken, UserMapper.userToUserResponseDTO(admin));
    }
}
