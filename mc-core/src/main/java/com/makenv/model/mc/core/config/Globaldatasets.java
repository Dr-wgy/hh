package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class Globaldatasets {
  private String dirPath;
  private GlobalMcip mcip;
  private GlobalMegan megan;
  private GlobalMetgrid metgrid;
  private GlobalWrf wrf;
//  private GlobalWrfDp wrfdp;

  public String getDirPath() {
    return dirPath;
  }

  public GlobalMcip getMcip() {
    return mcip;
  }

  public void setMcip(GlobalMcip mcip) {
    this.mcip = mcip;
  }

  public GlobalMegan getMegan() {
    return megan;
  }

  public void setMegan(GlobalMegan megan) {
    this.megan = megan;
  }

  public GlobalWrf getWrf() {
    return wrf;
  }

  public void setWrf(GlobalWrf wrf) {
    this.wrf = wrf;
  }

  public void setDirPath(String dirPath) {
    this.dirPath = dirPath;
  }

  public GlobalMetgrid getMetgrid() {
    return metgrid;
  }

  public void setMetgrid(GlobalMetgrid metgrid) {
    this.metgrid = metgrid;
  }

  public static class GlobalMcip {

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    private String dirPath;

  }

  public static class GlobalMegan {
    private String dirPath;
    private GlobalMeganEmproc emproc;
    private GlobalMeganMet2mgn met2mgn;
    private GlobalMeganMgn2mech mgn2mech;
    public String getDirPath() {
      return dirPath;
    }
    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    public GlobalMeganEmproc getEmproc() {
      return emproc;
    }

    public void setEmproc(GlobalMeganEmproc emproc) {
      this.emproc = emproc;
    }

    public GlobalMeganMet2mgn getMet2mgn() {
      return met2mgn;
    }

    public void setMet2mgn(GlobalMeganMet2mgn met2mgn) {
      this.met2mgn = met2mgn;
    }

    public GlobalMeganMgn2mech getMgn2mech() {
      return mgn2mech;
    }

    public void setMgn2mech(GlobalMeganMgn2mech mgn2mech) {
      this.mgn2mech = mgn2mech;
    }

    public static class GlobalMeganEmproc {
      private String dirPath;
      public String getDirPath() {
        return dirPath;
      }
      public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
      }
    }
    public static class GlobalMeganMet2mgn {
      private String dirPath;
      public String getDirPath() {
        return dirPath;
      }
      public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
      }
    }
    public static class GlobalMeganMgn2mech {
      private String dirPath;
      public String getDirPath() {
        return dirPath;
      }
      public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
      }
    }

  }

  public static class GlobalMetgrid {

    public String getDirPath() {
      return dirPath;
    }

    public void setDirPath(String dirPath) {
      this.dirPath = dirPath;
    }

    private String dirPath;

  }

}
