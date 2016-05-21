package com.cyatophilum.projetmenu;
/**
 * Cette classe permet d'instancier des lieux
 *
 * @author Karim Oubah
 */
public class Place {
    private int id;
    private String type;
    private String type_detail;
    private String nom;
    private String adresse;
    private String codepostal;
    private String commune;
    private String telephone;
    private String email;
    private String siteweb;
    private String facebook;
    private String classement;
    private String ouverture;
    private String tarifsencl;
    private String tarifsmin;
    private String tarifsmax;

    /**
     *
     * @param id
     * @param type
     * @param type_detail
     * @param nom
     * @param adresse
     * @param codepostal
     * @param commune
     * @param telephone
     * @param email
     * @param siteweb
     * @param facebook
     * @param classement
     * @param ouverture
     * @param tarifsencl
     * @param tarifsmin
     * @param tarifsmax
     */
        public Place(int id, String type, String type_detail, String nom, String adresse, String codepostal, String commune, String telephone, String email, String siteweb, String facebook, String classement, String ouverture, String tarifsencl, String tarifsmin, String tarifsmax) {
        this.id = id;
        this.type=type;
        this.type_detail=type_detail;
        this.nom=nom;
        this.adresse=adresse;
        this.codepostal=codepostal;
        this.commune=commune;
        this.telephone=telephone;
        this.email=email;
        this.siteweb=siteweb;
        this.facebook=facebook;
        this.classement=classement;
        this.ouverture=ouverture;
        this.tarifsencl=tarifsencl;
        this.tarifsmin=tarifsmin;
        this.tarifsmax=tarifsmax;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return type_detail
     */
    public String getType_detail() {
        return type_detail;
    }

    /**
     *
     * @param type_detail
     */
    public void setType_detail(String type_detail) {
        this.type_detail = type_detail;
    }

    /**
     *
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @return adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     *
     * @param adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     *
     * @return codepostal
     */
    public String getCP() {
        return codepostal;
    }

    /**
     *
     * @param codepostal
     */
    public void setCP(String codepostal) {
        this.codepostal = codepostal;
    }

    /**
     *
     * @return commune
     */
    public String getCommune() {
        return commune;
    }

    /**
     *
     * @param commune
     */
    public void setCommune(String commune) {
        this.commune = commune;
    }

    /**
     *
     * @return telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     *
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return siteweb
     */
    public String getSiteweb() {
        return siteweb;
    }

    /**
     *
     * @param siteweb
     */
    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    /**
     *
     * @return facebook
     */
    public String getFacebook() {
        return facebook;
    }

    /**
     *
     * @param facebook
     */
    public void setFacebok(String facebook) {
        this.facebook = facebook;
    }

    /**
     *
     * @return classement
     */
    public String getClassement() {
        return classement;
    }

    /**
     *
     * @param classement
     */
    public void setClassement(String classement) {
        this.classement = classement;
    }

    /**
     *
     * @return ouverture
     */
    public String getOuverture() {
        return ouverture;
    }

    /**
     *
     * @param ouverture
     */
    public void setOuverture(String ouverture) {
        this.ouverture = ouverture;
    }

    /**
     *
     * @return tarifsencl
     */
    public String getTarifsencl() {
        return tarifsencl;
    }

    /**
     *
     * @param tarifsencl
     */
    public void setTarifsencl(String tarifsencl) {
        this.tarifsencl = tarifsencl;
    }

    /**
     *
     * @return tarifsmin
     */
    public String getTarifsmin() {
        return tarifsmin;
    }

    /**
     *
     * @param tarifsmin
     */
    public void setTarifsmin(String tarifsmin) {
        this.tarifsmin = tarifsmin;
    }

    /**
     *
     * @return tarifsmax
     */
    public String getTarifsmax() {
        return tarifsmax;
    }

    /**
     *
     * @param tarifsmax
     */
    public void setTarifsmax(String tarifsmax) {
        this.tarifsmax = tarifsmax;
    }


}
