package com.project3_thuchanhweb.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project3_thuchanhweb.dto.RoleDTO;
import com.project3_thuchanhweb.entity.Role;
import com.project3_thuchanhweb.repository.RoleRepo;

public interface RoleService {
	void create(RoleDTO roleDTO);

	void updateName(RoleDTO roleDTO);

	void delete(int id);

	RoleDTO getbyId(int id);

}

@Service
class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepo roleRe;

	@Override
	@Transactional
	public void create(RoleDTO roleDTO) {
		Role role = new ModelMapper().map(roleDTO, Role.class);;
		roleRe.save(role);
		roleDTO.setId(role.getId());
	}

	@Transactional
	@Override
	public void updateName(RoleDTO roleDTO) {
		Role role = roleRe.findById(roleDTO.getId()).orElse(null);
		if (role != null) {
			role.setName(roleDTO.getName());
			roleRe.save(role);
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		Role role = roleRe.findById(id).orElse(null);
		if (role != null) {
			roleRe.deleteById(id);
		}

	}

	@Override
	public RoleDTO getbyId(int id) {
		Role role = roleRe.findById(id).orElse(null);
		if (role != null) {
			RoleDTO roleDTO = convert(role);
			return roleDTO;
		}
		return null;
	}

	
	private RoleDTO convert(Role role) {
		return new ModelMapper().map(role, RoleDTO.class);
	}

}