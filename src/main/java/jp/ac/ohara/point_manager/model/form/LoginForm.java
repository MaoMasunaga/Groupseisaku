package jp.ac.ohara.point_manager.model.form;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LoginForm {
	

	@Column(length = 10, nullable = false)
	private String tid;
	
	@Column(length = 30, nullable = false)
	private String password;
	

}
