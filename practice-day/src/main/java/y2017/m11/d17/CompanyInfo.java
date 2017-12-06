package y2017.m11.d17;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/11/17
 */
public class CompanyInfo {
    @NotNull
    private String companyName;

    @NotNull
    private String companyEnglishName;

    @NotNull
    private String legalRepresentative;

    @NotNull
    private double fund;

    @NotNull
    private String creditCode;

    @Null
    private String businessNumber;

    @NotNull
    private String location;

    @Null
    private String detailedLocation;

    @NotNull
    private Integer companyType;

    @NotNull
    private Integer industry;

    @NotNull
    private String mainProduct;

    @Null
    private Integer modal;

    @Null
    private Integer companyStaff;

    @Null
    private String companySite;

    @Null
    private String companyDescription;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEnglishName() {
        return companyEnglishName;
    }

    public void setCompanyEnglishName(String companyEnglishName) {
        this.companyEnglishName = companyEnglishName;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public double getFund() {
        return fund;
    }

    public void setFund(double fund) {
        this.fund = fund;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetailedLocation() {
        return detailedLocation;
    }

    public void setDetailedLocation(String detailedLocation) {
        this.detailedLocation = detailedLocation;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public String getMainProduct() {
        return mainProduct;
    }

    public void setMainProduct(String mainProduct) {
        this.mainProduct = mainProduct;
    }

    public Integer getModal() {
        return modal;
    }

    public void setModal(Integer modal) {
        this.modal = modal;
    }

    public Integer getCompanyStaff() {
        return companyStaff;
    }

    public void setCompanyStaff(Integer companyStaff) {
        this.companyStaff = companyStaff;
    }

    public String getCompanySite() {
        return companySite;
    }

    public void setCompanySite(String companySite) {
        this.companySite = companySite;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
}
