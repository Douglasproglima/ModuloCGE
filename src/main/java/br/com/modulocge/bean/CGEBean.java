package br.com.modulocge.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.modulocge.dao.CGEDAO;
import br.com.modulocge.domain.CGE;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CGEBean implements Serializable{
	private CGE cge;
	private List<CGE> cges;
	
	public CGE getCge() {
		return cge;
	}
	public void setCge(CGE cge) {
		this.cge = cge;
	}
	public List<CGE> getCges() {
		return cges;
	}
	public void setCges(List<CGE> cges) {
		this.cges = cges;
	}
	
	@PostConstruct
	public void listar(){
		try {
			CGEDAO cgedao = new CGEDAO();
			cges = cgedao.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar os registros, erro: "+ erro);
			erro.printStackTrace();
		}	
	}
	
	public void novo(){
		try {
			cge = new CGE();
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao inserir um novo registro, erro: "+ erro);
			erro.printStackTrace();
		}
	}
	
	public void salvar(){
		try {
			CGEDAO cgedao = new CGEDAO();
			
			cgedao.merge(cge);
			
			novo();
			
			cges = cgedao.listar();
			
			Messages.addGlobalInfo("Registro salvo com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao salvar o registro, erro: "+ erro);
			erro.printStackTrace();
		}
	}
	
	public void excluir(ActionEvent evento){
		try {
			cge =  (CGE) evento.getComponent().getAttributes().get("cgeSelecionado");
			
			CGEDAO cgedao = new CGEDAO();
			cgedao.excluir(cge);
			
			cges = cgedao.listar();
			
			Messages.addGlobalInfo("Registro removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir o registro, erro: "+ erro);
			erro.printStackTrace();
		}
	}
	
	public void editar(ActionEvent evento){
		try {
			cge =  (CGE) evento.getComponent().getAttributes().get("cgeSelecionado");
			
			Messages.addGlobalInfo("Registro alterado com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao editar o registro, erro: "+ erro);
			erro.printStackTrace();
		}
	}	
}
