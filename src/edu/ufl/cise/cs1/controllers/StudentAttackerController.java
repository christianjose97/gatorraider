package edu.ufl.cise.cs1.controllers;

import game.controllers.AttackerController;
import game.models.*;
import game.system._Attacker;

import java.util.List;

public final class StudentAttackerController implements AttackerController {


	public void init(Game game) {
	}

	public void shutdown(Game game) {
	}

	public int update(Game game, long timeDue) {

		//current score avg = 6621.5 with defender combo of 0,2,1,3 :)


		//Object for Attacker so i don't have to call game.getAttacker() every time
		Attacker pac_man = game.getAttacker();
		//default action
		int accíon = pac_man.getNextDir(pac_man.getTargetNode(game.getPillList(),true),true);;

		//Sets max value to defender and power pill locations allowable for data type
		int defensaDistancia = Integer.MAX_VALUE;
		int distanciaAlPP = Integer.MAX_VALUE;
		//Variable that will be used to determine the closeness of a Power Pill
		Node pPCerca = null;


		//first things first is to check whether our defenders so we iterate through a block of ifs.
		if(game.getDefender(0).isVulnerable()){
			return pac_man.getNextDir(game.getDefender(0).getLocation(),true);
		}
		if(game.getDefender(2).isVulnerable()){
			return pac_man.getNextDir(game.getDefender(2).getLocation(),true);
		}
		if(game.getDefender(1).isVulnerable()){

			return pac_man.getNextDir(game.getDefender(1).getLocation(),true);
		}
		if(game.getDefender(3).isVulnerable()){

			return pac_man.getNextDir(game.getDefender(3).getLocation(),true);
		}


		//Next portion of the plan figures out if there is a power pill we can grab real close.
		for (Node pPillcollector : game.getPowerPillList()) {

			//this condition checks to see if there is a power pill that is within sight of the pacman
			if (pac_man.getLocation().getPathDistance(pPillcollector) < distanciaAlPP) {
				//we get the first closest PP and set the location
				pPCerca = pPillcollector;
				distanciaAlPP = pac_man.getLocation().getPathDistance(pPillcollector);
			}

				//this for loop is responsible for looping through list of defenders for a more efficient way of checking
				for (Defender defensa : game.getDefenders()) {
					//Checks if Pill list is not null and if pacman is is close to a defender
					if (pPCerca != null && pac_man.getLocation().getPathDistance(defensa.getLocation()) < defensaDistancia) {
						//we then update defender distance to the distance between the pacman and the defender
						defensaDistancia = pac_man.getLocation().getPathDistance(defensa.getLocation());
				}

			}

				//if defender is close then we seize a power pill to eat them
			if (defensaDistancia <= 8) {
				return pac_man.getNextDir(pPCerca, true);
			}
			//if not close then we either wait till their close enough or we continue eating it anyway.
			else {

				if(distanciaAlPP <=  4){

					return pac_man.getReverse();
				}

				else{
					return pac_man.getNextDir(pPCerca,true);
				}
			}
		}
		//This default action makes the Attacker go for every normal pill in the maze that's close by.
		return accíon;
	}
}