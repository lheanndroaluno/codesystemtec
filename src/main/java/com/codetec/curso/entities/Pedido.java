package com.codetec.curso.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.codetec.curso.entities.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant dataPedido;

	private Integer status;

	// um ou vários pedidos corresponde a um cliente
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Usuario cliente;
	
	@OneToMany(mappedBy = "id.pedido")//um para muitos
	private Set<ItemPedido> itensPedido = new HashSet<>();
	
	@OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
	private Pagamento pagamento;

	public Pedido() {
	}

	public Pedido(Long id, Instant dataPedido, StatusPedido status, Usuario cliente) {
		super();
		this.id = id;
		this.dataPedido = dataPedido;
		setStatus(status);
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Instant dataPedido) {
		this.dataPedido = dataPedido;
	}

	public StatusPedido getStatus() {
		return StatusPedido.valueOf(status);
	}

	public void setStatus(StatusPedido status) {
		if (status != null) {
			this.status = status.getCodigo();
		}
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
	
	public Set<ItemPedido> getItensPedido(){
		return itensPedido;
	}
	
	public Pagamento getPagamento() {
		return pagamento;
	}
	
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	
	public Double getTotal() {
		double soma = 0.0;
		//para todo itemPedido
		for (ItemPedido x : itensPedido) {
			//soma = soma + x.getSubTotal();
			soma += x.getSubTotal();
		}
		
		return soma;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
