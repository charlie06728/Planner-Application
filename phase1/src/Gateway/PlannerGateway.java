package gateway;

import entity.Account;
import entity.Planner;
import UseCase.PlannerManager;

import java.util.HashMap;

public class PlannerGateway extends Reader<HashMap<Integer, Planner>> {

    private final String filePath = "idToPlannerMap.ser";
    private HashMap<Integer, Planner> idToPlanner = new HashMap<Integer, Planner>();

    private final PlannerManager pm;


    /** Initialize the Planner gateway with a PlannerManager.
     *
     * @param pm A PlannerManager object.
     */
    public PlannerGateway(PlannerManager pm) {
        this.pm = pm;
    }


    /**
     * Load in the data from database, call this function when initialize an Planner Manager.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean load() {
        return this.readMaps();
    }


    /**
     * Save the data to the database, call this function when a saving is needed. Must be called
     * when exit the application.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean save() {
        return this.writeMaps();
    }


    private boolean readMaps() {
        try {
            HashMap<Integer, Planner> hm = super.readSer(this.filePath);
            if (hm == null) {return true;} else{
                this.idToPlanner = hm;
            }
            pm.setIdToPlanner(this.idToPlanner);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    private boolean writeMaps() {
        for (Planner planner: pm.getAllPlanner()) {
            this.idToPlanner.put(planner.getID(), planner);
        }
        return super.writeSer(this.filePath, this.idToPlanner);
    }
}
