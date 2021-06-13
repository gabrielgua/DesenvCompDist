package control;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Usuario;
import service.UsuarioService;

@Named("UsuarioCT")
@ConversationScoped
public class UsuarioCT implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
	private List<Usuario> usuarios;
	private List<Usuario> validar;
	
	@Inject
	private UsuarioService service;
	@Inject
	private Conversation conversation;
		
	public UsuarioCT() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		usuarios = service.listar();
		validar = service.listarValidacao();
		
	}
	
	public String cadastrar() {
		usuario.setUsuario_id(null);
		usuario.setEmail(usuario.getEmail().toUpperCase());
		
		if (service.persistir(usuario) == null) {
			setUsuarios(service.listar());
			return "index.xhtml?faces-redirect=true";			
		}
		
		return "cadastrar.xhtml";
	}
	
	public String login(String e, String s) {
		Usuario u = service.logar(e, s);
		if (u != null) {
			conversation.begin();
			setUsuario(u);
				
			if (u.getRole() == 1) {
				return "admin.xhtml?faces-redirect=true";
			} else if (u.getRole() == 2) {
				System.out.println("2");
				return "main.xhtml?faces-redirect=true";
			} else {	
				conversation.end();
				System.out.println("fodase");
				System.out.println(u.getEmail());
				System.out.println(u.getRole());
				return "Conta em Validação!";
			}
		} 
		conversation.begin();
		conversation.end();
		return "Erro de login!";
	}
	
	public String index(Usuario u) {
		setUsuario(u);
		return "/usuario/main.xhtml?faces-redirect=true"; 
	}
	
	public String editar(Long id) {
		usuario = service.ver(id);
		return "/usuario/editar.xhtml";
	}
	
	public String remover(Long id) {
		
		if (service.remover(id) == null) {
			setUsuarios(service.listar());
			setValidar(service.listarValidacao());
			conversation.end();
			return "/usuario/index.xhtml?faces-redirect=true";			
		}
		
		return "/usuario/editar.xhtml";
		
	}
	
	public String admin(Usuario u) {
		setUsuario(u);
		return "/usuario/admin.xhtml?faces-redirect=true";
	}
	
	public void removerValidacao(Long id) {
		service.remover(id);
		validar = service.listarValidacao();
		admin(usuario);
	}
	
	public String aplicar() {		
		
		if (service.editar(usuario) == null) {
			usuarios = service.listar();
			validar = service.listarValidacao();
			return "/usuario/main.xhtml?faces-redirect=true";			
		}
		
		return "/usuario/editar.xhtml";
		
	}
	
	public void validar(Long id) {
		Usuario u = service.ver(id);
		service.validar(u);
		validar = service.listarValidacao();
		admin(usuario);
	}
	
	
	
	public List<Usuario> getValidar() {
		return validar;
	}

	public void setValidar(List<Usuario> validar) {
		this.validar = validar;
	}

	public Usuario Logado(Long id) {
		Usuario u = service.ver(id);
		return u;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
