package dao;

import org.springframework.stereotype.Repository;

import entity.User;

@Repository("userDao")
public interface UserDao {

	public User findUserByU_id(String u_id);
	public void addUser(User user);
}
