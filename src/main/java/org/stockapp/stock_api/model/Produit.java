package org.stockapp.stock_api.model;

import java.util.HashMap;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlRootElement;


/**
 *Created on 10/02/2022
 *by SAMBANY Michel Laurenzio 
 **/
@XmlRootElement
public class Produit {
	
	private String id;
	private String design;
	private int stock;

	private Map<String, BondeEntree> bondeEntrees;
	private Map<String, BondeSortie> bondeSorties;
	
	public Produit() {
		this.bondeEntrees = new HashMap<>();
		this.bondeSorties = new HashMap<>();
	}
	
	public Produit(String design, int stock) {
		super();
		this.design = design;
		this.stock = stock;
		this.bondeEntrees = new HashMap<>();
		this.bondeSorties = new HashMap<>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesign() {
		return design;
	}
	public void setDesign(String design) {
		this.design = design;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * Return Collection | BondeEntree
	 **/
	
	public Map<String, BondeEntree> getBondeEntrees() {
		return bondeEntrees;
	}
	
	public BondeEntree addBondeEntree(BondeEntree bondeEntree) {
		this.bondeEntrees.put(bondeEntree.getId(), bondeEntree);
		return this.bondeEntrees.get(bondeEntree.getId());
	}
	
	public BondeEntree removeBondeEntree(BondeEntree bondeEntree) {
		return this.bondeEntrees.remove(bondeEntree.getId()); 
	}
	
	/**
	 * Return Collection | BondeSortie
	 **/
	
	public Map<String, BondeSortie> getBondeSorties() {
		return bondeSorties;
	}

	public BondeSortie addBondeSortie(BondeSortie bondeSortie) {
		this.bondeSorties.put(bondeSortie.getId(), bondeSortie);
		return this.bondeSorties.get(bondeSortie.getId());
	}
	
	public BondeSortie removeBondeSortie(BondeSortie bondeSortie) {
		return this.bondeSorties.remove(bondeSortie.getId()); 
	}

}
