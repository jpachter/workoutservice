package com.gymlife.workoutservice.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gymlife.workoutservice.db.dto.Exercise;
import com.gymlife.workoutservice.db.dto.Routine;
import com.gymlife.workoutservice.db.dto.Routines;
import com.gymlife.workoutservice.db.util.ConnectionFactory;
import com.gymlife.workoutservice.db.util.DBUtil;

public class RoutineDaoImp implements RoutineDaoInterface {

	private Connection connection;
	private PreparedStatement loadAllStmt;
	private ExerciseDaoImp exerciseDao;

	public RoutineDaoImp() {
		exerciseDao = new ExerciseDaoImp();
	}

	@Override
	public Routines getRoutines() throws SQLException {
		Routines routines = new Routines();
		List<Routine> routineList = new ArrayList<Routine>();
		ResultSet result = null;

		try {
			connection = ConnectionFactory.getConnection();
			loadAllStmt = connection.prepareStatement("select * from routine");
			result = loadAllStmt.executeQuery();
			while (result.next()) {
				Routine r = new Routine();
				r.setId(result.getInt("id"));

				String[] exerciseIds = result.getString("exercises").split(",");
				List<Exercise> exers = new ArrayList<Exercise>();
				for (int i = 0; i < exerciseIds.length; i++) {
					exers.add(exerciseDao.getWorkout(Integer
							.parseInt(exerciseIds[i])));
				}

				r.setExercises(exers);
				r.setDifficulty(result.getInt("difficulty"));
				r.setNumDays(result.getInt("num_days"));
				r.setName(result.getString("name"));
				routineList.add(r);
			}
		} finally {
			DBUtil.close(result);
			DBUtil.close(loadAllStmt);
			DBUtil.close(connection);
		}
		
		routines.setRoutines(routineList);
		return routines;
	}

	@Override
	public Routine getRoutine(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addWorkout(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteWorkout(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRoutine(int id) {
		// TODO Auto-generated method stub

	}

}
