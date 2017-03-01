package com.makenv.model.mc.core.config;

/**
 * Created by wgy on 2017/2/28.
 */
public class DomainDp {

    private String dirPath;

    private String domain_conc;

    private String domain_conc_a;

    private String domain_conc_a_avg;

    private String domain_conc_a_avg_species;

    private String domain_conc_a_daily;

    private String domain_conc_a_hourly;

    private String domain_conc_b;

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getDomain_conc() {
        return domain_conc;
    }

    public void setDomain_conc(String domain_conc) {
        this.domain_conc = domain_conc;
    }

    public String getDomain_conc_a() {
        return domain_conc_a;
    }

    public void setDomain_conc_a(String domain_conc_a) {
        this.domain_conc_a = domain_conc_a;
    }

    public String getDomain_conc_a_avg() {
        return domain_conc_a_avg;
    }

    public void setDomain_conc_a_avg(String domain_conc_a_avg) {
        this.domain_conc_a_avg = domain_conc_a_avg;
    }

    public String getDomain_conc_a_avg_species() {
        return domain_conc_a_avg_species;
    }

    public void setDomain_conc_a_avg_species(String domain_conc_a_avg_species) {
        this.domain_conc_a_avg_species = domain_conc_a_avg_species;
    }

    public String getDomain_conc_a_daily() {
        return domain_conc_a_daily;
    }

    public void setDomain_conc_a_daily(String domain_conc_a_daily) {
        this.domain_conc_a_daily = domain_conc_a_daily;
    }

    public String getDomain_conc_a_hourly() {
        return domain_conc_a_hourly;
    }

    public void setDomain_conc_a_hourly(String domain_conc_a_hourly) {
        this.domain_conc_a_hourly = domain_conc_a_hourly;
    }

    public String getDomain_conc_b() {
        return domain_conc_b;
    }

    public void setDomain_conc_b(String domain_conc_b) {
        this.domain_conc_b = domain_conc_b;
    }

    public DomainEmis getEmis() {
        return emis;
    }

    public void setEmis(DomainEmis emis) {
        this.emis = emis;
    }

    private DomainEmis emis;

    public static class DomainEmis {

        public String getDirPath() {
            return dirPath;
        }

        public void setDirPath(String dirPath) {
            this.dirPath = dirPath;
        }

        private String dirPath;

        public String getEmis_a() {
            return emis_a;
        }

        public void setEmis_a(String emis_a) {
            this.emis_a = emis_a;
        }

        private String emis_a;
    }
}
