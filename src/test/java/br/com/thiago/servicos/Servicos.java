package br.com.thiago.servicos;

public enum Servicos {
	
	getUsers_PAGE ("/api/users?page={id}"),
	getUser_ID ("/api/users/{id}"),
	postUser ("/api/users"),
	putUser_ID ("/api/users/{id}"),
	deleteUser_ID ("/api/users/{id}");
	
	private final String valor;
	
	Servicos(String valor){
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}
}
