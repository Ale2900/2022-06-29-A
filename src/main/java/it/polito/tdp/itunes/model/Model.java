package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	 private List<Album> allAlbums;
	 private SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge> grafo;
	 
	 private ItunesDAO dao;
	 
	 public Model() {
		 this.allAlbums=new ArrayList<Album>();
		 this.dao=new ItunesDAO();
		 //nota che in questo caso non servono le mappe
	 }
	
	 public void creaGrafo(int n) {
		clearGraph();
		
		loadNodes(n);
		 //aggiungo i vertici
		 Graphs.addAllVertices(this.grafo, this.allAlbums);
		 //è uguale fare un metodo fuori dal metodo del grafo oppure farlo dentro
		 
		 //ora passo agli archi//NB: qui non c'è bisogno di fare un nuova query ma ho tutte le informazioni che mi servono per creare l'arco nel risultato della query per i vertici
		 for(Album a1: this.allAlbums) {
			 for(Album a2: this.allAlbums) {
				 int peso=a1.getNum()-a2.getNum(); //il num lo setto in ogni oggetto appena faccio la query degli archi col parametro n
				 if(peso>0) //allora significa che a1 ha più canzoni e l'arco parte da a2
					 Graphs.addEdge(this.grafo, a2, a1, peso);
			 }//questo sarebbe il caso in cui il peso è 0, ma non bisogna fare nulla
		 }

	 }
	 
	 //metodo per pulire il grafo precedente e crearme uno nuovo
	 //potevo ugualmente inizializzare il grafo dentro il metodo creaGrafo cosicche ogni volta che viene premuto il bottone creaGrafo
	 //quello precedente viene pulito e se ne crea uno nuovo
	 
	 public void clearGraph() {
		 this.allAlbums=new ArrayList<Album> ();
		 this.grafo=new SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	 }
	 
	 //questo metodo serve soltanto per inizializzare la lista dei vertici, ma avrei potuto fare tutto dentro il metodo del grafo
	 private void loadNodes(int n) {
		 if(this.allAlbums.isEmpty()) {
			 this.allAlbums=this.dao.getVertici(n);
		 }
	 }
	
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	 
	//metodo che mi restituisce la lista di vertici, mi serve per popolare la tendina
	public List<Album> getVertici(){
		List<Album> vertici=new ArrayList<Album>(this.grafo.vertexSet());
		Collections.sort(vertici);
		
		return vertici;
		
	}
	
	
	//METODO PER IL CALCOLO DEI SUCCESSORI DI UN NODO 
	public List<BilancioAlbum> getAdiacenti(Album root){
		List<Album> successori=Graphs.successorListOf(this.grafo, root);
		//creo una lista di bilanci e li setto
		List<BilancioAlbum> bilanciodeisuccessori=new ArrayList<BilancioAlbum>();
		
		for(Album a: successori) {
			bilanciodeisuccessori.add(new BilancioAlbum(a, getBilancio(a)));
		}
		Collections.sort(bilanciodeisuccessori);
		
		return bilanciodeisuccessori;
	}
	
	//COPIALO SE SERVE
	//METODO CHE CALCOLA IL BILANCIO DI UN VERTICE COME SOMMA E DIFFERENZA DEL PESO DEGLI ARCHI USCENTI ED ENTRANTI
	private int getBilancio(Album a) {
		int bilancio=0;
		//faccio due liste, una di archi entrarti e una di archi uscenti
		List<DefaultWeightedEdge> edgesIN=new ArrayList<DefaultWeightedEdge>(this.grafo.incomingEdgesOf(a)); // i metodi che chiamo dall'arco mi danno sempre archi adatti a quelli del grafo
		List<DefaultWeightedEdge> edgesOUT=new ArrayList<DefaultWeightedEdge>(this.grafo.outgoingEdgesOf(a));
		
		
		//ora per ogni arco entrante aggiungo il suo peso al bilancio mentre per ogni arco uscente lo sottraggo
		
		for(DefaultWeightedEdge e: edgesIN) {
		bilancio=(int) (bilancio+ this.grafo.getEdgeWeight(e)); 
		}
		
		for(DefaultWeightedEdge e: edgesOUT) {
			bilancio=(int) (bilancio-this.grafo.getEdgeWeight(e));
		}
		
		return bilancio;
		
	}

}
