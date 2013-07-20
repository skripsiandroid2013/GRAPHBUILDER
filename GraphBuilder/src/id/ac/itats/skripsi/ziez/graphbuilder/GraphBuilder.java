package id.ac.itats.skripsi.ziez.graphbuilder;

import id.ac.itats.skripsi.arga.orm.dao.DaoMaster;
import id.ac.itats.skripsi.arga.orm.dao.DaoSession;
import id.ac.itats.skripsi.arga.orm.dao.tb_edgeDao;
import id.ac.itats.skripsi.arga.orm.entity.tb_edge;
import id.ac.itats.skripsi.arga.orm.util.DatabaseHelper;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GraphBuilder {

	protected static final String TAG = "GraphBuilder";

	private final Context context;
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private tb_edgeDao edgeDao;
	private GraphBuilder graphBuilder;

	private RoutableGraph graph = new RoutableGraph();

	public GraphBuilder(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);

	}

	public GraphBuilder createDatabase() throws SQLException {
		try {
			dbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public GraphBuilder open() throws SQLException {
		try {
			dbHelper.openDataBase();
			dbHelper.close();
			db = dbHelper.getReadableDatabase();
			daoMaster = new DaoMaster(db);
			daoSession = daoMaster.newSession();
			edgeDao = daoSession.getTb_edgeDao();
			daoSession.getTb_nodeDao();

		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public GraphBuilder getInstance() {
		if (graphBuilder == null) {
			return new GraphBuilder(context);
		} else {
			return this;
		}
	}

	public RoutableGraph buildGraph() {

		// fromNode, toNode, jarak, edgeId

		int idx = 0;
		while (graph.getSize() != 22310) {
			List<tb_edge> result = edgeDao.queryBuilder().offset(idx)
					.limit(10000).orderAsc(tb_edgeDao.Properties.Id).list();

			for (tb_edge edge : result) {

				this.graph.addEdge(edge.getFrom_node(), edge.getTo_node(),
						Double.parseDouble(edge.getJarak()), edge.getId_edge());

			}

			idx += 10000;
		}

		return graph;

	}


}
