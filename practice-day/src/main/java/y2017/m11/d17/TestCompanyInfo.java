package y2017.m11.d17;

import org.junit.Test;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/11/17
 */
public class TestCompanyInfo {
    @Test
    public void testInfo() {
        CompanyInfo companyInfo = new CompanyInfo();
        System.out.println(ValidateUtil.validate(companyInfo));
    }

    @Test
    public void testInfoNotNull() {
        CompanyInfo companyInfo = new CompanyInfo();
        // not null property
        companyInfo.setCompanyName("");
        companyInfo.setCompanyEnglishName("");
        companyInfo.setCompanyType(1);
        companyInfo.setIndustry(1);
        companyInfo.setMainProduct("");
        companyInfo.setLegalRepresentative("");
        companyInfo.setFund(1);
        companyInfo.setCreditCode("");
        companyInfo.setLocation("");
        System.out.println(ValidateUtil.validate(companyInfo));
    }

    @Test
    public void testInfo1() {
        CompanyInfo companyInfo = new CompanyInfo();
        // not null property
        companyInfo.setCompanyName("");
        companyInfo.setCompanyEnglishName("");
        companyInfo.setCompanyType(1);
        companyInfo.setIndustry(1);
        companyInfo.setMainProduct("");
        companyInfo.setLegalRepresentative("");
        companyInfo.setFund(1);
        companyInfo.setCreditCode("");
        companyInfo.setLocation("");
        // null property
        companyInfo.setModal(1);
        System.out.println(ValidateUtil.validate(companyInfo));
    }

    @Test
    public void testInfo2() {
        CompanyInfo companyInfo = new CompanyInfo();
        // not null property
        companyInfo.setCompanyName("");
        companyInfo.setCompanyEnglishName("");
        companyInfo.setCompanyType(1);
        companyInfo.setIndustry(1);
        companyInfo.setMainProduct("");
        companyInfo.setLegalRepresentative("");
        companyInfo.setFund(1);
        companyInfo.setCreditCode("");
        companyInfo.setLocation("");
        // null property
        companyInfo.setModal(1);
        companyInfo.setCompanyDescription("");
        System.out.println(ValidateUtil.validate(companyInfo));
    }

    @Test
    public void testInfo3() {
        CompanyInfo companyInfo = new CompanyInfo();
        // not null property
        companyInfo.setCompanyName("");
        companyInfo.setCompanyEnglishName("");
        companyInfo.setCompanyType(1);
        companyInfo.setIndustry(1);
        companyInfo.setMainProduct("");
        companyInfo.setLegalRepresentative("");
        companyInfo.setFund(1);
        companyInfo.setCreditCode("");
        companyInfo.setLocation("");
        // null property
        companyInfo.setModal(1);
        companyInfo.setCompanyDescription("");
        companyInfo.setCompanyStaff(1);
        System.out.println(ValidateUtil.validate(companyInfo));
    }

    @Test
    public void testInfo4() {
        CompanyInfo companyInfo = new CompanyInfo();
        // not null property
        companyInfo.setCompanyName("");
        companyInfo.setCompanyEnglishName("");
        companyInfo.setCompanyType(1);
        companyInfo.setIndustry(1);
        companyInfo.setMainProduct("");
        companyInfo.setLegalRepresentative("");
        companyInfo.setFund(1);
        companyInfo.setCreditCode("");
        companyInfo.setLocation("");
        // null property
        companyInfo.setModal(1);
        companyInfo.setCompanyDescription("");
        companyInfo.setCompanyStaff(1);
        companyInfo.setCompanySite("");
        System.out.println(ValidateUtil.validate(companyInfo));
    }

    @Test
    public void testInfo5() {
        CompanyInfo companyInfo = new CompanyInfo();
        // not null property
        companyInfo.setCompanyName("");
        companyInfo.setCompanyEnglishName("");
        companyInfo.setCompanyType(1);
        companyInfo.setIndustry(1);
        companyInfo.setMainProduct("");
        companyInfo.setLegalRepresentative("");
        companyInfo.setFund(1);
        companyInfo.setCreditCode("");
        companyInfo.setLocation("");
        // null property
        companyInfo.setModal(1);
        companyInfo.setCompanyDescription("");
        companyInfo.setCompanyStaff(1);
        companyInfo.setCompanySite("");
        companyInfo.setBusinessNumber("");
        System.out.println(ValidateUtil.validate(companyInfo));
    }

    @Test
    public void testInfo6() {
        CompanyInfo companyInfo = new CompanyInfo();
        // not null property
        companyInfo.setCompanyName("");
        companyInfo.setCompanyEnglishName("");
        companyInfo.setCompanyType(1);
        companyInfo.setIndustry(1);
        companyInfo.setMainProduct("");
        companyInfo.setLegalRepresentative("");
        companyInfo.setFund(1);
        companyInfo.setCreditCode("");
        companyInfo.setLocation("");
        // null property
        companyInfo.setModal(1);
        companyInfo.setCompanyDescription("");
        companyInfo.setCompanyStaff(1);
        companyInfo.setCompanySite("");
        companyInfo.setBusinessNumber("");
        companyInfo.setDetailedLocation("");
        System.out.println(ValidateUtil.validate(companyInfo));
    }
}
