package service;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import model.Usuario;

/**
 * Session Bean implementation class UsuarioService
 */
@Stateless
public class UsuarioService {
	
	@PersistenceContext
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public UsuarioService() {
        // TODO Auto-generated constructor stub
    }
    
    
    public Usuario persistir(Usuario usuario) {
    	
    	Usuario u = new Usuario();
    	try {
			
    		String email = em.createNativeQuery("SELECT VALIDA_EMAIL('"+usuario.getEmail()+"') FROM DUAL").getSingleResult().toString();
    		
    		if (Integer.parseInt(email) != 1) {    			
    			u = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email")
    				.setParameter("email", usuario.getEmail().toUpperCase())
    				.getSingleResult();
    			
    			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email já cadastrado!", null);
    	    	FacesContext.getCurrentInstance().addMessage(null, msg);
    			
    	    	return u;
    		} 
    		
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email inválido!", null);
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		
    		return u;
    	} catch (NoResultException e) {
    		
    		em.persist(usuario);
    		return null;    		
    	} 
    }
    
    public String remover(Long id) {
    	em.remove(ver(id));
		return null;
    }
    
    public List<Usuario> listar() {
    	return em.createQuery("FROM Usuario u", Usuario.class).getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<Usuario> listarValidacao() {
    	
    	List<Number> aux = (List<Number>) em.createNativeQuery("SELECT USUARIO_ID FROM VALIDACAO").getResultList(); 
    	List<Usuario> usuarios = new ArrayList<>();
    	
    	if (aux.size() != 0) {
    		for (int i = 0; i < aux.size(); i++) { 
    			Long id = Long.parseLong(aux.get(i).toString());
    			usuarios.add(ver(id));
    		}
    		return usuarios;
    	} 
    	return null;
    }
    
   
    public Usuario logar(String email, String senha) {
    	
    	try {
    		Usuario u = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha =:senha")
    				.setParameter("email", email.toUpperCase())
    				.setParameter("senha", senha)
    				.getSingleResult();
    		return u;
    	} catch(NoResultException e) {
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de login", null);
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		return null;
    	} 
    	
    }
    
    public Usuario ver(Long id) {
    	Usuario usuario = em.find(Usuario.class, id);
    	return usuario;
    }
    
    public Usuario editar(Usuario u) {  
    	
    	Usuario usuario = new Usuario();
    	try {
    		u.setEmail(u.getEmail().toUpperCase());    	
    		String email = em.createNativeQuery("SELECT VALIDA_EMAIL('"+u.getEmail()+"') FROM DUAL").getSingleResult().toString();
    		
    		if (Integer.parseInt(email) != 1) {    			
    			usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.id != :id")
    					.setParameter("email", u.getEmail().toUpperCase())
    					.setParameter("id", u.getUsuario_id())
    					.getSingleResult();
    			
    			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email já cadastrado!", null);
    	    	FacesContext.getCurrentInstance().addMessage(null, msg);
    			
    	    	return usuario;
    		} 
    		
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email inválido!", null);
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		
    		return usuario;			
		} catch (NoResultException e) {
			em.merge(u); 
			return null;
		}
	}
    
public void validar(Usuario u) {  
		u.setRole(2);
		em.merge(u);
	}
}
