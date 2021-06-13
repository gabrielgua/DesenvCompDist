package model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_SEQUENCE")
	@SequenceGenerator(name="USUARIO_SEQUENCE", sequenceName="USUARIO_SEQUENCE")
	@Column(name="usuario_id")
	private Long usuario_id;
	
	@Column
	private String email;
	@Column
	private String senha;
	@Column
	private int role;
	
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
		
	}   
	public Long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
  
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + role;
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((usuario_id == null) ? 0 : usuario_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (role != other.role)
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (usuario_id == null) {
			if (other.usuario_id != null)
				return false;
		} else if (!usuario_id.equals(other.usuario_id))
			return false;
		return true;
	}
	
	
	
	
	
   
	
	
}
