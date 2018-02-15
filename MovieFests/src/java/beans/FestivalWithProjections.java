/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Festival;
import entities.Location;
import entities.Projection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author milanlazarevic
 */
public class FestivalWithProjections {

    
    
    private Festival festival;
    private List<ElemOfProLok> projections;

    public FestivalWithProjections(Festival festival, List<ElemOfProLok> projections) {
        this.festival = festival;
        this.projections = projections;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public List<ElemOfProLok> getProjections() {
        return projections;
    }

    public void setProjections(List<ElemOfProLok> projection) {
        this.projections = projection;
    }
    
    public String getStringProjections(){
        String temp="";
        if(projections!=null){
            for (Iterator<ElemOfProLok> iterator = projections.iterator(); iterator.hasNext();) {
                ElemOfProLok next = iterator.next();
                temp+=next.getProjection().getDate().toString()+" ";
                temp+=next.getLocation().getAdress()+"\n";
            }
        }
        return temp;
    }
    
}
