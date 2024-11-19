package com.example.demo.service;

import java.util.Optional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AdminDetails;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoDetails;
import com.example.demo.repo.AdminDetailRepository;
import com.example.demo.repo.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private UserInfoRepository repository;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AdminDetailRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserInfo> userDetail = repository.findByName(username);	
		//Converting userDetails to UserDetails
		return userDetail.map(UserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found" +username));
		
	}

	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		repository.save(userInfo);
		return "User Added Successfully";
	}
	
	public String createAdmin(AdminDetails adminDetails) {
				adminRepository.save(adminDetails);
		return "Admin created!";
	}
	public Optional<UserInfo> getUser(int id) {
		return repository.findById(id);
	
	}
	
	public Optional<AdminDetails> getAdmin(int id) {
		return adminRepository.findById(id);
	
	}
}
