package models;

import javax.persistence.*;

import play.Logger;

import java.lang.Override;
import java.lang.String;

@Entity(name= "Episodio")
public class Episodio implements Comparable<Episodio>{
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String nome;
	@Column
	private int numero;
	@Column
	private boolean assistido;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Temporada temporada;
	
	public Episodio() {
		assistido = false;
	}
	
	public Episodio(String nome, Temporada temp, int num){
		this();
		this.nome = nome;
		this.numero = num;
		this.temporada = temp;
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isAssistido() {
		return assistido;
	}

	public void setAssistido(boolean assistido) {
		this.assistido = assistido;
		this.temporada.verificaStatus();
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}

	@Override
	public int compareTo(Episodio outroEpisodio) {
		if (this.getNumero()>outroEpisodio.getNumero()) {
			return 1;
		} else if (this.getNumero()<outroEpisodio.getNumero()) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "S" + temporada.getNumero() + " EP" + numero + " " + nome;
	}
}
