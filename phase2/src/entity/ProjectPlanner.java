package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectPlanner extends Planner{
    private Map<String, List<String>> tasks;
    private int numTasks = 0;
    private int ID;

    public ProjectPlanner(String plannerName, String firstColName, String secondColName, String thirdColName) {
        super();
        initializePlannerVars(plannerName, firstColName, secondColName, thirdColName);
    }

    public ProjectPlanner(int numPlannersLoaded, String plannerName, String firstColName, String secondColName, String thirdColName) {
        super(numPlannersLoaded);
        initializePlannerVars(plannerName, firstColName, secondColName, thirdColName);
    }

    private void initializePlannerVars(String plannerName, String firstColName, String secondColName, String thirdColName) {
        this.ID = super.getID();
        this.plannerName = plannerName;
        this.tasks = new HashMap<>();
        tasks.put(firstColName, new ArrayList<>());
        tasks.put(secondColName, new ArrayList<>());
        tasks.put(thirdColName, new ArrayList<>());
    }

    /**
     * Returns a string representation of a Project Planner object.
     * @return A string representing a Project Planner.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        String separator = "====================\n";
        res.append("Status: ").append(this.getPrivacyStatus()).append("\n");
        res.append("Planner ID:").append(this.getID()).append("\n");
        res.append("Planner name: ").append(this.plannerName).append("\n");
        for (String columnName: tasks.keySet()) {
            res.append("The \"").append(columnName).append("\" column has following tasks:\n");
            for (String task: tasks.get(columnName)) {
                res.append(task).append("\n");
            }
            res.append(separator);
        }
        return res.toString();
    }

    /**
     * @return A String representing the type of the planner.
     */
    @Override
    public String getType() {
        return "project";
    }

    /**
     * Returns the total number of agendas stored in a Project Planner object.
     * @return An integer representing the total number of agendas.
     */
    @Override
    public int getNumAgendas() {
        return numTasks;
    }

    /**
     * Adds agenda to the column with columnName in a Project Planner.
     * @param columnName The name of the column where the agenda will be added.
     * @param agenda The agenda to be added.
     * @return True iff the agenda is successfully added to the planner.
     */
    @Override
    public Boolean add(String columnName, String agenda) {
        if (tasks.containsKey(columnName) && !tasks.get(columnName).contains(agenda)) {
            tasks.get(columnName).add(agenda);
            numTasks++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Edit agenda to current planner
     *
     * @param columnName the original agenda the user wants to change
     * @param agenda the new content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    @Override
    public Boolean edit(String columnName, String agenda) {
        return this.ChangeTaskStatus(columnName, agenda);
    }

    /**
     * Moves the agenda from its current column to newColumn.
     * @param newColumn The name of the column that the user wants to move agenda into.
     * @param agenda The agenda to change status for.
     * @return True iff the agenda is successfully moved to newColumn.
     */
    @Override
    public Boolean ChangeTaskStatus(String newColumn, String agenda) {
        String currColumn = null;
        for (String columnN: tasks.keySet()) {
            if (tasks.get(columnN).contains(agenda)) {
                currColumn = columnN;
            }
        }
        if (currColumn == null || currColumn.equals(newColumn) || !tasks.containsKey(newColumn)) {
            return false;
        } else {
            tasks.get(newColumn).add(agenda);
            tasks.get(currColumn).remove(agenda);
            return true;
        }
    }

    /**
     * Deletes the agenda from its current column.
     * @param agenda A String representing the agenda.
     * @return A boolean value representing whether the deletion is successful or not.
     */
    public boolean delete(String agenda) {
        String currColumn = null;
        for (String columnN: tasks.keySet()) {
            if (tasks.get(columnN).contains(agenda)) {
                currColumn = columnN;
            }
        }
        if (currColumn == null) {
            return false;
        } else {
            tasks.get(currColumn).remove(agenda);
            return true;
        }
    }
}
