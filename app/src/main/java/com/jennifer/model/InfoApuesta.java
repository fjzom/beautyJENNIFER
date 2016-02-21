package com.jennifer.model;

/**
 * Created by felix on 20/02/2016.
 */
public class InfoApuesta {

    private int acumulado;
    private int cantApuestas;
    private double Quantity;
    private int UserId;
    private int MatchId;
    private int TpyeMatch;
    private int OptionId;
    private double Won;
    private int Participate;


    public int getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(int acumulado) {
        this.acumulado = acumulado;
    }

    public int getCantApuestas() {
        return cantApuestas;
    }

    public void setCantApuestas(int cantApuestas) {
        this.cantApuestas = cantApuestas;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getMatchId() {
        return MatchId;
    }

    public void setMatchId(int matchId) {
        MatchId = matchId;
    }

    public int getTpyeMatch() {
        return TpyeMatch;
    }

    public void setTpyeMatch(int tpyeMatch) {
        TpyeMatch = tpyeMatch;
    }

    public int getOptionId() {
        return OptionId;
    }

    public void setOptionId(int optionId) {
        OptionId = optionId;
    }

    public double getWon() {
        return Won;
    }

    public void setWon(double won) {
        Won = won;
    }

    public int getParticipate() {
        return Participate;
    }

    public void setParticipate(int participate) {
        Participate = participate;
    }
}
