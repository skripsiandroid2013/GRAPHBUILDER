
package id.ac.itats.skripsi.ziez.graphbuilder;

import java.util.Collection;
import java.util.HashMap;


public class RoutableGraph
{
	
	private HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
	public void addEdge(String fromId, String toId, double cost, String name)
	{
		Vertex fromVertex = vertices.get(fromId);
		if (fromVertex == null) {
			fromVertex = new Vertex(fromId);
			vertices.put(fromId, fromVertex);
		}
		Vertex toVertex = vertices.get(toId);
		if (toVertex == null) {
			toVertex = new Vertex(toId);
			vertices.put(toId, toVertex);
		}
		fromVertex.adjacencies.add(new Edge(toVertex, cost, name));
	}
	
	
	public int getSize()
	{
		return this.vertices.size();
	}
	    
   
	public Vertex toVertex(String id) {
		return vertices.get(id);
	}
	
	
	public Collection<Vertex> getVertices() {
		return vertices.values();
	}




}

