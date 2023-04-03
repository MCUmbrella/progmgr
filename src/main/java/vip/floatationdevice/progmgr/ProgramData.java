package vip.floatationdevice.progmgr;

public class ProgramData
{
    private int id;
    private String name, type, point;
    private String[] actors;

    public int getId(){return id;}

    public String getName(){return name;}

    public String getType(){return type;}

    public String getPoint(){return point;}

    public String[] getActors(){return actors;}

    public void setId(int id){this.id = id;}

    public void setName(String name){this.name = name;}

    public void setType(String type){this.type = type;}

    public void setPoint(String point){this.point = point;}

    public void setActors(String... actors){this.actors = actors;}

    public void setActors(String actors){this.actors = actors.split(",");}
}
