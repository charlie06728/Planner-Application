package UseCase;
import Entity.DailyPlanner;
import Entity.Planner;
import Entity.ProjectPlanner;

import java.util.ArrayList;

public class PlannerManager {
    Planner planner;

    /** Create new DailyPlanner -- default start at 09:00, end at 17:00, interval 60 mins
     *
     * @return true iff a new DailyPlanner is created
     */
    public boolean NewDailyPlanner(){
        this.planner = new DailyPlanner("09:00", "17:00", 60);
        return true;
    }

    /** Create new DailyPlanner -- default interval 60 mins
     *
     * @param startTime start time of planner
     * @param endTime end time of planner
     * @return true iff a new DailyPlanner is created
     */
    public boolean NewDailyPlanner(String startTime, String endTime){
        this.planner = new DailyPlanner(startTime, endTime, 60);
        return true;
    }

    /** Create new DailyPlanner
     *
     * @param startTime start time of planner
     * @param endTime end time of planner
     * @param Interval interval
     * @return true
     */
    public boolean NewDailyPlanner(String startTime, String endTime, int Interval){
        this.planner = new DailyPlanner(startTime, endTime, Interval);
        return true;
    }

    /** Create an empty DailyPlanner
     *
     * @return true iff a new ProjectPlanner is correctly created
     */
    public boolean NewProjectPlanner(){
        this.planner = new ProjectPlanner();
        return true;
    }

    /** Create a DailyPlanner with Agendas
     *
     * @param Agendas: agendas for this agenda
     * @return true iff a new ProjectPlanner is correctly created and agendas are added
     */
    public boolean NewProjectPlanner(ArrayList<String> Agendas){
        this.planner = new ProjectPlanner();
        for (String Agenda: Agendas){
            this.planner.Add(Agenda);
        }
        return true;
    }

    /** Edit agenda on current planner
     *
     * @param i index of the agenda user wish to edit
     * @param s content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    public boolean Edit(int i, String s){
        planner.Edit(i, s);
        return true;
    }

    /** Edit agenda on DailyPlanner base on time stamp
     *
     * @param time: time slot on DailyPlanner, HH:MM
     * @param newAgenda: new agenda item
     * @return true iff is correctly edited
     */
    public boolean Edit(String time, String newAgenda){
        if (this.planner.getClass() == DailyPlanner.class){
            ((DailyPlanner)this.planner).Edit(time, newAgenda);
            return true;
        }
        else{
            return false;
        }
    }

    /** Change privacy status of current planner
     *
     * @param status "private" or "public"
     * @return true iff the status is correctly changed (from "public to "private or vise versa)
     */
    public boolean ChangePrivacyStatus(String status){
        return planner.ChangePrivacyStatus(status);
    }

}