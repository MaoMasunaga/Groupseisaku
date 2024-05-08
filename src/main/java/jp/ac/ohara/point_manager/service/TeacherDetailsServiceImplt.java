package jp.ac.ohara.point_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.ac.ohara.point_manager.model.TeacherModel;
import jp.ac.ohara.point_manager.repository.TeacherRepository;

@Service
public class TeacherDetailsServiceImplt implements UserDetailsService {

	@Autowired
	private TeacherRepository userRepository; // ユーザモデルのRepository
	

	/**
	 * ユーザの検索を行う
	 */
	@Override
	public UserDetails loadUserByUsername(String tid) throws UsernameNotFoundException {
		System.out.println("serach tid : " + tid);
		TeacherModel user = this.userRepository.findByTidEquals(tid); // emailで検索するので「EmailEquals」としている
		System.out.println(user.toString());
		String abc = "111";
		
		return user;
	
}
}