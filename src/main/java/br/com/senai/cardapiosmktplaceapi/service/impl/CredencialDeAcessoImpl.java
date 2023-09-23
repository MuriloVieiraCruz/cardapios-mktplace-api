package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Usuario;
import br.com.senai.cardapiosmktplaceapi.repository.UsuarioRepository;
import br.com.senai.cardapiosmktplaceapi.security.CredencialDeAcesso;

@Service
public class CredencialDeAcessoImpl implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = repository.buscarPor(login);
		Preconditions.checkNotNull(usuario,
				"O usuário não existe");
		return new CredencialDeAcesso(usuario);
	}
}
