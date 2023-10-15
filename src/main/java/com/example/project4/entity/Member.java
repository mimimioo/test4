package com.example.project4.entity;

import com.example.project4.constant.Role;
import com.example.project4.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.tomcat.jni.Address;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter @Setter
@ToString
public class Member extends BaseEntity{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)


    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    private String detail_Address;
    @Column(name = "zipCode")
    private int zipCode;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Like> likeList = new ArrayList<>();


    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setDetail_Address(memberFormDto.getDetail_Address());
        member.setZipCode(memberFormDto.getZipCode());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }

}
