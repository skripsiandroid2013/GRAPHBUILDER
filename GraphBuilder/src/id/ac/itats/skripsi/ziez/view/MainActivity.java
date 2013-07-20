package id.ac.itats.skripsi.ziez.view;


import id.ac.itats.skripsi.ziez.algo.Dijkstra;
import id.ac.itats.skripsi.ziez.graphbuilder.Edge;
import id.ac.itats.skripsi.ziez.graphbuilder.GraphBuilder;
import id.ac.itats.skripsi.ziez.graphbuilder.RoutableGraph;
import id.ac.itats.skripsi.ziez.graphbuilder.Vertex;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.graphbuilder.R;


public class MainActivity extends Activity {
	private RoutableGraph graph;
	private Button bDijkstra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bDijkstra = (Button) findViewById(R.id.button1);
		new AsyncTask<Void, Void, RoutableGraph>() {

			@Override
			protected RoutableGraph doInBackground(Void... params) {

				// return prepareGraph();
				return buildGraph();
			}

			@Override
			protected void onPostExecute(RoutableGraph result) {
				logUser("Graph ready ");
				log("graph size : "+result.getSize());
				
				for (Edge edge : result.toVertex("1722835557").adjacencies){
					log( "edge "+edge.id);
				}
				
				int i = 0;
				for (Vertex v : graph.getVertices()){
					log("v  "+i +" : "+v.name);
					i++;
				}
				
				
			}
		}.execute();

		bDijkstra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				logUser("bDijkstra click");
				
				Dijkstra dijkstra = new Dijkstra(graph);
				dijkstra.computePaths(graph.toVertex("1721121228"));
				List<Vertex> path = dijkstra.getShortestPathTo(graph.toVertex("1722835557"));
				System.out.println("path" + path);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private RoutableGraph buildGraph() {
		GraphBuilder builder = new GraphBuilder(this);
		builder.createDatabase();
		builder.open();
		graph =builder.buildGraph();
		builder.close();
		return graph;

	}


	private void log( String str )
    {
        Log.i(MainActivity.class.getName(), str);
    }
	
	private void logUser(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}



}
