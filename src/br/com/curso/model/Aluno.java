package br.com.curso.model;

import java.util.List;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import br.com.curso.utils.Constantes;

public class Aluno implements Parcelable {

	private int id;
	private Bitmap foto;
	private String nome;
	private String telefone;
	private String email;
	private String endereco;
	private String curso;
	private String sexo;
	private boolean matriculado;

	public Aluno() {
		
	}
	
	public Aluno(int id, Bitmap foto, String nome, String telefone, 
			String email, String endereco, 
			String curso, String sexo, boolean matriculado) {
		
		setId(id);
		setFoto(foto);
		setNome(nome);
		this.telefone = telefone;
		setEmail(email);
		setEndereco(endereco);
		setCurso(curso);
		setSexo(sexo);
		setMatriculado(matriculado);
	}

	public Aluno(List<String> conteudo) {		
		for (String linha : conteudo) {
			String [] split = linha.split("=");
			String chave = split[0];
			String valor = split[1];
			
			if(chave.equalsIgnoreCase("id")){
				setId(Integer.parseInt(valor));
			} if(chave.equalsIgnoreCase("nome")){
				setNome(valor);
			} if(chave.equalsIgnoreCase("telefone")){
				setTelefone(valor);
			} if(chave.equalsIgnoreCase("email")){
				setEmail(valor);
			} if(chave.equalsIgnoreCase("endereco")){
				setEndereco(valor);
			} if(chave.equalsIgnoreCase("curso")){
				setCurso(valor);
			} if(chave.equalsIgnoreCase("sexo")){
				setSexo(valor);
			} if(chave.equalsIgnoreCase("matriculado")){
				setMatriculado(Boolean.parseBoolean(valor));
			}
		}
		
	}

	public Aluno(Parcel in) {
		readFromParcel(in);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Bitmap getFoto() {
		return foto;
	}

	public void setFoto(Bitmap foto) {
		this.foto = foto;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public boolean isMatriculado() {
		return matriculado;
	}

	public void setMatriculado(boolean matriculado) {
		this.matriculado = matriculado;
	}
	
	@Override
	public String toString() {
		String saida = "";
		saida += "id=" + this.id;
		saida += Constantes.NEW_LINE;
		saida += "nome=" + this.nome;
		saida += Constantes.NEW_LINE;
		saida += "telefone=" + this.telefone;
		saida += Constantes.NEW_LINE;
		saida += "email=" + this.email;
		saida += Constantes.NEW_LINE;
		saida += "endereco=" + this.endereco;
		saida += Constantes.NEW_LINE;
		saida += "curso=" + this.curso;
		saida += Constantes.NEW_LINE;
		saida += "sexo=" + this.sexo;
		saida += Constantes.NEW_LINE;
		saida += "matriculado=" + this.matriculado;
		
		return saida;
	}

	//////////////////////////////////////////////////////////////
	// Parcelable
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeParcelable(foto, flags);
		dest.writeString(nome);
		dest.writeString(telefone);
		dest.writeString(email);
		dest.writeString(endereco);
		dest.writeString(curso);
		dest.writeString(sexo);
		dest.writeInt(matriculado ? 1 : 0);
	}
	
	private void readFromParcel(Parcel in) {
		id = in.readInt();
		foto = in.readParcelable(Bitmap.class.getClassLoader());
		nome = in.readString();
		telefone = in.readString();
		email = in.readString();
		endereco = in.readString();
		curso = in.readString();
		sexo = in.readString();
		matriculado = in.readInt() == 0 ? false : true;
	}

	public static final Parcelable.Creator<Aluno> CREATOR = new Parcelable.Creator<Aluno>() {
		public Aluno createFromParcel(Parcel in) {
			return new Aluno(in);
		}

		public Aluno[] newArray(int size) {
			return new Aluno[size];
		}
	};
}
