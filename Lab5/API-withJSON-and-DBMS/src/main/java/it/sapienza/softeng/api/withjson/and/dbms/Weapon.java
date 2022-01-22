
package it.sapienza.softeng.api.withjson.and.dbms;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "Weapon")
public class Weapon {
    private int id;
    private String name;
    private String imagesrc;
    private String level;
    private String range;
    private String p1name;
    private int p1icon;
    private String p2name;
    private int p2icon;
    private String p3name;
    private int p3icon;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getImageSrc() { return this.imagesrc; }

    public void setImageSrc(String imagesrc) { this.imagesrc = imagesrc; }

    public String getLevel() { return this.level; }

    public void setLevel(String level) { this.level = level; }

    public String getRange() { return this.range; }

    public void setRange(String range) { this.range = range; }

    public String getP1Name() { return this.p1name; }

    public void setP1Name(String p1name) { this.p1name = p1name; }

    public int getP1Icon() { return this.p1icon; }

    public void setP1Icon(int p1icon) { this.p1icon = p1icon; }

    public String getP2Name() { return this.p2name; }

    public void setP2Name(String p2name) { this.p2name = p2name; }

    public int getP2Icon() { return this.p2icon; }

    public void setP2Icon(int p2icon) { this.p2icon = p2icon; }
    
    public String getP3Name() { return this.p3name; }

    public void setP3Name(String p3name) { this.p3name = p3name; }

    public int getP3Icon() { return this.p3icon; }

    public void setP3Icon(int p3icon) { this.p3icon = p3icon; }

    @Override
    public int hashCode() {
        return id + name.hashCode() + p1name.hashCode() + p2name.hashCode() + p3name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Weapon) && 
        (id == ((Weapon) obj).getId()) && 
        (name.equals(((Weapon) obj).getName())) && 
        (p1name.equals(((Weapon) obj).getP1Name())) && 
        (p2name.equals(((Weapon) obj).getP2Name())) && 
        (p3name.equals(((Weapon) obj).getP3Name()));
    }
    @Override
    public String toString() {
        return "weapon: " + id + " " + name + " " + level + " " + range + " " + p1name + " " + p2name + " " + p3name;
    }
    

}

